import qrcode
import pyimgur

import app
from config import client_id

def getStaticQR(key, data):
    # имя конечного файла
    filename = "test.png"
    # генерируем qr-код
    img = qrcode.make(key + " " + data)
    # сохраняем img в файл
    img.save(filename)
    return uploadToImg(img)

def uploadToImg(path):
    id = client_id

    im = pyimgur.Imgur(id)
    upload_image = im.upload_image(path)
    return upload_image.link

def main():
    #print(getStaticQR("1", "text"))
    print(app.Quote.get(1, "etewt", "etewtwet"))

if __name__ == '__main__':
    main()