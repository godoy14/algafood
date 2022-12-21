package com.algaworks.algafood.domain.exception;

//@ResponseStatus(value = HttpStatus.NOT_FOUND) // EntidadeNaoEncontradaException ja tem NOT_FOUND mas pode deixar para ficar explicito
public class EstadoNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public EstadoNaoEncontradaException(String mensagem) {
		super(mensagem);
	}

	public EstadoNaoEncontradaException(Long estadoId) {
		this(String.format("Não existe cadastro de Estado com código %d", estadoId));
	}

}
