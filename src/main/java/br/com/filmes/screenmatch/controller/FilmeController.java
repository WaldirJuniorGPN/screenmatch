package br.com.filmes.screenmatch.controller;

import br.com.filmes.screenmatch.dto.DadosCadastroFilme;
import br.com.filmes.screenmatch.model.Filme;
import br.com.filmes.screenmatch.repository.FilmeRepository;
import br.com.filmes.screenmatch.service.FilmeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
@RequestMapping("/filmes")
public class FilmeController {

    @Autowired
    private FilmeRepository repository;

    @Autowired
    private FilmeService service;

    @GetMapping("/formulario")
    public String carregaPaginaFormulario() {
        return "filmes/formulario";
    }

    @GetMapping
    public String carregaPaginaListagem(Model model) {
        return this.service.carregaPaginaListagem(model);
    }

    @PostMapping
    public String cadastraFilme(DadosCadastroFilme dados) {
        return this.service.cadastra(dados);
    }

}
