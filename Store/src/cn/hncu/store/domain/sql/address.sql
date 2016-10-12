USE store;
DROP TABLE IF EXISTS addresses;
CREATE TABLE IF NOT EXISTS addresses(
   id VARCHAR(32) PRIMARY KEY,
   zipCode VARCHAR(10),
   address VARCHAR(100),
   user VARCHAR(32),
   CONSTRAINT fk_useraddr FOREIGN KEY(user) REFERENCES users(id)
);