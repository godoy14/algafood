package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.CidadeInputDisassembler;
import com.algaworks.algafood.api.assembler.CidadeModelAssembler;
import com.algaworks.algafood.api.model.CidadeModel;
import com.algaworks.algafood.api.model.input.CidadeInput;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.CadastroCidadeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "Cidades")
@RestController
@RequestMapping(value = "/cidades")
public class CidadeController {

	@Autowired
	CidadeRepository cidadeRepository;

	@Autowired
	CadastroCidadeService cadastroCidadeService;

	@Autowired
	private CidadeModelAssembler cidadeModelAssembler;

	@Autowired
	private CidadeInputDisassembler cidadeInputDisassembler;

	@ApiOperation(value = "Lista as cidades")
	@GetMapping
	public List<CidadeModel> listar() {

		List<Cidade> todasCidades = cidadeRepository.findAll();

		return cidadeModelAssembler.toCollectionModel(todasCidades);
	}

	@ApiOperation(value = "Busca uma cidade por ID")
	@GetMapping("/{cidadeId}")
	public CidadeModel buscar(@ApiParam(value = "ID de uma cidade", example = "1") @PathVariable Long cidadeId) {

		Cidade cidade = cadastroCidadeService.buscarOuFalhar(cidadeId);

		return cidadeModelAssembler.toModel(cidade);
	}

//	@PostMapping
//	public ResponseEntity<?> adicionar(@RequestBody Cidade cidade) {
//		
//		try {
//			cidade = cadastroCidadeService.salvar(cidade);
//			return ResponseEntity.status(HttpStatus.CREATED).body(cidade);
//			
//		} catch (EntidadeNaoEncontradaException e) {
//			return ResponseEntity.badRequest().body(e.getMessage());
//			
//		}
//		
//	}

	@ApiOperation(value = "Cadastra uma cidade")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CidadeModel adicionar(@ApiParam(value = "Representação de uma nova cidade", name = "corpo")
			@RequestBody @Valid CidadeInput cidadeInput) {
		try {

			Cidade cidade = cidadeInputDisassembler.toDomainObject(cidadeInput);

			cidade = cadastroCidadeService.salvar(cidade);

			return cidadeModelAssembler.toModel(cidade);
		} catch (EstadoNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

//	@PutMapping("/{cidadeId}")
//	public ResponseEntity<?> atualizar(@PathVariable Long cidadeId,@RequestBody Cidade cidade) {
//		
//		try {
//			Cidade cidadeAtual = cidadeRepository.findById(cidadeId).orElse(null);
//			
//			if (cidadeAtual != null) {
//				BeanUtils.copyProperties(cidade, cidadeAtual, "id");
//				
//				Cidade cidadeSalva = cadastroCidadeService.salvar(cidadeAtual);
//				return ResponseEntity.ok(cidadeSalva);
//				
//			}
//			
//			return ResponseEntity.notFound().build();
//		} catch (EntidadeNaoEncontradaException e) {
//			return ResponseEntity.badRequest().body(e.getMessage());
//		}
//	}

	@ApiOperation(value = "Atualiza uma cidade por ID")
	@PutMapping("/{cidadeId}")
	public CidadeModel atualizar(@ApiParam(value = "ID da cidade", example = "1")@PathVariable Long cidadeId,
			@ApiParam(value = "Representação de uma nova cidade com os novos dados", name = "corpo") @RequestBody @Valid CidadeInput cidadeInput) {

		try {

			Cidade cidadeAtual = cadastroCidadeService.buscarOuFalhar(cidadeId);

			cidadeInputDisassembler.copyToDomainObject(cidadeInput, cidadeAtual);

			cidadeAtual = cadastroCidadeService.salvar(cidadeAtual);

			return cidadeModelAssembler.toModel(cidadeAtual);
		} catch (EstadoNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@ApiOperation(value = "Deleta a cidade pelo ID")
	@DeleteMapping("/{cidadeId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@ApiParam(value = "ID da cidade", example = "1") @PathVariable Long cidadeId) {
		cadastroCidadeService.excluir(cidadeId);
	}

}
