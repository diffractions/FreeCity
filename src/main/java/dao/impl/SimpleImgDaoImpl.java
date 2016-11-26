package dao.impl;

import static utils.JBDCUtil.closeQuaetly;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.Part;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.mysql.jdbc.Statement;

import dao.ImgDao;
import dao.exceptions.DaoSystemException;
import dao.exceptions.NoSuchEntityException;
import entity.Img;
import entity.impl.SimpleImgImpl;
 

public class SimpleImgDaoImpl implements ImgDao {

	public static Logger log = Logger.getLogger(Thread.currentThread().getStackTrace()[1].getClassName());
	private DataSource dataSource;
	private final static String IMG_TABL_NAME = "img";
	private final static String IMG_ID_COL_NAME = "img.img_id";
	private final static String IMAGE_FILE_COL_NAME = "img.image";
	private final static String IMAGE_NAME_COL_NAME = "img.img_name";
	private final static String IMAGE_CONTENTTYPE_COL_NAME = "img.contenttype";

	String SQL_ADD_IMG = "INSERT INTO " + IMG_TABL_NAME + " (" + IMAGE_FILE_COL_NAME + ", " + IMAGE_NAME_COL_NAME + ", "
			+ IMAGE_CONTENTTYPE_COL_NAME + ") values (?,?,?)";

	String SQL_DEL_IMG = "DELETE FROM " + IMG_TABL_NAME + " WHERE " + IMG_ID_COL_NAME + "= ?";

	private final static String IMG_HEADER_LIST_TABL_NAME = "head_img_list";
	private final static String IMG_HEADER_LIST_ID_COL_NAME = "head_img_list.entity_id";
	private final static String IMG_HEADER_LIST_IMG_ID_COL_NAME = "head_img_list.img_id";

	String SQL_ADD_IMG_HEADER_LIST = "INSERT INTO " + IMG_HEADER_LIST_TABL_NAME + " (" + IMG_HEADER_LIST_ID_COL_NAME
			+ ", " + IMG_HEADER_LIST_IMG_ID_COL_NAME + ") values (?,?)";

	String SQL_UPD_IMG_HEADER_LIST = "UPDATE " + IMG_TABL_NAME + " SET " + IMAGE_FILE_COL_NAME + " = ? " + ", "
			+ IMAGE_NAME_COL_NAME + " = ? " + ", " + IMAGE_CONTENTTYPE_COL_NAME + " = ? " + " WHERE " + IMG_ID_COL_NAME
			+ " = (SELECT " + IMG_HEADER_LIST_IMG_ID_COL_NAME + " FROM " + IMG_HEADER_LIST_TABL_NAME + " WHERE "
			+ IMG_HEADER_LIST_ID_COL_NAME + " = ?)";

	String SQL_DEL_IMG_HEADER_LIST = "DELETE FROM " + IMG_HEADER_LIST_TABL_NAME + " WHERE "
			+ IMG_HEADER_LIST_ID_COL_NAME + "= ?";
	String SQL_GET_IMG_ID_BY_ENTITY_ID = "SELECT " + IMG_HEADER_LIST_IMG_ID_COL_NAME + " FROM "
			+ IMG_HEADER_LIST_TABL_NAME + " WHERE " + IMG_HEADER_LIST_ID_COL_NAME + "= ?";

	private final static String IMG_LIST_TABL_NAME = "img_list";
	private final static String IMG_LIST_ID_COL_NAME = "img_list.entity_id";
	private final static String IMG_LIST_IMG_ID_COL_NAME = "img_list.img_id";

	String SQL_ADD_IMG_LIST = "INSERT INTO " + IMG_LIST_TABL_NAME + " (" + IMG_LIST_ID_COL_NAME + ", "
			+ IMG_LIST_IMG_ID_COL_NAME + ") values (?,?)";

