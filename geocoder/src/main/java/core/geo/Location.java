package core.geo;

import java.util.List;

public class Location {

    private String geonameId;

    private String name;

    private String asciiName;
    private List<String> alternateNames;
    private Coordinate coordinate;
    private String featureClass;
    private String featureCode;
    private String countryCode;
    private String CC2;
    private String admin1Code;
    private String admin2Code;
    private String admin3Code;
    private String admin4Code;
    private long population;
    private String elevation;
    private String DEM;
    private String timezone;
    private String modificationDate;

    public void setGeonameId(String geonameId) {
        this.geonameId = geonameId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAsciiName(String asciiName) {
        this.asciiName = asciiName;
    }

    public void setAlternateNames(List<String> alternateNames) {
        this.alternateNames = alternateNames;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public void setFeatureClass(String featureClass) {
        this.featureClass = featureClass;
    }

    public void setFeatureCode(String featureCode) {
        this.featureCode = featureCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public void setCC2(String CC2) {
        this.CC2 = CC2;
    }

    public void setAdmin1Code(String admin1Code) {
        this.admin1Code = admin1Code;
    }

    public void setAdmin2Code(String admin2Code) {
        this.admin2Code = admin2Code;
    }

    public void setAdmin3Code(String admin3Code) {
        this.admin3Code = admin3Code;
    }

    public void setAdmin4Code(String admin4Code) {
        this.admin4Code = admin4Code;
    }

    public void setPopulation(long population) {
        this.population = population;
    }

    public void setElevation(String elevation) {
        this.elevation = elevation;
    }

    public void setDEM(String DEM) {
        this.DEM = DEM;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public void setModificationDate(String modificationDate) {
        this.modificationDate = modificationDate;
    }

    public String getGeonameId() {
        return geonameId;
    }

    public String getName() {
        return name;
    }

    public String getAsciiName() {
        return asciiName;
    }

    public List<String> getAlternateNames() {
        return alternateNames;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public String getFeatureClass() {
        return featureClass;
    }

    public String getFeatureCode() {
        return featureCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getCC2() {
        return CC2;
    }

    public String getAdmin1Code() {
        return admin1Code;
    }

    public String getAdmin2Code() {
        return admin2Code;
    }

    public String getAdmin3Code() {
        return admin3Code;
    }

    public String getAdmin4Code() {
        return admin4Code;
    }

    public long getPopulation() {
        return population;
    }

    public String getElevation() {
        return elevation;
    }

    public String getDEM() {
        return DEM;
    }

    public String getTimezone() {
        return timezone;
    }

    public String getModificationDate() {
        return modificationDate;
    }
}
