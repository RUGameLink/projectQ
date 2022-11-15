import qrcode

def getStaticQR(key, data):
    # имя конечного файла
    filename = "test.png"
    # генерируем qr-код
    img = qrcode.make(key + " " + data)
    # сохраняем img в файл
    img.save(filename)

def main():
    getStaticQR("1", "text")

if __name__ == '__main__':
    main()