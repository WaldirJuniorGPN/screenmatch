package br.com.filmes.screenmatch.principal;

import br.com.filmes.screenmatch.model.DadosSerie;
import br.com.filmes.screenmatch.service.ConsumoAPI;
import br.com.filmes.screenmatch.service.ConverteDados;
import br.com.filmes.screenmatch.service.IteradorDeTemporadas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

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
    }
}
