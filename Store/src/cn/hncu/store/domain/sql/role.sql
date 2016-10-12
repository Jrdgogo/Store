USE store;
DROP TABLE IF EXISTS roles;
CREATE TABLE IF NOT EXISTS roles(
   id VARCHAR(32) PRIMARY KEY,
   parentId VARCHAR(32) REFERENCES roles(id),
   name VARCHAR(32),
   createTime VARCHAR(20),
   descr VARCHAR(100)
);

INSERT INTO roles(id,name,parentId,descr) VALUES('System001','系统管理员','User001','具备访问系统模块权限');
INSERT INTO roles(id,name,parentId,descr) VALUES('System011','超级管理员','System001','具备所有权限，可进行所有操作');
INSERT INTO roles(id,name,parentId,descr) VALUES('System021','系统操作员','System001','具备系统权限，可进行对系统的管理');
INSERT INTO roles(id,name,parentId,descr) VALUES('System031','店铺管理员','System001','具备系统权限，可进行对注册商家的增加，修改及查询操作');

INSERT INTO roles(id,name,parentId,descr) VALUES('Store001','店铺管理员','User001','具备访问店铺模块权限');
INSERT INTO roles(id,name,parentId,descr) VALUES('Store011','店铺商家','Store001','具备店铺权限，可进行所有店铺的全部操作');
INSERT INTO roles(id,name,parentId,descr) VALUES('Store021','店铺进货员','Store001','具备店铺权限，可进行所属店铺的货物添加操作(进货订单)');
INSERT INTO roles(id,name,parentId,descr) VALUES('Store031','店铺销售员','Store001','具备店铺权限，可进行所属店铺的货物销售操作(打折促销，销售订单)');
INSERT INTO roles(id,name,parentId,descr) VALUES('Store041','店铺出货员','Store001','具备店铺权限，可进行所属店铺的货物出仓操作(快递订单,仓库管理)');
INSERT INTO roles(id,name,parentId,descr) VALUES('Store051','店铺售后员','Store001','具备店铺权限，可进行所属店铺的售后服务操作(售后服务，在线解答)');

INSERT INTO roles(id,name,parentId,descr) VALUES('User001','普通会员','Visitor001','具备会员权限，可进行商品浏览，购买操作');
INSERT INTO roles(id,name,parentId,descr) VALUES('Visitor001','游客',NULL,'只具备商品浏览权限');