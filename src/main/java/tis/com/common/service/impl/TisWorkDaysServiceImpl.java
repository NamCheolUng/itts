package tis.com.common.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import tis.com.common.service.TisWorkDaysService;
import tis.com.manager.managerHoliday.service.TbAllvcatnmanageService;
import tis.com.manager.managerHoliday.service.TbAllvcatnmanageVO;

@Service("TisWorkDaysService")
public class TisWorkDaysServiceImpl implements TisWorkDaysService {

	@Resource(name = "tbAllvcatnmanageService")
	private TbAllvcatnmanageService tbAllvcatnmanageService;
	
	@Override
	public int countWorkDays(String fromDate, String toDate, List<String> holidayList) throws Exception {
		if(fromDate == null || toDate == null) {
			return -1;
		}
		
		fromDate = fromDate.replace("/", "");
		toDate = toDate.replace("/", "");
		
		return countDays(fromDate, toDate) - countHoliday(fromDate, toDate, holidayList);
	}
	
	private static final int MILLISECOND_ON_DAY = 1000 * 60 * 60 * 24;
	
	private int countDays(String fromDate, String toDate) throws Exception {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
	    Date beginDate = formatter.parse(fromDate);
	    Date endDate = formatter.parse(toDate);
	    long diff = endDate.getTime() - beginDate.getTime();
	    int diffDays = (int)(diff / MILLISECOND_ON_DAY) + 1;
	    
	    return diffDays;
	}

	private int countHoliday(String fromDate, String toDate, List<String> holidayList) throws Exception {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		Calendar cal = Calendar.getInstance() ;
		
		Date beginDate = formatter.parse(fromDate);
		Date endDate = formatter.parse(toDate);
		Date checkDate = beginDate;
		
		int holidayCount = 0;
		
		boolean isStop = false;
		while(!isStop) {
			String checkDateString = formatter.format(checkDate);
			
			cal.setTime(checkDate);
			int dayInWeek = cal.get(Calendar.DAY_OF_WEEK);
			
			if(dayInWeek == Calendar.SUNDAY || dayInWeek == Calendar.SATURDAY) {
				holidayCount++;
			} else if(holidayList != null && holidayList.contains(checkDateString)) {
				holidayCount++;
			}

			if(checkDateString.equals(formatter.format(endDate))) {
				isStop = true;
				break;
			}
			
			checkDate = addDay(checkDate, 1);
		}
		
		return holidayCount;
	}
	
	private Date addDay(Date date, int day) {
		return new Date(date.getTime() + (day * MILLISECOND_ON_DAY));
	}
	
	private String addDay(String dateString, SimpleDateFormat returnFormatter, int day) throws Exception {
		Calendar cal = Calendar.getInstance();
	    cal.setTime(returnFormatter.parse(dateString));
	    cal.add(Calendar.DATE, day);
	    
	    return returnFormatter.format(cal.getTime());
	}

	@Override
	public List<String> getHolidayList(String fromDate, String toDate) throws Exception {
		List<String> holidayList = new ArrayList<String>();
		
		TbAllvcatnmanageVO tbAllvcatnmanageVO = new TbAllvcatnmanageVO();
		tbAllvcatnmanageVO.setBgnde(fromDate.substring(0,6));
		tbAllvcatnmanageVO.setEndde(toDate.substring(0,6));
		List<TbAllvcatnmanageVO> tbAllvcatnmanageList = (List<TbAllvcatnmanageVO>) tbAllvcatnmanageService.selectTbAllvcatnmanageListForMonth(tbAllvcatnmanageVO);
		
		if(tbAllvcatnmanageList.size() > 0) {
			SimpleDateFormat formatterHour = new SimpleDateFormat("yyyyMMdd");
			boolean isStop = false;
			
			// 전체사원 연차 리스트 전체 돌면서 공휴일 리스트 계산
			for(TbAllvcatnmanageVO vo : tbAllvcatnmanageList) {
				String currentDate = vo.getBgnde();
				do {
					holidayList.add(currentDate);
					
					// currentDate가  endde 보다 크면 계산 중단
					if(currentDate.compareTo(vo.getEndde()) >= 0) {
						isStop = true;
						break;
					}
					
					currentDate = addDay(currentDate, formatterHour, 1);
				} while(!isStop);
			}
		}
		
		return holidayList;
	}
}
