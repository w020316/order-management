CREATE DATABASE IF NOT EXISTS shop_db DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

USE shop_db;

CREATE TABLE IF NOT EXISTS user (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL,
    phone VARCHAR(20)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS product (
    id INT PRIMARY KEY AUTO_INCREMENT,
    product_name VARCHAR(100) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    stock INT NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS orders (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL,
    total_price DECIMAL(10,2) NOT NULL,
    order_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (product_id) REFERENCES product(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO user (username, phone) VALUES ('张三', '13800138001');
INSERT INTO user (username, phone) VALUES ('李四', '13800138002');

INSERT INTO product (product_name, price, stock) VALUES ('iPhone 15', 5999.00, 100);
INSERT INTO product (product_name, price, stock) VALUES ('MacBook Pro', 12999.00, 50);
INSERT INTO product (product_name, price, stock) VALUES ('AirPods Pro', 1899.00, 200);
