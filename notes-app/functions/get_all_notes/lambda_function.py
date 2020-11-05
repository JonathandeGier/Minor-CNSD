import json
import boto3
import os
from boto3.dynamodb.conditions import Key


table = boto3.resource("dynamodb").Table(os.environ["TABLENAME"])


def scan_notes():
    scan_kwargs = {
        "ProjectionExpression": "id, content, created_at, updated_at"
    }

    notes = []
    done = False
    start_key = None
    while not done:
        if start_key:
            scan_kwargs["ExclusiveStartKey"] = start_key
        response = table.scan(**scan_kwargs)
        notes.extend(response.get("Items", []))
        start_key = response.get("LastEvaluatedKey", None)
        done = start_key is None
    
    return notes


def query_notes_by_content(content):
    response = table.query(
        IndexName="ContentIndex",
        KeyConditionExpression=Key("content").eq(content)
    )
    return response["Items"]


def lambda_handler(event, context):
    queryParameters = event["queryStringParameters"]
    
    if "content" in queryParameters:
        notes = query_notes_by_content(queryParameters["content"])
    else:
        notes = scan_notes()
    
    return {
        "statusCode": 200,
        "body": json.dumps(notes)
    }
