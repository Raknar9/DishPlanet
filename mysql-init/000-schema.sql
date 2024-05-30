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



CREATE TABLE Pedido (
                                id_Pedido INT PRIMARY KEY AUTO_INCREMENT,
                                id_plato INT,
                                nombre_plato VARCHAR(255),
                                precio DOUBLE,
                                FOREIGN KEY (id_plato) REFERENCES Plato(id_plato)
);
CREATE TABLE Detalle_Pedido (
                                id_detalle INT PRIMARY KEY AUTO_INCREMENT,
                                nombres_platos TEXT,
                                precio_total DOUBLE,
                                usuario varchar(255),
                                id_usuario INT,
                                FOREIGN KEY ( id_usuario) REFERENCES Usuario(id_user)
);
CREATE TABLE Ingredientes_Usados (
                         id_ingredienteUsado INT PRIMARY KEY AUTO_INCREMENT,
                         id_Plato INT,
                         ingredientes TEXT,
                         FOREIGN KEY ( id_Plato) REFERENCES Plato(id_plato)
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
CREATE TABLE Inventario (
                            id_inventario INT AUTO_INCREMENT PRIMARY KEY,
                            nombre VARCHAR(255),
                            categoria VARCHAR(255),
                            cantidad INT,
                            unidad_medida VARCHAR(50),
                            precio_unitario DOUBLE
);








