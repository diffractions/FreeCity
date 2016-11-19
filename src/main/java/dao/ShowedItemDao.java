package dao;

import java.util.concurrent.CopyOnWriteArraySet;

import dao.exceptions.DaoSystemException;
import dao.exceptions.NoSuchEntityException;
import entity.ShowedItem;

public interface ShowedItemDao {

	public CopyOnWriteArraySet<ShowedItem> selectAll()
			throws DaoSystemException, NoSuchEntityException, NumberFormatException;

	public CopyOnWriteArraySet<ShowedItem> search(String str) throws DaoSystemException;

//	CopyOnWriteArraySet<ShowedItem> executeSelectAll(String city, String date, String section, String section_view,
//			String user, String from, String limit, String tag, String action)
//			throws NoSuchEntityException, NumberFormatException, DaoSystemException;
	
	CopyOnWriteArraySet<ShowedItem> executeSelectAll(String city, String date, String section, String section_view,
			String user, String from, String limit, String tag, String action, int [] count)
			throws NoSuchEntityException, NumberFormatException, DaoSystemException;

	public ShowedItem selectById(String id) throws DaoSystemException, NoSuchEntityException, NumberFormatException;

}
