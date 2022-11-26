from flask import Flask
from flask_restful import Api, Resource
import qrcode
import pyimgur
import uuid

app = Flask(__name__)
api = Api()

class Main(Resource):
    def get(self):
        key, url = getStaticQR()
        response = {
            "Key": key,
            "URL": url
        }
        return response

api.add_resource(Main, "/api/getqr")
api.init_app(app)

def getStaticQR():
    #UUID Пользователя
    key = str(uuid.uuid4())
    # имя конечного файла
    filename = f"{key}.png"
    # генерируем qr-код
    img = qrcode.make(key)
    # сохраняем img в файл
    img.save(filename)
    url = uploadToImg(filename)
    return key, url

def uploadToImg(path):
    id = '2513abfb1d6eab5' #id для Imgur

    im = pyimgur.Imgur(id) #Подключение к Imgur
    upload_image = im.upload_image(path) #Загрузка файла
    return upload_image.link #Отправка ссылки на файл

if __name__ == "__main__":
    app.run(debug=True, port=3000, host="127.0.0.1")

