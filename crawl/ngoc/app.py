from bs4 import BeautifulSoup
import urllib.request
import os
import csv
import json
import philong

from flask import Flask, Request, Response 

app = Flask(__name__)

base_url = "https://philong.com.vn"
urls_and_names = [["https://philong.com.vn/may-tinh-xach-tay.html", "may tinh xach tay"],
                  ["https://philong.com.vn/may-tinh-bo-all-in-one.html", "may tinh bo all in one"],
                  ["https://philong.com.vn/dien-thoai-table-do-choi.html", "dien thoai - table - do choi"],
                  ["https://philong.com.vn/linh-kien-may-tinh.html", "linh kien may tinh"],
                  ["https://philong.com.vn/phu-kien-gaming-gear.html", "phu kien - gaming gear"],
                  ["https://philong.com.vn/thiet-bi-mang.html", "thiet bi mang - wifi"],
                  ["https://philong.com.vn/thiet-bi-van-phong.html", "thiet bi van phong"],
                  ["https://philong.com.vn/camera-an-ninh-giai-phap.html", "camera - an ninh - giai phap"],
                  ["https://philong.com.vn/thiet-bi-sieu-thi-gia-dinh.html", "thiet bi sieu thi - gia dinh"],
                  ["https://philong.com.vn/am-thanh-chieu-sang.html", "am thanh - chieu sang"],
                  ["https://philong.com.vn/apple-center-pl.html", "apple center"]]

def get_origin_image_url(url_image):
    return url_image.replace("/120-", "")

def get_resize_image_url(url_image):
    index = url_image.rfind("/")
    return url_image[:(index + 1)] + "120-" + url_image[(index + 1):]

def write_csv(warranty, price, image_item_resize, item_infos):
    with open("temp.csv", mode="a") as csv_file:
        fieldnames = ["warranty", 
                      "price", 
                      "image_item_resize",
                      "item_infos"]
        writer = csv.DictWriter(csv_file, fieldnames)

        writer.writeheader()
        writer.writerow({"warranty": str(warranty),
                         "price": str(price),
                         "image_item_resize": str(image_item_resize),
                         "item_infos": str(item_infos)})

def convert_utf8_to_plain(text):
    return bytes(text, 'utf-8').decode('utf-8')

def get_data_of_an_item(url_item):
    response = urllib.request.urlopen(url_item)
    html_page = BeautifulSoup(response.read().decode('utf-8'), "html.parser")

    warranty = None
    price = None
    image_item_resize = []
    image_item_origin = []
    item_infos = []
    item_infos_technical = []


    temp = html_page.find("div", attrs={"class": "entry-header"}).find_all("span")
    if (len(temp) == 4):
        warranty = temp[2].text
        warranty = warranty[(warranty.rfind(":") + 1):].strip()
    print("Bao hanh: " + warranty)


    temp = html_page.find("div", attrs={"class": "entry-summary"})\
                    .find("span", attrs={"class": "p-price"})\
                    .find("span").text
    price = temp[:(len(temp) - 1)].replace('.', '')
    print("Price: " + price)


    temp = html_page.find("div", attrs={"id": "sync2"})\
                    .find_all("div", attrs={"class": "item text-center"})
    if (len(temp) == 0):
        origin_image_url = base_url + html_page\
                                      .find("div", attrs={"id": "sync1"})\
                                      .find("a", attrs={"id": "Zoomer"})["href"]
        image_item_origin.append(origin_image_url)
        image_item_resize.append(get_resize_image_url(origin_image_url))
    else:
        for div in temp:
            origin_image_url = base_url + div.a["href"]
            image_item_origin.append(origin_image_url)
            image_item_resize.append(get_resize_image_url(origin_image_url))
    print(image_item_origin)
    print(image_item_resize)

    temp = html_page.find("div", attrs={"class": "pro-info"})\
                    .find_all("p")
    if (len(temp) != 0):
        for p in temp:
            item_infos.append(p.text)
    print(item_infos)

    temp = html_page.find("div", attrs={"id": "product_spec"})\
                    .find("div", attrs={"class": "tbl-technical"})\
                    .find("table")

    trs = temp.find_all("tr")
    for tr in trs:
        item_info_technical = {}

        tds = tr.find_all("td")
        
        # title of item_info_technical
        title = convert_utf8_to_plain(tds[0].text)
        item_info_technical['title'] = title#.replace('\n', '\\n')
        
        # value of item_info_technical
        if (len(tds) > 1): 
            value = convert_utf8_to_plain(tds[1].text)
            item_info_technical['value'] = value#.replace('\n', '\\n')
        
        item_infos_technical.append(item_info_technical)

    a.temp_response = item_infos_technical
    print(json.dumps(item_infos_technical))


    temp = html_page.find()

    write_csv(warranty= warranty, price= price, image_item_resize= image_item_resize, item_infos= item_infos)
    
get_data_of_an_item("https://philong.com.vn/laptop-acer-nitro5-an515-54-784p-core-i7-9750h-ram-8gb-hdd-1tb-man-hinh-15.6quot-fhd-1650-4g-win-10.html")

# @app.route('/user')
# def recommendation_items_for_user():
#     return Response(json.dumps(a.temp_response),  mimetype='application/json')

if __name__ == '__main__':
    app.run(debug=True, port=5000) #run app in debug mode on port 5000