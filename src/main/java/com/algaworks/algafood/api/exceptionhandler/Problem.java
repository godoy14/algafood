package com.algaworks.algafood.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@ApiModel("Problema")
@JsonInclude(Include.NON_NULL)
@Getter
@Builder
public class Problem {

	@ApiModelProperty(example = "400", position = 1)
	private Integer status;
	
	@ApiModelProperty(position = 10)
	private String type;
	
	@ApiModelProperty(position = 20)
	private String title;
	
	@ApiModelProperty(position = 30)
	private String detail;

	@ApiModelProperty(position = 40)
	private String userMessage;
	
	@ApiModelProperty(position = 50)
	private OffsetDateTime timestamp;

	@ApiModelProperty(value = "Lista de objetos ou campos que geraram o erro (opcional)",
			position = 100)
	private List<Object> objects;

	@ApiModel("ObjetoProblema")
	@Getter
	@Builder
	public static class Object {

		@ApiModelProperty(example = "preco")
		private String name;
		@ApiModelProperty(example = "o preco e obrigatorio")
		private String userMessage;
	}

}
