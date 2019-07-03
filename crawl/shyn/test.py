from bs4 import BeautifulSoup
import urllib.request
import os
import csv
import json

base_url = "https://dochoixedoc.vn"
urls = ["https://dochoixedoc.vn/danh-muc/phu-tung/page/1",
        "https://dochoixedoc.vn/danh-muc/phu-tung/page/2",
        "https://dochoixedoc.vn/danh-muc/phu-tung/page/3",
        "https://dochoixedoc.vn/danh-muc/phu-tung/page/4",
        "https://dochoixedoc.vn/danh-muc/do-choi-xe/page/1",
        "https://dochoixedoc.vn/danh-muc/do-choi-xe/page/2",
        "https://dochoixedoc.vn/danh-muc/do-choi-xe/page/3",
        "https://dochoixedoc.vn/danh-muc/do-choi-xe/page/4",
        "https://dochoixedoc.vn/danh-muc/do-choi-xe/page/5"]


def get_all_items_link(urls):
    
    urls_result = []

    id = 0

    with open("url.csv", mode="a") as csv_file:
        fieldnames = ["ID", "Url"]
        writer = csv.DictWriter(csv_file, fieldnames)
        writer.writeheader()
        for url in urls:
            page = urllib.request.urlopen(url)
            html_soup = BeautifulSoup(page, "html.parser")

            products_link = html_soup.find_all("a", attrs={"class": "woocommerce-LoopProduct-link"})

            for product_link in products_link:
                print(id)
                
                writer.writerow({"ID": str(id), "Url": product_link["href"]})
                id = id + 1
                
                urls_result.append(product_link["href"])

    return urls_result

item_urls = get_all_items_link(urls)

def get_info_of_items(urls):
    id = 0

    with open("items.csv", mode="a") as csv_file:
        fieldnames = ["id", "title", "price", "primary_image", "item_images"]
        
        writer = csv.DictWriter(csv_file, fieldnames)
        writer.writeheader()

        for url in urls:
            page = urllib.request.urlopen(url)
            html_soup = BeautifulSoup(page, "html.parser")

            title = html_soup.find("div", attrs={"class": "entry-title"}).find("h1").text

            price = html_soup.find("p", attrs={"class": "price"})\
                            .find("span", attrs={"class": "woocommerce-Price-amount"})

            if(price == None):
                price = "None"
            else:
                price = price.text.replace("₫", "").replace(".", "")

            primary_image = html_soup.find("img", attrs={"class": "attachment-shop_single"})["data-src"]

            item_images = []

            figures = html_soup.find_all("figure", attrs={"class": "gallery-item"})

            for figure in figures:
                item_images.append(figure.find("img")["src"])

            writer.writerow({"id": str(id),
                            "title": title,
                            "price": str(price),
                            "primary_image": primary_image,
                            "item_images": json.dumps(item_images)})
            
            print(id)

            id = id + 1

get_info_of_items(item_urls)
# def get_info_product(url):
#     page = urllib.request.urlopen(url)
#     html_soup = BeautifulSoup(page, "html.parser")
#     product_name = html_soup.find("h1").text
#     product_price = html_soup.find("div", attrs={"summary", "entry-summary"}) \
#                              .find("p", attrs={"price"}).span
#     print(url)
#     if (product_price != None):
#         product_price = product_price.text
#         product_price = product_price[:(len(product_price) - 1)].replace(".", "")
#     print(product_price)

# for url in urls:
#     index = urls.index(url)
#     print(url)
#     for page_number in range(1, maximun_page[index] + 1):
#         page = urllib.request.urlopen(url + "page/" + str(page_number) + "/")
#         html_soup = BeautifulSoup(page, "html.parser")

#         products = html_soup.find_all("li", attrs={"product"})
#         for product in products:
#             product_url = product.a["href"]
#             product_img_urls = product.div.a.img["srcset"]
#             product_img_urls = product_img_urls[1:]
#             product_img_urls = product_img_urls.split(", /")

#             for product_img_url in product_img_urls:
#                 product_img_url = product_img_url[0:(product_img_url.index(" "))]
#                 product_img_url = "https:/" + product_img_url
#                 name_img = str(product_id) + "-" + product_img_url[(product_img_url.rindex("/") + 16):]
#                 #print(product_img_url + " - " + name_img)
#                 #urllib.request.urlretrieve(product_img_url, "storages/" + name_img)
            
#             get_info_product(product_url)

#             product_id += 1

class Item:
    def set_title(self, title):
        self.title = title

    def get_title(self):
        return self.title

    def set_price(self, price):
        self.price = price

    def get_price(self):
        return self.price

    def set_primary_image(self, primary_image):
        self.primary_image = primary_image

    def get_primary_image(self):
        return self.primary_image

    def set_item_images(self, item_images):
        self.item_images = item_images
    
    def get_item_images(self):
        return self.item_images