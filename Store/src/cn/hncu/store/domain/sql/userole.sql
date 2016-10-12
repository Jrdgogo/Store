USE store;
DROP TABLE IF EXISTS userole;
CREATE TABLE IF NOT EXISTS userole(
   user VARCHAR(32),
   role VARCHAR(32),
   CONSTRAINT pk_userole PRIMARY KEY(user,role),
   CONSTRAINT fk_user FOREIGN KEY(user) REFERENCES users(id),
   CONSTRAINT fk_role FOREIGN KEY(role) REFERENCES roles(id)
);
INSERT INTO userole(user,role) VALUES('Admin001','System011');
