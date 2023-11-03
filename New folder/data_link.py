import mysql.connector
from tkinter import *

class DataLink:
    def __init__(self):
        self.connection = mysql.connector.connect(
            host="localhost",
            user="root",
            password="123456789",
            database="app_users"
        )
        print("The connection is established!!")

    def sign_up(self, id, name, user_password, mobile, address, email, user_type):
        cursor = self.connection.cursor()
        query = "INSERT INTO user_details (user_id, user_name, password, phone_num, address, email, user_type) VALUES (%s, %s, %s, %s, %s, %s, %s)"
        values = (id, name, user_password, mobile, address, email, user_type)
        cursor.execute(query, values)
        self.connection.commit()
        cursor.close()
        print("You are registered successfully!!")

    def login(self, user_id, password):
        cursor = self.connection.cursor()
        query = "SELECT * FROM user_details WHERE user_id = %s AND password = %s"
        values = (user_id, password)
        cursor.execute(query, values)
        result = cursor.fetchone()

        if result:
            print("Login successful!")
        else:
            print("Invalid user_id or password.")

        cursor.close()

class SignUpGUI:
    def __init__(self, data_link):
        self.data_link = data_link

        self.signwin = Tk()
        self.signwin.title("Sign Up")

        self.userIdField = Entry(self.signwin)
        self.nameField = Entry(self.signwin)
        self.passwordField = Entry(self.signwin, show="*")
        self.mobileField = Entry(self.signwin)
        self.addressField = Entry(self.signwin)
        self.emailField = Entry(self.signwin)

        user_types = ["male", "female"]
        self.userTypeComboBox = StringVar(self.signwin)
        self.userTypeComboBox.set(user_types[0])
        self.userTypeMenu = OptionMenu(self.signwin, self.userTypeComboBox, *user_types)

        self.signUpButton = Button(self.signwin, text="Sign Up", command=self.sign_up)

        self.__layout()

    def __layout(self):
        Label(self.signwin, text="Enter User ID: ").grid(row=0, column=0)
        self.userIdField.grid(row=0, column=1)
        Label(self.signwin, text="Enter Your Name: ").grid(row=1, column=0)
        self.nameField.grid(row=1, column=1)
        Label(self.signwin, text="Enter Password: ").grid(row=2, column=0)
        self.passwordField.grid(row=2, column=1)
        Label(self.signwin, text="Enter Mobile Number: ").grid(row=3, column=0)
        self.mobileField.grid(row=3, column=1)
        Label(self.signwin, text="Enter Address: ").grid(row=4, column=0)
        self.addressField.grid(row=4, column=1)
        Label(self.signwin, text="Enter Email: ").grid(row=5, column=0)
        self.emailField.grid(row=5, column=1)
        Label(self.signwin, text="Select User Type: ").grid(row=6, column=0)
        self.userTypeMenu.grid(row=6, column=1)
        self.signUpButton.grid(row=7, columnspan=2)

    def sign_up(self):
        id = self.userIdField.get()
        name = self.nameField.get()
        user_password = self.passwordField.get()
        mobile = int(self.mobileField.get())
        address = self.addressField.get()
        email = self.emailField.get()
        user_type = self.userTypeComboBox.get()

        self.data_link.sign_up(id, name, user_password, mobile, address, email, user_type)

    def run(self):
        self.signwin.mainloop()

def main():
    dl = DataLink()
    signUpGui = SignUpGUI(dl)
    signUpGui.run()

if __name__ == "__main__":
    main()
