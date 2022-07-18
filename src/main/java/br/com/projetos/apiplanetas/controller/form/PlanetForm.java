package br.com.projetos.apiplanetas.controller.form;

import br.com.projetos.apiplanetas.model.Planet;

public class PlanetForm {
	private String name;
	private String climate;
	private String terrain;
	
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

	public static Planet convert(PlanetForm planetForm) {
		// TODO Auto-generated method stub
		return new Planet(planetForm.name, planetForm.climate, planetForm.terrain);
	}
	
}
