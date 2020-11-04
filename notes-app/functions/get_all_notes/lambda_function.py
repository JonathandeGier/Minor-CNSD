import json
import boto3
import os


table = boto3.resource('dynamodb').Table(os.environ['TABLENAME'])


def lambda_handler(event, context):
    scan_kwargs = {
        'ProjectionExpression': "id, content, created_at, updated_at"
    }

    notes = []
    done = False
    start_key = None
    while not done:
        if start_key:
            scan_kwargs['ExclusiveStartKey'] = start_key
        response = table.scan(**scan_kwargs)
        notes.extend(response.get('Items', []))
        start_key = response.get('LastEvaluatedKey', None)
        done = start_key is None

    return {
        "statusCode": 200,
        "body": json.dumps(notes)
    }
