package com.algaworks.algafood.api;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/teste")
public class TesteController {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @GetMapping("/cozinhas/por-nome")
    public List<Cozinha> buscaCozinhasPorNome(String nome) {
        return cozinhaRepository.findTodasByNomeContaining(nome);
    }

    @GetMapping("/cozinhas/top-por-nome")
    public List<Cozinha> buscaCozinhasPorNomeComTop(String nome) {
        return cozinhaRepository.findTop2ByNomeContaining(nome);
    }

    @GetMapping("/cozinhas/unica-por-nome")
    public Optional<Cozinha> cozinhaPorNome(String nome) {
        return cozinhaRepository.findFirstCozinhaByNome(nome);
    }

    @GetMapping("/restaurantes/por-nome-e-id-cozinha")
    public List<Restaurante> buscaRestaurantesPorNomeECozinhaId(String nome, Long cozinhaId) {
        return restauranteRepository.buscaPorNomeECozinha(nome, cozinhaId);
    }

    @GetMapping("/restaurantes/por-taxa-frete")
    public List<Restaurante> buscaCozinhasPorNomeComTop(BigDecimal taxaInicial, BigDecimal taxaFinal) {
        return restauranteRepository.findRestauranteByTaxaFreteBetween(taxaInicial, taxaFinal);
    }
}