package tis.com.common.service;

import java.util.List;


public interface TisWorkHoursService {
	
	public int countWorkHours(String fromDate, String toDate) throws Exception;
	
	public int countWorkHours(String fromDate, String toDate, List<String> holidayList) throws Exception;
}
