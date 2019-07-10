import mysql.connector

mydb = mysql.connector.connect(
  host="localhost",
  user="root",
  passwd="root",
  database="TechShop"
)

mycursor = mydb.cursor()

mycursor.execute("SELECT * FROM Account")

myresult = mycursor.fetchall()

for x in myresult:
  print(x)