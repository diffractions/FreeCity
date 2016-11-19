package entity.work.impl;

import java.util.LinkedList;
import java.util.List;

import entity.work.WorkDays;
import entity.work.WorkTime;

public class SimpleWorkDaysImpl implements WorkDays {
	@Override
	public String toString() {
		return "SimpleWorkDaysImpl [from=" + from + ", to=" + to + ", time=" + time + "]";
	}

	private int from;
	private int to;
	List<WorkTime> time;

	public SimpleWorkDaysImpl(int from, int to){
		this.from = from;	
		this.to = to;	
	}

	public SimpleWorkDaysImpl(int from, int to, WorkTime time){
		this.from = from;	
		this.to = to;	
		this.addTime(time);	
	}


	@Override
	public int getDaysFrom(){
		return from;
	}


	@Override
	public int getDaysTo(){
		return to;
	}


	@Override
	public List<WorkTime> getTime(){
		return time;
	}

	public void addTime(WorkTime times){
		if(this.time== null)
			this.time=new LinkedList<WorkTime>();
		this.time.add(times);
	}

}
