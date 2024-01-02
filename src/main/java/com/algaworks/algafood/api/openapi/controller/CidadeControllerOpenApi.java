package com.algaworks.algafood.api.openapi.controller;

import org.springframework.hateoas.CollectionModel;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.CidadeModel;
import com.algaworks.algafood.api.model.input.CidadeInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Cidades")
public interface CidadeControllerOpenApi {
	
	@ApiOperation(value = "Lista as cidades")
	CollectionModel<CidadeModel> listar();

	
	@ApiOperation(value = "Busca uma cidade por ID")
	@ApiResponses({
		@ApiResponse(code = 400, message = "ID da cidade inválida", response = Problem.class),
		@ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
	})
	CidadeModel buscar(
			@ApiParam(value = "ID de uma cidade", example = "1", required = true) Long cidadeId);


	@ApiOperation(value = "Cadastra uma cidade")
	@ApiResponses({
		@ApiResponse(code = 201, message = "Cidade cadastrada")
	})
	CidadeModel adicionar(@ApiParam(value = "Representação de uma nova cidade", name = "corpo", required = true)
			CidadeInput cidadeInput);


	@ApiOperation(value = "Atualiza uma cidade por ID")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Cidade atualizada"),
		@ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
	})
	CidadeModel atualizar(@ApiParam(value = "ID da cidade", example = "1", required = true) Long cidadeId,
			@ApiParam(value = "Representação de uma nova cidade com os novos dados", name = "corpo", required = true) CidadeInput cidadeInput);

	
	@ApiOperation(value = "Deleta a cidade pelo ID")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Cidade excluida"),
		@ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
	})
	void remover(@ApiParam(value = "ID da cidade", example = "1", required = true) Long cidadeId);

}
