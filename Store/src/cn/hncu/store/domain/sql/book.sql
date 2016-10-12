USE store;
DROP TABLE IF EXISTS books;
CREATE TABLE IF NOT EXISTS books(
	id VARCHAR(32) PRIMARY KEY,
	publisher VARCHAR(100),
	publishdate VARCHAR(19),
	pages INT,
	size INT,
	printtimes INT,
	versions INT,
	brief TEXT,
	content TEXT
);

INSERT INTO books(id,publisher,publishdate,pages,size,printtimes,versions,brief,content) VALUES('B001','电子工业出版社','2012-01-09',344,16,1,1,'计算机基础教学','第一章：计算机入门<br/>第二章：计算机管理');
INSERT INTO books(id,publisher,publishdate,pages,size,printtimes,versions,brief,content) VALUES('B002','机械工业出版社','2011-10-09',344,16,1,1,'一本关于政治的图书','第一章：计算机入门<br/>第二章：计算机管理');
INSERT INTO books(id,publisher,publishdate,pages,size,printtimes,versions,brief,content) VALUES('B003','机械工业出版社','2011-01-09',344,16,1,1,'小学语言学','第一章：计算机入门<br/>第二章：计算机管理');
INSERT INTO books(id,publisher,publishdate,pages,size,printtimes,versions,brief,content) VALUES('B005','机械工业出版社','2011-02-09',344,16,1,1,'人海战术','第一章：计算机入门<br/>第二章：计算机管理');
INSERT INTO books(id,publisher,publishdate,pages,size,printtimes,versions,brief,content) VALUES('B006','电子工业出版社','2013-01-29',344,16,1,1,'网络的配置说明','第一章：计算机入门<br/>第二章：计算机管理');
INSERT INTO books(id,publisher,publishdate,pages,size,printtimes,versions,brief,content) VALUES('B007','清华大学出版社','2011-03-09',344,16,1,1,'大家都说说这是为什么','第一章：计算机入门<br/>第二章：计算机管理');
INSERT INTO books(id,publisher,publishdate,pages,size,printtimes,versions,brief,content) VALUES('B008','清华大学出版社','2011-01-09',344,16,1,1,'海关总说明书','第一章：计算机入门<br/>第二章：计算机管理');
