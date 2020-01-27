from flask import Flask, jsonify, request
import mysql.connector
import requests
app = Flask(__name__)


@app.route("/", methods=["GET"])
def root():
    return "welcome to the application"


@app.route("/UID", methods=["POST"])
def post_userdata():
    uid = request.json.get('UID')   #get uid from node mcu
    name = request.json.get('Name') #get user name from nodemcu
    amount = request.json.get('Amount') #get toll amount from node mcu
    vehicalno = request.json.get('vehical_number') #get vehical number from node mcu
    vehicaltype = request.json.get('vehical_type') #get vehical type from  node mcu
    uid1 = uid.strip()   #delete extra spaces
    connection = mysql.connector.connect(host="localhost", user="root", password="root", database="tollbooth") #connect to mysql database
    cursor = connection.cursor()
    statement = f"select id, UID, Name, Amount from user_data WHERE UID = '{uid1}'" #select uid,name,amount from user data where uid
    cursor.execute(statement)
    data = cursor.fetchall()
    user = []
    for temp in data:
        user.append({
            "id": temp[0],
            "UID": temp[1],
            "Name": temp[2],
            "Amount": temp[3]
        })

    cursor.close()
    connection.close()
    if(temp[3]<amount):
        result = {"status": "insufficient balance"}
        return jsonify(result)
    else:

        #update user data
        connection = mysql.connector.connect(host="localhost", user="root", password="root", database="tollbooth")
        cursor = connection.cursor()
        statement = f"insert into {uid}(UID, Name, Amount, vehical_number, vehical_type) values ('{uid}','{name}','{amount}','{vehicalno}','{vehicaltype}')"
        cursor.execute(statement)
        cursor.close()
        connection.commit()
        connection.close()

        #update admin data
        connection = mysql.connector.connect(host="localhost", user="root", password="root", database="tollbooth")
        cursor = connection.cursor()
        statement = f"insert into admin_data(UID, Name, Amount, vehical_number, vehical_type) values ('{uid}','{name}','{amount}','{vehicalno}','{vehicaltype}')"
        cursor.execute(statement)
        cursor.close()
        connection.commit()
        connection.close()
        #result = {"status": "success"}
        #update payment from database
        connection = mysql.connector.connect(host="localhost", user="root", password="root", database="tollbooth")
        cursor = connection.cursor()

        var = float(temp[3])
        amount1 = float(amount)

        bal = var-amount1
        print(uid)

        statement = f"UPDATE user_data SET Amount = '{bal}' WHERE UID = '{uid1}' "
        print(statement)
        cursor.execute(statement)
        cursor.close()
        connection.commit()
        connection.close()
        result = {"status": "success"}
        return jsonify(result)


#@app.route("/UID", methods=["POST"])
#def post_temperature():
    #userid = request.json.get("UID")
    #connection = mysql.connector.connect(host="localhost", user="root", password="root", database="tollbooth")
    #cursor = connection.cursor()
    #statement = f"insert into user_data (UID) values ({userid})"
    #cursor.execute(statement)
    #connection.commit()
    #cursor.close()
    #connection.close()
    #return "inserted new UID"


@app.route("/UID", methods=["GET"])
def get_temperatures():
    connection = mysql.connector.connect(host="localhost", user="root", password="root", database="tollbooth")
    cursor = connection.cursor()
    statement = "select * from tollbooth"
    cursor.execute(statement)
    data = cursor.fetchall()
    temperatures = []
    for temp in data:
        temperatures.append({
            "id": temp[0],
            "UID": temp[1]
        })

    cursor.close()
    connection.close()
    return jsonify(temperatures)

#get balance of user
@app.route("/balance", methods=["GET"])
def get_balance():
    uid4 = request.args.get('UID')
    connection = mysql.connector.connect(host="localhost", user="root", password="root", database="tollbooth")
    cursor = connection.cursor()
    statement = f"select Name,Amount from user_data where UID = '{uid4}"
    cursor.execute(statement)
    data = cursor.fetchall()
    balance = []
    for (Name,Amount) in data:
        balance.append({
            "Name": Name,
            "Amount": Amount
        })

    cursor.close()
    connection.close()
    return jsonify(balance)


@app.route("/user", methods=["GET"])
def get_users():
    connection = mysql.connector.connect(host="localhost", user="root", password="root", database="tollbooth")
    cursor = connection.cursor()
    statement = "select id, UID, Name, Amount from user_data"
    cursor.execute(statement)
    data = cursor.fetchall()
    user = []
    for temp in data:
        user.append({
            "id": temp[0],
            "UID": temp[1],
           "Name": temp[2],
           "Amount": temp[3]
        })

    cursor.close()
    connection.close()
    return jsonify(user)

