from bs4 import BeautifulSoup
import urllib.request
import os
import csv
import pandas
import json
import philong

from flask import Flask, Request, Response 

app = Flask(__name__)

current_path = os.getcwd()

print(current_path)

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

def write_csv(item):
    with open("temp.csv", mode="a") as csv_file:
        fieldnames = ["title",
                  "warranty", 
                  "price", 
                  "image_item_origin",
                  "image_item_resize",
                  "item_infos",
                  "item_infos_technical"]
        writer = csv.DictWriter(csv_file, fieldnames)

        if (item == None):
            writer.writerow({"title": 'None',
                            "warranty": 'None',
                            "price": 'None',
                            "image_item_origin": 'None',
                            "image_item_resize": 'None',
                            "item_infos": 'None',
                            "item_infos_technical": 'None'})
        else:
            writer.writerow({"title": item.get_title(),
                            "warranty": item.get_warranty(),
                            "price": item.get_price(),
                            "image_item_origin": item.get_item_image_origin(),
                            "image_item_resize": str(item.get_item_image_resize()),
                            "item_infos": str(item.get_item_infos()),
                            "item_infos_technical": str(item.get_item_infos_technical())})

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
    item = philong.Item()

    error_not_found = html_page.find("div", attrs={"class": "agile-info"})
    if (error_not_found != None and error_not_found.find('h2') != None):
        write_csv(None)
        return

    ## Get title
    title = html_page.find("div", attrs={"class": "entry-header"}).find('h1').text
    #print("Tieu he: " + title)
    item.set_title(title)

    ## Get warranty
    temp = html_page.find("div", attrs={"class": "entry-header"}).find_all("span")
    if (len(temp) == 4):
        warranty = temp[2].text
        warranty = warranty[(warranty.rfind(":") + 1):].strip()
        
    #print("Bao hanh: " + str(warranty))
    item.set_warranty(warranty)

    ## Get price
    temp = html_page.find("div", attrs={"class": "entry-summary"})\
                    .find("span", attrs={"class": "p-price"})\
                    .find("span").text
    price = temp[:(len(temp) - 1)].replace('.', '')
    #print("Price: " + price)
    item.set_price(price)

    ## Get image
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
    
    #print(json.dumps(image_item_origin))
    item.set_item_image_origin(json.dumps(image_item_origin))

    #print(json.dumps(image_item_resize))
    item.set_item_image_resize(json.dumps(image_item_resize))

    temp = html_page.find("div", attrs={"class": "pro-info"})

    if (temp != None):
        temp = temp.find_all("p")

        if (len(temp) != 0):
            for p in temp:
                item_infos.append(p.text)
        
        #print(item_infos)
        item.set_item_infos(json.dumps(item_infos))
    else:
        item.set_item_infos(str(None))

    try:
        temp = html_page.find("div", attrs={"id": "product_spec"})\
                        .find("div", attrs={"class": "tbl-technical"})

        if (temp != None):
            temp = temp.find("table")

        if (temp != None):           

            trs = temp.find_all("tr")
            for tr in trs:
                item_info_technical = {}

                tds = tr.find_all("td")
                
                if (len(tds) == 0):
                    tds = tr.find_all("th")

                # title of item_info_technical
                title = convert_utf8_to_plain(tds[0].text)
                item_info_technical['title'] = title#.replace('\n', '\\n')
                
                # value of item_info_technical
                if (len(tds) > 1): 
                    value = convert_utf8_to_plain(tds[1].text)
                    item_info_technical['value'] = value#.replace('\n', '\\n')
                
                item_infos_technical.append(item_info_technical)

            #print(json.dumps(item_infos_technical))
            item.set_item_infos_technical(json.dumps(item_infos_technical))
        else:
            item.set_item_infos_technical(str(None))
    except:
        print("An exception technical infos")
        item.set_item_infos_technical(str(None))

    write_csv(item)
    
def get_all_url_items(urls_and_names):
    urls = []
    categoryID = 0
    for url_and_name in urls_and_names:
        url = url_and_name[0]
        condition = True
        page = 1
        while(condition):
            response = urllib.request.urlopen(url + "?page=" + str(page))
            html_page = BeautifulSoup(response.read().decode('utf-8'), "html.parser")

            product_item_in_list = html_page.find("div", attrs={"id": "product_item_in_list"})

            if (product_item_in_list == None):
                condition = False
            else:
                print("page " + str(page))
                page = page + 1
                product_items = product_item_in_list.find_all("h4", attrs={"class": "p-name"})
                for product_item in product_items:
                    urls.append([base_url + product_item.find('a')['href'], categoryID])
                    print("url " + base_url + product_item.find('a')['href'])

        categoryID = categoryID + 1

    return urls

def write_urls_to_file(urls_and_names):
    urls = get_all_url_items(urls_and_names)

    with open("urls.csv", mode="a") as csv_file:
        fieldnames = ["ID", "url", "categoryID"]
        writer = csv.DictWriter(csv_file, fieldnames)
        writer.writeheader()
        id = 0

        for url in urls:
            writer.writerow({"ID": id,
                            "url": url[0],
                            "categoryID": str(url[1])})
            
            id = id + 1

def get_all_urls_from_file(file_name):
    urls = []
    
    with open(file_name) as csv_file:
        csv_reader = csv.reader(csv_file, delimiter=',')

        line = 0
        for row in csv_reader:
            if(line != 0):
                urls.append(row[1])
            else:
                line += 1

    return urls

write_urls_to_file(get_all_url_items(urls_and_names))

urls = get_all_urls_from_file("urls.csv")

with open("temp.csv", mode="a") as csv_file:
    fieldnames = ["title",
                  "warranty", 
                  "price", 
                  "image_item_origin",
                  "image_item_resize",
                  "item_infos",
                  "item_infos_technical"]
    writer = csv.DictWriter(csv_file, fieldnames)
    writer.writeheader()

index = 1
for url in urls:
    print(index)
    get_data_of_an_item(url)
    index = index + 1

#get_data_of_an_item("https://philong.com.vn/laptop-acer-nitro5-an515-54-784p-core-i7-9750h-ram-8gb-hdd-1tb-man-hinh-15.6quot-fhd-1650-4g-win-10.html")