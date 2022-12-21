package com.algaworks.algafood.api.model;

import java.math.BigDecimal;

import com.algaworks.algafood.api.model.view.RestauranteView;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RestauranteModel {

	@JsonView( { RestauranteView.Resumo.class, RestauranteView.ApenasNomes.class} )
	private Long id;
	
	@JsonView( { RestauranteView.Resumo.class, RestauranteView.ApenasNomes.class} )
	private String nome;
	
	@JsonView(RestauranteView.Resumo.class)
	private BigDecimal precoFrete;
	
	@JsonView(RestauranteView.Resumo.class)
	private CozinhaModel cozinha;
	private Boolean ativo;
	private EnderecoModel endereco;
	private Boolean aberto;

}
