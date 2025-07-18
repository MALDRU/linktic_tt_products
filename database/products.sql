CREATE SEQUENCE products_sequence;
CREATE TABLE IF NOT EXISTS products (
    id INT8 PRIMARY KEY DEFAULT nextval('products_sequence'::regclass),
    name VARCHAR(60) NOT NULL,
    price NUMERIC(10,2) NOT NULL,
    description VARCHAR(255) NULL
);