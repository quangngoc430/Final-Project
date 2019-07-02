from bs4 import BeautifulSoup
import urllib.request
import os
import csv

base_url = "https://dochoixedoc.vn"
urls = ["https://dochoixedoc.vn/danh-muc/phu-tung/",
        "https://dochoixedoc.vn/danh-muc/do-choi-xe/"]
maximun_page = [4, 5]
product_id = 1

def get_info_product(url):
    page = urllib.request.urlopen(url)
    html_soup = BeautifulSoup(page, "html.parser")
    product_name = html_soup.find("h1").text
    product_price = html_soup.find("div", attrs={"summary", "entry-summary"}) \
                             .find("p", attrs={"price"}).span
    print(url)
    if (product_price != None):
        product_price = product_price.text
        product_price = product_price[:(len(product_price) - 1)].replace(".", "")
    print(product_price)

for url in urls:
    index = urls.index(url)
    print(url)
    for page_number in range(1, maximun_page[index] + 1):
        page = urllib.request.urlopen(url + "page/" + str(page_number) + "/")
        html_soup = BeautifulSoup(page, "html.parser")

        products = html_soup.find_all("li", attrs={"product"})
        for product in products:
            product_url = product.a["href"]
            product_img_urls = product.div.a.img["srcset"]
            product_img_urls = product_img_urls[1:]
            product_img_urls = product_img_urls.split(", /")

            for product_img_url in product_img_urls:
                product_img_url = product_img_url[0:(product_img_url.index(" "))]
                product_img_url = "https:/" + product_img_url
                name_img = str(product_id) + "-" + product_img_url[(product_img_url.rindex("/") + 16):]
                #print(product_img_url + " - " + name_img)
                #urllib.request.urlretrieve(product_img_url, "storages/" + name_img)
            
            get_info_product(product_url)

            product_id += 1