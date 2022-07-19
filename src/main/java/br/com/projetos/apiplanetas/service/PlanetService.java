package br.com.projetos.apiplanetas.service;

import java.util.List;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

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
		return planetRepository.findByName(name);
	}
	
	public Planet findById(Long id) {
		Optional<Planet> planet = planetRepository.findById(id);
		
		if(!planet.isPresent()) {
			return null;
		}
		
		return planet.get();
	}
	
	public Planet save(Planet planet) throws UnirestException {
		Planet planetWithFilms = setCountFilms(planet);
			
		return planetRepository.save(planetWithFilms);
	}
	
	public void deleteById(Long id) {
		planetRepository.deleteById(id);
	}
	
	private JSONObject fetchPlanet(String name) throws UnirestException {
		String url = "https://swapi.dev/api/planets?search=" + name;
		
		JSONArray arrPlanets = Unirest.get(url).asJson().getBody().getObject().getJSONArray("results");
		
		return arrPlanets.getJSONObject(0);
	}
	
	private Planet setCountFilms(Planet planet) throws UnirestException {
		JSONObject planetInJSON = fetchPlanet(planet.getName());
			
		if(planet.getName().equals(planetInJSON.getString("name"))) {
			planet.setCountFilms(planetInJSON.getJSONArray("films").length());
		}
		
		return planet;
	}
}
