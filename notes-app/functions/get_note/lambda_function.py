import json
import boto3
import os
from botocore.exceptions import ClientError

table = boto3.resource('dynamodb').Table(os.environ['TABLENAME'])


def lambda_handler(event, context):
    id = event['pathParameters']['NoteId']

    try:
        response = table.get_item(Key={'id': id})
        if "Item" in response:
            return {
                "statusCode": 200,
                "body": json.dumps(response['Item'])
            }
        else:
            return {
                "statusCode": 404
            }
    except ClientError as e:
        print(e.response)
        return {
            "statusCode": 500,
            "body": json.dumps(e.response['Error']['Message'])
        }
