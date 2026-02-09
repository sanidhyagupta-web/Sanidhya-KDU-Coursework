import json
import boto3

dynamodb = boto3.resource('dynamodb')
table = dynamodb.Table('counter-table')

def handler(event, context):
    method = event["httpMethod"]

    if method == "GET":
        response = table.get_item(Key={"id": "main"})
        value = response.get("Item", {}).get("value", 0)

        return {
            "statusCode": 200,
            "headers": {
                "Access-Control-Allow-Origin": "*"
            },
            "body": json.dumps({"value": value})
        }

    elif method == "PUT":
        response = table.update_item(
            Key={"id": "main"},
            UpdateExpression="SET #v = if_not_exists(#v, :start) + :inc",
            ExpressionAttributeNames={"#v": "value"},
            ExpressionAttributeValues={
                ":inc": 1,
                ":start": 0
            },
            ReturnValues="UPDATED_NEW"
        )

        return {
            "statusCode": 200,
            "headers": {
                "Access-Control-Allow-Origin": "*"
            },
            "body": json.dumps({"value": response["Attributes"]["value"]})
        }

    else:
        return {
            "statusCode": 400,
            "body": "Unsupported method"
        }