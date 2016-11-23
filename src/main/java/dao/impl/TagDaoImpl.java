package dao.impl;

import static utils.JBDCUtil.closeQuaetly;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement; 
import java.util.concurrent.CopyOnWriteArraySet;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import dao.TagDao;
import dao.exceptions.DaoSystemException; 
import entity.Tag; 
import entity.impl.SimpleTagImpl;

public class TagDaoImpl implements TagDao {

	public static Logger log = Logger.getLogger(Thread.currentThread().getStackTrace()[1].getClassName());

	
	private final static String TAG_TABL_NAME = "freecity.tags";
	private final static String TAG_ID_COL_NAME = "tags.tag_id";
	private final static String TAG_NAME_COL_NAME = "tags.tag_name";

	private final static String SELECT_ALL_SQL =

			"SELECT " + TAG_ID_COL_NAME + ", " + TAG_NAME_COL_NAME + " FROM " + TAG_TABL_NAME + " ;";
 

	private final static String SELECT_TAG_BY_NAME_SQL =

			"SELECT " + TAG_ID_COL_NAME +  " FROM " + TAG_TABL_NAME + " where "+TAG_NAME_COL_NAME+" = ?";
	
	private final static String CREATE_TAG=
	
	"INSERT INTO "+TAG_TABL_NAME+" ("+TAG_NAME_COL_NAME+") VALUES (?)";
	
	
	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public DataSource getDataSource() {
		return dataSource;
	}
	
	@Override
	public CopyOnWriteArraySet<Tag> selectAll() throws DaoSystemException {
		Statement selectAllStat = null;
		ResultSet selectAllResultSet = null;
		CopyOnWriteArraySet<Tag> tags = new CopyOnWriteArraySet<Tag>();
		Connection conn = null;
		try {

			conn = dataSource.getConnection();
			log.debug("SQL to Execute : " + SELECT_ALL_SQL);

			selectAllStat = conn.createStatement();
			selectAllStat.execute(SELECT_ALL_SQL);
			selectAllResultSet = selectAllStat.getResultSet();
			log.debug("SQL : " + SELECT_ALL_SQL + " was execute!");

			while (selectAllResultSet.next()) {
				Tag proportiesTable = new SimpleTagImpl(selectAllResultSet.getInt(TAG_ID_COL_NAME),
						selectAllResultSet.getString(TAG_NAME_COL_NAME));
				tags.add(proportiesTable);
			}

			log.debug("Selected cityes:" + tags);

		} catch (SQLException e) {
			log.error("pribt exception: ", e);
			throw new DaoSystemException(e);
		} finally {
			log.trace("Close SELECT_ALL_SQL ResultSet and SELECT_ALL_SQL Statement");
			try {
				closeQuaetly(selectAllResultSet, selectAllStat);
			} catch (Exception e1) {
				log.error("pribt exception: ", e1);
				throw new DaoSystemException(e1);
			}
		}

		return tags;
	}

	@Override
	public int getTagIdByName(String tag) throws DaoSystemException {

		
		
		Connection con = null;
		PreparedStatement stat = null;
		ResultSet tagstSet = null;
		try {
			con = dataSource.getConnection();
			stat = con.prepareStatement(SELECT_TAG_BY_NAME_SQL);
			stat.setString(1, tag);
			tagstSet = stat.executeQuery();
			if(tagstSet.next())
			return tagstSet.getInt(TAG_ID_COL_NAME);
		} catch (SQLException e) {
			log.error("Error description", e);
			throw new DaoSystemException(e);
		} finally {
			log.debug("Close SQL_SECTION_ADD ResultSet and SQL_SECTION_ADD Statement");
			try {
				closeQuaetly(con, stat, tagstSet);
			} catch (Exception e1) {
				log.error(e1);
				throw new DaoSystemException(e1);
			}
		}
		return -1;
	}

	@Override
	public int createTagByName(String tag) throws DaoSystemException {
		Connection con = null;
		PreparedStatement stat = null;
		ResultSet tagstSet = null;
		try {
			con = dataSource.getConnection();
			stat = con.prepareStatement(CREATE_TAG, Statement.RETURN_GENERATED_KEYS);
			stat.setString(1, tag);
			int row = stat.executeUpdate();
			tagstSet = stat.getGeneratedKeys();
			if (tagstSet.next()){
				int tagId= tagstSet.getInt(1);
				log.info("tag was created, tag name:" +tag +", tagid:" + tagId);
				return tagId;
			}
		} catch (SQLException e) {
			log.error("Error description", e);
			throw new DaoSystemException(e);
		} finally {
			log.debug("Close SQL_SECTION_ADD ResultSet and SQL_SECTION_ADD Statement");
			try {
				closeQuaetly(con, stat, tagstSet);
			} catch (Exception e1) {
				log.error(e1);
				throw new DaoSystemException(e1);
			}
		}
		return -1;
	}
	
	
 
}
