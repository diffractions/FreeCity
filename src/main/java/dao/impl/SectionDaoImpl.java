package dao.impl;

import static utils.JBDCUtil.closeQuaetly;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement; 
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import dao.SectionDao;
import dao.exceptions.DaoSystemException;
import dao.exceptions.NoSuchEntityException;
import entity.Section;
import entity.impl.SimpleSection;

public class SectionDaoImpl implements SectionDao {

	private final static String SECTION_TABL_NAME = "freecity.section";
	private final static String SECTION_ID_COL_NAME = "section.section_id";
	private final static String SECTION_NAME_COL_NAME = "section.name";
	private final static String SECTION_PARRENT_ID_COL_NAME = "section.section_parrent";
	private final static String SECTION_VIEW_COL_NAME = "section.section_view";

	public static final String SQL_SECTION_UPDATE = "";

	public static final String SQL_SECTION_DEL = "DELETE FROM " + SECTION_TABL_NAME + " WHERE " + SECTION_ID_COL_NAME
			+ "= ?";
	public static final String SQL_SECTION_ADD = "insert into " + SECTION_TABL_NAME + "(" + SECTION_NAME_COL_NAME + ", "
			+ SECTION_PARRENT_ID_COL_NAME + ", " + SECTION_VIEW_COL_NAME + ") values (?, ?, ?)";
	private static final String SQL_SELECT_ALL_SECTIONS = "select " + SECTION_ID_COL_NAME + ", " + SECTION_NAME_COL_NAME
			+ ", " + SECTION_PARRENT_ID_COL_NAME + ", " + SECTION_VIEW_COL_NAME + " from " + SECTION_TABL_NAME;

	public static Logger log = Logger.getLogger(Thread.currentThread().getStackTrace()[1].getClassName());
	private List<Section> sectionRootList;
	private Map<Integer, Section> sectionList;

	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public List<Section> getSectionList() throws DaoSystemException {
		if (sectionRootList == null)
			updateSectionList();
		return sectionRootList;
	}

	@Override
	public int updateSection(int sectionId, String sectionName, int parentSectionId)
			throws DaoSystemException, NoSuchEntityException {
		Connection con = null;
		PreparedStatement stat = null;
		try {
			con = dataSource.getConnection();
			stat = con.prepareStatement(SQL_SECTION_UPDATE);
			stat.setInt(1, sectionId);
			stat.setString(2, sectionName);
			stat.setInt(3, parentSectionId);
			int updatedCount = stat.executeUpdate();
			if (updatedCount > 0) {
				updateSectionList();
				return updatedCount;
			}
		} catch (SQLException e) {
			log.error("Error description", e);
			throw new DaoSystemException(e);
		} finally {
			log.debug("Close SELECT_ALL_SQL ResultSet and SELECT_ALL_SQL Statement");
			try {
				closeQuaetly(con, stat);
			} catch (Exception e1) {
				log.error(e1);
				throw new DaoSystemException(e1);
			}
		}
		throw new NoSuchEntityException("section id :" + sectionId + " not found!");
	}

	private void updateSectionList() throws DaoSystemException {
		Connection con = null;
		Statement stat = null;
		try {
			con = dataSource.getConnection();
			stat = con.createStatement();

			ResultSet sectionResSet = stat.executeQuery(SQL_SELECT_ALL_SECTIONS);
			if (!sectionResSet.wasNull()) {
				sectionRootList = new LinkedList<Section>();
				sectionList = new HashMap<Integer, Section>();
			}

			while (sectionResSet.next()) {
				int sectionId = sectionResSet.getInt(SECTION_ID_COL_NAME);
				int parrentId = sectionResSet.getInt(SECTION_PARRENT_ID_COL_NAME);
				String sectionName = sectionResSet.getString(SECTION_NAME_COL_NAME);
				int sectionView = sectionResSet.getInt(SECTION_VIEW_COL_NAME);
				Section add = new SimpleSection(sectionId, sectionName, parrentId, sectionView);
				if (add.getParrentId() == add.getSectionId()) {
					add.setLevel(0);
					sectionRootList.add(add);
				}
				sectionList.put(sectionId, add);
			}
			for (Section s : sectionRootList) {
				fillChilds(s);
			}

		} catch (SQLException e) {
			log.error("Error description", e);
			throw new DaoSystemException(e);
		} finally {

			Collections.sort(sectionRootList, new Comparator<Section>() {
				@Override
				public int compare(Section o1, Section o2) {
					return o1.getParrentId() - o2.getParrentId();
				}
			});

			log.debug("Close SELECT_ALL_SQL ResultSet and SELECT_ALL_SQL Statement");
			try {
				closeQuaetly(con, stat);
			} catch (Exception e1) {
				log.error(e1);
				throw new DaoSystemException(e1);
			}
		}

	}

	private void addChildSections(List<Section> list, Section s) {
		for (Section s0 : s.getChildSections()) {
			list.add(s0);
			if (s0.getChildSections() != null) {
				addChildSections(list, s0);
			}
		}
	}

	private void fillChilds(Section a) {

		for (Section s : sectionList.values()) {
			if (a.getSectionId() == s.getParrentId() && a.getSectionId() != s.getSectionId()) {
				s.setParrent(a);
				s.setLevel(a.getLevel() + 1);
				a.addChildSection(s);
				fillChilds(s);
			}
		}

		List<Section> list = new LinkedList<>();
		list.add(a);
		if (a.getChildSections() != null) {
			addChildSections(list, a);
		}
		a.setAllChilds(list);
	}

	@Override
	public Section getSectionById(int sectionId) throws NoSuchEntityException, DaoSystemException {
		if (sectionRootList == null)
			updateSectionList();
		Section s = null;
		if ((s = sectionList.get(sectionId)) != null)
			return s;

		throw new NoSuchEntityException("Section with section id :" + sectionId + " not found.");

	}

	@Override
	public int addSection(String sectionName, String parentSectionId, String view) throws DaoSystemException {
		Connection con = null;
		PreparedStatement stat = null;
		try {
			con = dataSource.getConnection();
			stat = con.prepareStatement(SQL_SECTION_ADD);
			stat.setString(1, sectionName);
			stat.setInt(2, Integer.parseInt(parentSectionId));
			stat.setInt(3, Integer.parseInt(view));
			int addedCount = stat.executeUpdate();
			return addedCount;
		} catch (SQLException e) {
			log.error("Error description", e);
			throw new DaoSystemException(e);
		} finally {

			updateSectionList();
			log.debug("Close SQL_SECTION_ADD ResultSet and SQL_SECTION_ADD Statement");
			try {
				closeQuaetly(con, stat);
			} catch (Exception e1) {
				log.error(e1);
				throw new DaoSystemException(e1);
			}
		}

	}

	@Override
	public int delSection(String trim) throws DaoSystemException {
		Connection con = null;
		PreparedStatement stat = null;
		try {
			con = dataSource.getConnection();
			stat = con.prepareStatement(SQL_SECTION_DEL);
			stat.setInt(1, Integer.parseInt(trim));
			int addedCount = stat.executeUpdate();
			return addedCount;
		} catch (SQLException e) {
			log.error("Error description", e);
			throw new DaoSystemException(e);
		} finally {
			updateSectionList();
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
