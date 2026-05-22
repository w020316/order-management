# Order Management 电商订单管理系统

基于 **Spring Boot + MyBatis + MySQL** 构建的电商订单管理后端服务，涵盖用户、商品、订单三表关联，支持事务控制、并发安全库存扣减、订单状态流转等核心业务能力。

> 项目在线演示：https://w020316.github.io/order-management/

---

## 功能特性

### 订单管理
- 创建订单 — 校验用户/商品存在性 → 库存校验 → 原子性扣库存（乐观锁防超卖）→ 生成订单，全部步骤在 `@Transactional` 事务中完成
- 订单详情查询 — MyBatis `resultMap` + SQL JOIN 三表联查，一次返回订单+用户名+商品名
- 按用户查询订单列表 — 支持分页
- 取消订单 — 仅待支付可取消，取消后自动归还库存（事务保证）
- 支付订单 — 状态机流转：待支付(0) → 已支付(1) / 已取消(2) / 已完成(3)

### 商品管理
- 商品列表（分页）、详情、新增、修改、删除

### 用户管理
- 用户列表、详情、注册、修改、删除

### 工程规范
- 统一响应封装 `Result<T>`
- 全局异常处理 `@ControllerAdvice`（BusinessException / 参数校验异常 / 参数绑定异常）
- DTO/VO 分层设计（请求参数校验 `@Validated`）
- Swagger API 文档
- CORS 跨域配置
- 单元测试覆盖核心业务逻辑

---

## 技术栈

| 技术 | 版本 | 用途 |
|------|------|------|
| Spring Boot | 2.7.18 | 基础框架 |
| MyBatis | 2.3.2 | ORM 持久层 |
| MySQL | 8.x | 关系型数据库 |
| Lombok | - | 减少样板代码 |
| Spring Validation | - | JSR-303 参数校验 |
| Swagger (Springfox) | 3.0 | API 文档 |
| JUnit 5 + Mockito | - | 单元测试 |

---

## 数据库设计

```
user (用户表)
├── id          INT PK AUTO_INCREMENT
├── username    VARCHAR(50)
└── phone       VARCHAR(20)

product (商品表)
├── id            INT PK AUTO_INCREMENT
├── product_name  VARCHAR(100)
├── price         DECIMAL(10,2)
└── stock         INT DEFAULT 0

orders (订单表)
├── id           INT PK AUTO_INCREMENT
├── user_id      INT FK → user.id
├── product_id   INT FK → product.id
├── quantity     INT
├── total_price  DECIMAL(10,2)
├── status       INT DEFAULT 0  (0:待支付 1:已支付 2:已取消 3:已完成)
└── order_time   DATETIME DEFAULT CURRENT_TIMESTAMP
```

初始化 SQL 见 [sql/init.sql](sql/init.sql)，包含建库建表语句和示例数据。

---

## API 接口文档

### 订单模块 `/api/orders`

| 方法 | 路径 | 说明 |
|------|------|------|
| POST | `/api/orders` | 创建订单 |
| GET | `/api/orders/{id}` | 查询订单详情 |
| GET | `/api/orders/user/{userId}` | 按用户查询订单（分页） |
| PUT | `/api/orders/{id}/cancel` | 取消订单（归还库存） |
| PUT | `/api/orders/{id}/pay` | 支付订单 |

**创建订单请求体示例：**
```json
{
  "userId": 1,
  "productId": 1,
  "quantity": 2
}
```

**订单详情响应示例：**
```json
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "orderId": 1,
    "userName": "张三",
    "productName": "iPhone 15",
    "quantity": 2,
    "totalPrice": 11998.00,
    "status": 1,
    "statusDesc": "已支付",
    "orderTime": "2026-05-23 10:30:00"
  }
}
```

### 商品模块 `/api/products`

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | `/api/products?pageNum=1&pageSize=10` | 商品列表（分页） |
| GET | `/api/products/{id}` | 商品详情 |
| POST | `/api/products` | 新增商品 |
| PUT | `/api/products/{id}` | 修改商品 |
| DELETE | `/api/products/{id}` | 删除商品 |

