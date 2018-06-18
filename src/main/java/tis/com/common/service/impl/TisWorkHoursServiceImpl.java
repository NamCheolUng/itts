package tis.com.common.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import tis.com.common.service.TisWorkDaysService;
import tis.com.common.service.TisWorkHoursService;

@Service("TisWorkHoursService")
public class TisWorkHoursServiceImpl implements TisWorkHoursService {
	
	private static final int MILLISECOND_FOR_A_HOUR = 1000 * 60 * 60;
	
	@Resource(name = "TisWorkDaysService")
	private TisWorkDaysService tisWorkDaysService;
	
	@Override
	public int countWorkHours(String fromDate, String toDate) throws Exception {
		if(fromDate == null || toDate == null) {
			return -1;
		}
		
		fromDate = fromDate.replace("/", "").replace(":", "").replace(" ", "");
		toDate = toDate.replace("/", "").replace(":", "").replace(" ", "");
		
		List<String> holidayList = tisWorkDaysService.getHolidayList(fromDate, toDate);
		
		return countWorkHours(fromDate, toDate, holidayList);
	}

	@Override
	public int countWorkHours(String fromDate, String toDate, List<String> holidayList) throws Exception {
		if(fromDate == null || toDate == null) {
			return -1;
		}
		
		fromDate = fromDate.replace("/", "").replace(":", "").replace(" ", "");
		toDate = toDate.replace("/", "").replace(":", "").replace(" ", "");
		
		return countHours(fromDate, toDate, holidayList);
	}
	
	private int countHours(String fromDate, String toDate, List<String> holidayList) throws Exception {
	    if(isOneDay(fromDate, toDate)) {
	    	return countHoursDiff(fromDate, toDate);
	    } else {
	    	return countHoursDiffMoreThanOneDay(fromDate, toDate, holidayList);
	    }
	}
	
	private boolean isOneDay(String fromDate, String toDate) throws Exception {
		SimpleDateFormat formatterHour = new SimpleDateFormat("yyyyMMddHHmm");
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(formatterHour.parse(toDate));
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		
		if(hour == 0) {
			cal.add(Calendar.DAY_OF_YEAR, -1);
			toDate = formatterHour.format(cal.getTime());
		}
		
		return fromDate.substring(0, 8).equals(toDate.substring(0, 8));
	}

	private int countHoursDiff(String fromDate, String toDate) throws Exception {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmm");
		
	    long diff = formatter.parse(toDate).getTime() - formatter.parse(fromDate).getTime();
	    int diffHours = (int)(diff / MILLISECOND_FOR_A_HOUR);
	    
	    return diffHours;
	}
	
	private int countHoursDiffMoreThanOneDay(String fromDate, String toDate, List<String> holidayList) throws Exception {
		SimpleDateFormat formatterDay = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat formatterHour = new SimpleDateFormat("yyyyMMddHHmm");
		
		String toDateYYYYMMDD = toDate.substring(0, 8);
		String checkDay = fromDate.substring(0, 8);
		
		Calendar cal = Calendar.getInstance();
		
		int workHours = 0;
		
		do {
			// 공휴일 제외
			if(holidayList != null && holidayList.contains(checkDay)) {
				checkDay = addDay(checkDay, formatterDay, 1);
				continue;
			}
			
			// 주말 제외
			cal.setTime(formatterDay.parse(checkDay));
			int dayInWeek = cal.get(Calendar.DAY_OF_WEEK);
			if(dayInWeek == Calendar.SUNDAY || dayInWeek == Calendar.SATURDAY) {
				checkDay = addDay(checkDay, formatterDay, 1);
				continue;
			}
			
			// 시작일이면
			if(checkDay.equals(fromDate.substring(0, 8))) {
				// 18시까지 근무 시간 구하기
			    cal.setTime(formatterHour.parse(fromDate));
			    cal.set(Calendar.HOUR_OF_DAY, 18);
			    String workEndHourOfDay = formatterHour.format(cal.getTime());
			    
			    workHours += countHoursDiff(fromDate, workEndHourOfDay);
			} else if(checkDay.equals(toDateYYYYMMDD)) { // 종료일이면
				// 9시부서 근무 시간 구하기
			    cal.setTime(formatterHour.parse(toDate));
			    cal.set(Calendar.HOUR_OF_DAY, 9);
			    String workStartHourOfDay = formatterHour.format(cal.getTime());

			    workHours += countHoursDiff(workStartHourOfDay, toDate);
			} else { // 시작,종료 제외한 일이면
				workHours += 9;
			}
			
			checkDay = addDay(checkDay, formatterDay, 1);
		} while(checkDay.compareTo(toDateYYYYMMDD) <= 0); // 계산중인 일이 종료일보다 작거나 같으면 loop
		
		return workHours;
	}
	
	private String addDay(String dateString, SimpleDateFormat returnFormatter, int day) throws Exception {
		Calendar cal = Calendar.getInstance();
	    cal.setTime(returnFormatter.parse(dateString));
	    cal.add(Calendar.DATE, day);
	    
	    return returnFormatter.format(cal.getTime());
	}
}
