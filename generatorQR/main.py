import qrcode
import pyimgur
from config import client_id

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

def main():
    getStaticQR("1", "text")

if __name__ == '__main__':
    main()