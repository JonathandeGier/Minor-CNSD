import json
import boto3
import psycopg2
import os

db_identifier = 'notes'
client = boto3.client('rds')

instances = client.describe_db_instances(DBInstanceIdentifier=db_identifier)
instance = instances['DBInstances'][0]

user = instance['MasterUsername']
password = os.environ['DB_PASSWORD']
host = instance['Endpoint']['Address']
port = instance['Endpoint']['Port']

conn = psycopg2.connect(
    database=db_identifier,
    user=user,
    password=password,
    host=host,
    port=port
)

cur = conn.cursor()
cur.execute(
    "prepare prepare_get as "
    "select * from notes where id = $1"
)


def lambda_handler(event, context):
    id = event['params']['path']['id']
    cur.execute("execute prepare_get (%s)", id)

    result = cur.fetchall()

    if len(result) != 1:
        raise Exception('Not Found')

    note = {
        "id": result[0][0],
        "text": result[0][1],
        "created_at": result[0][2].strftime('%Y-%m-%d %H:%M:%S:%f'),
        "updated_at": result[0][3].strftime('%Y-%m-%d %H:%M:%S:%f')
    }

    return {
        'statusCode': 200,
        'body': note
    }
