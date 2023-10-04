package com.algaworks.algafood.api;

import com.algaworks.algafood.domain.exceptions.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CadastroRestauranteService cadastroRestauranteService;

    @GetMapping
    public ResponseEntity<List<Restaurante>> listar() {
        final List<Restaurante> restaurantes = restauranteRepository.listar();
        return ResponseEntity.ok(restaurantes);
    }

    @GetMapping("/{restauranteId}")
    public ResponseEntity<Restaurante> buscar(@PathVariable Long restauranteId) {
        final Restaurante restaurante = restauranteRepository.buscar(restauranteId);
        if (restaurante == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(restaurante);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> cadastroRestaurante(@RequestBody Restaurante restaurante) {
        try {
            restaurante = cadastroRestauranteService.salvar(restaurante);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(restaurante);
        } catch (EntidadeNaoEncontradaException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PutMapping("/{restauranteId}")
    public ResponseEntity<?> atualizacaoRestaurante(
            @RequestBody Restaurante restaurante,
            @PathVariable Long restauranteId) {
        Restaurante restauranteExistente = restauranteRepository.buscar(restauranteId);
        if (restauranteExistente == null) {
            return ResponseEntity.notFound().build();
        }
        try {
            BeanUtils.copyProperties(restaurante, restauranteExistente, "id");
            restauranteExistente = cadastroRestauranteService.salvar(restauranteExistente);
            return ResponseEntity.ok(restauranteExistente);
        } catch (EntidadeNaoEncontradaException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PatchMapping("/{restauranteId}")
    public ResponseEntity<?> atualizarParcial(
            @PathVariable Long restauranteId,
            @RequestBody Map<String, Object> campos
    ) {
        Restaurante restauranteAtual = restauranteRepository.buscar(restauranteId);

        if (restauranteAtual == null) {
            return ResponseEntity.notFound().build();
        }

        Restaurante restauranteMergeado = merge(campos, restauranteAtual);

        return atualizacaoRestaurante(restauranteMergeado, restauranteId);
    }

    private Restaurante merge(
            Map<String, Object> dadosOrigem,
            Restaurante restauranteAtual
    ) {
        ObjectMapper objectMapper = new ObjectMapper();
        Restaurante restauranteDestino = objectMapper.convertValue(dadosOrigem, Restaurante.class);

        String[] keys = dadosOrigem.keySet().toArray(new String[]{});
        BeanUtils.copyProperties(restauranteAtual, restauranteDestino, keys);
        return restauranteDestino;
    }
}
