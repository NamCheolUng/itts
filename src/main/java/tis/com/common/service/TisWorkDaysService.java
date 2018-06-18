package tis.com.common.service;

import java.util.List;


public interface TisWorkDaysService {
	
	public int countWorkDays(String fromDate, String toDate, List<String> holidayList) throws Exception;
	
	public List<String> getHolidayList(String fromDate, String toDate) throws Exception;
}
