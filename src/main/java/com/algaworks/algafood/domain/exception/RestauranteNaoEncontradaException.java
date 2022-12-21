package com.algaworks.algafood.domain.exception;

//@ResponseStatus(value = HttpStatus.NOT_FOUND) // EntidadeNaoEncontradaException ja tem NOT_FOUND mas pode deixar para ficar explicito
public class RestauranteNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public RestauranteNaoEncontradaException(String mensagem) {
		super(mensagem);
	}

	public RestauranteNaoEncontradaException(Long restauranteId) {
		this(String.format("Não existe um cadastro de Restaurante com código %d", restauranteId));
	}

}
