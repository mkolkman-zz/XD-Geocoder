package core.geo;

public class Coordinate {


	private float latitude;
	private float longitude;
	
	public Coordinate(float latitude, float longitude){
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public Coordinate(String latitude, String longitude) {
		this.latitude = Float.parseFloat(latitude);
		this.longitude = Float.parseFloat(longitude);
	}

	public float getLatitude() {
		return latitude;
	}
	
	public float getLongitude() {
		return longitude;
	}
	
}
