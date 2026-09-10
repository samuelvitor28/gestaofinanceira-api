CREATE TABLE contas(
    id BINARY (16) NOT NULL,
    nome VARCHAR(50) NOT NULL,
    usuario_id BINARY(16) NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

CREATE TABLE transacoes(
    id BINARY(16) NOT NULL,
    descricao VARCHAR(100) NOT NULL,
    valor DOUBLE NOT NULL,
    categoria VARCHAR(20) NOT NULL,
    tipo VARCHAR(10) NOT NULL,
    data DATE NOT NULL,
    conta_id BINARY(16) NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (conta_id) REFERENCES contas(id)
);
