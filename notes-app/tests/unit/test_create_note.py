import mock
import boto3
import json

import functions.create_note.lambda_function as app

from moto import mock_dynamodb2


def mock_dynamodb():
    table_name = "TestTable"
    conn = boto3.client(
        "dynamodb",
        region_name="us-east-1",
    )

    conn.create_table(
        TableName=table_name,
        AttributeDefinitions=[
            {"AttributeName": "id", "AttributeType": "S"},
            {"AttributeName": "content", "AttributeType": "S"}],
        KeySchema=[
            {"AttributeName": "id", "KeyType": "HASH"}
        ],
        GlobalSecondaryIndexes=[{
            "IndexName":"ContentIndex",
            "KeySchema": [
                {"AttributeName": "content", "KeyType": "HASH"}
            ],
            "Projection": {
                "ProjectionType": "ALL"
            }
        }],
        BillingMode="PAY_PER_REQUEST",
    )

@mock_dynamodb2
@mock.patch.dict('functions.create_note.lambda_function.os.environ', {"TABLENAME": "TestTable"})
def test_create_note():
    mock_dynamodb()

    content = "test create"
    event = {
        "body": '{"content": "' + content + '"}'
    }

    result = app.lambda_handler(event, "")

    assert result["statusCode"] == 200
    assert json.loads(result["body"])["content"] == content