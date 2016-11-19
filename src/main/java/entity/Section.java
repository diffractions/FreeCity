package entity;
 
import java.util.List;

public interface Section  {

	int getSectionId();
	String getSectionName(); 
	int getParrentId(); 
	Section getParrent(); 
	void setParrent(Section parrent); 
	List<Section> getChildSections();  
	void addChildSection(Section section);  
	public void setAllChilds(List<Section> allChilds);
	void setLevel(int level);
	int getLevel();
	List<Section> getAllChilds();
	Section getRootSetion();
	int getSectionView();
	
}
