package br.com.filmes.screenmatch.service;

import br.com.filmes.screenmatch.dto.DadosCadastroFilme;
import br.com.filmes.screenmatch.model.Filme;
import br.com.filmes.screenmatch.repository.FilmeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class FilmeService {

    @Autowired
    private FilmeRepository repository;

    public String carregaPaginaListagem(Model model) {
        var listaDeFilmes = repository.findAll();
        model.addAttribute("lista", listaDeFilmes);
        return "filmes/listagem";
    }

    public String cadastra(DadosCadastroFilme dados) {
        var filme = new Filme(dados);
        repository.save(filme);
        return "redirect:/filmes";
    }

}
