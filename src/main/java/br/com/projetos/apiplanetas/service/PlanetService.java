package br.com.projetos.apiplanetas.service;

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
	
	public Planet save(Planet planet) throws UnirestException {
			Planet planetWithFilms = setCountFilms(planet);
			
			return planetRepository.save(planetWithFilms);
	}
	
	private JSONArray fetchAllPlanets() throws UnirestException {
		String url = "https://swapi.dev/api/planets";
		
		return Unirest.get(url).asJson().getBody().getObject().getJSONArray("results");
	}
	
	private Planet setCountFilms(Planet planet) throws UnirestException {
		JSONArray planetsInJSON = fetchAllPlanets();
		
		for(int i = 0; i < planetsInJSON.length(); i++) {
			JSONObject planetInJSON = planetsInJSON.getJSONObject(i);
			
			if(planet.getName().equals(planetInJSON.getString("name"))) {
				planet.setCountFilms(planetInJSON.getJSONArray("films").length());
			}
		}
		
		return planet;
	}
}
