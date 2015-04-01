package core.language.document.tweet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import core.language.document.Document;
import core.language.word.Toponym;
import core.geo.Coordinate;

public class Tweet extends Document {

	private String user;
	private String text;
	private String tgnText;
	private long date;
	private Coordinate geotag;
	private List<Toponym> toponyms = new ArrayList<Toponym>();
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}

	public String getTgnText() {
		return tgnText;
	}
	
	public Coordinate getGeotag() {
		return this.geotag;
	}

	public void setGeotag(Coordinate coordinate) {
		this.geotag = coordinate;
	}
	
	public List<Toponym> getToponyms() {
		return toponyms;
	}
	
	public void setToponyms(List<Toponym> toponyms) {
		this.toponyms = toponyms;
		buildTgnMessage();		
	}

	private void buildTgnMessage() {
		tgnText = text;
		for(Toponym t : this.toponyms) {
			addTagToEachToponymOccurenceInTweetText(t.getGeonamesIds(), t.getText());
		}
	}

	private void addTagToEachToponymOccurenceInTweetText(List<String> geonamesIds, String toponymText) {
		if(geonamesIds != null && geonamesIds.size() > 0){
			String geonamesId = geonamesIds.get(0);
			String replacement = "tgn," + geonamesId + "-" + toponymText + "-]]";
			tgnText = tgnText.replaceAll(toponymText, replacement);
		}
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
	
	public long getDate() {
		return date;
	}
	
	public void setDate(long date) {
		this.date = date;
	}

	public void setDate(String date) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		Date d = formatter.parse(date);		
		this.date = d.getTime();
	}

}
