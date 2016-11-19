package entity.work.impl;

import entity.work.WorkTime;

public class SimpleWorkTimeImpl implements WorkTime {
	@Override
	public String toString() {
		return "SimpleWorkTimeImpl [from=" + from + ", to=" + to + "]";
	}

	private String from;
	private String to;

	public SimpleWorkTimeImpl(String from, String to) {
		this.from = from;
		this.to = to;
	}

	@Override
	public String getTimeFrom() {
		return from;
	}

	@Override
	public String getTimeTo() {
		return to;
	}
}
