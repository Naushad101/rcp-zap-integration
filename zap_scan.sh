#!/bin/bash

# Enhanced ZAP Security Scanning Script with Best Practices
# Version: 2.0

set -euo pipefail  # Exit on error, undefined vars, pipe failures

# =============================================================================
# CONFIGURATION AND VALIDATION
# =============================================================================

# Configuration with validation
APP_URL="${APP_URL:-http://rcp-backend:8081}"
ZAP_URL="${ZAP_URL:-http://localhost:8090}"
OPENAPI_URL="${OPENAPI_URL:-$APP_URL/v3/api-docs}"
REPORTS_DIR="${REPORTS_DIR:-./zap_reports}"
ZAP_API_KEY="${ZAP_API_KEY:-}"

# Security and performance settings
MAX_SCAN_TIME="${MAX_SCAN_TIME:-3600}"  # Max scan time in seconds (1 hour)
THREAD_COUNT="${THREAD_COUNT:-5}"       # Number of concurrent threads
DELAY_MS="${DELAY_MS:-1000}"           # Delay between requests in milliseconds
ENVIRONMENT="${ENVIRONMENT:-test}"      # Environment validation

# Logging setup
LOG_FILE="$REPORTS_DIR/zap-scan.log"
TIMESTAMP=$(date '+%Y%m%d_%H%M%S')

# =============================================================================
# HELPER FUNCTIONS
# =============================================================================

log() {
    echo "[$(date '+%Y-%m-%d %H:%M:%S')] $1" | tee -a "$LOG_FILE"
}

error() {
    log "ERROR: $1" >&2
    exit 1
}

validate_environment() {
    log "Validating environment and prerequisites..."
    
    # Check if running against production
    if [[ "$ENVIRONMENT" == "production" ]]; then
        error "Running security scans against production is not allowed!"
    fi
    
    # Validate required tools
    command -v curl >/dev/null 2>&1 || error "curl is required but not installed"
    command -v jq >/dev/null 2>&1 || log "WARNING: jq not found - JSON parsing will be limited"
    
    # Validate URLs
    if ! curl -s --head "$APP_URL" >/dev/null 2>&1; then
        error "Application URL $APP_URL is not accessible"
    fi
    
    log "Environment validation passed"
}

wait_for_zap() {
    log "Waiting for ZAP to be available at $ZAP_URL..."
    local timeout=300  # 5 minutes timeout
    local count=0
    
    while [ $count -lt $timeout ]; do
        if curl -s -o /dev/null -w "%{http_code}" "$ZAP_URL" 2>/dev/null | grep -q "200"; then
            log "ZAP is ready"
            return 0
        fi
        sleep 5
        ((count+=5))
    done
    
    error "ZAP failed to start within $timeout seconds"
}

zap_api_call() {
    local endpoint="$1"
    local method="${2:-GET}"
    local api_key_param=""
    
    if [[ -n "$ZAP_API_KEY" ]]; then
        api_key_param="&apikey=$ZAP_API_KEY"
    fi
    
    local url="$ZAP_URL/JSON/$endpoint$api_key_param"
    
    if [[ "$method" == "GET" ]]; then
        curl -s "$url" || error "Failed to call ZAP API: $endpoint"
    else
        curl -s -X "$method" "$url" || error "Failed to call ZAP API: $endpoint"
    fi
}

configure_zap_settings() {
    log "Configuring ZAP security settings..."
    
    # Set scan policy with custom settings
    zap_api_call "ascan/action/setOptionAttackPolicy/?String=Default%20Policy"
    
    # Configure thread count and delays
    zap_api_call "ascan/action/setOptionThreadPerHost/?Integer=$THREAD_COUNT"
    zap_api_call "ascan/action/setOptionDelayInMs/?Integer=$DELAY_MS"
    
    # Set reasonable timeouts
    zap_api_call "ascan/action/setOptionMaxScanDurationInMins/?Integer=$((MAX_SCAN_TIME/60))"
    
    # Exclude potentially dangerous operations
    zap_api_call "core/action/excludeFromProxy/?regex=.*logout.*"
    zap_api_call "core/action/excludeFromProxy/?regex=.*delete.*"
    zap_api_call "core/action/excludeFromProxy/?regex=.*remove.*"
    
    log "ZAP configuration completed"
}

import_openapi_spec() {
    log "Importing OpenAPI specification from $OPENAPI_URL..."
    
    # Check if OpenAPI spec is accessible
    if ! curl -s --head "$OPENAPI_URL" >/dev/null 2>&1; then
        log "WARNING: OpenAPI specification not accessible at $OPENAPI_URL"
        return 1
    fi
    
    # Import OpenAPI spec with error handling
    local result
    result=$(zap_api_call "openapi/action/importUrl/?url=$OPENAPI_URL" "GET")
    
    if [[ "$result" == *"OK"* ]] || [[ "$result" == *"success"* ]]; then
        log "OpenAPI specification imported successfully"
        sleep 10  # Allow ZAP to process the import
        return 0
    else
        log "WARNING: Failed to import OpenAPI specification: $result"
        return 1
    fi
}

