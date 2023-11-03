import mysql.connector
conn = mysql.connector.connect(host='localhost',password='123456789',user='root')

if conn.is_connected():
    print("Connection Established")