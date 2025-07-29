-- USE master
-- GO

-- CREATE DATABASE db_product;

-- USE db_product;


CREATE TABLE categories
(
    id INT IDENTITY(1,1) PRIMARY KEY,
    name NVARCHAR(200) NOT NULL UNIQUE,
    description NVARCHAR(1000),
    url_key NVARCHAR(250),
    status CHAR(1) NOT NULL DEFAULT 'A',
    created_at DATETIME NOT NULL DEFAULT GETDATE(),
    updated_at DATETIME
);

CREATE TABLE products
(
    id INT IDENTITY(1,1) PRIMARY KEY,
    name NVARCHAR(300) NOT NULL,
    description NVARCHAR(4000),
    price DECIMAL(18,2) NOT NULL DEFAULT 0,
    stock INT NOT NULL DEFAULT 0,
    category_id INT,
    status CHAR(1) NOT NULL DEFAULT 'A',
    created_at DATETIME NOT NULL DEFAULT GETDATE(),
    updated_at DATETIME,
    CONSTRAINT products_category_id_fk FOREIGN KEY (category_id) REFERENCES categories(id),
    CONSTRAINT products_name_category_id_uq UNIQUE (name, category_id)
);