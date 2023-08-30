package br.com.filmes.screenmatch.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Entity(name = "Episodio")
@Table(name = "episodios")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
public class Episodio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer temporada;
    private String titulo;
    private Double avaliacao;
    private LocalDate dataLancamento;

    public Episodio(Integer numeroTemporada, DadosEpisodios dados) {
        this.temporada = numeroTemporada;
        this.titulo = dados.titulo();

        try {
            this.avaliacao = Double.valueOf(dados.avaliacao());
        } catch (NumberFormatException ex) {
            this.avaliacao = 0.0;
        }

        try {
            this.dataLancamento = LocalDate.parse(dados.dataLancamento());
        } catch (DateTimeParseException ex) {
            this.dataLancamento = null;
        }

    }

    @Override
    public String toString() {

        var formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        String novaString = "Temporada: " + this.temporada +
                "\nTitulo: " + this.titulo +
                "\nAvaliacao: " + this.avaliacao +
                "\nData de Lançamento: " + this.dataLancamento.format(formatador) + "\n";
        return novaString;
    }
}
