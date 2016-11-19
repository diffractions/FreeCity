package entity;

import java.util.List;
import java.util.Date;

public interface ModifyItem {

	public int getId();

	public String getHeader();

	public void setHeader(String title);

	public void updHeader(String title);

	public String getDescription();

	public void setDescription(String description);

	public void updDescription(String description);

	public Date getDate();

	public void setDate(Date date);

	public void updDate(Date date);

	public int getUserId();
 
	public int getImageId();

	public void setImageId(int imageId);

	public void updImageId(int id);

	public List<Integer> getSectionIds();

	public void addSectionId(int id);

	public void delSectionId(int id);

	public List<Integer> getCityIds();

	public void addCityId(int id);

	public void delCityId(int id);

}
