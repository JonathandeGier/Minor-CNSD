import json
import boto3
import datetime
import uuid
import os

table = boto3.resource('dynamodb').Table(os.environ['TABLENAME'])


def lambda_handler(event, context):

    body = json.loads(event['body'])
    text = body['content']
    id = str(uuid.uuid4())

    table.put_item(
        Item = {
            "id": id,
            "content": text,
            "created_at": datetime.datetime.utcnow().isoformat(),
            "updated_at": datetime.datetime.utcnow().isoformat()
        }
    )

    response = table.get_item(Key={'id': id})
    note = response['Item']

    return {
        "statusCode": 200,
        "body": json.dumps(note)
    }
