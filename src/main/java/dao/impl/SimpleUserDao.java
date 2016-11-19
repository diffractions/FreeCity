package dao.impl;

import static utils.JBDCUtil.closeQuaetly;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import dao.UserDao;
import dao.exceptions.DaoSystemException;
import dao.exceptions.NoSuchEntityException;
import entity.ShowedUser;
import entity.impl.SimpleShowedUser;

public class SimpleUserDao implements UserDao {

	public static Logger log = Logger.getLogger(Thread.currentThread().getStackTrace()[1].getClassName());

	private final static String USER_TABL_NAME = "freecity.user";
	private final static String USER_ID_COL_NAME = "user_id";
	private final static String USER_FIRST_NAME_COL_NAME = "first_name";
	private final static String USER_LAST_NAME_COL_NAME = "last_name";
	private final static String USER_EMAIL_COL_NAME = "email";
	private final static String USER_LOGIN_COL_NAME = "login";
	private final static String USER_PASSWORD_COL_NAME = "password";

	private final static String ADD_USER_SQL_WITH_LATNAME = "insert into " + USER_TABL_NAME + "("
			+ USER_FIRST_NAME_COL_NAME + ", " + USER_LAST_NAME_COL_NAME + ", " + USER_EMAIL_COL_NAME + ", "
			+ USER_LOGIN_COL_NAME + ", " + USER_PASSWORD_COL_NAME + ") values (?,?,?,?,?)";

	private final static String ADD_USER_SQL = "insert into " + USER_TABL_NAME + "(" + USER_FIRST_NAME_COL_NAME + ", "
			+ USER_EMAIL_COL_NAME + ", " + USER_LOGIN_COL_NAME + ", " + USER_PASSWORD_COL_NAME + ") values (?,?,?,?)";

	private final static String GET_USER_SQL = "select " + USER_ID_COL_NAME + ", " + USER_LAST_NAME_COL_NAME + ", "
			+ USER_FIRST_NAME_COL_NAME + ", " + USER_EMAIL_COL_NAME + ", " + USER_LOGIN_COL_NAME + " from "
			+ USER_TABL_NAME + " where " + USER_LOGIN_COL_NAME + "= ? and " + USER_PASSWORD_COL_NAME + "= ?";

	private DataSource dataSource;

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public ShowedUser createUser(String login, String first_name, String last_name, String email, String password)
			throws DaoSystemException {
		Connection con = null;
		PreparedStatement stat = null;
		try {
			con = dataSource.getConnection();
			stat = con.prepareStatement(ADD_USER_SQL_WITH_LATNAME);
			stat.setString(1, first_name);
			stat.setString(2, last_name);
			stat.setString(3, email);
			stat.setString(4, login);
			stat.setString(5, password);
			int addedCount = stat.executeUpdate();
			log.debug("Count of added user " + addedCount);
			if (addedCount == 1)
				return getUser(login, password);
			throw new DaoSystemException("USER NOT CREATED");
		} catch (SQLException e) {
			log.error("Error description", e);
			throw new DaoSystemException(e);
		} catch (NoSuchEntityException e) {
			log.error("Error description", e);
			throw new DaoSystemException(e);
		} finally {
			log.debug("Close ADD_CITY_SQL ResultSet and ADD_CITY_SQL Statement");
			try {
				closeQuaetly(con, stat);
			} catch (Exception e1) {
				log.error(e1);
				throw new DaoSystemException(e1);
			}
		}

	}

	@Override
	public ShowedUser createUser(String login, String first_name, String email, String password)
			throws DaoSystemException {
		Connection con = null;
		PreparedStatement stat = null;
		try {
			con = dataSource.getConnection();
			stat = con.prepareStatement(ADD_USER_SQL);
			stat.setString(1, first_name);
			stat.setString(2, email);
			stat.setString(3, login);
			stat.setString(4, password);
			int addedCount = stat.executeUpdate();
			log.debug("Count of added user " + addedCount);
			if (addedCount == 1)
				return getUser(login, password);
			throw new DaoSystemException("USER NOT CREATED");
		} catch (SQLException e) {
			log.error("Error description", e);
			throw new DaoSystemException(e);
		} catch (NoSuchEntityException e) {
			log.error("Error description", e);
			throw new DaoSystemException(e);
		} finally {
			log.debug("Close ADD_CITY_SQL ResultSet and ADD_CITY_SQL Statement");
			try {
				closeQuaetly(con, stat);
			} catch (Exception e1) {
				log.error(e1);
				throw new DaoSystemException(e1);
			}
		}

	}

	@Override
	public ShowedUser getUser(String login, String password) throws DaoSystemException, NoSuchEntityException {
		Connection con = null;
		PreparedStatement stat = null;
		ResultSet rset = null;
		try {
			con = dataSource.getConnection();
			stat = con.prepareStatement(GET_USER_SQL);
			stat.setString(1, login);
			stat.setString(2, password);

			rset = stat.executeQuery();

			if (rset.next()) {
				return new SimpleShowedUser(rset.getString(USER_LOGIN_COL_NAME),
						rset.getString(USER_FIRST_NAME_COL_NAME), rset.getString(USER_LAST_NAME_COL_NAME),
						rset.getString(USER_EMAIL_COL_NAME), rset.getInt(USER_ID_COL_NAME));
			}

		} catch (SQLException e) {
			log.error("Error description", e);
			throw new DaoSystemException(e);
		} finally {
			log.debug("Close ADD_CITY_SQL ResultSet and ADD_CITY_SQL Statement");
			try {
				closeQuaetly(con, stat, rset);
			} catch (Exception e1) {
				log.error(e1);
				throw new DaoSystemException(e1);
			}
		}
		throw new NoSuchEntityException("user name or password is incorrect");
	}

}
