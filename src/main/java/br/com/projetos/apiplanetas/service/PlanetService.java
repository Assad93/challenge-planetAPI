package br.com.projetos.apiplanetas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import br.com.projetos.apiplanetas.exception.PlanetNotFoundException;
import br.com.projetos.apiplanetas.model.Planet;
import br.com.projetos.apiplanetas.repository.PlanetRepository;

@Service
public class PlanetService {
	
	@Autowired
	PlanetRepository planetRepository;
	
	public List<Planet> findAll() {
		return planetRepository.findAll();
	}
	
	public Planet findByName(String name) {
		Optional<Planet> planet = planetRepository.findByName(name);
		
		return planet.orElseThrow(() -> new PlanetNotFoundException());
	}
	
	public Planet findById(Long id) {
		Optional<Planet> planet = planetRepository.findById(id);
		
		return planet.orElseThrow(() -> new PlanetNotFoundException());
	}
	
	public Planet save(Planet planet) throws UnirestException {
		Planet planetWithFilms = setCountFilms(planet);
			
		return planetRepository.save(planetWithFilms);
	}
	
	public void deleteById(Long id) {
		findById(id);
		planetRepository.deleteById(id);
	}
	
	private Integer fetchNumberFilms(String name) throws UnirestException {
		String url = "https://swapi.dev/api/planets?search=" + name;
		
		Boolean planetsFounded = isFoundOnePlanet(name);
		
		if(!planetsFounded) {
			return 0;
		}
		
		Integer countFilms = Unirest
				.get(url)
				.asJson()
				.getBody()
				.getObject()
				.getJSONArray("results")
				.getJSONObject(0)
				.getJSONArray("films")
				.length();
		
		return countFilms;
	}
	
	private Planet setCountFilms(Planet planet) throws UnirestException {
		Integer countFilms = fetchNumberFilms(planet.getName());
		
		planet.setCountFilms(countFilms);
			
		return planet;
	}
	
	private Boolean isFoundOnePlanet(String name) throws UnirestException {
		String url = "https://swapi.dev/api/planets?search=" + name;
		
		Integer planetsFounded = Unirest
						.get(url)
						.asJson()
						.getBody()
						.getObject()
						.getInt("count");
		
		if(planetsFounded != 1) {
			return false;
		}
		
		return true;
	}
}
