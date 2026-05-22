CREATE DATABASE IF NOT EXISTS shop_db DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

USE shop_db;

DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL,
    phone VARCHAR(20)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE product (
    id INT PRIMARY KEY AUTO_INCREMENT,
    product_name VARCHAR(100) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    stock INT NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE orders (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL,
    total_price DECIMAL(10,2) NOT NULL,
    status INT NOT NULL DEFAULT 0 COMMENT '0-待支付 1-已支付 2-已取消 3-已完成',
    order_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES `user`(id),
    FOREIGN KEY (product_id) REFERENCES product(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `user` (username, phone) VALUES ('张三', '13800138001');
INSERT INTO `user` (username, phone) VALUES ('李四', '13800138002');
INSERT INTO `user` (username, phone) VALUES ('王五', '13800138003');
INSERT INTO `user` (username, phone) VALUES ('赵六', '13800138004');

INSERT INTO product (product_name, price, stock) VALUES ('iPhone 15', 5999.00, 100);
INSERT INTO product (product_name, price, stock) VALUES ('MacBook Pro', 12999.00, 50);
INSERT INTO product (product_name, price, stock) VALUES ('AirPods Pro', 1899.00, 200);
INSERT INTO product (product_name, price, stock) VALUES ('iPad Air', 4799.00, 80);
INSERT INTO product (product_name, price, stock) VALUES ('Apple Watch', 2999.00, 120);
