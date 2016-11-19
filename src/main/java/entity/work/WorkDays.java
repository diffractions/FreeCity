package entity.work;

import java.util.List;
 

public interface WorkDays {

	public int getDaysFrom();
	public int getDaysTo();
	public List<WorkTime> getTime();
	public void addTime(WorkTime workTime);
}
