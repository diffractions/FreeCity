package dao;

import java.util.List;

import dao.exceptions.DaoSystemException;
import dao.exceptions.NoSuchEntityException;
import entity.Section;

public interface SectionDao {
	public List<Section> getSectionList() throws DaoSystemException;

	public int updateSection(int sectionId, String sectionName, int parentSectionId)
			throws DaoSystemException, NoSuchEntityException; 
	public Section getSectionById(int sectionId) throws NoSuchEntityException, DaoSystemException;

	public int addSection(String sectionName, String parentSectionId, String view) throws DaoSystemException;

	public int delSection(String trim) throws DaoSystemException;
 
}
