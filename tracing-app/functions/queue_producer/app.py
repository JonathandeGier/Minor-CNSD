import json
import boto3
import os
from aws_xray_sdk.core import xray_recorder
from aws_xray_sdk.core import patch_all

patch_all()
queue_url = os.environ["QUEUE_URL"]


def lambda_handler(event, context):

    sqs = boto3.client("sqs")

    sqs.send_message(
        QueueUrl=queue_url,
        MessageBody="Hello World",
    )

    return {
        "statusCode": 201,
        "body": json.dumps({
            "message": "CREATED",
        }),
    }
