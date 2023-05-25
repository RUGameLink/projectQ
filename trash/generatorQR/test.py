import requests

url = "https://generator-qr.p.rapidapi.com/4t4t4te/4554h54hhhhh"

headers = {
	"X-RapidAPI-Key": "8fac8d93edmshc4380d7d88505cdp17d5dfjsndf4c3b2501a4",
	"X-RapidAPI-Host": "generator-qr.p.rapidapi.com"
}

response = requests.request("GET", url, headers=headers)

print(response.text)