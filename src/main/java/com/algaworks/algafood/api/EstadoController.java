package com.algaworks.algafood.api;

import com.algaworks.algafood.domain.exceptions.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exceptions.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import com.algaworks.algafood.domain.service.CadastroEstadoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/estados")
public class EstadoController {

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private CadastroEstadoService cadastroEstadoService;

    @GetMapping
    public List<Estado> listar() {
        return estadoRepository.findAll();
    }

    @GetMapping("/{estadoId}")
    public ResponseEntity<Estado> buscar(@PathVariable Long estadoId) {
        final Optional<Estado> estado = estadoRepository.findById(estadoId);
        return estado.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Estado cadastrarEstado(@RequestBody Estado estado) {
        return cadastroEstadoService.salvar(estado);
    }

    @PutMapping("/{estadoId}")
    public ResponseEntity<?> atualizarEstado(
            @RequestBody Estado estado,
            @PathVariable Long estadoId
    ) {
        Optional<Estado> estadoExistente = estadoRepository.findById(estadoId);
        if (estadoExistente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        BeanUtils.copyProperties(estado, estadoExistente, "id");
        final Estado estadoAtualizado = cadastroEstadoService.salvar(estadoExistente.get());
        return ResponseEntity.ok(estadoAtualizado);
    }

    @DeleteMapping("/{estadoId}")
    public ResponseEntity<?> excluirEstado(@PathVariable Long estadoId) {
        try {
            cadastroEstadoService.excluirEstado(estadoId);
            return ResponseEntity.noContent().build();
        } catch (EntidadeNaoEncontradaException ex) {
            return ResponseEntity.notFound().build();
        } catch (EntidadeEmUsoException ex) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ex.getMessage());
        }
    }
}
