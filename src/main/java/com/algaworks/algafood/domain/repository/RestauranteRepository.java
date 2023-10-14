package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface RestauranteRepository extends
        CustomJpaRepository<Restaurante, Long>,
        RestauranteRepositoryQueries,
        JpaSpecificationExecutor<Restaurante>
{

    List<Restaurante> findRestauranteByTaxaFreteBetween(BigDecimal taxaInicial, BigDecimal taxaFinal);

//    @Query("from Restaurante where nome like %:nome% and cozinha.id = :id")
    List<Restaurante> buscaPorNomeECozinha(String nome, @Param("id") Long cozinha);

    List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal);


}
