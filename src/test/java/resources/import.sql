
-- INSERTAR VALORES PARA LOGIN CON OAHUT2

INSERT INTO usuarios ( username, url_foto, enabled, descripcion_perfil, password) VALUES ( 'admin','mi-perfil-1.png',1,'Sin Descripción', '$2a$10$/6lpxiXbljVPmHSh6QGOae1RNf6SyeXUrZL/NCFslEJ8l4wOLnJoy')
INSERT INTO usuarios ( username, url_foto, enabled, descripcion_perfil, password) VALUES ( 'user','mi-perfil-2.png',1,'Sin Descripción', '$2a$10$/6lpxiXbljVPmHSh6QGOae1RNf6SyeXUrZL/NCFslEJ8l4wOLnJoy')

 INSERT INTO roles (NOMBRE) VALUES('ROLE_ADMIN');
 INSERT INTO roles (NOMBRE) VALUES('ROLE_USER');

 INSERT INTO usuarios_roles VALUES(1,1);
 INSERT INTO usuarios_roles VALUES(2,2);
 
 

 -- INSERTAR VALORES PARA LAS CATEGORIAS
 INSERT INTO categorias (nombre) VALUES ('amigos')
 INSERT INTO categorias (nombre) VALUES ('familia')
 INSERT INTO categorias (nombre) VALUES ('conocido')
 INSERT INTO categorias (nombre) VALUES('mio')
 INSERT INTO categorias (nombre) VALUES('pareja')
 
 
  INSERTAR VALORES PARA LOS SECRETOS
  INSERT INTO secretos (secreto, fecha_creacion, id_categoria) VALUES ('Lorem ipsum es el texto que se usa habitualmente en diseño gráfico','2023-01-01',1)
  INSERT INTO secretos (secreto, fecha_creacion, id_categoria) VALUES ('Lorem ipsum es el texto que se usa habitualmente en diseño gráfico','2023-01-01',2)
  INSERT INTO secretos (secreto, fecha_creacion, id_categoria) VALUES ('Lorem ipsum es el texto que se usa habitualmente en diseño gráfico','2023-01-01',3)
  INSERT INTO secretos (secreto, fecha_creacion, id_categoria) VALUES ('Lorem ipsum es el texto que se usa habitualmente en diseño gráfico','2023-01-01',4)
   INSERT INTO secretos (secreto, fecha_creacion, id_categoria) VALUES ('Lorem ipsum es el texto que se usa habitualmente en diseño gráfico','2023-01-01',5)
   INSERT INTO secretos (secreto, fecha_creacion, id_categoria) VALUES ('Lorem ipsum es el texto que se usa habitualmente en diseño gráfico','2023-01-01',1)
  INSERT INTO secretos (secreto, fecha_creacion, id_categoria) VALUES ('Lorem ipsum es el texto que se usa habitualmente en diseño gráfico','2023-01-01',2)
  INSERT INTO secretos (secreto, fecha_creacion, id_categoria) VALUES ('Lorem ipsum es el texto que se usa habitualmente en diseño gráfico','2023-01-01',3)
  INSERT INTO secretos (secreto, fecha_creacion, id_categoria) VALUES ('Lorem ipsum es el texto que se usa habitualmente en diseño gráfico','2023-01-01',4)
  INSERT INTO secretos (secreto, fecha_creacion, id_categoria) VALUES ('Lorem ipsum es el texto que se usa habitualmente en diseño gráfico','2023-01-01',5)