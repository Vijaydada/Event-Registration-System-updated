-- Create the database
CREATE DATABASE event_registration;

-- Connect to the newly created database
\c event_registration;

-- Create the users table
CREATE TABLE users (
    user_id INT PRIMARY KEY NOT NULL,
    username VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role varchar(100) NOT NULL
);

-- Create the events table
CREATE TABLE events (
    event_id INT PRIMARY KEY NOT NULL,
    name VARCHAR(255) NOT NULL,
    date DATE NOT NULL,
    location VARCHAR(255) NOT NULL,
    user_id INT NOT NULL
);

-- Create the registrations table with foreign keys
CREATE TABLE registrations (
    register_id INT PRIMARY KEY DEFAULT NOT NULL,
    user_id INT,
    event_id INT,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (event_id) REFERENCES events(event_id)ON DELETE CASCADE ON UPDATE CASCADE
);
-- Create sequence for user_id
CREATE SEQUENCE user_seq START 1;

-- Create sequence for event_id
CREATE SEQUENCE event_seq START 1;

-- Create sequence for registration_id
CREATE SEQUENCE regis_seq START 100;