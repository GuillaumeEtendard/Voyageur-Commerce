package model;

import java.util.ArrayList;
import java.util.List;

public class Solution {
	
	private List<City> cities = new ArrayList<City>();
	private Double score = null;

	public Solution(List<City> cities, Double score) {
		this.cities = cities;
		this.score = score;
	}

	public List<City> getCities() {
		return cities;
	}

	public void setCities(List<City> cities) {
		this.cities = cities;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	@Override
	public String toString() {
		return "Solution [cities=" + cities + ", score=" + score + "]";
	}
	
}