	String SQL_GET_IMG_BY_ID = "SELECT " + IMG_ID_COL_NAME + ", " + IMAGE_FILE_COL_NAME + ", " + IMAGE_NAME_COL_NAME
			+ ", " + IMAGE_CONTENTTYPE_COL_NAME + " FROM " + IMG_TABL_NAME + " where " + IMG_ID_COL_NAME + " = ? ;";

	String SQL_GET_IMG_BY_NAME = "SELECT " + IMG_ID_COL_NAME + ", " + IMAGE_FILE_COL_NAME + ", " + IMAGE_NAME_COL_NAME
			+ ", " + IMAGE_CONTENTTYPE_COL_NAME + " FROM " + IMG_TABL_NAME + " where " + IMAGE_NAME_COL_NAME + " = ? ;";

	String SQL_GET_IMG_LIST_BY_ENTITY = "SELECT " + IMAGE_NAME_COL_NAME + " FROM " + IMG_LIST_TABL_NAME + ", "
			+ IMG_TABL_NAME + " where " + IMG_LIST_IMG_ID_COL_NAME + " = " + IMG_ID_COL_NAME + " and "
			+ IMG_LIST_ID_COL_NAME + " = ? ;";

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public int addImgToList(int entityId, Part filePart) throws NoSuchEntityException, DaoSystemException, IOException {
		return addition(entityId, filePart, SQL_ADD_IMG_LIST);
	}

	@Override
	public int addImg(int entityId, Part filePart) throws NoSuchEntityException, DaoSystemException, IOException {
		return addition(entityId, filePart, SQL_ADD_IMG_HEADER_LIST);
	}

	private int addition(int entityId, Part filePart, String SQL)
			throws NoSuchEntityException, DaoSystemException, IOException {
		Connection con = null;
		PreparedStatement stat = null;
		ResultSet set = null;
		String fileName = null;
		PreparedStatement stat1 = null;

		PreparedStatement prepCyr = null;
		try {
			fileName = getFileName(filePart);
			if (fileName != null && !fileName.equals("")) { 
				log.info("user add phohto : " + filePart.getName() + ", " + filePart.getSize() + ", "
						+ filePart.getContentType() + ", " + fileName);
				InputStream inputStream = filePart.getInputStream();

				con = dataSource.getConnection();
				
				prepCyr = con.prepareStatement("SET NAMES utf8mb4");
				prepCyr.executeQuery();
				
				stat = con.prepareStatement(SQL_ADD_IMG, Statement.RETURN_GENERATED_KEYS);
				stat.setBlob(1, inputStream);

				stat.setString(2, fileName);
				stat.setString(3, filePart.getContentType());

				int row = stat.executeUpdate();
				if (row > 0) {
					log.debug("File uploaded and saved into database");
				}

				set = stat.getGeneratedKeys();
				if (set.next()) {
					int img_id = set.getInt(1);
					stat1 = con.prepareStatement(SQL);
					stat1.setInt(1, entityId);
					stat1.setInt(2, img_id);
					int set1 = stat1.executeUpdate();
					if (set1 > 0)
						return img_id;
				}
				throw new DaoSystemException("ID IS NOT APPEND TO THIS IMAGE");
			} else {
				log.debug("img not found in request");
				return -1;
			}

		} catch (SQLException e) {
			log.error("Error description", e);
			throw new DaoSystemException(e);
		} finally {
			log.debug("Close " + SQL + " ResultSet and " + SQL + " Statement");
			try {
				closeQuaetly( prepCyr,stat, stat1, set, con);
			} catch (Exception e1) {
				log.error(e1);
				throw new DaoSystemException(e1);
			}
		}
	}

