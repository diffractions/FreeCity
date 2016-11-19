package entity.work.impl;

import org.apache.log4j.Logger;

import entity.work.Work;
import entity.work.WorkDate;
import entity.work.WorkDays;
import entity.work.WorkFactory;
import entity.work.WorkMonth;
import entity.work.WorkTime;

public class SimpleWorkFactoryImpl implements WorkFactory {

	public static Logger log = Logger.getLogger(Thread.currentThread().getStackTrace()[1].getClassName());
	SimpleWorkImpl work;

	public SimpleWorkFactoryImpl() {
		work = new SimpleWorkImpl();
	}

	// @Override
	// public void addDate(String dateFrom, String dateTo, int dayFrom, int
	// dayTo, String timeFrom, String timeTo) {
	// if(work.getDate()!=null)
	// for (WorkDate date : work.getDate()) {
	// if (date.getDateFrom().equals(dateFrom) &&
	// date.getDateTo().equals(dateTo)) {
	//
	// for (WorkDays day : date.getMonths()) {
	// if (day.getDaysFrom() == dayFrom && day.getDaysTo() == dayTo) {
	//
	// for (WorkTime time : day.getTime()) {
	// if (time.getTimeFrom().equals(timeFrom) &&
	// time.getTimeFrom().equals(timeTo)) {
	// return;
	// }
	// }
	// day.addTime(new SimpleWorkTimeImpl(timeFrom, timeTo));
	// return;
	// }
	// }
	// date.addMonths(new SimpleWorkDaysImpl(dayFrom, dayTo, new
	// SimpleWorkTimeImpl(timeFrom, timeTo)));
	// return;
	// }
	// }
	// work.addDate(new SimpleWorkDateImpl(dateFrom, dateTo,
	// new SimpleWorkDaysImpl(dayFrom, dayTo, new SimpleWorkTimeImpl(timeFrom,
	// timeTo))));
	// }

	@Override
	public void addDate(String dateFrom, String dateTo, int monthFrom, int monthTo, int dayFrom, int dayTo,
			String timeFrom, String timeTo) {

		if (work.getDate() != null)
			for (WorkDate date : work.getDate()) {

				if (date.getDateFrom().equals(dateFrom) && date.getDateTo().equals(dateTo)) {
					for (WorkMonth month : date.getMonths()) {

						if (month.getMonthFrom() == monthFrom && month.getMonthTo() == monthTo) {
							for (WorkDays day : month.getDays()) {

								if (day.getDaysFrom() == dayFrom && day.getDaysTo() == dayTo) {
									for (WorkTime time : day.getTime()) {

										if (time.getTimeFrom().equals(timeFrom) && time.getTimeFrom().equals(timeTo)) {
											return;
										}

									}
									day.addTime(new SimpleWorkTimeImpl(timeFrom, timeTo));
									return;
								}

							}
							month.addDay(
									new SimpleWorkDaysImpl(dayFrom, dayTo, new SimpleWorkTimeImpl(timeFrom, timeTo)));
							return;
						}

					}
					date.addMonths(new SimpleWorkMonthImpl(monthFrom, monthTo,
							new SimpleWorkDaysImpl(dayFrom, dayTo, new SimpleWorkTimeImpl(timeFrom, timeTo))));
					return;
				}

			}
		work.addDate(new SimpleWorkDateImpl(dateFrom, dateTo, new SimpleWorkMonthImpl(monthFrom, monthTo,
				new SimpleWorkDaysImpl(dayFrom, dayTo, new SimpleWorkTimeImpl(timeFrom, timeTo)))));
	}

	@Override
	public Work getWork() {
		log.debug("WORKING DATE: " + work);
		return work;
	}

	@Override
	public String toString() {
		return "SimpleWorkFactoryImpl [work=" + work + "]";
	}

}
