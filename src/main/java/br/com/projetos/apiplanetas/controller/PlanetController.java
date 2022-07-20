package br.com.projetos.apiplanetas.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.mashape.unirest.http.exceptions.UnirestException;

import br.com.projetos.apiplanetas.controller.dto.PlanetDto;
import br.com.projetos.apiplanetas.controller.form.PlanetForm;
import br.com.projetos.apiplanetas.controller.message.Message;
import br.com.projetos.apiplanetas.model.Planet;
import br.com.projetos.apiplanetas.service.PlanetService;

@RestController
@RequestMapping("/planets")
public class PlanetController {
	
	@Autowired
	PlanetService planetService;
	
	
	@GetMapping
	public ResponseEntity<List<PlanetDto>> findAll() {
		
		List<Planet> planets = planetService.findAll();
		
		List<PlanetDto> planetsDto = PlanetDto.convertList(planets);
		
		return ResponseEntity.ok(planetsDto);
	}
	
	@GetMapping("/name/{name}")
	public ResponseEntity<PlanetDto> findByName(@PathVariable String name) {
		Planet planet = planetService.findByName(name);
		
		PlanetDto planetDto = new PlanetDto(planet);
		
		return ResponseEntity.ok(planetDto);
	}
	
	@GetMapping("/id/{id}")
	public ResponseEntity<PlanetDto> findById(@PathVariable Long id) {
		Planet planet = planetService.findById(id);
		
		PlanetDto planetDto = new PlanetDto(planet);
		
		return ResponseEntity.ok(planetDto);
	}
	
	
	@PostMapping
	public ResponseEntity<PlanetDto>save(@RequestBody @Valid PlanetForm planetForm,  UriComponentsBuilder uriBuilder) throws UnirestException {
		
		Planet planet = PlanetForm.convert(planetForm);
		
		Planet planetCreated = planetService.save(planet);
		
		PlanetDto planetDto = new PlanetDto(planetCreated);
		
		URI uri = uriBuilder.path("/planets/search/{id}").buildAndExpand(planetCreated.getId()).toUri();
		
		return ResponseEntity.created(uri).body(planetDto);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Message> deleteById(@PathVariable Long id) {
		planetService.deleteById(id);
		
		Message message = new Message("Produto deletado com sucesso");
		
		return ResponseEntity.ok().body(message);
	}
	
}
