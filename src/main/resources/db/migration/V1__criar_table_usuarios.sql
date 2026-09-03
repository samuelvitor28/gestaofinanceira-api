CREATE TABLE usuarios(
    id BINARY(16) NOT NULL,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL,
    senha VARCHAR(255),
    criado_em DATETIME NOT NULL,

    PRIMARY KEY (id),
    UNIQUE (email),
    UNIQUE (username)
);