	@Override
	public Img getImgById(int imageId) throws NoSuchEntityException, DaoSystemException {

		Connection con = null;
		PreparedStatement stat = null;
		ResultSet set = null;

		PreparedStatement prepCyr = null;
		try {

			con = dataSource.getConnection();
			
			prepCyr = con.prepareStatement("SET NAMES utf8mb4");
			prepCyr.executeQuery();
			
			
			stat = con.prepareStatement(SQL_GET_IMG_BY_ID);
			stat.setInt(1, imageId);

			set = stat.executeQuery();

			if (set.next()) {

				Blob image = set.getBlob(IMAGE_FILE_COL_NAME);
				int length = (int) image.length();
				byte ret[] = image.getBytes(0, length);

				Img img = new SimpleImgImpl(imageId, set.getString(IMAGE_NAME_COL_NAME),
						set.getString(IMAGE_CONTENTTYPE_COL_NAME), ret);
				log.trace("length of getted img: " + ret.length);
				return img;
			}
			throw new DaoSystemException(" IMAGE was not found in this ID");

		} catch (SQLException e) {
			log.error("Error description", e);
			throw new DaoSystemException(e);
		} finally {
			log.debug("Close SQL_SECTION_ADD ResultSet and SQL_SECTION_ADD Statement");
			try {
				closeQuaetly(prepCyr, stat, set, con);
			} catch (Exception e1) {
				log.error(e1);
				throw new DaoSystemException(e1);
			}
		}
	}

	@Override
	public Img getImgByName(String imageName) throws NoSuchEntityException, DaoSystemException {

		Connection con = null;
		PreparedStatement stat = null;
		ResultSet set = null;
		PreparedStatement prepCyr = null;
		try {

			con = dataSource.getConnection();
			
			prepCyr = con.prepareStatement("SET NAMES utf8mb4");
			prepCyr.executeQuery();
			
			
			stat = con.prepareStatement(SQL_GET_IMG_BY_NAME);
			stat.setString(1, imageName);

			log.trace("SQL to EXUQUTE: " + SQL_GET_IMG_BY_NAME);
			set = stat.executeQuery();

			log.trace("SQL to was exucute: " + SQL_GET_IMG_BY_NAME);
			if (set.next()) {

				Blob image = set.getBlob(IMAGE_FILE_COL_NAME);
				int length = (int) image.length();
				byte ret[] = image.getBytes(1, length);

				Img img = new SimpleImgImpl(set.getInt(IMG_ID_COL_NAME), set.getString(IMAGE_NAME_COL_NAME),
						set.getString(IMAGE_CONTENTTYPE_COL_NAME), ret);
				log.trace("length of getted img: " + ret.length);
				return img;
			}
			log.warn(" IMAGE was not found in this name");
			return null;

		} catch (SQLException e) {
			log.error("Error description", e);
			throw new DaoSystemException(e);
		} finally {
			log.debug("Close SQL_SECTION_ADD ResultSet and SQL_SECTION_ADD Statement");
			try {
				closeQuaetly(prepCyr, stat, set, con);
			} catch (Exception e1) {
				log.error(e1);
				throw new DaoSystemException(e1);
			}
		}
	}

	private String getFileName(final Part part) throws UnsupportedEncodingException {

		log.trace(new String(part.getHeader("content-disposition")));
		for (String content : new String(part.getHeader("content-disposition")).split(";")) {
			if (content.trim().startsWith("filename")) {
				return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
			}
		}
		return null;
	}

	@Override
	public List<String> getImgListByEntity(int intityId) throws NoSuchEntityException, DaoSystemException {

		Connection con = null;
		PreparedStatement stat = null;
		ResultSet set = null;

		PreparedStatement prepCyr = null;
		
		try {

			con = dataSource.getConnection();
			
			prepCyr = con.prepareStatement("SET NAMES utf8mb4");
			prepCyr.executeQuery();
			
			stat = con.prepareStatement(SQL_GET_IMG_LIST_BY_ENTITY);
			stat.setInt(1, intityId);
			log.trace("SQL to EXUQUTE: " + SQL_GET_IMG_LIST_BY_ENTITY);
			set = stat.executeQuery();
			log.trace("SQL to was exucute: " + SQL_GET_IMG_LIST_BY_ENTITY);
			List<String> img_list = new LinkedList<>();
			while (set.next()) {
				String item = set.getString(IMAGE_NAME_COL_NAME);
				img_list.add(item);
			}

			return img_list;

		} catch (SQLException e) {
			log.error("Error description", e);
			throw new DaoSystemException(e);
		} finally {
			log.debug("Close SQL_SECTION_ADD ResultSet and SQL_SECTION_ADD Statement");
			try {
				closeQuaetly(prepCyr, stat, set, con);
			} catch (Exception e1) {
				log.error(e1);
				throw new DaoSystemException(e1);
			}
		}

	}

