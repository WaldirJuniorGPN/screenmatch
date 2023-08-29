CREATE TABLE episodios (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    temporada INTEGER,
    titulo VARCHAR(255),
    avaliacao DOUBLE,
    data_lancamento DATE
);
