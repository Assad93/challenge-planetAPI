package br.com.projetos.apiplanetas.controller.dto;

import java.util.List;
import java.util.stream.Collectors;

import br.com.projetos.apiplanetas.model.Planet;

public class PlanetDto {
	private String name;
	private String climate;
	private String terrain;
	
	public PlanetDto(Planet planet) {
		this.name = planet.getName();
		this.climate = planet.getClimate();
		this.terrain = planet.getTerrain();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getClimate() {
		return climate;
	}
	public void setClimate(String climate) {
		this.climate = climate;
	}
	public String getTerrain() {
		return terrain;
	}
	public void setTerrain(String terrain) {
		this.terrain = terrain;
	}

	public static List<PlanetDto> convertList(List<Planet> planets) {
		return planets.stream().map(PlanetDto::new).collect(Collectors.toList());
	}
}
