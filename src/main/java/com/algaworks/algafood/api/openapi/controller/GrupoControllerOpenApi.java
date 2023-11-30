package com.algaworks.algafood.api.openapi.controller;

import java.util.List;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.GrupoModel;
import com.algaworks.algafood.api.model.input.GrupoInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Grupos")
public interface GrupoControllerOpenApi {
	
	@ApiOperation(value = "Lista as grupos")
	public List<GrupoModel> listar();
	
	
	@ApiOperation(value = "Busca grupo por id")
	@ApiResponses({
		@ApiResponse(code = 400, message = "ID do grupo inválida", response = Problem.class),
		@ApiResponse(code = 404, message = "Grupo não encontrada", response = Problem.class)
	})
	GrupoModel buscar(@ApiParam(value = "ID de um grupo", example = "1", required = true) Long groupId);
	
	
	@ApiOperation(value = "Cadastrar novo grupo")
	@ApiResponses({
		@ApiResponse(code = 201, message = "Grupo cadastrado")
	})
	GrupoModel adicionar(@ApiParam(name = "corpo", value = "Representação de um novo grupo", required = true) GrupoInput grupoInput);
	
	
	
	@ApiOperation(value = "Atualizar um grupo pelo id")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Grupo atualizado", response = Problem.class),
		@ApiResponse(code = 404, message = "Grupo não encontrada", response = Problem.class)
	})
	GrupoModel atualizar(@ApiParam(value = "ID de um grupo", example = "1", required = true)Long grupoId,
			@ApiParam(name = "corpo", value = "Representação de um novo grupo", required = true) GrupoInput grupoInput);
	
	
	
	@ApiOperation(value = "Deletar o grupo pelo id")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Grupo excluido", response = Problem.class),
		@ApiResponse(code = 404, message = "Grupo não encontrada", response = Problem.class)
	})
	void remover(@ApiParam(value = "ID de um grupo", example = "1", required = true) Long grupoId);

}
