# Java-Contact-Management-System

# 📞 Java Contact Management System

A robust, console-based CRUD (Create, Read, Update, Delete) application built to securely manage and categorize contact information.

## 🛠️ Tech Stack
* **Language:** Java
* **Database:** MySQL
* **Connectivity:** JDBC (MySQL Connector/J)

## ✨ Key Features
* **Add Contacts:** Securely store names, phone numbers, emails, and categories.
* **View Directory:** Dynamically generates a formatted, aligned table in the console for easy reading.
* **Delete Records:** Safely remove outdated contacts using their unique database ID.
* **Data Security:** Utilizes `PreparedStatement` to protect against SQL injection.

## 🚀 How to Run Locally
1. Run the SQL setup script to create the `contact_db` database and `contacts` table.
2. Update the database credentials inside `ContactApp.java`.
3. Compile and run using the MySQL JDBC Driver:
   ```bash
   javac -cp "mysql-connector-j-9.6.0.jar" ContactApp.java
   java -cp ".;mysql-connector-j-9.6.0.jar" ContactApp
