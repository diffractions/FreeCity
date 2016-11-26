package dao;

import java.util.concurrent.CopyOnWriteArrayList;

import dao.exceptions.DaoSystemException;
import dao.exceptions.NoSuchEntityException;
import entity.City;

public interface CityDao {
	public City getCityById(int cityId) throws NoSuchEntityException, DaoSystemException;

	public City getCityByShorName(String shortName) throws NoSuchEntityException, DaoSystemException;

	public CopyOnWriteArrayList<City> getCityAll() throws DaoSystemException;

	public int addCity(String cityName) throws DaoSystemException;

	public int delCity(String cityId) throws DaoSystemException;
}
