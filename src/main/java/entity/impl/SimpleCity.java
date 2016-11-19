package entity.impl;

import entity.City;

public class SimpleCity implements City {

	private String name;
	private int cityId;
	private String shortName;
	private double lat;
	private double lng;
	private double zoom;

	@Override
	public int getId() {
		return cityId;
	}
	
	
	

	public SimpleCity( int cityId,String name, String shortName, double lat, double lng, double zoom) {
		super();
		this.name = name;
		this.cityId = cityId;
		this.shortName = shortName;
		this.lat = lat;
		this.lng = lng;
		this.zoom = zoom;
	}




	protected SimpleCity(int cityId, String name, String shortName) {
		super();
		this.cityId = cityId;
		this.name = name;
		this.shortName = shortName;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "SimpleCity [name=" + name + ", cityId=" + cityId + ", shortName=" + shortName + "]";
	}

	@Override
	public String getShortName() {
		return shortName;
	}

	@Override
	public double getLat() { 
		return lat;
	}

	@Override
	public double getLng() { 
		return lng;
	}

	@Override
	public double getZoom() { 
		return zoom;
	}

}
