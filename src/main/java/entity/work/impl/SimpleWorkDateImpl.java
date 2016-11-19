package entity.work.impl;

import java.util.LinkedList;
import java.util.List;

import entity.work.WorkDate; 
import entity.work.WorkMonth;

public class SimpleWorkDateImpl implements WorkDate {

	@Override
	public String toString() {
		return "SimpleWorkDateImpl [from=" + from + ", to=" + to + ", days=" + month + "]";
	}

	private String from;
	private String to;
	List<WorkMonth> month;

	public SimpleWorkDateImpl(String from, String to){
		this.from = from;	
		this.to = to;	
	}

	public SimpleWorkDateImpl(String from, String to, WorkMonth months){
		this.from = from;	
		this.to = to;	
		this.addMonths(months);	
	}

	public String getDateFrom(){
		return from;
	}
	public String getDateTo(){
		return to;
	}
	public List<WorkMonth> getMonths(){
		return month;
	}

	public void addMonths(WorkMonth day){
		if(this.month== null)
			this.month=new LinkedList<WorkMonth>();
		this.month.add(day);
	}

}
