package tis.com.common.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import tis.com.common.service.TisUtilService;

@Service("tisUtilService")
public class TisUtilServiceImpl implements TisUtilService {

	@Override
	public String getFirstDayOfMonth(Date date) throws Exception {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		
		int startDay = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
		
		String yearString = Integer.toString(year);
		String monthString = String.format("%02d", month + 1);
		String startDayString = String.format("%02d", startDay);
		
		return yearString + "/" + monthString + "/" + startDayString;
	}

	@Override
	public String getLastDayOfMonth(Date date) throws Exception {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		
		int endDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		
		String yearString = Integer.toString(year);
		String monthString = String.format("%02d", month + 1);
		String endDayString = String.format("%02d", endDay);
		
		return yearString + "/" + monthString + "/" + endDayString;
	}
}
