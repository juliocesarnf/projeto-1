CREATE DATABASE IF NOT EXISTS Universidade;
USE Universidade;

CREATE TABLE Professores (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    senha VARCHAR(255) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL
);

CREATE TABLE Disciplinas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    apelido VARCHAR(50),
    periodo VARCHAR(20) NOT NULL
);

CREATE TABLE Links (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(50) NOT NULL,
    endereco VARCHAR(2048) NOT NUll,
    descricao varchar(200)
    disciplina_id INT,
    foreign KEY (disciplina_id) REFERENCESS Disciplinas(id)
)
