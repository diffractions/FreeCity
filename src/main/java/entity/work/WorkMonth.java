package entity.work;

import java.util.List;

public interface WorkMonth {

	public int getMonthFrom();
	public int getMonthTo();
	public List<WorkDays> getDays();
	public void addDay(WorkDays day);
}
