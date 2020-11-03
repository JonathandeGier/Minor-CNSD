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
    "prepare prepare_create_note as "
    "insert into notes(id, text, created_at, updated_at) values ($1, $2, now(), now())"
)
cur.execute(
    "prepare prepare_get_note as "
    "select * from notes where id = $1"
)


def lambda_handler(event, context):
    body = json.loads(event['body'])
    text = body['text']

    cur.execute("select nextval('notes_id')")
    id = cur.fetchall()[0][0]

    cur.execute("execute prepare_create_note (%s, %s)", (id, text))

    cur.execute("execute prepare_get_note (%s)", [id])
    result = cur.fetchall()
    conn.commit()

    note = {
        "Id": result[0][0],
        "Text": result[0][1],
        "Created_at": result[0][2].strftime('%Y-%m-%d %H:%M:%S:%f'),
        "Updated_at": result[0][3].strftime('%Y-%m-%d %H:%M:%S:%f')
    }

    return {
        "statusCode": 200,
        "body": json.dumps(note)
    }
