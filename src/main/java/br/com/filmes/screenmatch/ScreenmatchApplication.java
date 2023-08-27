package br.com.filmes.screenmatch;

import br.com.filmes.screenmatch.model.DadosEpisodios;
import br.com.filmes.screenmatch.model.DadosSerie;
import br.com.filmes.screenmatch.service.ConsumoAPI;
import br.com.filmes.screenmatch.service.ConverteDados;
import br.com.filmes.screenmatch.service.IteradorDeTemporadas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}
	@Autowired
	private ConverteDados conversor;
	@Autowired
	private IteradorDeTemporadas iteradorDeTemporadas;

	@Override
	public void run(String... args) throws Exception {
		var consumoAPI = new ConsumoAPI();
		var json = consumoAPI.obterDados("https://www.omdbapi.com/?t=gilmore+girls&apikey=6585022c");
		System.out.println(json);

		var dadosSerie = conversor.obterDados(json, DadosSerie.class);
		System.out.println(dadosSerie);

		System.out.println();

		json = consumoAPI.obterDados("https://www.omdbapi.com/?t=gilmore+girls&season=1&episode=2&apikey=6585022c");
		var dadosEpisodio = conversor.obterDados(json, DadosEpisodios.class);
		System.out.println(dadosEpisodio);

		System.out.println();

		var listaDeTemporadas = iteradorDeTemporadas.listar(dadosSerie);
		listaDeTemporadas.forEach(System.out::println);
	}
}
