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
    "prepare prepare_delete_note as "
    "delete from notes where id = $1"
)


def lambda_handler(event, context):
    id = event['params']['path']['id']
    cur.execute("execute prepare_delete_note (%s)", [id])
    conn.commit()