run_spider_scan() {
    log "Starting spider/crawl scan..."
    
    local spider_result
    spider_result=$(zap_api_call "spider/action/scan/?url=$APP_URL/")
    local spider_id
    spider_id=$(echo "$spider_result" | grep -o '"scan":"[0-9]*"' | cut -d'"' -f4)
    
    if [[ -z "$spider_id" ]]; then
        error "Failed to start spider scan"
    fi
    
    log "Spider scan started with ID: $spider_id"
    
    # Monitor spider progress with timeout
    local start_time=$(date +%s)
    while true; do
        local current_time=$(date +%s)
        if (( current_time - start_time > MAX_SCAN_TIME )); then
            log "WARNING: Spider scan timed out after $MAX_SCAN_TIME seconds"
            break
        fi
        
        local status_result
        status_result=$(zap_api_call "spider/view/status/?scanId=$spider_id")
        local status
        status=$(echo "$status_result" | grep -o '"status":"[0-9]*"' | cut -d'"' -f4)
        
        log "Spider progress: $status%"
        
        if [[ "$status" == "100" ]]; then
            log "Spider scan completed successfully"
            break
        fi
        
        sleep 10
    done
    
    # Get spider results
    local urls_found
    urls_found=$(zap_api_call "core/view/urls/" | grep -o '"urls":\[[^]]*\]' | wc -c)
    log "Spider discovered $urls_found URLs"
}

run_active_scan() {
    log "Starting active security scan..."
    
    local scan_result
    scan_result=$(zap_api_call "ascan/action/scan/?url=$APP_URL/")
    local scan_id
    scan_id=$(echo "$scan_result" | grep -o '"scan":"[0-9]*"' | cut -d'"' -f4)
    
    if [[ -z "$scan_id" ]]; then
        error "Failed to start active scan"
    fi
    
    log "Active scan started with ID: $scan_id"
    
    # Monitor active scan progress with timeout
    local start_time=$(date +%s)
    while true; do
        local current_time=$(date +%s)
        if (( current_time - start_time > MAX_SCAN_TIME )); then
            log "WARNING: Active scan timed out after $MAX_SCAN_TIME seconds"
            zap_api_call "ascan/action/stop/?scanId=$scan_id"
            break
        fi
        
        local status_result
        status_result=$(zap_api_call "ascan/view/status/?scanId=$scan_id")
        local status
        status=$(echo "$status_result" | grep -o '"status":"[0-9]*"' | cut -d'"' -f4)
        
        log "Active scan progress: $status%"
        
        if [[ "$status" == "100" ]]; then
            log "Active scan completed successfully"
            break
        fi
        
        sleep 30
    done
}

generate_reports() {
    log "Generating comprehensive security reports..."
    
    local report_prefix="$REPORTS_DIR/zap-security-report-$TIMESTAMP"
    
    # Generate multiple report formats
    curl -s "$ZAP_URL/OTHER/core/other/htmlreport/" > "$report_prefix.html" || log "WARNING: Failed to generate HTML report"
    curl -s "$ZAP_URL/JSON/core/view/alerts/" > "$report_prefix-alerts.json" || log "WARNING: Failed to generate JSON alerts"
    curl -s "$ZAP_URL/XML/core/view/alerts/" > "$report_prefix-alerts.xml" || log "WARNING: Failed to generate XML alerts"
    
    # Generate detailed summary with risk analysis
    generate_summary_report "$report_prefix-summary.txt"
    
    # Generate CSV report for easy analysis
    generate_csv_report "$report_prefix-alerts.csv"
    
    log "Reports generated with prefix: $report_prefix"
}

