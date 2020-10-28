import json
import boto3
import base64

s3 = boto3.resource('s3')
destinationBucket = 'cnsd-processed-files'


# this is from the pipeline
def lambda_handler(event, context):
    for record in event['Records']:

        # Get message details
        messageBody = json.loads(record['body'])
        s3Bucket = messageBody[1]['s3BucketName']
        s3Key = messageBody[1]['s3Key']

        # Get file from S3
        orgObject = s3.Object(s3Bucket, s3Key)
        file = orgObject.get()

        # Decode file
        decodedFile = base64.b64decode(file['Body'].read())

        # Upload file to other S3 Bucket
        newObject = s3.Object(destinationBucket, s3Key + '.jpg')
        newObject.put(Body=decodedFile)

        # Remove origional file
        orgObject.delete()