package br.com.projetos.apiplanetas.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.projetos.apiplanetas.controller.dto.PlanetDto;
import br.com.projetos.apiplanetas.controller.form.PlanetForm;
import br.com.projetos.apiplanetas.model.Planet;
import br.com.projetos.apiplanetas.repository.PlanetRepository;

@RestController
@RequestMapping("/planets")
public class PlanetController {
	
	@Autowired
	PlanetRepository planetRepository;
	
	
	@GetMapping
	public ResponseEntity<List<PlanetDto>> findAll() {
		
		List<Planet> planets = planetRepository.findAll();
		
		List<PlanetDto> planetsDto = PlanetDto.convertList(planets);
		
		return ResponseEntity.ok(planetsDto);
	}
	
	@GetMapping("/search")
	public ResponseEntity<PlanetDto> findByName(@RequestParam("name") String name) {
		Planet planet = planetRepository.findByName(name);
		
		PlanetDto planetDto = new PlanetDto(planet);
		
		return ResponseEntity.ok(planetDto);
	}
	
	@GetMapping("/search/{id}")
	public ResponseEntity<PlanetDto> findById(@PathVariable Long id) {
		Optional<Planet> planet = planetRepository.findById(id);
		
		if(!planet.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		PlanetDto planetDto = new PlanetDto(planet.get());
		
		return ResponseEntity.ok(planetDto);
	}
	
	@PostMapping
	public ResponseEntity<PlanetDto>save(@RequestBody PlanetForm planetForm,  UriComponentsBuilder uriBuilder) {
		
		Planet planet = PlanetForm.convert(planetForm);
		
		Planet planetCreated = planetRepository.save(planet);
		
		PlanetDto planetDto = new PlanetDto(planetCreated);
		
		URI uri = uriBuilder.path("/planets/search/{id}").buildAndExpand(planetCreated.getId()).toUri();
		
		return ResponseEntity.created(uri).body(planetDto);
	}
	
}
