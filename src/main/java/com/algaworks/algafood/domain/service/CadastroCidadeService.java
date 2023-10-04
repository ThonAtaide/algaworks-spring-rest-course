package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exceptions.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CadastroCidadeService {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    public Cidade salvar(Cidade cidade) {
        Long estadoId = cidade.getEstado().getId();
        Estado estado = estadoRepository.buscar(estadoId);
        if (estado == null) {
            throw new EntidadeNaoEncontradaException(
                    String.format("Não existe um cadastro de estado com o id %d", estadoId)
            );
        }
        cidade.setEstado(estado);
        return cidadeRepository.salvar(cidade);
    }

    public void excluirCidade(Long cidadeId) {
        final Cidade cidade = cidadeRepository.buscar(cidadeId);
        if (cidade == null) {
            throw new EntidadeNaoEncontradaException(
                    String.format("Não existe um cadastro de cidade com o id %d.", cidadeId)
            );
        }
        cidadeRepository.remover(cidade);
    }
}
