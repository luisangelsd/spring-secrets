
-- INSERTAR VALORES PARA LOGIN CON OAHUT2

INSERT INTO usuarios ( username, url_foto, enabled, descripcion_perfil, password) VALUES ( 'admin','8223-A6509768.JPG',1,'Sin Descripción', '$2a$10$/6lpxiXbljVPmHSh6QGOae1RNf6SyeXUrZL/NCFslEJ8l4wOLnJoy')
INSERT INTO usuarios ( username, url_foto, enabled, descripcion_perfil, password) VALUES ( 'user','474-A6509768.JPG',1,'Sin Descripción', '$2a$10$/6lpxiXbljVPmHSh6QGOae1RNf6SyeXUrZL/NCFslEJ8l4wOLnJoy')

 INSERT INTO roles (NOMBRE) VALUES('ROLE_ADMIN');
 INSERT INTO roles (NOMBRE) VALUES('ROLE_USER');

 INSERT INTO usuarios_roles VALUES(1,1);
 INSERT INTO usuarios_roles VALUES(2,2);
