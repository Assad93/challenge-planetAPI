package br.com.projetos.apiplanetas.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.projetos.apiplanetas.model.Planet;

@Repository
public interface PlanetRepository extends JpaRepository<Planet, Long>{
	public Optional<Planet> findByName(String name);
}
