USE store;
DROP TABLE IF EXISTS modules;
CREATE TABLE IF NOT EXISTS modules(
   id VARCHAR(32) PRIMARY KEY,
   parentId VARCHAR(32) REFERENCES modules(id),
   name VARCHAR(32),
   url VARCHAR(100),
   descr VARCHAR(100)
);

INSERT INTO modules(id,parentId,name,url,descr) VALUES('System001',NULL,'系统模块','/system','对系统的所有操作及商家的管理模块');
INSERT INTO modules(id,parentId,name,url,descr) VALUES('System011','System001','系统管理模块','/operate','对系统的所有操作模块');
INSERT INTO modules(id,parentId,name,url,descr) VALUES('System021','System001','系统商家模块','/store','对系统商家的管理模块');
INSERT INTO modules(id,parentId,name,url,descr) VALUES('System031','System001','系统删除管理模块','/admin','对系统的其他高权限操作模块(删除操作)');


INSERT INTO modules(id,parentId,name,url,descr) VALUES('Store001',NULL,'商家模块','/store','商家的管理模块');
INSERT INTO modules(id,parentId,name,url,descr) VALUES('Store011','Store001','商家管理模块','/admin','对店铺的其他高权限操作模块(删除订单)');
INSERT INTO modules(id,parentId,name,url,descr) VALUES('Store021','Store001','进货管理模块','/add','对店铺的进货操作模块');
INSERT INTO modules(id,parentId,name,url,descr) VALUES('Store031','Store001','销售管理模块','/sell','对店铺的销售操作模块');
INSERT INTO modules(id,parentId,name,url,descr) VALUES('Store041','Store001','出货管理模块','/clear','对店铺的出货操作模块');
INSERT INTO modules(id,parentId,name,url,descr) VALUES('Store051','Store001','售后管理模块','/afSe','对店铺的售后操作模块');


INSERT INTO modules(id,parentId,name,url,descr) VALUES('User001',NULL,'会员模块','/user','用户中心模块(资料)');
INSERT INTO modules(id,parentId,name,url,descr) VALUES('Buy001',NULL,'购物模块','/buy','安全模块(用户购物车,支付模块)');
INSERT INTO modules(id,parentId,name,url,descr) VALUES('Public001',NULL,'开放模块','/public','开放模块(主页，商品浏览)');