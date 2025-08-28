#!/bin/bash

echo "=== Starting ZAP Security Scan ==="

# Get config from environment variables
APP_URLS="${APP_URLS:-http://rcp-backend:8081 http://dataclient:8082}"  
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

    OPENAPI_URL="${APP_URL}/v3/api-docs"
    TARGET_NAME=$(echo "$APP_URL" | sed 's~http[s]*://~~; s~/~_-_~g')

    # Import OpenAPI
    echo "Importing OpenAPI spec: $OPENAPI_URL"
    curl -s -X GET "$ZAP_URL/JSON/openapi/action/importUrl/?url=$OPENAPI_URL"
    sleep 5

    # Spider
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

    # Reports
    echo "Generating reports for $APP_URL..."
    curl -s "$ZAP_URL/OTHER/core/other/htmlreport/" > "$REPORTS_DIR/security-report-${TARGET_NAME}.html"
    curl -s "$ZAP_URL/JSON/core/view/alerts/?baseurl=$APP_URL" > "$REPORTS_DIR/alerts-${TARGET_NAME}.json"
    curl -s "$ZAP_URL/XML/core/view/alerts/?baseurl=$APP_URL" > "$REPORTS_DIR/alerts-${TARGET_NAME}.xml"

    # Summary
    SUMMARY=$(curl -s "$ZAP_URL/JSON/core/view/alertsSummary/?baseurl=$APP_URL")
    echo "=== SECURITY SCAN SUMMARY ($APP_URL) ===" > "$REPORTS_DIR/scan-summary-${TARGET_NAME}.txt"
    echo "Scan completed at: $(date)" >> "$REPORTS_DIR/scan-summary-${TARGET_NAME}.txt"
    echo "High: $(echo "$SUMMARY" | jq -r '.High')" >> "$REPORTS_DIR/scan-summary-${TARGET_NAME}.txt"
    echo "Medium: $(echo "$SUMMARY" | jq -r '.Medium')" >> "$REPORTS_DIR/scan-summary-${TARGET_NAME}.txt"
    echo "Low: $(echo "$SUMMARY" | jq -r '.Low')" >> "$REPORTS_DIR/scan-summary-${TARGET_NAME}.txt"
    echo "Info: $(echo "$SUMMARY" | jq -r '.Informational')" >> "$REPORTS_DIR/scan-summary-${TARGET_NAME}.txt"

done

echo "All scans completed!"