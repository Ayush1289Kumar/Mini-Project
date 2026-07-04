import pyttsx3
import mysql.connector
from datetime import datetime

# Function for text-to-speech


def speak(text):
    voice = pyttsx3.init()
    voice.say(text)
    voice.runAndWait()


# Connect to MySQL
conn = mysql.connector.connect(
    host="localhost",
    user="root",
    passwd="mysql@1289",
    database="project"
)
cursor = conn.cursor()

# Function to create the database schema


def setup_database():
    cursor.execute("""
    CREATE TABLE IF NOT EXISTS users (
        username VARCHAR(50) PRIMARY KEY,
        pin VARCHAR(10) NOT NULL,
        balance FLOAT DEFAULT 0.0
    )
    """)

    cursor.execute("""
    CREATE TABLE IF NOT EXISTS transactions (
        id INT AUTO_INCREMENT PRIMARY KEY,
        username VARCHAR(50),
        transaction_type VARCHAR(20),
        amount FLOAT,
        timestamp DATETIME DEFAULT CURRENT_TIMESTAMP,
        FOREIGN KEY (username) REFERENCES users(username)
    )
    """)
    conn.commit()


setup_database()
username = ""
# Function to register a new user


def register_user():
    speak("Enter a username for registration.")
    username = input("Enter a username: ").lower()

    cursor.execute("SELECT * FROM users WHERE username = %s", (username,))
    if cursor.fetchone():
        speak("Username already exists. Try a different username.")
        print("Username already exists. Try a different username.")
        return

    speak("Enter a 4-digit PIN.")
    pin = input("Enter a 4-digit PIN: ")
    speak("Enter an initial deposit amount.")
    balance = float(input("Enter an initial deposit amount: "))

    cursor.execute("INSERT INTO users (username, pin, balance) VALUES (%s, %s, %s)",
                   (username, pin, balance))
    conn.commit()
    speak("User registered successfully.")
    print("User registered successfully!")

# Function to view transaction history


def view_transaction_history(username):
    cursor.execute(
        "SELECT transaction_type, amount, timestamp FROM transactions WHERE username = %s", (username,))
    transactions = cursor.fetchall()

    if transactions:
        speak("Here is your transaction history.")
        print("\nTransaction History:")
        for transaction in transactions:
            print(
                f"{transaction[0]} | Amount: {transaction[1]} | Time= {transaction[2]}")
    else:
        speak("No transactions found.")
        print("No transactions found.")

# Function for admin features


def admin_features():
    while True:
        speak("Admin Menu: View all users, Add funds to any user, or Delete a user.")
        print("\nAdmin Menu:\n1. View All Users\n2. Add Funds to User\n3. Delete User\n4. Exit Admin Menu")
        choice = input("Your Choice: ").lower()

        if choice == "1":
            global username
            if username:
                speak("No data available!")
                print("No data available!")
            cursor.execute("SELECT username, balance FROM users")
            users = cursor.fetchall()
            print("\nAll Users and Balances:")
            for user in users:
                print(f"Username: {user[0]}, Balance: {user[1]}")
                speak(f"user {user[0]} has balance {user[1]}")

        elif choice == "2":
            speak("Enter the username to add funds.")
            username = input("Enter the username: ").lower()
            cursor.execute(
                "SELECT * FROM users WHERE username = %s", (username,))
            user = cursor.fetchone()

            if user:
                speak("Enter the amount to add.")
                amount = float(input("Enter the amount to add: "))
                new_balance = user[2] + amount
                cursor.execute(
                    "UPDATE users SET balance = %s WHERE username = %s", (new_balance, username))
                cursor.execute("INSERT INTO transactions (username, transaction_type, amount) VALUES (%s, %s, %s)",
                               (username, 'admin_add', amount))
                conn.commit()
                speak("Funds added successfully.")
                print("Funds added successfully.")
            else:
                speak("User not found.")
                print("User not found.")

        elif choice == "3":
            speak("Enter the username to delete.")
            username = input("Enter the username: ").lower()
            cursor.execute(
                "DELETE FROM transactions WHERE username = %s", (username,))
            cursor.execute(
                "DELETE FROM users WHERE username = %s", (username,))
            conn.commit()
            speak("User deleted successfully.")
            print("User deleted successfully.")

        elif choice == "4":
            speak("Exiting admin menu.")
            break
        else:
            speak("Invalid choice. Please try again.")
            print("Invalid choice. Please try again.")

