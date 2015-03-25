package core;

import java.util.List;

import core.geo.Coordinate;

public class Toponym {

	private String text;
	private Coordinate coordinates;
	private List<String> geonamesIds;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Coordinate getCoordinates() {
		return coordinates;
	}
	
	public void setCoordinates(Coordinate coordinates) {
		this.coordinates = coordinates;
	}

	public List<String> getGeonamesIds() {
		return geonamesIds;
	}
	
	public void setGeonamesIds(List<String> geonamesIds) {
		this.geonamesIds = geonamesIds;
	}
}
