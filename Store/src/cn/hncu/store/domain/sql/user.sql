USE store;
DROP TABLE IF EXISTS users;
CREATE TABLE IF NOT EXISTS users(
    id VARCHAR(32) PRIMARY KEY,
	name VARCHAR(32) UNIQUE NOT NULL,
	password VARCHAR(32) NOT NULL,
	realName VARCHAR(20),
	phone VARCHAR(20) NOT NULL,
	email VARCHAR(30) NOT NULL,
	sex ENUM('man','women'),
    birth VARCHAR(20),
    address VARCHAR(80),
    photo BLOB,
    createTime VARCHAR(20),
    lastLoginIP VARCHAR(20),
    lastLoginTime VARCHAR(20),
    descr VARCHAR(100) 
);

INSERT INTO users(id,name,password,phone,email,descr) VALUES('Admin001','Jrd0302','jrdoneself555','15576254691','1477450172@qq.com','系统默认存在的用户');