# Main ATM simulator function


def atm_simulator():
    speak("Welcome to ATM SIMULATOR")
    print("\nWelcome to ATM SIMULATOR")

    while True:
        speak("Login or Register")
        print("\n1. Login\n2. Register\n3. Admin Login\n4. Exit")
        choice = input("Your Choice: ").lower()

        if choice == "1":
            # User login
            count = 0
            countuser = 0
            while True:
                speak("Enter your username.")
                user = input("Enter your username: ").lower()
                cursor.execute(
                    "SELECT * FROM users WHERE username = %s", (user,))
                user_data = cursor.fetchone()
                countuser += 1
                if countuser == 3:
                    speak("3 Unsuccessful Username Attempts.")
                    print("3 Unsuccessful Username Attempts.")
                    exit()

                if user_data:
                    break
                else:
                    speak("Invalid username.")
                    print("Invalid username!!!")

            while count < 3:
                speak("Enter your pin.")
                pin = input("Enter your pin: ")

                if user_data[1] == pin:
                    amount = user_data[2]
                    break
                else:
                    count += 1
                    speak("Invalid pin.")
                    print("Invalid pin!!!")

            if count == 3:
                speak("3 Unsuccessful Pin Attempts. Your Card has been Locked.")
                print("3 Unsuccessful Pin Attempts. Your Card has been Locked.")
                exit()

            # User authenticated, ATM operations
            while True:
                speak("Select from the Following Options")
                print("\n\nSelect from the Following Options: \n1. Statement\n2. Withdraw\n3. Deposit\n4. View Transaction History\n5. Exit")
                choice = input("Your Choice: ").lower()

                if choice == "1":
                   # speak(f"Hello {user}, you have {amount} Rupees.")
                    print(f"\nHello {user}, You have {amount} Rupees")

                elif choice == "2":
                    speak("Enter The Amount You Want to Withdraw")
                    cash_out = float(
                        input("\nEnter The Amount You Want to Withdraw: "))

                    if cash_out > amount:
                        speak("Insufficient balance")
                        print("\nInsufficient balance")
                        speak("Please Try Again")
                    else:
                        amount -= cash_out
                        cursor.execute(
                            "UPDATE users SET balance = %s WHERE username = %s", (amount, user))
                        cursor.execute("INSERT INTO transactions (username, transaction_type, amount) VALUES (%s, %s, %s)",
                                       (user, 'withdraw', cash_out))
                        conn.commit()
                        speak(f"Your new balance is {amount} Rupees.")
                        print(f"\nYour new balance is {amount}")

                elif choice == "3":
                    speak("Enter The Amount You Want To Deposit")
                    cash_in = float(
                        input("\nEnter The Amount You Want To Deposit: "))
                    amount += cash_in
                    cursor.execute(
                        "UPDATE users SET balance = %s WHERE username = %s", (amount, user))
                    cursor.execute("INSERT INTO transactions (username, transaction_type, amount) VALUES (%s, %s, %s)",
                                   (user, 'deposit', cash_in))
                    conn.commit()
                    speak(f"Your new balance is {amount} Rupees.")
                    print(f"\nYour new balance is {amount}")

                elif choice == "4":
                    view_transaction_history(user)

                elif choice == "5":
                    speak("Thanks for Using")
                    print("\nThanks For Using")
                    break

                else:
                    speak("Invalid choice. Please try again.")
                    print("\nInvalid choice. Please try again.")

        elif choice == "2":
            register_user()

        elif choice == "3":
            admin_features()

        elif choice == "4":
            speak("Goodbye!")
            print("Goodbye!")
            break

        else:
            speak("Invalid choice. Please try again.")
            print("Invalid choice. Please try again.")


# Run the ATM simulator
atm_simulator()
