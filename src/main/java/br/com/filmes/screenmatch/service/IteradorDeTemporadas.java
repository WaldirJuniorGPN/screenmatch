package br.com.filmes.screenmatch.service;

import br.com.filmes.screenmatch.model.DadosSerie;
import br.com.filmes.screenmatch.model.DadosTemporada;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class IteradorDeTemporadas {

    private final String TEMPORADA = "&season=";
    @Autowired
    private ConsumoAPI consumoAPI;
    @Autowired
    private ConverteDados conversor;
    private List<DadosTemporada> lista = new ArrayList<>();

    public List<DadosTemporada> listar(DadosSerie dadosSerie, String url1, String url2, String url3) {
        var numeroTemporadas = dadosSerie.totalTemporadas();

        for (int i = 1; i <= numeroTemporadas; i++) {
            var json = consumoAPI.obterDados(url1 + url2.replace(" ", "+") + TEMPORADA + i + url3);
            var dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
            lista.add(dadosTemporada);
        }
        return lista;
    }
}
