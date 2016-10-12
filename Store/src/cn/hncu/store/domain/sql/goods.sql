USE store;
DROP TABLE IF EXISTS goods;
CREATE TABLE IF NOT EXISTS goods(
    id VARCHAR(32) PRIMARY KEY,
	name VARCHAR(32) NOT NULL,
	selltype VARCHAR(32) NOT NULL,
	brand VARCHAR(32),
	price DOUBLE(8,2) UNSIGNED,
	rebate FLOAT(3,2) UNSIGNED,
    photo VARCHAR(100),
    stock INT(11) UNSIGNED,
    onlineTime VARCHAR(20),
    descr VARCHAR(100),
    CONSTRAINT fk_selltype FOREIGN KEY(selltype) REFERENCES selltypes(id),
    CONSTRAINT fk_brand FOREIGN KEY(brand) REFERENCES brands(id) 
);

INSERT INTO goods(id,name,selltype,brand,price,rebate,stock,descr) VALUES('Goods001','TG80-1229EDS','Type011','Brand001',1998.00,1.00,1000,'8公斤变频滚筒洗衣机 大容量 ');
INSERT INTO goods(id,name,selltype,brand,price,rebate,stock,descr) VALUES('Goods002','BCD-458WDVMU1','Type021','Brand002',5599.00,1.00,1000,'458升变频风冷无霜多门冰箱 干湿分储五区保鲜 (手机APP控制)');
INSERT INTO goods(id,name,selltype,brand,price,rebate,stock,descr) VALUES('Goods003','Apple MacBook Air','Type022','Brand004',6988.00,1.00,1000,'13.3英寸笔记本电脑 银色(Core i5 处理器/8GB内存/128GB SSD闪存 MMGF2CH)');
INSERT INTO goods(id,name,selltype,brand,price,rebate,stock,descr) VALUES('Goods004','小米5','Type012','Brand006',1799.00,1.00,1000,'全网通 标准版 3GB内存 32GB ROM 白色 移动联通电信4G手机');
INSERT INTO goods(id,name,selltype,brand,price,rebate,stock,descr) VALUES('Goods005','七匹狼 T恤','Type013','Brand008',55.00,1.00,1000,'男 短袖纯棉V领打底衫 纯色透气汗衫 经典黑 XL(175100)');

INSERT INTO goods(id,name,selltype,brand,price,rebate,stock) VALUES('Goods006','计算机基础','Type004',NULL,32.00,1.00,1000);
INSERT INTO goods(id,name,selltype,brand,price,rebate,stock) VALUES('Goods007','管理学基础','Type004',NULL,42.00,0.71,1000);
INSERT INTO goods(id,name,selltype,brand,price,rebate,stock) VALUES('Goods008','大家风范','Type004',NULL,42.00,0.66,1000);
INSERT INTO goods(id,name,selltype,brand,price,rebate,stock) VALUES('Goods009','Java基础','Type004',NULL,39.00,0.90,1000);
INSERT INTO goods(id,name,selltype,brand,price,rebate,stock) VALUES('Goods010','数据库入门','Type004',NULL,24.00,1.00,1000);
INSERT INTO goods(id,name,selltype,brand,price,rebate,stock) VALUES('Goods011','SqlServer','Type004',NULL,19.00,1.00,1000);
INSERT INTO goods(id,name,selltype,brand,price,rebate,stock) VALUES('Goods012','JavaEE','Type004',NULL,34.00,0.80,1000);



