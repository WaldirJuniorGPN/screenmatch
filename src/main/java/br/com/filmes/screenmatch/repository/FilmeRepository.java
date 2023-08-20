package br.com.filmes.screenmatch.repository;

import br.com.filmes.screenmatch.model.Filme;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmeRepository extends JpaRepository<Filme, Long> {
}
