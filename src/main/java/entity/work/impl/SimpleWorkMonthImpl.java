package entity.work.impl;

import java.util.LinkedList;
import java.util.List;

import entity.work.WorkDays;
import entity.work.WorkMonth;

public class SimpleWorkMonthImpl implements WorkMonth {

	private int from;
	private int to;
	List<WorkDays> days;

	public SimpleWorkMonthImpl(int from, int to) {
		this.from = from;
		this.to = to;
	}

	public SimpleWorkMonthImpl(int from, int to, WorkDays day) {
		this.from = from;
		this.to = to;
		this.addDay(day);
	}

	@Override
	public String toString() {
		return "SimpleWorkMonthImpl [from=" + from + ", to=" + to + ", days=" + days + "]";
	}

	@Override
	public int getMonthFrom() {
		return from;
	}

	@Override
	public int getMonthTo() {
		return to;
	}

	@Override
	public List<WorkDays> getDays() {
		return days;
	}

	@Override
	public void addDay(WorkDays day) {
		if (this.days == null)
			this.days = new LinkedList<WorkDays>();
		this.days.add(day);

	}

}
