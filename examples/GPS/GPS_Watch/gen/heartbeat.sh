#!/bin/bash

# invoke heartbeat every 1 second

while true; do
  curl -X POST 'https://7t6vbnkhn4.execute-api.us-east-1.amazonaws.com/test/message' -H 'content-type: application/json' -H 'InvocationType: Event' -d '{"heartbeat": "true"}'
  sleep 10
done
