package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.FormaDePagamento;

import java.util.List;

public interface FormaDePagamentoRepository {

	List<FormaDePagamento> listar();
	FormaDePagamento buscar(Long id);
	FormaDePagamento salvar(FormaDePagamento formaDePagamento);
	void remover(FormaDePagamento formaDePagamento);
	
}
