USE store;
DROP TABLE IF EXISTS selltypes;
CREATE TABLE IF NOT EXISTS selltypes(
   id VARCHAR(32) PRIMARY KEY,
   parentId VARCHAR(32) REFERENCES selltypes(id),
   name VARCHAR(32),
   createTime VARCHAR(20),
   descr VARCHAR(100)
);

INSERT INTO selltypes(id,name,parentId,descr) VALUES('Type001','家用电器',NULL,'日常家用生活电器');
INSERT INTO selltypes(id,name,parentId,descr) VALUES('Type011','洗衣机','Type001','生活电器');
INSERT INTO selltypes(id,name,parentId,descr) VALUES('Type021','冰箱','Type001','生活电器');
INSERT INTO selltypes(id,name,parentId,descr) VALUES('Type031','电视','Type001','生活电器');

INSERT INTO selltypes(id,name,parentId,descr) VALUES('Type002','数码电器',NULL,'移动娱乐办公类科技电器');
INSERT INTO selltypes(id,name,parentId,descr) VALUES('Type012','手机','Type002','数码电器');
INSERT INTO selltypes(id,name,parentId,descr) VALUES('Type022','电脑','Type002','数码电器');
INSERT INTO selltypes(id,name,parentId,descr) VALUES('Type032','照相机','Type002','数码电器');

INSERT INTO selltypes(id,name,parentId,descr) VALUES('Type003','衣物',NULL,'衣物产品');
INSERT INTO selltypes(id,name,parentId,descr) VALUES('Type013','T恤','Type003','衣物产品');
INSERT INTO selltypes(id,name,parentId,descr) VALUES('Type023','牛仔裤','Type003','衣物产品');

INSERT INTO selltypes(id,name,parentId,descr) VALUES('Type004','图书',NULL,'书籍产品');
