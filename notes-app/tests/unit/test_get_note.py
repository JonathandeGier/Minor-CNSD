import mock
import boto3

import functions.get_note.lambda_function as app

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

    conn.put_item(
        TableName=table_name,
        Item={
            "id": {"S": "some-id"},
            "content": {"S": "Some content"},
            "created_at": {"S": "2020-11-19T12:00:00"},
            "updated_at": {"S": "2020-11-19T12:00:00"}
        }
    )


@mock_dynamodb2
@mock.patch.dict('functions.get_note.lambda_function.os.environ', {"TABLENAME": "TestTable"})
def test_get_note():
    mock_dynamodb()

    event = {
        "pathParameters": {
            "NoteId": "some-id"
        }
    }

    result = app.lambda_handler(event, "")
    assert result["statusCode"] == 200
    assert result["body"] == '{"id": "some-id", "content": "Some content", "created_at": "2020-11-19T12:00:00", "updated_at": "2020-11-19T12:00:00"}'


@mock_dynamodb2
@mock.patch.dict('functions.get_note.lambda_function.os.environ', {"TABLENAME": "TestTable"})
def test_note_not_found():
    mock_dynamodb()

    event = {
        "pathParameters": {
            "NoteId": "random-id"
        }
    }

    result = app.lambda_handler(event, "")
    assert result["statusCode"] == 404
