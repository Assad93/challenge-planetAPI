package br.com.projetos.apiplanetas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	public List<PlanetDto> findAll() {
		
		List<Planet> planets = planetRepository.findAll();
		
		List<PlanetDto> planetsDto = PlanetDto.convertList(planets);
		
		return planetsDto;
	}
	
	
	@PostMapping
	public PlanetDto save(@RequestBody PlanetForm planetForm) {
		
		Planet planet = PlanetForm.convert(planetForm);
		
		Planet planetCreated = planetRepository.save(planet);
		
		PlanetDto planetDto = new PlanetDto(planetCreated);
		
		return planetDto;
	}
	
}