@app.route("/user/register", methods=["POST"])
def register_users():
    name = request.json.get('name')
    email = request.json.get('email')
    phoneno = request.json.get('phoneno')
    vehicaltype = request.json.get('vehicaltype')
    vehicalno = request.json.get('vehicalno')
    password = request.json.get('password')
    connection = mysql.connector.connect(host="localhost", user="root", password="root", database="tollbooth")
    cursor = connection.cursor()
    statement = f"insert into user_data(Name, email_id, phone_no, Password, vehical_type, vehical_number) values ('{name}','{email}','{phoneno}','{password}','{vehicaltype}','{vehicalno}')"
    cursor.execute(statement)
    cursor.close()
    connection.commit()
    connection.close()
    result = {"status":"success"}
    return jsonify(result)

#admin registration
@app.route("/admin/register", methods=["POST"])
def register_admins():
    name = request.json.get('name')
    email = request.json.get('email')
    phoneno = request.json.get('phone')
    password = request.json.get('password')
    connection = mysql.connector.connect(host="localhost", user="root", password="root", database="tollbooth")
    cursor = connection.cursor()
    statement = f"insert into admin_login(Name, email_id, phoneNo, password) values ('{name}','{email}','{phoneno}','{password}')"
    cursor.execute(statement)
    cursor.close()
    connection.commit()
    connection.close()
    result = {"status":"success"}
    return jsonify(result)

@app.route("/user/login", methods=["POST"])
def user_login():
    email = request.json.get('email')
    password = request.json.get('password')
    connection = mysql.connector.connect(host="localhost", user="root", password="root", database="tollbooth")

    statement = f"select UID, Name, phone_no, vehical_type,vehical_number, Amount,email_id from user_data where email_id='{email}' and Password = '{password}'"
    cursor = connection.cursor()
    cursor.execute(statement)
    result = cursor.fetchall()
    cursor.close()
    connection.commit()
    connection.close()
    user_status = ""
    if len(result)==0:
        user_status = "error"
    else:
        connection = mysql.connector.connect(host="localhost", user="root", password="root", database="tollbooth")

        statement = f"select UID from user_data where email_id='{email}' and Password = '{password}'"
        cursor = connection.cursor()
        cursor.execute(statement)
        data = cursor.fetchall()
        cursor.close()
        connection.commit()
        connection.close()
        users = []
        for (UID) in data:
            user = {
                "UID": UID

            }
            users.append(user)

        user_status = UID
        print("KEY",user_status)
    response = {
        "UID":user_status
    }
    return jsonify(response)


#admin login

@app.route("/admin/login", methods=["POST"])
def admin_login():
    email = request.json.get('email')
    password = request.json.get('password')
    connection = mysql.connector.connect(host="localhost", user="root", password="root", database="tollbooth")

    statement = f"select Name,email_id from admin_login where email_id='{email}' and Password = '{password}'"
    cursor = connection.cursor()
    cursor.execute(statement)
    result = cursor.fetchall()
    cursor.close()
    connection.commit()
    connection.close()
    user_status = ""
    if len(result)==0:
        user_status = "error"
    else:

        user_status = "success"
        print("KEY",user_status)
    response = {
        "UID":user_status
    }
    return jsonify(response)



@app.route("/tollinfo", methods=["GET"])
def get_tollinfo():
    uid4 = request.args.get('UID')
    print(uid4)
    connection = mysql.connector.connect(host="localhost", user="root", password="root", database="tollbooth")
    cursor = connection.cursor()
    statement = f"select Name, Amount, vehical_type, vehical_number, timestamp from {uid4}"
    cursor.execute(statement)
    data = cursor.fetchall()
    users = []
    for (Name, Amount, vehical_type, vehical_number, timestamp) in data:
        user = {
            "Name":Name,
            "Amount":Amount,
            "vehical_type": vehical_type,
            "vehical_number": vehical_number,
            "timestamp": timestamp,
        }
        users.append(user)
    cursor.close()
    connection.close()
    return jsonify(users)


@app.route("/admin", methods=["GET"])
def get_admininfo():
    connection = mysql.connector.connect(host="localhost", user="root", password="root", database="tollbooth")
    cursor = connection.cursor()
    statement = f"select Name, Amount, vehical_type, vehical_number, timestamp from admin_data"
    cursor.execute(statement)
    data = cursor.fetchall()
    users = []
    for (Name, Amount, vehical_type, vehical_number, timestamp) in data:
        user = {
            "Name":Name,
            "Amount":Amount,
            "vehical_type": vehical_type,
            "vehical_number": vehical_number,
            "timestamp": timestamp,
        }
        users.append(user)
    cursor.close()
    connection.close()
    return jsonify(users)

if __name__ == '__main__':
    #app.run(host='172.18.4.204',port=5000, debug= 'true')
    app.run(host='192.168.43.216', port=5000, debug='true')
