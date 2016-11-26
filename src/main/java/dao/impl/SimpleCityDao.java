package dao.impl;

import static utils.JBDCUtil.closeQuaetly;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import dao.CityDao;
import dao.exceptions.DaoSystemException;
import dao.exceptions.NoSuchEntityException;
import entity.City;
import entity.impl.SimpleCity;

public class SimpleCityDao implements CityDao {

	public static Logger log = Logger.getLogger(Thread.currentThread().getStackTrace()[1].getClassName());

	private final static String CITY_TABL_NAME = "freecity.city";
	private final static String CITY_ID_COL_NAME = "city.city_id";
	private final static String CITY_NAME_COL_NAME = "city.name";
	private final static String CITY_SHORT_NAME_COL_NAME = "city.short_name";
	private final static String CITY_LAT_COL_NAME = "city.lat";
	private final static String CITY_LNG_COL_NAME = "city.lng";
	private final static String CITY_ZOOM_COL_NAME = "city.zoom";

	private final static String SELECT_ALL_SQL = "SELECT " + CITY_ID_COL_NAME + ", " + CITY_NAME_COL_NAME + ", "
			+ CITY_SHORT_NAME_COL_NAME + ", " + CITY_LAT_COL_NAME + ", " + CITY_LNG_COL_NAME + ", " + CITY_ZOOM_COL_NAME
			+ " FROM " + CITY_TABL_NAME + " ;";
	private final static String ADD_CITY_SQL = "insert into " + CITY_TABL_NAME + "(" + CITY_NAME_COL_NAME
			+ ") values (?)";

	private static final String SQL_CITY_DEL = "DELETE FROM " + CITY_TABL_NAME + " WHERE " + CITY_ID_COL_NAME + "= ?";
	private static final String SQL_SELECT_BY_ID = "select " + CITY_ID_COL_NAME + ", " + CITY_NAME_COL_NAME + ", "
			+ CITY_SHORT_NAME_COL_NAME + ", " + CITY_LAT_COL_NAME + ", " + CITY_LNG_COL_NAME + ", " + CITY_ZOOM_COL_NAME
			+ " FROM " + CITY_TABL_NAME + " WHERE " + CITY_ID_COL_NAME + "= ?";

	private DataSource dataSource;

	private String SQL_SELECT_BY_SHORT_NAME = "select " + CITY_ID_COL_NAME + ", " + CITY_NAME_COL_NAME + ", "
			+ CITY_SHORT_NAME_COL_NAME + " FROM " + CITY_TABL_NAME + " WHERE " + CITY_SHORT_NAME_COL_NAME + "= ?";

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public City getCityById(int cityId) throws NoSuchEntityException, DaoSystemException {
		Connection con = null;
		PreparedStatement stat = null;
		ResultSet set = null;
		try {
			con = dataSource.getConnection();
			stat = con.prepareStatement(SQL_SELECT_BY_ID);
			stat.setInt(1, cityId);
			set = stat.executeQuery();

			if (set.next())
				return creatCityFromResultSet(set);
			throw new NoSuchEntityException("city not found");
		} catch (SQLException e) {
			log.error("Error description", e);
			throw new DaoSystemException(e);
		} finally {
			log.debug("Close SQL_SECTION_ADD ResultSet and SQL_SECTION_ADD Statement");
			try {
				closeQuaetly(stat, set, con);
			} catch (Exception e1) {
				log.error(e1);
				throw new DaoSystemException(e1);
			}
		}
	}

	@Override
	public City getCityByShorName(String shortName) throws NoSuchEntityException, DaoSystemException {
		Connection con = null;
		PreparedStatement stat = null;
		ResultSet set = null;
		try {
			con = dataSource.getConnection();
			stat = con.prepareStatement(SQL_SELECT_BY_SHORT_NAME);
			stat.setString(1, shortName);
			set = stat.executeQuery();

			if (set.next())
				return creatCityFromResultSet(set);
			throw new NoSuchEntityException("city not found");
		} catch (SQLException e) {
			log.error("Error description", e);
			throw new DaoSystemException(e);
		} finally {
			log.debug("Close SQL_SECTION_ADD ResultSet and SQL_SECTION_ADD Statement");
			try {
				closeQuaetly(stat, set, con);
			} catch (Exception e1) {
				log.error(e1);
				throw new DaoSystemException(e1);
			}
		}
	}

