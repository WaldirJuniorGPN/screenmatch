package br.com.filmes.screenmatch.principal;

import br.com.filmes.screenmatch.model.DadosEpisodios;
import br.com.filmes.screenmatch.model.DadosSerie;
import br.com.filmes.screenmatch.model.Episodio;
import br.com.filmes.screenmatch.service.ConsumoAPI;
import br.com.filmes.screenmatch.service.ConverteDados;
import br.com.filmes.screenmatch.service.IteradorDeTemporadas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

@Component
public class Principal {

    @Autowired
    private ConsumoAPI consumoAPI;
    @Autowired
    private ConverteDados conversor;
    @Autowired
    private IteradorDeTemporadas iteradorDeTemporadas;
    private Scanner leitura = new Scanner(System.in);
    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=6585022c";

    public void exibeMenu() {
        System.out.println("Digite o nome da série para a busca: ");
        var nomeSerie = leitura.nextLine();
        var url = ENDERECO + nomeSerie.replace(" ", "+") + API_KEY;
        var json = consumoAPI.obterDados(url);

        var dadosSerie = conversor.obterDados(json, DadosSerie.class);
        System.out.println(dadosSerie);

        var lista = iteradorDeTemporadas.listar(dadosSerie, ENDERECO,nomeSerie, API_KEY);
        lista.forEach(System.out::println);
        lista.forEach(dadosTemporada -> dadosTemporada.episodios().forEach(episodios -> System.out.println(episodios.titulo())));

        List<DadosEpisodios> listaDadosEpisodios = lista
                .stream()
                .flatMap(temporada -> temporada.episodios().stream())
                .collect(Collectors.toList());

        System.out.println("\nTop 5 episódios da Série " + dadosSerie.titulo());
        listaDadosEpisodios
                .stream()
                .filter(episodios -> !episodios.avaliacao().equalsIgnoreCase("N/A"))
                .sorted(Comparator.comparing(DadosEpisodios::avaliacao).reversed())
                .limit(5)
                .forEach(System.out::println);

        List<Episodio> episodios = lista.stream()
                .flatMap(t -> t.episodios().stream()
                        .map(dadosEpisodios -> new Episodio((t.numeroTemporada()),dadosEpisodios)))
                .collect(Collectors.toList());

        episodios.stream()
                .sorted(Comparator.comparing(Episodio::getAvaliacao).reversed())
                .limit(5)
                .forEach(System.out::println);

    }
}
