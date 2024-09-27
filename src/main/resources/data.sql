-- ALTER TABLE Usuario
--     ADD CONSTRAINT unique_email_nombreusuario UNIQUE (email, username);

INSERT IGNORE INTO Usuario(id, email, password, rol, activo, nombre, apellido, username) VALUES(null, 'test@unlam.edu.ar', 'test', 'ADMIN', true, 'nombreTest', 'apellidoTest', 'usernameTest');
INSERT IGNORE INTO Usuario(id, email, password, rol, activo, nombre, apellido, username) VALUES(null, 'cliente@gmail.com', '12345', 'CLIENTE', true, 'Leo', 'Messi', 'Leo Messi');