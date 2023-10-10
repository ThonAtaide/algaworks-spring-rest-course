package com.algaworks.algafood.infrastructure.repository;

import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepositoryQueries;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries {

    @PersistenceContext
    private EntityManager entityManager;

//    @Override
//    public List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {
//        var jpql = new StringBuilder();
//        jpql.append("from Restaurante where 0 = 0 ");
//
//        var parametros = new HashMap<String, Object>();
//
//        if (StringUtils.hasLength(nome)) {
//            jpql.append("and nome like :nome ");
//            parametros.put("nome", "%" + nome + "%");
//        }
//
//        if (taxaFreteInicial != null) {
//            jpql.append("and taxaFrete >= :taxaInicial ");
//            parametros.put("taxaInicial", taxaFreteInicial);
//        }
//
//        if (taxaFreteFinal != null) {
//            jpql.append("and taxaFrete <= :taxaFinal ");
//            parametros.put("taxaFinal", taxaFreteFinal);
//        }
//
//        TypedQuery<Restaurante> query = entityManager
//                .createQuery(jpql.toString(), Restaurante.class);
//
//        parametros.forEach(query::setParameter);
//
//        return query.getResultList();
//    }

    @Override
    public List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Restaurante> criteria = criteriaBuilder.createQuery(Restaurante.class);
        Root<Restaurante> root = criteria.from(Restaurante.class);

        final List<Predicate> predicates = new ArrayList<>();

        if (StringUtils.hasText(nome)) {
            predicates.add(criteriaBuilder.like(root.get("nome"), "%" + nome + "%"));
        }

        if (taxaFreteInicial != null) {
            predicates.add(criteriaBuilder
                    .greaterThanOrEqualTo(root.get("taxaFrete"), taxaFreteInicial));
        }

        if (taxaFreteFinal != null) {
            predicates.add(criteriaBuilder
                    .lessThanOrEqualTo(root.get("taxaFrete"), taxaFreteFinal));
        }

        criteria.where(predicates.toArray(new Predicate[0]));
        return entityManager.createQuery(criteria).getResultList();
    }
}
