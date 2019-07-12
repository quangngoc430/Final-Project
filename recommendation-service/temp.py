import mysql.connector

mydb = mysql.connector.connect(
  host="localhost",
  user="root",
  passwd="root",
  database="TechShop"
)

mycursor = mydb.cursor()

mycursor.execute("SELECT * FROM Rating")

row = mycursor.fetchall()
while row is not None:
    print(row[0], row[3], row[4])
    row = mycursor.fetchone()