generate_summary_report() {
    local summary_file="$1"
    log "Generating detailed summary report..."
    
    {
        echo "=== ZAP SECURITY SCAN SUMMARY ==="
        echo "Scan completed at: $(date)"
        echo "Application URL: $APP_URL"
        echo "OpenAPI Spec: $OPENAPI_URL"
        echo "Environment: $ENVIRONMENT"
        echo "Scan Configuration:"
        echo "  - Thread Count: $THREAD_COUNT"
        echo "  - Delay: ${DELAY_MS}ms"
        echo "  - Max Scan Time: ${MAX_SCAN_TIME}s"
        echo ""
        
        # Get alert summary with proper error handling
        local summary_result
        summary_result=$(zap_api_call "core/view/alertsSummary/?baseurl=$APP_URL" 2>/dev/null || echo '{}')
        
        # Parse alert counts (more robust parsing)
        local high_alerts medium_alerts low_alerts info_alerts
        high_alerts=$(echo "$summary_result" | grep -o '"High":"[0-9]*"' | cut -d'"' -f4 || echo "0")
        medium_alerts=$(echo "$summary_result" | grep -o '"Medium":"[0-9]*"' | cut -d'"' -f4 || echo "0")
        low_alerts=$(echo "$summary_result" | grep -o '"Low":"[0-9]*"' | cut -d'"' -f4 || echo "0")
        info_alerts=$(echo "$summary_result" | grep -o '"Informational":"[0-9]*"' | cut -d'"' -f4 || echo "0")
        
        echo "RISK SUMMARY:"
        echo "  High Risk Alerts: ${high_alerts:-0}"
        echo "  Medium Risk Alerts: ${medium_alerts:-0}"
        echo "  Low Risk Alerts: ${low_alerts:-0}"
        echo "  Informational Alerts: ${info_alerts:-0}"
        echo ""
        
        local total_alerts=$((${high_alerts:-0} + ${medium_alerts:-0} + ${low_alerts:-0} + ${info_alerts:-0}))
        echo "Total Alerts: $total_alerts"
        
        # Risk assessment
        if [[ ${high_alerts:-0} -gt 0 ]]; then
            echo "RISK LEVEL: HIGH - Immediate attention required"
        elif [[ ${medium_alerts:-0} -gt 5 ]]; then
            echo "RISK LEVEL: MEDIUM - Review and remediate soon"
        elif [[ ${low_alerts:-0} -gt 10 ]]; then
            echo "RISK LEVEL: LOW - Consider remediation during next cycle"
        else
            echo "RISK LEVEL: MINIMAL - Good security posture"
        fi
        
    } > "$summary_file"
}

generate_csv_report() {
    local csv_file="$1"
    log "Generating CSV report for analysis..."
    
    # Get all alerts and convert to CSV format
    local alerts_json
    alerts_json=$(zap_api_call "core/view/alerts/")
    
    {
        echo "Risk,Alert,URL,Description,Solution,Reference"
        echo "$alerts_json" | grep -o '"risk":"[^"]*","alert":"[^"]*","url":"[^"]*","description":"[^"]*","solution":"[^"]*","reference":"[^"]*"' | \
        sed 's/"risk":"//' | sed 's/","alert":"/,/' | sed 's/","url":"/,/' | \
        sed 's/","description":"/,/' | sed 's/","solution":"/,/' | sed 's/","reference":"//g' | sed 's/"$//'
    } > "$csv_file" 2>/dev/null || log "WARNING: Failed to generate CSV report"
}

cleanup_and_shutdown() {
    log "Performing cleanup and shutdown..."
    
    # Save ZAP session for later analysis
    zap_api_call "core/action/saveSession/?name=scan-session-$TIMESTAMP&overwrite=true" 2>/dev/null || true
    
    # Shutdown ZAP gracefully
    log "Shutting down ZAP..."
    zap_api_call "core/action/shutdown/" 2>/dev/null || true
    
    # Wait for ZAP to shutdown
    sleep 10
    
    log "Cleanup completed"
}

# =============================================================================
# MAIN EXECUTION
# =============================================================================

main() {
    log "=== Starting Enhanced ZAP Security Scan ==="
    
    # Create reports directory
    mkdir -p "$REPORTS_DIR"
    
    # Validation and setup
    validate_environment
    wait_for_zap
    configure_zap_settings
    
    # Import API specification
    if ! import_openapi_spec; then
        log "Proceeding without OpenAPI specification"
    fi
    
    # Display discovered URLs
    log "=== URLs loaded into ZAP ==="
    zap_api_call "core/view/urls/" >> "$LOG_FILE"
    
    # Execute scans
    run_spider_scan
    run_active_scan
    
    # Generate reports and summary
    generate_reports
    
    # Cleanup
    cleanup_and_shutdown
    
    log "=== Enhanced ZAP Security Scan Completed Successfully ==="
    log "Reports and logs available in: $REPORTS_DIR"
    
    # Display quick summary to console
    if [[ -f "$REPORTS_DIR/zap-security-report-$TIMESTAMP-summary.txt" ]]; then
        echo ""
        echo "=== QUICK SUMMARY ==="
        tail -n 10 "$REPORTS_DIR/zap-security-report-$TIMESTAMP-summary.txt"
    fi
}

# Trap for cleanup on script interruption
trap cleanup_and_shutdown EXIT INT TERM

# Execute main function
main "$@"