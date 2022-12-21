package com.algaworks.algafood.domain.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Restaurante;

@Repository
public interface RestauranteRepository extends CustomJpaRepository<Restaurante, Long>, RestauranteRepositoryQueries,
		JpaSpecificationExecutor<Restaurante> {

	// pra achar formas de pagamentos junto na query
	// left join fetch r.formasPagamento
	@Query("from Restaurante r join fetch r.cozinha")
	List<Restaurante> findAll();

	// Consulta JPA usando Query Methods
	// https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation

	// find query get stream read -> todos iguais

	List<Restaurante> findByTaxaFreteBetween(BigDecimal taxaInicial, BigDecimal taxaFinal);

	// @Query foi para o arquivo orm.xml
//	@Query("from Restaurante where nome like %:nome% and cozinha.id = :id")
	List<Restaurante> consultarPorNome(String nome, @Param("id") Long cozinhaId);
	// mesma consulta mas em cima usando queries JPQL customizadas
	// List<Restaurante> findByNomeContainingAndCozinhaId(String nome, Long
	// cozinhaId);

	// flag first

	Optional<Restaurante> findFirstByNomeContaining(String nome);

	// flag Top2

	List<Restaurante> findTop2ByNomeContaining(String nome);

	// prefixos exists count

	int countByCozinhaId(Long cozinhaId);

	public List<Restaurante> consultar(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal);

}
