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


def lambda_handler(event, context):
    cur.execute('''
            SELECT *
            FROM notes;
            ''')

    result = cur.fetchall()

    notes = []
    for databaseNote in result:
        note = {
            "Id": databaseNote[0],
            "Text": databaseNote[1],
            "Created_at": databaseNote[2].strftime('%Y-%m-%d %H:%M:%S:%f'),
            "Updated_at": databaseNote[3].strftime('%Y-%m-%d %H:%M:%S:%f')
        }
        notes.append(note)

    return {
        "statusCode": 200,
        "body": json.dumps(notes)
    }
