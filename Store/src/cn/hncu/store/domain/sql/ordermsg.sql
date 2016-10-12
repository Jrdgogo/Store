USE store;
DROP TABLE IF EXISTS ordermsg;
CREATE TABLE IF NOT EXISTS ordermsg(
	id VARCHAR(32) PRIMARY KEY,
	orderId VARCHAR(32),
	goods VARCHAR(32),
	num INT,
	price NUMERIC(10,2),
	CONSTRAINT orderline_fk FOREIGN KEY(orderId) REFERENCES orders(id),
	CONSTRAINT orderline_fk2 FOREIGN KEY(goods) REFERENCES goods(id)
);