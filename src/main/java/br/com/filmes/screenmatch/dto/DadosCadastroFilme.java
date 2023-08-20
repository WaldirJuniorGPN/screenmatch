package br.com.filmes.screenmatch.dto;

public record DadosCadastroFilme(
        String nome,
        Integer duracao,
        Integer ano,
        String genero
) {
}
