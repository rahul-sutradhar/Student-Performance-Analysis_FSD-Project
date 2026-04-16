CREATE DATABASE IF NOT EXISTS student_performance_db;
USE student_performance_db;

CREATE TABLE IF NOT EXISTS students (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(120) NOT NULL,
    department VARCHAR(30) NOT NULL,
    math INT NOT NULL,
    science INT NOT NULL,
    programming INT NOT NULL,
    attendance INT NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS app_users (
    id BIGINT NOT NULL AUTO_INCREMENT,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(30) NOT NULL,
    PRIMARY KEY (id)
);

INSERT INTO students (name, department, math, science, programming, attendance) VALUES
('Amit Sharma', 'CS', 78, 85, 90, 92),
('Riya Verma', 'CS', 88, 79, 85, 90),
('Karan Patil', 'IT', 65, 70, 72, 80),
('Pooja Nair', 'IT', 92, 91, 95, 96),
('Rahul Singh', 'CS', 55, 60, 58, 70),
('Sneha Kulkarni', 'ENTC', 74, 81, 76, 88),
('Arjun Deshmukh', 'MECHANICAL', 61, 59, 57, 68),
('Neha Joshi', 'CIVIL', 83, 87, 80, 91);
