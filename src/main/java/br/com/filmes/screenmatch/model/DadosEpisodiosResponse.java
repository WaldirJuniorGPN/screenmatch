package br.com.filmes.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosEpisodiosResponse(
        @JsonAlias("Title")
        String titulo,
        @JsonAlias("imdbRating")
        String avaliacao
) {
}
