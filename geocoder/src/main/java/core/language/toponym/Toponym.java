package core.language.toponym;

import java.util.ArrayList;
import java.util.List;

import core.geo.Coordinate;

public class Toponym {

	private int start;
	private int end;
	private String text;
	private Coordinate coordinates;
	private List<String> geonamesIds = new ArrayList<String>();

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

	public void addGeonamesId(String geonamesId) {
		geonamesIds.add(geonamesId);
	}

	public int getGeonamesIdCount() {
		return geonamesIds.size();
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}
}
