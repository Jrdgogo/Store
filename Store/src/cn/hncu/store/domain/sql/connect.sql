USE store;
DROP TABLE IF EXISTS connect;
CREATE TABLE IF NOT EXISTS connect(
   goods VARCHAR(32) UNIQUE,
   tableName VARCHAR(32) NOT NULL,
   pid VARCHAR(32),
   CONSTRAINT pk_goodsMsg PRIMARY KEY(goods,pid),
   CONSTRAINT fk_goodsid FOREIGN KEY(goods) REFERENCES goods(id)
);

INSERT INTO connect(goods,tableName,pid) VALUES('Goods006','books','B001');
INSERT INTO connect(goods,tableName,pid) VALUES('Goods007','books','B002');
INSERT INTO connect(goods,tableName,pid) VALUES('Goods008','books','B003');
INSERT INTO connect(goods,tableName,pid) VALUES('Goods009','books','B005');
INSERT INTO connect(goods,tableName,pid) VALUES('Goods010','books','B006');
INSERT INTO connect(goods,tableName,pid) VALUES('Goods011','books','B007');
INSERT INTO connect(goods,tableName,pid) VALUES('Goods012','books','B008');
