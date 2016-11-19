package dao;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.Part;

import dao.exceptions.DaoSystemException;
import dao.exceptions.NoSuchEntityException;
import entity.Img;

public interface ImgDao { 

	int addImg(int entityId, Part filePart) throws NoSuchEntityException, DaoSystemException, IOException;
	void updImg(int parseInt, Part filePart)throws NoSuchEntityException, DaoSystemException, IOException;
	Img getImgById(int imageId) throws NoSuchEntityException, DaoSystemException; 
	Img getImgByName(String imgName) throws NoSuchEntityException, DaoSystemException; 
	
	int addImgToList(int entityId, Part filePart) throws NoSuchEntityException, DaoSystemException, IOException;
	List<String> getImgListByEntity(int intityId)throws NoSuchEntityException, DaoSystemException;
	
	void delImgByEntityId(int entityId)throws NoSuchEntityException, DaoSystemException;
	

	
	
}