### 用户模块 `/api/users`

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | `/api/users` | 用户列表 |
| GET | `/api/users/{id}` | 用户详情 |
| POST | `/api/users` | 注册用户 |
| PUT | `/api/users/{id}` | 修改用户 |
| DELETE | `/api/users/{id}` | 删除用户 |

启动项目后访问 Swagger 文档：`http://localhost:8080/swagger-ui/index.html`

---

## 项目结构

```
src/main/java/com/example/order/
├── common/
│   ├── Result.java            # 统一响应封装
│   └── OrderStatus.java       # 订单状态枚举
├── config/
│   ├── CorsConfig.java        # CORS 跨域配置
│   └── SwaggerConfig.java     # Swagger 配置
├── controller/
│   ├── OrderController.java   # 订单接口
│   ├── ProductController.java # 商品接口
│   └── UserController.java    # 用户接口
├── dto/
│   ├── CreateOrderRequest.java
│   ├── CreateProductRequest.java
│   ├── CreateUserRequest.java
│   ├── PageRequest.java       # 分页请求
│   ├── UpdateProductRequest.java
│   └── UpdateUserRequest.java
├── entity/
│   ├── Orders.java            # 订单实体
│   ├── Product.java           # 商品实体
│   ├── User.java              # 用户实体
│   └── OrderDetail.java       # 订单联查结果
├── exception/
│   ├── BusinessException.java # 业务异常
│   └── GlobalExceptionHandler.java  # 全局异常处理
├── mapper/
│   ├── OrderMapper.java       # 订单 Mapper 接口
│   ├── ProductMapper.java     # 商品 Mapper 接口
│   └── UserMapper.java        # 用户 Mapper 接口
├── service/
│   ├── OrderService.java      # 订单 Service 接口
│   ├── ProductService.java    # 商品 Service 接口
│   ├── UserService.java       # 用户 Service 接口
│   └── impl/
│       ├── OrderServiceImpl.java
│       ├── ProductServiceImpl.java
│       └── UserServiceImpl.java
├── vo/
│   ├── OrderDetailVO.java     # 订单详情视图对象
│   └── PageResult.java        # 分页响应
└── OrderManagementApplication.java

src/main/resources/
├── application.yml             # 配置文件
└── mapper/
    ├── OrderMapper.xml         # 订单 SQL 映射
    ├── ProductMapper.xml       # 商品 SQL 映射
    └── UserMapper.xml          # 用户 SQL 映射
```

---

## 快速开始

### 环境要求
- JDK 21+
- Maven 3.6+
- MySQL 8.0+

### 启动步骤

```bash
# 1. 克隆项目
git clone https://github.com/w020316/order-management.git
cd order-management

# 2. 初始化数据库
mysql -u root -p < sql/init.sql

# 3. 配置数据库连接
# 编辑 src/main/resources/application.yml 设置 MYSQL_PASSWORD

# 4. 编译运行
mvn spring-boot:run

# 5. 访问 API 文档
# http://localhost:8080/swagger-ui/index.html
```

### 运行测试
```bash
mvn test
```

---

## 核心考察点

- **多表关联查询** — MyBatis `<resultMap>` + SQL JOIN 实现 One-to-One 映射
- **Spring 声明式事务管理** — `@Transactional(rollbackFor = Exception.class)` 保证数据一致性
- **复杂业务逻辑实现** — 库存校验、价格计算、原子性扣减
- **并发安全** — 乐观锁策略 `UPDATE ... WHERE stock >= #{quantity}`
- **全局异常处理机制** — `@ControllerAdvice` + `@ExceptionHandler`
- **DTO/VO 分层** — 请求参数校验与响应格式隔离
- **RESTful API 设计** — 标准 HTTP 方法 + 资源路径设计

---

## License

MIT