	@Override
	public void updImg(int entityId, Part filePart) throws NoSuchEntityException, DaoSystemException, IOException {

		Connection con = null;
		PreparedStatement stat = null;
		PreparedStatement prepCyr = null;
		
		try {

			if (filePart.getSize() > 0) {
				// prints out some information for debugging
				log.info("user upd phohto : " + filePart.getName() + ", " + filePart.getSize() + ", "
						+ filePart.getContentType());
				InputStream inputStream = filePart.getInputStream();

				con = dataSource.getConnection();
				
				prepCyr = con.prepareStatement("SET NAMES utf8mb4");
				prepCyr.executeQuery();
				
				
				stat = con.prepareStatement(SQL_UPD_IMG_HEADER_LIST);
				stat.setBlob(1, inputStream);
				stat.setString(2, getFileName(filePart));
				stat.setString(3, filePart.getContentType());
				stat.setInt(4, entityId);
				int row = stat.executeUpdate();

				log.debug("UPDATET ROWS AFTER UPD IMG " + row);
				if (row == 0) {
					addImg(entityId, filePart);
				}
			}
		} catch (SQLException e) {
			log.error("Error description", e);
			throw new DaoSystemException(e);
		} finally {
			log.debug("Close " + SQL_UPD_IMG_HEADER_LIST + " ResultSet and " + SQL_UPD_IMG_HEADER_LIST + " Statement");
			try {
				closeQuaetly(prepCyr, stat, con);
			} catch (Exception e1) {
				log.error(e1);
				throw new DaoSystemException(e1);
			}
		}
	}

	@Override
	public void delImgByEntityId(int entityId) throws NoSuchEntityException, DaoSystemException {

		Connection con = null;
		PreparedStatement stat = null;
		PreparedStatement stat1 = null;
		PreparedStatement stat0 = null;
		ResultSet res = null;

		try {

			// prints out some information for debugging
			log.info("user del phohto by id : " + entityId);

			con = dataSource.getConnection();

			stat0 = con.prepareStatement(SQL_GET_IMG_ID_BY_ENTITY_ID);
			stat0.setInt(1, entityId);
			res = stat0.executeQuery();

			int imgId = -1;
			if (res.next()) {
				imgId = res.getInt(IMG_HEADER_LIST_IMG_ID_COL_NAME);
				log.warn("ROWS to delete with entity id " + entityId + " was found in " + IMG_HEADER_LIST_TABL_NAME
						+ ", img id = " + imgId);
			} else {
				log.warn("ROWS to delete with entity id " + entityId + " not found in " + IMG_HEADER_LIST_TABL_NAME);
				return;
			}

			stat1 = con.prepareStatement(SQL_DEL_IMG_HEADER_LIST);
			stat1.setInt(1, entityId);
			stat1.executeUpdate();

			stat = con.prepareStatement(SQL_DEL_IMG);
			stat.setInt(1, imgId);
			stat.executeUpdate();

		} catch (SQLException e) {
			log.error("Error description", e);
			throw new DaoSystemException(e);
		} finally {
			log.debug("Close " + SQL_UPD_IMG_HEADER_LIST + " ResultSet and " + SQL_UPD_IMG_HEADER_LIST + " Statement");
			try {
				closeQuaetly(stat, stat1, stat0, con);
			} catch (Exception e1) {
				log.error(e1);
				throw new DaoSystemException(e1);
			}
		}

	}

}
