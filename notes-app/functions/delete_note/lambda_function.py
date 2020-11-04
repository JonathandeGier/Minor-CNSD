import boto3
import os
from botocore.exceptions import ClientError

table = boto3.resource('dynamodb').Table(os.environ['TABLENAME'])


def lambda_handler(event, context):
    id = event['pathParameters']['NoteId']

    try:
        response = table.delete_item(Key={ 'id': id })
        return {
            "statusCode": 200,
        }
    except ClientError as e:
        return {
            "statusCode": 404
        }
