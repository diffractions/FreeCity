package entity.work;

public interface WorkFactory {

	void addDate(String dateFrom, String dateTo, int monthFrom, int monthTo, int dayFrom, int dayTo, String timeFrom,
			String timeTo);

	Work getWork();

}
