from flask import Flask
from flask_restful import Api, Resource, reqparse
import qrcode
import pyimgur

app = Flask(__name__)
api = Api(app)

class Quote(Resource):
    def get(self, key, data):
        url = "getStaticQR(key, data)"
        response = {
            "Key": key,
            "Data": data,
            "URL": url
        }
        return response


