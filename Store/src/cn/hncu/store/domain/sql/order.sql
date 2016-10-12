USE store;
DROP TABLE IF EXISTS orders;
CREATE TABLE IF NOT EXISTS orders(
	id VARCHAR(32) PRIMARY KEY,
	user VARCHAR(32),
	consignee VARCHAR(300),
	paytype CHAR(1),
	amount NUMERIC(10,2),
	state CHAR(1),
	orderdate VARCHAR(19),
	CONSTRAINT fk_userorder FOREIGN KEY(user) REFERENCES users(id)
);