#!/bin/bash

# URL of the endpoint
URL="http://localhost:8000/jobs/import-customers"

# URL of the endpoint for checking job status
STATUS_URL="http://localhost:8000/jobs/status"

# File name to send in the request body
FILE_NAME="customers-1m.csv"

JOB_EXECUTION_ID_LIST=()

# Send the POST request with plain text data
JOB_EXECUTION_ID_LIST+=($(curl -s -X POST "$URL" -H "Content-Type: text/plain" -d "$FILE_NAME"))
JOB_EXECUTION_ID_LIST+=($(curl -s -X POST "$URL" -H "Content-Type: text/plain" -d "$FILE_NAME"))
JOB_EXECUTION_ID_LIST+=($(curl -s -X POST "$URL" -H "Content-Type: text/plain" -d "$FILE_NAME"))

# Loop to check job status every 5 seconds
while true; do
  for JOB_EXECUTION_ID in "${JOB_EXECUTION_ID_LIST[@]}"; do
    PROGRESS=$(curl -s -X GET "$STATUS_URL/$JOB_EXECUTION_ID")
    echo "Job Execution ID: $JOB_EXECUTION_ID - Progress: $PROGRESS"
  done
  echo ""
  # Wait for 5 seconds before repeating the loop
  sleep 2
done
