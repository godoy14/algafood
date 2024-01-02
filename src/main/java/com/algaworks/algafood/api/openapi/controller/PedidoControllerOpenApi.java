package com.algaworks.algafood.api.openapi.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.PedidoModel;
import com.algaworks.algafood.api.model.PedidoResumoModel;
import com.algaworks.algafood.api.model.input.PedidoInput;
import com.algaworks.algafood.domain.filter.PedidoFilter;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@Api(tags = "Pedidos")
public interface PedidoControllerOpenApi {
	
	@ApiImplicitParams({
		@ApiImplicitParam(
				value = "Nome das propriedades para filtrar na resposta, separados por vírgula",
				name = "campos",
				paramType = "query",
				type = "string"
				)
	})
	@ApiOperation("Pesquisa os pedidos")
	PagedModel<PedidoResumoModel> pesquisar(
			PedidoFilter filtro,
			Pageable pageable);
	
	
	@ApiImplicitParams({
		@ApiImplicitParam(
				value = "Nome das propriedades para filtrar na resposta, separados por vírgula",
				name = "campos",
				paramType = "query",
				type = "string"
				)
	})
	@ApiOperation("Busca um pedido por código")
    @ApiResponses({
        @ApiResponse(code = 404, message = "Pedido não encontrado", response = Problem.class)
    })
	PedidoModel buscar(String codigoPedido);
	
	@ApiOperation("Registra um pedido")
    @ApiResponses({
        @ApiResponse(code = 201, message = "Pedido registrado"),
    })
	PedidoModel adicionar(PedidoInput pedidoInput);


}
