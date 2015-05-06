package core.language.word;

import java.util.ArrayList;
import java.util.List;

import core.gazetteer.Coordinate;

public class Toponym extends Word {

	private int start;
	private int end;
	private String text;
	private Coordinate coordinates;
	private List<String> geonamesIds = new ArrayList<String>();

	public Toponym() {}

	public Toponym(String text) {
		super(text);
	}

	public Toponym(int start, int end, String text) {
		super(start, end, text);
	}

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
