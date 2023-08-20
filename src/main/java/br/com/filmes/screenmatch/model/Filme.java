package br.com.filmes.screenmatch.model;

import br.com.filmes.screenmatch.dto.DadosCadastroFilme;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "filmes")
@Entity(name = "Filme")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
public class Filme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private Integer duracao;
    private Integer ano;
    private String genero;

    public Filme(DadosCadastroFilme dados) {
        this.nome = dados.nome();
        this.duracao = dados.duracao();
        this.ano = dados.ano();
        this.genero = dados.genero();
    }
}
