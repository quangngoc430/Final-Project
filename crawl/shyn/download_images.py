import csv
import json
import os
import urllib.request

primary_images = []
items_images = []
    
pwd = os.getcwd()

with open("items.csv") as csv_file:
  csv_reader = csv.reader(csv_file, delimiter=',')

  line = 0
  for row in csv_reader:
    if(line != 0):
      print(line)
      primary_images.append(row[3])
      items_images.append(json.loads(row[4]))
      line = line + 1
    else:
      line += 1

print("###############################")
print("####### Download images #######")
print("###############################")

for index in range(len(primary_images)):
  folder_path = pwd + "/storages/" + str(index)

  os.mkdir(folder_path)

  urllib.request.urlretrieve(primary_images[index], folder_path + "/primary_image.jpg")

  id = 0
  for image in items_images[index]:
    urllib.request.urlretrieve(image, folder_path + "/" + str(id) +".jpg")
    id = id + 1

  print(index)