CREATE DATABASE carbon_credits;

USE carbon_credits;

DROP TABLE IF EXISTS rewards;
DROP TABLE IF EXISTS travel_records;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS companies;
DROP TABLE IF EXISTS company_types;

CREATE TABLE users (
  user_id INT PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(50) NOT NULL,
  password VARCHAR(50) NOT NULL,
  age INT NOT NULL,
  gender VARCHAR(10),
  image VARCHAR(1000) NOT NULL,
  email VARCHAR(100),
  credit_amount INT NOT NULL,
  exp INT NOT NULL
);
CREATE TABLE company_types (
  type_id INT PRIMARY KEY AUTO_INCREMENT,
  type_name VARCHAR(50) NOT NULL,
  type_photo VARCHAR(1000),
  distance_rate FLOAT NOT NULL,  #碳排放量与距离的转换比例(emission=rate*distance)
  credit_rate FLOAT NOT NULL  #积分与碳排放量的转换比例(credit=rate*emission)
);
CREATE TABLE companies (
  company_id INT PRIMARY KEY AUTO_INCREMENT,
  company_name VARCHAR(100) NOT NULL,
  password VARCHAR(50) NOT NULL,
  contact_phone VARCHAR(20) NOT NULL,
  contact_email VARCHAR(100) NOT NULL,
  type_id INT NOT NULL,
  total_emission INT NOT NULL,
  carbon_cost FLOAT NOT NULL,  #购买一单位碳排放量，需要多少钱
  FOREIGN KEY (type_id) REFERENCES company_types(type_id)
);
CREATE TABLE travel_records (
  record_id INT PRIMARY KEY AUTO_INCREMENT,
  user_id INT NOT NULL,
  company_id INT NOT NULL,
  duration INT NOT NULL,
  travel_distance INT NOT NULL,
  credits INT NOT NULL,
  FOREIGN KEY (user_id) REFERENCES users(user_id),
  FOREIGN KEY (company_id) REFERENCES companies(company_id)
);
CREATE TABLE rewards (
    reward_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    image VARCHAR(1000) NOT NULL,
    require_credits INT NOT NULL,
    require_level INT NOT NULL,
    inventory INT NOT NULL
);

INSERT INTO users VALUES (null, 'czm', '123456', 20, 'male', 'https://img2.woyaogexing.com/2018/07/15/4f9e38fa399a487087af709fcce57132!400x400.jpeg', 'c@qq.com', 8000, 0);

INSERT INTO company_types VALUES (1, 'authentication', null, 1, 1);
INSERT INTO company_types VALUES (2, 'subway', 'https://img2.baidu.com/it/u=1901963787,2901418076&fm=253&fmt=auto&app=138&f=JPEG?w=631&h=500', 0.5, 0.5);
INSERT INTO company_types VALUES (3, 'bus', 'https://img2.baidu.com/it/u=1688114310,4146070461&fm=253&fmt=auto&app=138&f=JPEG?w=1046&h=500', 0.5, 0.5);
INSERT INTO company_types VALUES (4, 'bike', 'https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fsafe-img.xhscdn.com%2Fbw1%2F697733b2-20b0-4361-99b7-7947cf00f576%3FimageView2%2F2%2Fw%2F1080%2Fformat%2Fjpg&refer=http%3A%2F%2Fsafe-img.xhscdn.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1686923293&t=8a5c0ced41f2c86cea4adaf00e2e0f95', 0.5, 0.5);

INSERT INTO companies VALUES (null, 'root', 'root', '10000', 'auth@qq.com', 1, 10000, 0);
INSERT INTO companies VALUES (null, 'subway_A', '123456', '12345', 'A@qq.com', 2, 5000, 50);
INSERT INTO companies VALUES (null, 'bus_B', '123456', '12345', 'A@qq.com', 3, 5000, 50);
INSERT INTO companies VALUES (null, 'bike_C', '123456', '12345', 'A@qq.com', 4, 5000, 50);

INSERT INTO travel_records VALUES (null, 1, 2, 100, 100, 25);
INSERT INTO travel_records VALUES (null, 1, 2, 200, 200, 25);
INSERT INTO travel_records VALUES (null, 1, 2, 300, 300, 25);
INSERT INTO travel_records VALUES (null, 1, 2, 400, 230, 25);
INSERT INTO travel_records VALUES (null, 1, 2, 230, 210, 25);
INSERT INTO travel_records VALUES (null, 1, 2, 560, 450, 25);
INSERT INTO travel_records VALUES (null, 1, 2, 220, 240, 25);
INSERT INTO travel_records VALUES (null, 1, 2, 120, 170, 25);
INSERT INTO travel_records VALUES (null, 1, 2, 120, 170, 25);
INSERT INTO travel_records VALUES (null, 1, 3, 300, 40, 25);
INSERT INTO travel_records VALUES (null, 1, 3, 260, 200, 25);
INSERT INTO travel_records VALUES (null, 1, 4, 124, 100, 25);
INSERT INTO travel_records VALUES (null, 1, 4, 353, 300, 25);
INSERT INTO travel_records VALUES (null, 1, 4, 290, 300, 25);
INSERT INTO travel_records VALUES (null, 1, 4, 400, 340, 25);

INSERT INTO rewards VALUES (null, '太阳能手表', 'http://mms1.baidu.com/it/u=527798579,3457219233&fm=253&app=138&f=JPEG?w=240&h=240', 30000, 1, 30);
INSERT INTO rewards VALUES (null, '太阳能充电宝', 'http://mms2.baidu.com/it/u=3568496005,2711617658&fm=253&app=138&f=JPEG?w=500&h=500', 10000, 1, 30);
INSERT INTO rewards VALUES (null, 'LED节能台灯', 'http://mms2.baidu.com/it/u=1132036,3306762155&fm=253&app=138&f=JPEG&fmt=auto&q=75&fmt=auto&q=75?w=735&h=500', 5000, 1, 30);
INSERT INTO rewards VALUES (null, '可降解咖啡杯', 'http://mms2.baidu.com/it/u=319719837,1642742569&fm=253&app=120&f=JPEG&fmt=auto&q=75&fmt=auto&q=75?w=500&h=500', 1000, 1, 30);
INSERT INTO rewards VALUES (null, '环境科普书籍', 'http://mms1.baidu.com/it/u=3675725808,3089667496&fm=253&app=138&f=JPEG&fmt=auto&q=75&fmt=auto&q=75?w=280&h=280', 2000, 1, 30);
INSERT INTO rewards VALUES (null, '健康亲肤抽纸', 'http://mms1.baidu.com/it/u=962762254,4115534595&fm=253&app=138&f=JPEG&fmt=auto&q=75&fmt=auto&q=75?w=400&h=400', 200, 1, 30);
