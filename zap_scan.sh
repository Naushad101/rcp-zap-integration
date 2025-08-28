#!/bin/bash

echo "=== Starting ZAP Security Scan ==="

# Get config from environment variables
APP_URLS="${APP_URLS:-http://rcp-backend:8081 http://frontend:80}"  
ZAP_URL="${ZAP_URL:-http://localhost:8090}"        
REPORTS_DIR="${REPORTS_DIR:-./zap_reports}"        

mkdir -p "$REPORTS_DIR"

# Wait for ZAP
echo "Waiting for ZAP to be available at $ZAP_URL..."
until curl -s -o /dev/null -w "%{http_code}" "$ZAP_URL" | grep -q "200"; do
  sleep 5
done
echo "ZAP is ready, starting scan..."

# Loop over each application URL
for APP_URL in $APP_URLS; do
    echo "==============================="
    echo "Scanning target: $APP_URL"
    echo "==============================="

    TARGET_NAME=$(echo "$APP_URL" | sed 's~http[s]*://~~; s~/~_-_~g')

    # Check if this is backend API or frontend
    if [[ "$APP_URL" == *"backend"* ]] || [[ "$APP_URL" == *":8081"* ]]; then
        echo "Detected backend service - attempting OpenAPI import"
        OPENAPI_URL="${APP_URL}/v3/api-docs"
        
        # Import OpenAPI if available
        echo "Importing OpenAPI spec: $OPENAPI_URL"
        OPENAPI_RESPONSE=$(curl -s -o /dev/null -w "%{http_code}" "$OPENAPI_URL")
        if [ "$OPENAPI_RESPONSE" == "200" ]; then
            curl -s -X GET "$ZAP_URL/JSON/openapi/action/importUrl/?url=$OPENAPI_URL"
            echo "OpenAPI spec imported successfully"
        else
            echo "OpenAPI spec not available at $OPENAPI_URL (HTTP $OPENAPI_RESPONSE)"
        fi
        sleep 5
    else
        echo "Detected frontend service - skipping OpenAPI import"
    fi

    # Spider scan
    echo "Starting spider scan..."
    SPIDER_ID=$(curl -s "$ZAP_URL/JSON/spider/action/scan/?url=$APP_URL/" | sed -E 's/.*"scan":"([0-9]+)".*/\1/')
    while true; do
        STATUS=$(curl -s "$ZAP_URL/JSON/spider/view/status/?scanId=$SPIDER_ID" | sed -E 's/.*"status":"([0-9]+)".*/\1/')
        echo "Spider progress: $STATUS%"
        [ "$STATUS" == "100" ] && break
        sleep 5
    done

    # Active scan
    echo "Starting active scan..."
    SCAN_ID=$(curl -s "$ZAP_URL/JSON/ascan/action/scan/?url=$APP_URL/" | sed -E 's/.*"scan":"([0-9]+)".*/\1/')
    while true; do
        STATUS=$(curl -s "$ZAP_URL/JSON/ascan/view/status/?scanId=$SCAN_ID" | sed -E 's/.*"status":"([0-9]+)".*/\1/')
        echo "Active scan progress: $STATUS%"
        [ "$STATUS" == "100" ] && break
        sleep 30
    done

    # Generate reports
    echo "Generating reports for $APP_URL..."
    curl -s "$ZAP_URL/OTHER/core/other/htmlreport/" > "$REPORTS_DIR/security-report-${TARGET_NAME}.html"
    curl -s "$ZAP_URL/JSON/core/view/alerts/?baseurl=$APP_URL" > "$REPORTS_DIR/alerts-${TARGET_NAME}.json"
    curl -s "$ZAP_URL/XML/core/view/alerts/?baseurl=$APP_URL" > "$REPORTS_DIR/alerts-${TARGET_NAME}.xml"

    # Generate summary
    SUMMARY=$(curl -s "$ZAP_URL/JSON/core/view/alertsSummary/?baseurl=$APP_URL")
    echo "=== SECURITY SCAN SUMMARY ($APP_URL) ===" > "$REPORTS_DIR/scan-summary-${TARGET_NAME}.txt"
    echo "Scan completed at: $(date)" >> "$REPORTS_DIR/scan-summary-${TARGET_NAME}.txt"
    echo "High: $(echo "$SUMMARY" | jq -r '.High // 0')" >> "$REPORTS_DIR/scan-summary-${TARGET_NAME}.txt"
    echo "Medium: $(echo "$SUMMARY" | jq -r '.Medium // 0')" >> "$REPORTS_DIR/scan-summary-${TARGET_NAME}.txt"
    echo "Low: $(echo "$SUMMARY" | jq -r '.Low // 0')" >> "$REPORTS_DIR/scan-summary-${TARGET_NAME}.txt"
    echo "Info: $(echo "$SUMMARY" | jq -r '.Informational // 0')" >> "$REPORTS_DIR/scan-summary-${TARGET_NAME}.txt"

    echo "Completed scan for: $APP_URL"
done

echo "All scans completed!"