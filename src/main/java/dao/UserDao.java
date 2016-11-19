package dao;

import dao.exceptions.DaoSystemException;
import dao.exceptions.NoSuchEntityException;
import entity.ShowedUser;

public interface UserDao {
	ShowedUser createUser(String login, String first_name, String last_name, String email, String password)
			throws DaoSystemException;

	ShowedUser createUser(String login, String first_name, String email, String password) throws DaoSystemException;

	ShowedUser getUser(String login, String password) throws DaoSystemException, NoSuchEntityException;
}
