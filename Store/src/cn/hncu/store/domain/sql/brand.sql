USE store;
DROP TABLE IF EXISTS brands;
CREATE TABLE IF NOT EXISTS brands(
   id VARCHAR(32) PRIMARY KEY,
   name VARCHAR(32),
   photo BLOB,
   createTime VARCHAR(20)
);

INSERT INTO brands(id,name) VALUES('Brand001','小天鹅(LittleSwan)');
INSERT INTO brands(id,name) VALUES('Brand002','海尔(Haier)');
INSERT INTO brands(id,name) VALUES('Brand003','美的(Midea)');
INSERT INTO brands(id,name) VALUES('Brand004','Apple');
INSERT INTO brands(id,name) VALUES('Brand005','华为(HUAWEI)');
INSERT INTO brands(id,name) VALUES('Brand006','小米(MI)');
INSERT INTO brands(id,name) VALUES('Brand007','花花公子(PLAYBOY)');
INSERT INTO brands(id,name) VALUES('Brand008','七匹狼(SEPTWOLVES)');


