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
import java.util.Optional;

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
        return cidadeRepository.findAll();
    }

    @GetMapping("/{cidadeId}")
    public ResponseEntity<Cidade> listar(@PathVariable Long cidadeId) {
        final Optional<Cidade> cidade = cidadeRepository.findById(cidadeId);
        return cidade
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
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
        Optional<Cidade> cidadeExistente = cidadeRepository.findById(cidadeId);
        if (cidadeExistente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        try {
            BeanUtils.copyProperties(cidade, cidadeExistente.get(), "id");
            final Cidade cidadeAtualizada = cadastroCidadeService.salvar(cidadeExistente.get());
            return ResponseEntity.ok(cidadeAtualizada);
        } catch (EntidadeNaoEncontradaException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @DeleteMapping("/{cidadeId}")
    public ResponseEntity<?> removerCidade(
            @PathVariable Long cidadeId
    ) {
        Optional<Cidade> cidadeExistente = cidadeRepository.findById(cidadeId);
        if (cidadeExistente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        cadastroCidadeService.excluirCidade(cidadeId);
        return ResponseEntity.noContent().build();
    }
}
