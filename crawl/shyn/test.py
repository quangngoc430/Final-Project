from bs4 import BeautifulSoup
import urllib.request
import os
import csv
import json

base_url = "https://dochoixedoc.vn"
urls = [["https://dochoixedoc.vn/danh-muc/phu-tung/page/1", 1],
        ["https://dochoixedoc.vn/danh-muc/phu-tung/page/2", 1],
        ["https://dochoixedoc.vn/danh-muc/phu-tung/page/3", 1],
        ["https://dochoixedoc.vn/danh-muc/phu-tung/page/4", 1],
        ["https://dochoixedoc.vn/danh-muc/do-choi-xe/page/1", 2],
        ["https://dochoixedoc.vn/danh-muc/do-choi-xe/page/2", 2],
        ["https://dochoixedoc.vn/danh-muc/do-choi-xe/page/3", 2],
        ["https://dochoixedoc.vn/danh-muc/do-choi-xe/page/4", 2],
        ["https://dochoixedoc.vn/danh-muc/do-choi-xe/page/5", 2]]


def get_all_items_link(urls):
    
    urls_result = []

    id = 0

    with open("url.csv", mode="a") as csv_file:
        fieldnames = ["ID", "Url"]
        writer = csv.DictWriter(csv_file, fieldnames)
        writer.writeheader()
        for url in urls:
            page = urllib.request.urlopen(url[0])
            html_soup = BeautifulSoup(page, "html.parser")

            products_link = html_soup.find_all("a", attrs={"class": "woocommerce-LoopProduct-link"})

            for product_link in products_link:
                print(id)
                
                writer.writerow({"ID": str(id), "Url": product_link["href"]})
                id = id + 1
                
                urls_result.append([product_link["href"], url[1]])

    return urls_result

item_urls = get_all_items_link(urls);

def get_info_of_items(urls):
    id = 0

    with open("items.csv", mode="a") as csv_file:
        fieldnames = ["id", "title", "price", "primary_image", "item_images", "category_id"]
        
        writer = csv.DictWriter(csv_file, fieldnames)
        writer.writeheader()

        for url in urls:
            page = urllib.request.urlopen(url[0])
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
                            "item_images": json.dumps(item_images),
                            "category_id": str(url[1])})
            
            print(id)

            id = id + 1

get_info_of_items(item_urls)

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