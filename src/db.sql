CREATE DATABASE library;

USE library;

CREATE TABLE users (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       name VARCHAR(50),
                       username VARCHAR(50) UNIQUE,
                       password VARCHAR(50),
                       type VARCHAR(10)
);

CREATE TABLE books (
                       isbn VARCHAR(13) PRIMARY KEY,
                       title VARCHAR(100),
                       author VARCHAR(100)
);

CREATE TABLE loans (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       user_id INT,
                       book_isbn VARCHAR(13),
                       issue_date DATE,
                       due_date DATE,
                       FOREIGN KEY (user_id) REFERENCES users(id),
                       FOREIGN KEY (book_isbn) REFERENCES books(isbn)
);