from flask import Flask
from flask_restful import Api, Resource, reqparse
from config import client_id
import qrcode
import pyimgur

app = Flask(__name__)
api = Api(app)


def getStaticQR(key, data):
    # имя конечного файла
    filename = "test.png"
    # генерируем qr-код
    img = qrcode.make(key + " " + data)
    # сохраняем img в файл
    img.save(filename)
    return uploadToImg(filename)


def uploadToImg(path):
    id = client_id

    im = pyimgur.Imgur(id)
    upload_image = im.upload_image(path)
    return upload_image.link

class Quote(Resource):
    def get(self, key, data):
        url = getStaticQR(key, data)
        response = {
            "Key": key,
            "Data": data,
            "URL": url
        }
        return response

