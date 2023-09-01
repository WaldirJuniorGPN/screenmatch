package br.com.filmes.screenmatch.principal;

import br.com.filmes.screenmatch.model.DadosEpisodios;
import br.com.filmes.screenmatch.model.DadosSerie;
import br.com.filmes.screenmatch.model.Episodio;
import br.com.filmes.screenmatch.service.ConsumoAPI;
import br.com.filmes.screenmatch.service.ConverteDados;
import br.com.filmes.screenmatch.service.IteradorDeTemporadas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;
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

        var lista = iteradorDeTemporadas.listar(dadosSerie, ENDERECO, nomeSerie, API_KEY);
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
                        .map(dadosEpisodios -> new Episodio((t.numeroTemporada()), dadosEpisodios)))
                .collect(Collectors.toList());

//        System.out.println("Digite o trecho do título que deseja");
//        var trechoTitulo = leitura.nextLine();
//
//        Optional<Episodio> episodioBuscado = episodios.stream()
//                .filter(e -> e.getTitulo().toUpperCase().contains(trechoTitulo.toUpperCase()))
//                .findFirst();
//
//        if (episodioBuscado.isPresent()) {
//            System.out.println("Episódio encontrado!");
//            System.out.println(episodioBuscado.get());
//        } else {
//            System.out.println("Episódio não encontrado!");
//        }

//        episodios.stream()
//                .sorted(Comparator.comparing(Episodio::getAvaliacao).reversed())
//                .limit(5)
//                .forEach(System.out::println);
//
//        System.out.println("\nDigite a data para mostrar os episódios a partir dessa data: ");
//        var ano = leitura.nextInt();
//        leitura.nextLine();
//        var dataBusca = LocalDate.of(ano, 1, 1);
//
//        episodios.stream()
//                .filter(e -> e.getDataLancamento() != null && e.getDataLancamento().isAfter(dataBusca))
//                .forEach(System.out::println);

        Map<Integer, Double> avaliacoesPorTemporada = episodios.stream()
                .filter(e -> e.getAvaliacao() > 0.0)
                .collect(Collectors.groupingBy(Episodio::getTemporada, Collectors.averagingDouble(Episodio::getAvaliacao)));

        System.out.println(avaliacoesPorTemporada);

        DoubleSummaryStatistics est = episodios.stream()
                .filter(e-> e.getAvaliacao() > 0.0)
                .collect(Collectors.summarizingDouble(Episodio::getAvaliacao));

        System.out.println(est);
    }
}
