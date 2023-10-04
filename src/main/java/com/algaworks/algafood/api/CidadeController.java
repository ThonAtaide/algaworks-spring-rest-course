package com.algaworks.algafood.api;

import com.algaworks.algafood.domain.exceptions.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.CadastroCidadeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private CadastroCidadeService cadastroCidadeService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Cidade> listar() {
        return cidadeRepository.listar();
    }

    @GetMapping("/{cidadeId}")
    public ResponseEntity<Cidade> listar(@PathVariable Long cidadeId) {
        final Cidade cidade = cidadeRepository.buscar(cidadeId);
        if (cidade == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cidade);
    }

    @PostMapping
    public ResponseEntity<?> cadastrarCidade(@RequestBody Cidade cidade) {
        try {
            final Cidade cidadeCriada = cadastroCidadeService.salvar(cidade);
            return ResponseEntity.status(HttpStatus.CREATED).body(cidadeCriada);
        } catch (EntidadeNaoEncontradaException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @PutMapping("/{cidadeId}")
    public ResponseEntity<?> atualizarCidade(
            @RequestBody Cidade cidade,
            @PathVariable Long cidadeId
    ) {
        Cidade cidadeExistente = cidadeRepository.buscar(cidadeId);
        if (cidadeExistente == null) {
            return ResponseEntity.notFound().build();
        }

        try {
            BeanUtils.copyProperties(cidade, cidadeExistente, "id");
            cidadeExistente = cadastroCidadeService.salvar(cidadeExistente);
            return ResponseEntity.ok(cidadeExistente);
        } catch (EntidadeNaoEncontradaException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @DeleteMapping("/{cidadeId}")
    public ResponseEntity<?> removerCidade(
            @PathVariable Long cidadeId
    ) {
        Cidade cidadeExistente = cidadeRepository.buscar(cidadeId);
        if (cidadeExistente == null) {
            return ResponseEntity.notFound().build();
        }
        cadastroCidadeService.excluirCidade(cidadeId);
        return ResponseEntity.noContent().build();
    }
}
