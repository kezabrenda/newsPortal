# newsPortal
For news organisation to post their news.

### author name
* Brenda UMUTONIWASE KEZA

### Project setup instructions
* install INTELLIJ IDEA
* JAVA as programming language
* Have POSTGRES and MAVEN
* Access to HEROKU

#### Setup Requirements for Database
* CREATE DATABASE newsportal;
* \c newsportal;
* CREATE TABLE departments(id serial PRIMARY KEY, name VARCHAR, description VARCHAR);
* CREATE TABLE users(id serial PRIMARY KEY, name VARCHAR, position VARCHAR, department VARCHAR ,departments_id INTEGER);
* CREATE TABLE news(id serial PRIMARY KEY, title VARCHAR, writtenby VARCHAR, content VARCHAR, type VARCHAR, users_id INTEGER, departments_id INTEGER, createdat timestamp);
* CREATE TABLE users_departments(id serial PRIMARY KEY, users_id INTEGER, departments_id INTEGER);
* CREATE TABLE general_news(id serial PRIMARY KEY,title VARCHAR, writtenby VARCHAR, content VARCHAR,createdat timestamp, users_id INTEGER);
* CREATE TABLE users_generalnews(id serial PRIMARY KEY, users_id INTEGER,generalnews_id INTEGER);
* CREATE TABLE departments_news(id serial PRIMARY KEY,title VARCHAR, writtenby VARCHAR, content VARCHAR,createdat timestamp, users_id INTEGER,departments_id INTEGER);
* CREATE TABLE users_departmentnews(id serial PRIMARY KEY, users_id INTEGER, departmentnews_id INTEGER);
* CREATE TABLE departments_departmentnews(id serial PRIMARY KEY, departments_id INTEGER, departmentnews_id INTEGER);
* CREATE DATABASE newsportal_test WITH TEMPLATE newsportal;

## copyright and license information
For any further explaination, questions or suggestions, feel free to contact me keza1brenda@gmail.com

License and Copyright information.
Copyright (c) [2020] [Brenda UMUTONIWASE KEZA]

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
