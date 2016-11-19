package entity.work.impl;

import java.util.LinkedList;
import java.util.List;

import entity.work.Work;
import entity.work.WorkDate; 

public class SimpleWorkImpl implements Work {



	@Override
	public String toString() {
		return "SimpleWorkImpl [dates=" + dates + "]";
	}

	List<WorkDate> dates;

	public List<WorkDate> getDate(){
		return dates;
	}

	public void addDate(WorkDate date){
		if(this.dates== null)
			this.dates=new LinkedList<WorkDate>();
		this.dates.add(date);
	}
}