	@Override
	public CopyOnWriteArrayList<City> getCityAll() throws DaoSystemException {
		Statement selectAllStat = null;
		ResultSet selectAllResultSet = null;
		CopyOnWriteArrayList<City> cityes = new CopyOnWriteArrayList<City>();
		Connection conn = null;
		try {

			conn = dataSource.getConnection();
			log.debug("SQL to Execute : " + SELECT_ALL_SQL);

			selectAllStat = conn.createStatement();
			selectAllStat.execute(SELECT_ALL_SQL);
			selectAllResultSet = selectAllStat.getResultSet();
			log.debug("SQL : " + SELECT_ALL_SQL + " was execute!");

			while (selectAllResultSet.next()) {
				City proportiesTable = creatCityFromResultSet(selectAllResultSet);
				cityes.add(proportiesTable);
			}

			log.debug("Selected cityes:" + cityes);

		} catch (SQLException e) {
			log.error("pribt exception: ", e);
			throw new DaoSystemException(e);
		} finally {
			log.trace("Close SELECT_ALL_SQL ResultSet and SELECT_ALL_SQL Statement");
			try {
				closeQuaetly(selectAllResultSet, selectAllStat, conn);
			} catch (Exception e1) {
				log.error("pribt exception: ", e1);
				throw new DaoSystemException(e1);
			}
		}

		return cityes;
	}

	private City creatCityFromResultSet(ResultSet selectAllResultSet) throws SQLException {
		City proportiesTable = new SimpleCity(selectAllResultSet.getInt(CITY_ID_COL_NAME),
				selectAllResultSet.getString(CITY_NAME_COL_NAME),
				selectAllResultSet.getString(CITY_SHORT_NAME_COL_NAME), selectAllResultSet.getDouble(CITY_LAT_COL_NAME),
				selectAllResultSet.getDouble(CITY_LNG_COL_NAME), selectAllResultSet.getDouble(CITY_ZOOM_COL_NAME));
		return proportiesTable;
	}

	@Override
	public int addCity(String cityName) throws DaoSystemException {
		Connection con = null;
		PreparedStatement stat = null;

		PreparedStatement prepCyr = null;
		try {
			con = dataSource.getConnection();
			prepCyr = con.prepareStatement("SET NAMES utf8mb4");
			prepCyr.executeQuery();
			
			stat = con.prepareStatement(ADD_CITY_SQL);
			stat.setString(1, cityName);
			int addedCount = stat.executeUpdate();
			return addedCount;
		} catch (SQLException e) {
			log.error("Error description", e);
			throw new DaoSystemException(e);
		} finally {
			log.debug("Close ADD_CITY_SQL ResultSet and ADD_CITY_SQL Statement");
			try {
				closeQuaetly(prepCyr, stat,con);
			} catch (Exception e1) {
				log.error(e1);
				throw new DaoSystemException(e1);
			}
		}

	}

	@Override
	public int delCity(String cityId) throws DaoSystemException {
		Connection con = null;
		PreparedStatement stat = null;
		try {
			con = dataSource.getConnection();
			stat = con.prepareStatement(SQL_CITY_DEL);
			stat.setInt(1, Integer.parseInt(cityId));
			int addedCount = stat.executeUpdate();
			return addedCount;
		} catch (SQLException e) {
			log.error("Error description", e);
			throw new DaoSystemException(e);
		} finally {
			log.debug("Close SQL_SECTION_ADD ResultSet and SQL_SECTION_ADD Statement");
			try {
				closeQuaetly(con, stat);
			} catch (Exception e1) {
				log.error(e1);
				throw new DaoSystemException(e1);
			}
		}

	}

}
