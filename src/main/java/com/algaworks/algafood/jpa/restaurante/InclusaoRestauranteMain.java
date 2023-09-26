package com.algaworks.algafood.jpa.restaurante;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import java.math.BigDecimal;

public class InclusaoRestauranteMain {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
		
		RestauranteRepository restauranteRepository = applicationContext.getBean(RestauranteRepository.class);

		Restaurante restaurante = new Restaurante();
		restaurante.setNome("Pé de Fava");
		restaurante.setTaxaFrete(BigDecimal.valueOf(15.50));

		Restaurante oneReal = new Restaurante();
		oneReal.setNome("1 Real");
		oneReal.setTaxaFrete(BigDecimal.valueOf(10.50));
		
		restaurante = restauranteRepository.salvar(restaurante);
		oneReal = restauranteRepository.salvar(oneReal);
		
		System.out.printf("%d - %s\n", restaurante.getId(), restaurante.getNome());
		System.out.printf("%d - %s\n", oneReal.getId(), oneReal.getNome());
	}
	
}
