USE store;
DROP TABLE IF EXISTS rolemodule;
CREATE TABLE IF NOT EXISTS rolemodule(
   role VARCHAR(32),
   module VARCHAR(32),
   CONSTRAINT pk_rolemodule PRIMARY KEY(role,module),
   CONSTRAINT fk_role0 FOREIGN KEY(role) REFERENCES roles(id),
   CONSTRAINT fk_module FOREIGN KEY(module) REFERENCES modules(id)
);

INSERT INTO rolemodule(role,module) VALUES('System001','System001');

INSERT INTO rolemodule(role,module) VALUES('System011','System011');
INSERT INTO rolemodule(role,module) VALUES('System011','System021');
INSERT INTO rolemodule(role,module) VALUES('System011','System031');

INSERT INTO rolemodule(role,module) VALUES('System021','System011');
INSERT INTO rolemodule(role,module) VALUES('System031','System021');


INSERT INTO rolemodule(role,module) VALUES('Store001','Store001');

INSERT INTO rolemodule(role,module) VALUES('Store011','Store011');
INSERT INTO rolemodule(role,module) VALUES('Store011','Store021');
INSERT INTO rolemodule(role,module) VALUES('Store011','Store031');
INSERT INTO rolemodule(role,module) VALUES('Store011','Store041');
INSERT INTO rolemodule(role,module) VALUES('Store011','Store051');

INSERT INTO rolemodule(role,module) VALUES('Store021','Store021');
INSERT INTO rolemodule(role,module) VALUES('Store031','Store031');
INSERT INTO rolemodule(role,module) VALUES('Store041','Store041');
INSERT INTO rolemodule(role,module) VALUES('Store051','Store051');


INSERT INTO rolemodule(role,module) VALUES('User001','User001');
INSERT INTO rolemodule(role,module) VALUES('User001','Buy001');
INSERT INTO rolemodule(role,module) VALUES('Visitor001','Public001');