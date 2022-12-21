package com.algaworks.algafood.domain.exception;

// todas as ResponseStatus podem ser retiradas pois estao sendo tratadas
// a partir do ExceptionHandler global
//@ResponseStatus(value = HttpStatus.NOT_FOUND) // EntidadeNaoEncontradaException ja tem NOT_FOUND mas pode deixar para ficar explicito
public class CozinhaNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public CozinhaNaoEncontradaException(String mensagem) {
		super(mensagem);
	}

	public CozinhaNaoEncontradaException(Long cozinhaId) {
		this(String.format("Não existe cadastro de Cozinha com código %d", cozinhaId));
	}

}
