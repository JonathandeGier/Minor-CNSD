import json
import boto3
import datetime
import os
from botocore.exceptions import ClientError


def lambda_handler(event, context):
    table = boto3.resource('dynamodb').Table(os.environ['TABLENAME'])

    id = event['pathParameters']['NoteId']
    body = json.loads(event['body'])
    content = body['content']

    try:
        response = table.update_item(
            Key={'id': id},
            UpdateExpression="set content=:c, updated_at=:u, created_at=if_not_exists(created_at, :n)",
            ExpressionAttributeValues={
                ':c': content,
                ':u': datetime.datetime.utcnow().isoformat(),
                ':n': datetime.datetime.utcnow().isoformat()
            },
            ReturnValues="ALL_NEW"
        )

        return {
            "statusCode": 200,
            "body": json.dumps(response["Attributes"])
        }
    except ClientError as e:
        print(e.response)
        return {
            "statusCode": 500,
            "body": json.dumps(e.response['Error']['Message'])
        }