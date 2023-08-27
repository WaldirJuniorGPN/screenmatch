package br.com.filmes.screenmatch;

import br.com.filmes.screenmatch.principal.Principal;
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
	@Autowired
	private Principal principal;

	@Override
	public void run(String... args) throws Exception {

		principal.exibeMenu();

	}
}
