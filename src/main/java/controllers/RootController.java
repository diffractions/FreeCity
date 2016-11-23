package controllers;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import dao.CityDao;
import dao.SectionDao;
import dao.ShowedItemDao;
import dao.TagDao;
import dao.exceptions.DaoSystemException;
import dao.exceptions.NoSuchEntityException;
import entity.City;
import entity.Section;
import entity.ShowedItem;
import entity.Tag;
import inject.Inject;

public class RootController extends DependencyInjectionServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static Logger log = Logger.getLogger(Thread.currentThread().getStackTrace()[1].getClassName());
	@Inject("sectionDao")
	public SectionDao menu;

	@Inject("cityDao")
	protected CityDao cityDao;

	@Inject("tagDao")
	protected TagDao tagDao;

	@Inject("entityShowDao")
	public ShowedItemDao entityDao;

	public static final String ATTRIBUTE_MENU_MODEL_TO_VIEW = "root_menu";
	public static final String ATTRIBUTE_CITIES_MODEL_TO_VIEW = "cities";
	public static final String ATTRIBUTE_TAGS_MODEL_TO_VIEW = "tag_atr";
	public static final String ATTRIBUTE_CITY_MODEL_TO_VIEW = "city_atr";
	public static final String ATTRIBUTE_ROOT_SECTION_TO_VIEW = "rootSection";
	public static final String ATTRIBUTE_SHOWED_SECTION_TO_VIEW = "showedSection";
	public static final String COOKIE_CITY = "city";

	public static final String ATTRIBUTE_LAST_PLACES_MODEL_TO_VIEW = "last_places";
	public static final String ATTRIBUTE_LAST_PLACES_ID = "101";
	public static final String ATTRIBUTE_LAST_OFFER_MODEL_TO_VIEW = "last_odffer";
	public static final String ATTRIBUTE_LAST_OFFER_ID = "102";

	public static final String ATTRIBUTE_SECTION_COLL_NAME = "section";
	public static final String ATTRIBUTE_SECTION_VIEW = "";
	public static final String ATTRIBUTE_SECTION_VIEW_MODEL_TO_VIEW = "section_view";
	public static final String ATTRIBUTE_HEAD_TITLE = "head_title";

	// public static final String ATTRIBUTE_GROUP_COLL_NAME = "group";

	protected void addStandartModels(HttpServletRequest request, int city, int section)
			throws DaoSystemException, NoSuchEntityException {
		addStandartModels(request, menu, cityDao, city, tagDao, section);
	}

	
	protected void addStandartModels(HttpServletRequest request)
			throws DaoSystemException, NoSuchEntityException {
		addStandartModels(request, -1);
	}
	
	
	protected void addStandartModels(HttpServletRequest request, int section)
			throws DaoSystemException, NoSuchEntityException {
		int cityInCook = -1;
		int city = -1;
		Cookie[] cookies = request.getCookies();
		if ((cityInCook = findCity(cookies)) != -1) {
			city = Integer.parseInt(cookies[cityInCook].getValue());
		}
		addStandartModels(request, city, section);
	}

	private void addStandartModels(HttpServletRequest request, SectionDao menuDao, CityDao cityDao, int city,
			TagDao tagDao, int section) throws DaoSystemException, NoSuchEntityException {

		List<Section> sectionModel = menuDao.getSectionList();
		log.debug(">>>  Add " + ATTRIBUTE_MENU_MODEL_TO_VIEW + " to request attribute");
		request.setAttribute(ATTRIBUTE_MENU_MODEL_TO_VIEW, sectionModel);
		log.debug(">>> " + ATTRIBUTE_MENU_MODEL_TO_VIEW + " MODEL:" + sectionModel);
		log.debug("ATTRIBUTE_MODEL_TO_VIEW : + " + ATTRIBUTE_MENU_MODEL_TO_VIEW + " befor JSP : "
				+ request.getAttribute(ATTRIBUTE_MENU_MODEL_TO_VIEW));

		if (section != -1) {

			Section section2 = menuDao.getSectionById(section);

			int rSection = section2.getRootSetion().getSectionId();
			log.debug(">>>  Add " + ATTRIBUTE_ROOT_SECTION_TO_VIEW + " to request attribute");
			request.setAttribute(ATTRIBUTE_ROOT_SECTION_TO_VIEW, rSection);
			log.trace(">>> " + ATTRIBUTE_ROOT_SECTION_TO_VIEW + " MODEL:" + rSection);

			log.debug(">>>  Add " + ATTRIBUTE_SHOWED_SECTION_TO_VIEW + " to request attribute");
			request.setAttribute(ATTRIBUTE_SHOWED_SECTION_TO_VIEW, section);
			log.trace(">>> " + ATTRIBUTE_SHOWED_SECTION_TO_VIEW + " MODEL:" + section);

			int s_v = section2.getSectionView();
			log.debug(">>>  Add " + ATTRIBUTE_SECTION_VIEW_MODEL_TO_VIEW + " to request attribute");
			request.setAttribute(ATTRIBUTE_SECTION_VIEW_MODEL_TO_VIEW, s_v);
			log.trace(">>> " + ATTRIBUTE_SECTION_VIEW_MODEL_TO_VIEW + " MODEL:" + s_v);

			String head_title = section2.getSectionName();
			log.debug(">>>  Add " + ATTRIBUTE_HEAD_TITLE + " to request attribute");
			request.setAttribute(ATTRIBUTE_HEAD_TITLE, head_title);
			log.trace(">>> " + ATTRIBUTE_HEAD_TITLE + " MODEL:" + head_title);

		}

		List<City> cityModel = cityDao.getCityAll();
		log.debug(">>>  Add " + ATTRIBUTE_CITIES_MODEL_TO_VIEW + " to request attribute");
		request.setAttribute(ATTRIBUTE_CITIES_MODEL_TO_VIEW, cityModel);
		log.trace(">>> " + ATTRIBUTE_CITIES_MODEL_TO_VIEW + " MODEL:" + cityModel);
		log.trace("ATTRIBUTE_MODEL_TO_VIEW : + " + ATTRIBUTE_CITIES_MODEL_TO_VIEW + " befor JSP : "
				+ request.getAttribute(ATTRIBUTE_CITIES_MODEL_TO_VIEW));

		log.debug("city from cookie : " + city);

		String city_sel = "";
		if (city != -1) {
			City city_sel1 = cityDao.getCityById(city);
			log.debug(">>>  Add " + ATTRIBUTE_CITY_MODEL_TO_VIEW + " to request attribute");
			request.setAttribute(ATTRIBUTE_CITY_MODEL_TO_VIEW, city_sel1);
			log.trace(">>> " + ATTRIBUTE_CITY_MODEL_TO_VIEW + " MODEL: " + city_sel1);
			city_sel = "" + city_sel1.getId();
		}

		CopyOnWriteArraySet<ShowedItem> lastPlacesItemModel = null;
		lastPlacesItemModel = entityDao.executeSelectAll(city_sel, null, ATTRIBUTE_LAST_PLACES_ID, "1,2,3,4,5,6,7,8,9",
				null, null, "" + 5, null, null, null);

		log.debug(">>>  Add " + ATTRIBUTE_LAST_PLACES_MODEL_TO_VIEW + " to request attribute");
		request.setAttribute(ATTRIBUTE_LAST_PLACES_MODEL_TO_VIEW, lastPlacesItemModel);
		log.trace(">>> " + ATTRIBUTE_LAST_PLACES_MODEL_TO_VIEW + " MODEL:" + lastPlacesItemModel);

		CopyOnWriteArraySet<ShowedItem> lastOfferItemModel = null;
		lastOfferItemModel = entityDao.executeSelectAll(city_sel, null, ATTRIBUTE_LAST_OFFER_ID, "1,2,3,4,5,6,7,8,9",
				null, null, "" + 5, null, null, null);

		log.debug(">>>  Add " + ATTRIBUTE_LAST_OFFER_MODEL_TO_VIEW + " to request attribute");
		request.setAttribute(ATTRIBUTE_LAST_OFFER_MODEL_TO_VIEW, lastOfferItemModel);
		log.trace(">>> " + ATTRIBUTE_LAST_OFFER_MODEL_TO_VIEW + " MODEL:" + lastOfferItemModel);

		CopyOnWriteArraySet<Tag> tagModel = tagDao.selectAll();
		log.debug(">>>  Add " + ATTRIBUTE_TAGS_MODEL_TO_VIEW + " to request attribute");
		request.setAttribute(ATTRIBUTE_TAGS_MODEL_TO_VIEW, tagModel);
		log.trace(">>> " + ATTRIBUTE_TAGS_MODEL_TO_VIEW + " MODEL:" + lastOfferItemModel);

	}

	protected int findCity(Cookie[] coockies) {

		log.trace("coockies: " + Arrays.deepToString(coockies));

		if (coockies == null)
			return -1;
		for (int i = 0; i < coockies.length; i++) {

			log.trace("coockie: " + coockies[i].getName() + " " + coockies[i].getValue());
			if (coockies[i].getName().equals(COOKIE_CITY))
				return i;
		}
		return -1;
	}
	


}
