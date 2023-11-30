package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.RestauranteInputDisassembler;
import com.algaworks.algafood.api.assembler.RestauranteModelAssembler;
import com.algaworks.algafood.api.model.RestauranteModel;
import com.algaworks.algafood.api.model.input.RestauranteInput;
import com.algaworks.algafood.api.model.view.RestauranteView;
import com.algaworks.algafood.api.openapi.controller.RestauranteControllerOpenApi;
import com.algaworks.algafood.domain.exception.CidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradaException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import com.fasterxml.jackson.annotation.JsonView;

@RestController
@RequestMapping(path = "/restaurantes", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteController implements RestauranteControllerOpenApi {

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private CadastroRestauranteService cadastroRestauranteService;

	@Autowired
	private RestauranteModelAssembler restauranteModelAssembler;

	@Autowired
	private RestauranteInputDisassembler restauranteInputDisassembler;
	
	
	@JsonView(RestauranteView.Resumo.class)
	@GetMapping
	public List<RestauranteModel> listar() {
		return restauranteModelAssembler.toCollectionModel(restauranteRepository.findAll());
	}
	
	
	
	@JsonView(RestauranteView.ApenasNomes.class)
	@GetMapping(params = "projecao=apenas-nome")
	public List<RestauranteModel> listarResumido() {
		return listar();
	}
	

//	@Autowired
//	private SmartValidator validator;
	
//	@GetMapping
//	public MappingJacksonValue listar(@RequestParam(required = false) String projecao) {
//		List<Restaurante> restaurantes = restauranteRepository.findAll();
//		List<RestauranteModel> restaurantesModel = restauranteModelAssembler.toCollectionModel(restaurantes);
//		
//		MappingJacksonValue restauranteWrapper = new MappingJacksonValue(restaurantesModel);
//		
//		restauranteWrapper.setSerializationView(RestauranteView.Resumo.class);
//		
//		if ("apenas-nome".equals(projecao)) {
//			restauranteWrapper.setSerializationView(RestauranteView.ApenasNomes.class);
//		} else if ("completo".equals(projecao)) {
//			restauranteWrapper.setSerializationView(null);
//		}
//		
//		
//		return restauranteWrapper;
//	}

//	@GetMapping
//	public List<RestauranteModel> listar() {
//		return restauranteModelAssembler.toCollectionModel(restauranteRepository.findAll());
//	}
//	
//	@JsonView(RestauranteView.Resumo.class)
//	@GetMapping(params = "projecao=resumo")
//	public List<RestauranteModel> listarResumido() {
//		return listar();
//	}
//	
//	@JsonView(RestauranteView.ApenasNomes.class)
//	@GetMapping(params = "projecao=apenas-nome")
//	public List<RestauranteModel> listarApenasNomes() {
//		return listar();
//	}

	@GetMapping("/{restauranteId}")
	public RestauranteModel buscar(@PathVariable Long restauranteId) {
		Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(restauranteId);

		return restauranteModelAssembler.toModel(restaurante);
	}

//	@PostMapping
//	@ResponseStatus(HttpStatus.CREATED)
//	public ResponseEntity<?>  adicionar(@RequestBody Restaurante restaurante) {
//		try {
//			restaurante = cadastroRestauranteService.salvar(restaurante);
//			
//			return ResponseEntity.status(HttpStatus.CREATED).body(restaurante);
//			
//		} catch (EntidadeNaoEncontradaException e) {
//			return ResponseEntity.badRequest().body(e.getMessage());
//		}
//	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public RestauranteModel adicionar(@RequestBody @Valid RestauranteInput restauranteInput) {
		try {

			Restaurante restaurante = restauranteInputDisassembler.toDomainObject(restauranteInput);

			return restauranteModelAssembler.toModel(cadastroRestauranteService.salvar(restaurante));
		} catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}

//	@PutMapping("/{restauranteId}")
//	public ResponseEntity<?> atualizar(@PathVariable Long restauranteId ,@RequestBody Restaurante restaurante) {
//		try {
//			Restaurante restauranteAtual = restauranteRepository.findById(restauranteId).orElse(null);
//			
//			if (restauranteAtual != null) {
//				BeanUtils.copyProperties(restaurante, restauranteAtual,
//						"id", "formasPagamento", "endereco", "dataCadastro", "produtos");
//				Restaurante restauranteSalvar = cadastroRestauranteService.salvar(restauranteAtual);
//				
//				return ResponseEntity.ok(restauranteSalvar);
//				
//			}
//			
//			return ResponseEntity.notFound().build();
//		} catch (EntidadeNaoEncontradaException e) {
//			return ResponseEntity.badRequest().body(e.getMessage());
//			
//		}
//		
//	}

	@PutMapping("/{restauranteId}")
	public RestauranteModel atualizar(@PathVariable Long restauranteId,
			@RequestBody @Valid RestauranteInput restauranteInput) {
		try {

			Restaurante restauranteAtual = cadastroRestauranteService.buscarOuFalhar(restauranteId);

			restauranteInputDisassembler.copyToDomainObject(restauranteInput, restauranteAtual);

			return restauranteModelAssembler.toModel(cadastroRestauranteService.salvar(restauranteAtual));
		} catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}

	@PutMapping("/{restauranteId}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativar(@PathVariable Long restauranteId) {
		cadastroRestauranteService.ativar(restauranteId);
	}
	
	@PutMapping("/{restauranteId}/abertura")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void abrir(@PathVariable Long restauranteId) {
		
		cadastroRestauranteService.abrir(restauranteId);
		
	}
	
	@PutMapping("/{restauranteId}/fechamento")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void fechar(@PathVariable Long restauranteId) {
		
		cadastroRestauranteService.fechar(restauranteId);
		
	}

	@DeleteMapping("/{restauranteId}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inativar(@PathVariable Long restauranteId) {
		cadastroRestauranteService.inativar(restauranteId);
	}
	
	@PutMapping("/ativacoes")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativarMultiplos(@RequestBody List<Long> restaurantesIds) {
		try {
			cadastroRestauranteService.ativar(restaurantesIds);
		} catch (RestauranteNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@DeleteMapping("/ativacoes")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inativarMultiplos(@RequestBody List<Long> restaurantesIds) {
		try {
			cadastroRestauranteService.inativar(restaurantesIds);
		} catch (RestauranteNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

//	@DeleteMapping("/{restauranteId}")
//	public ResponseEntity<Restaurante> remover(@PathVariable Long restauranteId) {
//		
//		try {
//			cadastroRestauranteService.excluir(restauranteId);
//			return ResponseEntity.noContent().build();
//		} catch (EntidadeNaoEncontradaException e) {
//			return ResponseEntity.notFound().build();
//		}
//		
//	}

//	@PatchMapping("/{restauranteId}")
//	public Restaurante atualizarParcial(@PathVariable Long restauranteId,
//			@RequestBody Map<String, Object> campos, HttpServletRequest request) {
//		
//		Restaurante restauranteAtual = cadastroRestauranteService.buscarOuFalhar(restauranteId);
//		
//		merge(campos, restauranteAtual, request);
//		validate(restauranteAtual, "restaurante");
//		
//		return atualizar(restauranteId, restauranteAtual);
//	}

//	private void validate(Restaurante restaurante, String objectName) {
//		
//		BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(restaurante, objectName);
//	
//		validator.validate(restaurante, bindingResult);
//		
//		if (bindingResult.hasErrors()) {
//			throw new ValidacaoException(bindingResult);
//		}
//}
//
//	private void merge(Map<String, Object> dadosOrigem, Restaurante restauranteDestino,
//			HttpServletRequest request) {
//		ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(request);
//		
//		try {
//			ObjectMapper objectMapper = new ObjectMapper();
//			objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
//			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
//			
//			Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);
//			
//			dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
//				Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
//				field.setAccessible(true);
//				
//				Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
//				
//				ReflectionUtils.setField(field, restauranteDestino, novoValor);
//			});
//		} catch (IllegalArgumentException e) {
//			Throwable rootCause = ExceptionUtils.getRootCause(e);
//			throw new HttpMessageNotReadableException(e.getMessage(), rootCause, serverHttpRequest);
//		}
//	}

}
