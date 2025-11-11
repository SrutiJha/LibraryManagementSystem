# LibraryManagementSystem
## 🧩 Overview
The Library Management System is a Java-based application designed to manage book inventory, user records, and transactions. It uses Object-Oriented Programming (OOP) principles and connects to a MySQL database via JDBC. This project demonstrates concepts like Encapsulation,Inheritance,Polymorphism,Database Connectivity &amp;CRUD operations.
## ⚙️ Tech Stack

Language: Java (JDK 8 or above)

Database: MySQL

Libraries: JDBC

IDE: IntelliJ IDEA / Eclipse / VS Code

Concepts Used: OOP, DBMS, SQL, Exception Handling, Collections
## 🗄️ Database Setup

Open MySQL and run the following commands:

CREATE DATABASE library_db;

USE library_db;

CREATE TABLE books (
  id INT AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR(100),
  author VARCHAR(100),
  available BOOLEAN DEFAULT TRUE
);

CREATE TABLE users (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100)
);

CREATE TABLE transactions (
  id INT AUTO_INCREMENT PRIMARY KEY,
  user_id INT,
  book_id INT,
  issue_date DATE,
  return_date DATE,
  FOREIGN KEY (user_id) REFERENCES users(id),
  FOREIGN KEY (book_id) REFERENCES books(id)
);


## Update your database credentials in the DBConnection class:

private static final String URL = "jdbc:mysql://localhost:3306/library_db";
private static final String USER = "root";
private static final String PASSWORD = "your_password";

## 🧠 Features

✅ Add new books to the library
✅ View all available and issued books
✅ Issue a book to a user
✅ Return a book
✅ Maintain a transaction record (with issue/return dates)
✅ Modular design following OOP principles

## 📂 Project Structure
LibraryManagementSystem/
│
├── src/
│   ├── DBConnection.java
│   ├── Book.java
│   ├── LibraryService.java
│   └── LibraryManagementSystem.java
│
├── README.md
└── pom.xml  (if using Maven)

## ▶️ How to Run

Clone or download this repository.

Open the project in your preferred IDE.

Add the MySQL Connector JAR to your classpath (e.g., mysql-connector-j-8.0.x.jar).

Create the database using the SQL commands above.

Update the database credentials in DBConnection.java.

Run LibraryManagementSystem.java.

Use the console menu to add, issue, and return books.

## 🧮 Example Output
=== LIBRARY MENU ===
1. Add Book
2. View Books
3. Issue Book
4. Return Book
5. Exit
Enter your choice: 1
Enter Book Title: Java Fundamentals
Enter Author: James Gosling
✅ Book added successfully!

Enter your choice: 2
📚 Book List:
1 | Java Fundamentals by James Gosling | Available

## 💡 Future Enhancements

Add JavaFX GUI for better user experience

Implement user authentication (Admin vs Member)

Add due date tracking and fine calculation

Integrate email notifications for reminders
