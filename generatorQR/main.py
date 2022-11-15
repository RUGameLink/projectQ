import qrcode

# пример данных
data = "Пiхуй, пляшем! Плевать, вальсируем!"
# имя конечного файла
filename = "test.png"
# генерируем qr-код
img = qrcode.make(data)
# сохраняем img в файл
img.save(filename)