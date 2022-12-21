package com.algaworks.algafood;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.util.DatabaseCleaner;
import com.algaworks.algafood.util.ResourceUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
class CadastroCozinhaIT {

	private static final int COZINHA_ID_INEXISTENTE = 100;

	private Cozinha cozinhaAmericana;
	private int quantidadeCozinhasCadastradas;
	private String jsonCorretoCozinhaChinesa;

	@LocalServerPort
	private int port;

//	@Autowired
//	private Flyway flyway;
//	

	@Autowired
	private DatabaseCleaner dataBaseCleaner;

	@Autowired
	private CozinhaRepository cozinhaRepository;

	@BeforeEach
	public void setUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/cozinhas";

		// reseta a massa de dados, para cada teste ter o mesmo banco de dados
//		flyway.migrate();

		jsonCorretoCozinhaChinesa = ResourceUtils.getContentFromResource("/json/correto/cozinha-chinesa.json");

		dataBaseCleaner.clearTables();

		prepararDados();
	}

	@Test
	public void deveRetornarStatus200_QuandoConsultarCozinhas() {

		// mostra o erro
//		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

		given().accept(ContentType.JSON).when().get().then().statusCode(HttpStatus.OK.value());
	}

	@Test
	public void deveRetornarQuantidadeCorretaDeCozinhas_QuandoConsultarCozinhas() {

		given().accept(ContentType.JSON).when().get().then().body("", hasSize(quantidadeCozinhasCadastradas)); // tem
																												// que
																												// ter 4
																												// cozinhas
		// .body("nome", hasItems("Indiana", "Tailandesa")); // as 2 cozinhas precisam
		// constar na resposta
	}

	@Test
	public void deveRetornarStatus201_QuandoCadastrarCozinha() {
		given().body(jsonCorretoCozinhaChinesa).contentType(ContentType.JSON).accept(ContentType.JSON).when().post()
				.then().statusCode(HttpStatus.CREATED.value());
	}

	// GET /cozinhas/{cozinhaID}
	@Test
	public void deveRetonarRespostaEStatusCorretos_QuandoConsultarCozinhaExistente() {

		given().pathParam("cozinhaId", cozinhaAmericana.getId()).accept(ContentType.JSON).when().get("/{cozinhaId}")
				.then().statusCode(HttpStatus.OK.value()).body("nome", equalTo(cozinhaAmericana.getNome()));
	}

	@Test
	public void deveRetonarStatus404_QuandoConsultarCozinhaInexistente() {

		given().pathParam("cozinhaId", COZINHA_ID_INEXISTENTE).accept(ContentType.JSON).when().get("/{cozinhaId}")
				.then().statusCode(HttpStatus.NOT_FOUND.value());
	}

	private void prepararDados() {
		Cozinha cozinhaTailandesa = new Cozinha();
		cozinhaTailandesa.setNome("Tailandesa");
		cozinhaRepository.save(cozinhaTailandesa);

		cozinhaAmericana = new Cozinha();
		cozinhaAmericana.setNome("Americana");
		cozinhaRepository.save(cozinhaAmericana);

		quantidadeCozinhasCadastradas = (int) cozinhaRepository.count();

	}

}
