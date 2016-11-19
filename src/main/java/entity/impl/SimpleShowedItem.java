package entity.impl;
 
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar; 
import java.util.List;

import entity.Adress;
import entity.City;
import entity.Map;
import entity.Rating;
import entity.Section;
import entity.ShowedItem;
import entity.ShowedUser;
import entity.Tag;
import entity.work.Work;

public class SimpleShowedItem implements ShowedItem {

	private  String image ;
	private int entity_id;
	private String header;
	private String description;
	private Calendar date;
	private ShowedUser user;
	List<Section> sectionIds = null;
	List<City> cityIds = null;

	private int imageId = -1;
	private List<String> urls;
	private List<Map> maplist;
	private Work work;
	private List<Adress> aress;
	private List<Tag> tag;
	private Rating rating;
	private List<String> images;
	private int activ;

	@Override
	public List<Map> getMaps() {
		return maplist;
	}

	private SimpleShowedItem(int entity_id, String header, String description, String date, ShowedUser user,
			List<City> cityIds, List<Section> sectionIds, List<String> urls) {
		super();
		this.entity_id = entity_id;
		this.header = header;
		this.description = description;
		this.date = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {

			this.date.setTimeInMillis(format.parse(date).getTime());

		} catch (ParseException e) { 
			e.printStackTrace();
		}
		this.user = user;
		this.cityIds = cityIds;
		this.sectionIds = sectionIds;
		this.urls = urls;
	}

	private SimpleShowedItem(int entity_id, String header, String description, String date, ShowedUser user,
			List<City> cityIds, List<Section> sectionIds, List<String> urls, List<Map> maplist) {
		this(entity_id, header, description, date, user, cityIds, sectionIds, urls);

		this.maplist = maplist;
	}

	private SimpleShowedItem(int entity_id, String header, String description, String date, ShowedUser user,
			List<City> cityIds, List<Section> sectionIds, List<String> urls, List<Map> maplist, Work work) {
		this(entity_id, header, description, date, user, cityIds, sectionIds, urls, maplist);

		this.work = work;
	}

	private SimpleShowedItem(int entity_id, String header, String description, String date, ShowedUser user,
			List<City> cityIds, List<Section> sectionIds, List<String> urls, List<Map> maplist, Work work,
			List<Adress> aress) {
		this(entity_id, header, description, date, user, cityIds, sectionIds, urls, maplist, work);

		this.aress = aress;
	}
	
	private SimpleShowedItem(int entity_id, String header, String description, String date, ShowedUser user,
			List<City> cityIds, List<Section> sectionIds, List<String> urls, List<Map> maplist, Work work,
			List<Adress> aress, List<Tag> tag) {
		this(entity_id, header, description, date, user, cityIds, sectionIds, urls, maplist, work, aress);

		this.tag = tag;
	}



	private SimpleShowedItem(int entity_id, String header, String description, String date, ShowedUser user,
			List<City> cityIds, List<Section> sectionIds, List<String> urls, List<Map> maplist, Work work,
			List<Adress> aress, List<Tag> tag, Rating rating) {
		this(entity_id, header, description, date, user, cityIds, sectionIds, urls, maplist, work, aress, tag);

		this.rating = rating;
	}

	public SimpleShowedItem(int entity_id, String header, String description, String date, ShowedUser user,
			List<City> cityIds, List<Section> sectionIds, List<String> urls, List<Map> maplist, Work work,
			List<Adress> aress, List<Tag> tag, Rating rating, String image) {
		this(entity_id, header, description, date, user, cityIds, sectionIds, urls, maplist, work, aress, tag, rating);

		this.image = image;
	}
	
	public SimpleShowedItem(int entity_id, String header, String description, String date, ShowedUser user,
			List<City> cityIds, List<Section> sectionIds, List<String> urls, List<Map> maplist, Work work,
			List<Adress> aress, List<Tag> tag, Rating rating, String image, List<String> images) {
		this(entity_id, header, description, date, user, cityIds, sectionIds, urls, maplist, work, aress, tag, rating, image);

		this.images = images;
	}
	

	
	public SimpleShowedItem(int entity_id, String header, String description, String date, ShowedUser user,
			List<City> cityIds, List<Section> sectionIds, List<String> urls, List<Map> maplist, Work work,
			List<Adress> aress, List<Tag> tag, Rating rating, String image, List<String> images,int activ) { 
		this(entity_id, header, description, date, user, cityIds, sectionIds, urls, maplist, work, aress, tag, rating, image, images);
		this.activ = activ;
	}

	@Override
	public int getId() {
		return entity_id;

	}

	@Override
	public String getHeader() {
		return header;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public Calendar getDate() {
		return date;
	}

	@Override
	public int getImageId() {
		return imageId;
	}

	@Override
	public ShowedUser getUser() {
		return user;
	}

	@Override
	public List<Section> getSections() {
		return sectionIds;
	}

	@Override
	public List<City> getCities() {
		return cityIds;
	}

	@Override
	public List<String> getURLs() {
		return urls;
	}

	@Override
	public String toString() {
		return "SimpleShowedItem [entity_id=" + entity_id + ", header=" + header + ", description=" + description
				+ ", date=" + date.get(Calendar.DATE) + " " + date.get(Calendar.MONTH) + " " + date.get(Calendar.YEAR)
				+ ", user=" + user + ", sectionIds=" + sectionIds + ", cityIds=" + cityIds + ", imageId=" + imageId
				+ ", urls=" + urls + "]";
	}

	@Override
	public Work getWork() {
		return work;
	}

	@Override
	public List<Adress> getAdress() {
		return aress;
	}

	@Override
	public List<Tag> getTags() {
		return tag;
	}

	@Override
	public Rating getRating() {
		return rating;
	}

	@Override
	public List<String> getImages() { 
		return images;
	}

	@Override
	public String getImagName(){ 
		return image; 
	}

	@Override
	public int getActiv() {
		return activ;
	}

}
