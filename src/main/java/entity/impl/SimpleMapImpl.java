package entity.impl;

import entity.Map;

public class SimpleMapImpl implements Map {

	private int mapId;
	private double lat;
	private double lng;

	
	@Override
	public String toString() {
		return "SimpleMapImpl [mapId=" + mapId + ", lat=" + lat + ", lng=" + lng + "]";
	}

	public SimpleMapImpl(int mapId, double lat, double lng) {
		super();
		this.mapId = mapId;
		this.lat = lat;
		this.lng = lng;
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
	public int getMapId() {
		return mapId;
	}

}
