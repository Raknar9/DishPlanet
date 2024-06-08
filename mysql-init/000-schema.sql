CREATE TABLE Usuario (
                      id_user INT AUTO_INCREMENT PRIMARY KEY,
                      username VARCHAR(255) NOT NULL UNIQUE,
                      email VARCHAR(255) NOT NULL,
                      password VARCHAR(255) NOT NULL
);


CREATE TABLE Plato (
                       id_plato INT PRIMARY KEY AUTO_INCREMENT,
                       nombre VARCHAR(255),
                       descripcion VARCHAR(255),
                       precio DOUBLE,
                       tipo VARCHAR(255),
                       imagen VARCHAR(255),
                       ingredientes TEXT
);
CREATE TABLE menu (
                      id_Menu INT AUTO_INCREMENT PRIMARY KEY,
                      nombre varchar(255),
                      entrante VARCHAR(255),
                      principal VARCHAR(255),
                      postre VARCHAR(255),
                      bebida VARCHAR(255),
                      veces_Pedidas INT,
                      precio DOUBLE,
                      ingredientes text

);


CREATE TABLE Pedido (
                        id_Pedido INT PRIMARY KEY AUTO_INCREMENT,
                        id_plato INT,
                        id_menu INT,
                        nombre_plato VARCHAR(255),
                        precio DOUBLE,
                        FOREIGN KEY (id_plato) REFERENCES Plato(id_plato) ON DELETE CASCADE,
                        FOREIGN KEY (id_menu) REFERENCES Menu(id_Menu) ON DELETE CASCADE
);
CREATE TABLE Detalle_Pedido (
                                id_detalle INT PRIMARY KEY AUTO_INCREMENT,
                                nombres_platos TEXT,
                                precio_total DOUBLE,
                                usuario VARCHAR(255),
                                id_usuario INT,
                                FOREIGN KEY (id_usuario) REFERENCES Usuario(id_user) ON DELETE CASCADE
);

CREATE TABLE Ingredientes_Usados (
                                     id_ingredienteUsado INT PRIMARY KEY AUTO_INCREMENT,
                                     id_plato INT,
                                     id_menu INT,
                                     ingredientes TEXT,
                                     FOREIGN KEY (id_plato) REFERENCES Plato(id_plato) ON DELETE CASCADE,
                                     FOREIGN KEY (id_menu) REFERENCES Menu(id_Menu) ON DELETE CASCADE
);





CREATE TABLE Reserva (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         usuario_id INT,
                         fecha_Hora DATETIME,
                         FOREIGN KEY (usuario_id) REFERENCES Usuario(id_user)
);
CREATE TABLE Inventario (
                            id_inventario INT AUTO_INCREMENT PRIMARY KEY,
                            nombre VARCHAR(255),
                            categoria VARCHAR(255),
                            cantidad INT,
                            unidad_medida VARCHAR(50),
                            precio_unitario DOUBLE
);










