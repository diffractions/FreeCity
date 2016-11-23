package entity;

import java.util.List;

import entity.work.Work;

import java.util.Calendar;

public interface ShowedItem {

	public int getId();

	public String getHeader();

	public List<String> getURLs();

	public String getDescription();

	public Calendar getDate();

	public ShowedUser getUser();

	public int getImageId();

	public List<Section> getSections();

	public List<City> getCities();

	public List<Map> getMaps();

	public Work getWork();

	public List<Adress> getAdress();
	
	public List<Tag> getTags();

	public Rating getRating();

	public List<String> getImages();
	
	public String getImagName();

	public int getActiv();
	
	public int getStatus();

}
