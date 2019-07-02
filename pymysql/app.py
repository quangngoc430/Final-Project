import mysql.connector
from mysql.connector import Error
try:
  mySQLconnection = mysql.connector.connect(host='localhost',
                                            database='TechShop',
                                            user='root',
                                            password='root')

  sql_select_Query = "select * from Rating"
  cursor = mySQLconnection .cursor()
  cursor.execute(sql_select_Query)
  records = cursor.fetchall()

  for row in records:
    print("Id = ", row[0])
    print("Value = ", row[1])
    print("ItemID", row[2])
    print("AccountID", row[3], "\n")
  cursor.close()
   
except Error as e :
  print ("Error while connecting to MySQL", e)
finally:
  #closing database connection.
  if(mySQLconnection.is_connected()):
    mySQLconnection.close()
    print("MySQL connection is closed")