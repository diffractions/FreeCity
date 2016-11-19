package entity.work;

import java.util.List;
 

public interface WorkDate {

	public String getDateFrom();
	public String getDateTo();
	public List<WorkMonth> getMonths();
	public void addMonths(WorkMonth workMonths);
}
