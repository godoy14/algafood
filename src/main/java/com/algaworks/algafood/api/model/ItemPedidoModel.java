package com.algaworks.algafood.api.model;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemPedidoModel {
	
	@ApiModelProperty(example = "1")
	private Long produtoId;
	
	@ApiModelProperty(example = "Porco com molho agridoce")
	private String produtoNome;
	
	@ApiModelProperty(example = "3")
	private Integer quantidade;
	
	@ApiModelProperty(example = "78.90")
	private BigDecimal precoUnitario;
	
	@ApiModelProperty(example = "222.90")
	private BigDecimal precoTotal;
	
	@ApiModelProperty(example = "Mais cebola, por favor")
	private String observacao;

}
