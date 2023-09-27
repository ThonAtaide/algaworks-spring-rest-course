package com.algaworks.algafood.infrastructure.repository;

import com.algaworks.algafood.domain.model.FormaDePagamento;
import com.algaworks.algafood.domain.repository.FormaDePagamentoRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
public class FormaDePagamentoRepositoryImpl implements FormaDePagamentoRepository {

    @PersistenceContext
    private EntityManager manager;


    @Override
    public List<FormaDePagamento> listar() {
        return manager.createQuery("from FormaDePagamento", FormaDePagamento.class)
                .getResultList();
    }

    @Override
    public FormaDePagamento buscar(Long id) {
        return manager.find(FormaDePagamento.class, id);
    }

    @Override
    @Transactional
    public FormaDePagamento salvar(FormaDePagamento formaDePagamento) {
        return manager.merge(formaDePagamento);
    }

    @Override
    @Transactional
    public void remover(FormaDePagamento formaDePagamento) {
        final FormaDePagamento aux = buscar(formaDePagamento.getId());
        manager.remove(aux);
    }
}
