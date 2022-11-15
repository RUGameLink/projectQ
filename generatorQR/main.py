from flask import Flask
from flask_restful import Api, Resource, reqparse
import qrcode
import pyimgur

app = Flask(__name__)
api = Api()

class Main(Resource):
    def get(self, key, data):
        url = getStaticQR(key, data)
        response = {
            "Key": key,
            "Data": data,
            "URL": url
        }
        return response

api.add_resource(Main, "/api/<key>&&<data>")
api.init_app(app)

def getStaticQR(key, data):
    # имя конечного файла
    filename = "test.png"
    # генерируем qr-код
    img = qrcode.make(key + " " + data)
    # сохраняем img в файл
    img.save(filename)
    return uploadToImg(filename)


def uploadToImg(path):
    id = '2513abfb1d6eab5'

    im = pyimgur.Imgur(id)
    upload_image = im.upload_image(path)
    return upload_image.link

if __name__ == "__main__":
    app.run(debug=True, port=3000, host="127.0.0.1")

