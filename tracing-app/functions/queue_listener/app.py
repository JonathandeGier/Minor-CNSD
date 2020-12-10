import json
from aws_xray_sdk.core import xray_recorder
from aws_xray_sdk.core import patch_all

patch_all()


def lambda_handler(event, context):

    if "Records" not in event:
        return

    for record in event["Records"]:
        print(record["body"])

    return {
        "statusCode": 200,
        "body": json.dumps({
            "message": "hello world",
        }),
    }
