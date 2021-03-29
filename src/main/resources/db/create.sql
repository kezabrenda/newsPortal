CREATE DATABASE newsportal;

\c newsportal;

CREATE TABLE departments (id serial PRIMARY KEY,name VARCHAR, description VARCHAR);

CREATE TABLE users(id serial PRIMARY KEY,name VARCHAR, position VARCHAR, role VARCHAR,departments_id INTEGER);

CREATE TABLE users_departments(id serial PRIMARY KEY,users_id INTEGER, departments_id INTEGER);

CREATE TABLE general_news(id serial PRIMARY KEY,title VARCHAR, writtenby VARCHAR, content VARCHAR,createdat timestamp, users_id INTEGER);

CREATE TABLE users_generalnews(id serial PRIMARY KEY, users_id INTEGER,generalnews_id INTEGER);

CREATE TABLE departments_news(id serial PRIMARY KEY,title VARCHAR, writtenby VARCHAR, content VARCHAR,createdat timestamp, users_id INTEGER,departments_id INTEGER);

CREATE TABLE users_departmentnews(id serial PRIMARY KEY, users_id INTEGER,departmentnews_id INTEGER);

CREATE TABLE departments_departmentnews(id serial PRIMARY KEY, departments_id INTEGER,departmentnews_id INTEGER);