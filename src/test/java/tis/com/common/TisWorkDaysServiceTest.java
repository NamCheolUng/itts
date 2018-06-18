package tis.com.common;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import tis.com.common.service.TisWorkDaysService;
import tis.com.common.service.impl.TisWorkDaysServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class TisWorkDaysServiceTest {
	
	@InjectMocks
	private TisWorkDaysService tisWorkDaysService = new TisWorkDaysServiceImpl();
	
	@Before
	public void setup() {
	}

	@Test
	public void workDayCountWithNoWeekendTest() throws Exception {
		int actual = tisWorkDaysService.countWorkDays("2017/12/20", "2017/12/20", null);
		Assert.assertEquals(1, actual);
		
		actual = tisWorkDaysService.countWorkDays("2017/12/20", "2017/12/21", null);
		Assert.assertEquals(2, actual);
	}
	
	@Test
	public void workDayCountWithWeekendTest() throws Exception {
		List<String> holidayList = null;
		
		int actual = tisWorkDaysService.countWorkDays("2018/01/05", "2018/01/08", holidayList);
		Assert.assertEquals(2, actual);
		
		actual = tisWorkDaysService.countWorkDays("2018/01/05", "2018/01/07", holidayList);
		Assert.assertEquals(1, actual);
		
		actual = tisWorkDaysService.countWorkDays("2018/01/06", "2018/01/08", holidayList);
		Assert.assertEquals(1, actual);
		
		actual = tisWorkDaysService.countWorkDays("2018/01/05", "2018/01/18", holidayList);
		Assert.assertEquals(10, actual);
	}
	
	@Test
	public void workDayCountWithHolidayTest() throws Exception {
		List<String> holidayList = new ArrayList<String>();
		
		holidayList.clear();
		holidayList.add("20180101");
		int actual = tisWorkDaysService.countWorkDays("2018/01/01", "2018/01/05", holidayList);
		Assert.assertEquals(4, actual);
		
		holidayList.clear();
		holidayList.add("20180105");
		actual = tisWorkDaysService.countWorkDays("2018/01/01", "2018/01/05", holidayList);
		Assert.assertEquals(4, actual);
		
		holidayList.clear();
		holidayList.add("20180102");
		holidayList.add("20180104");
		actual = tisWorkDaysService.countWorkDays("2018/01/01", "2018/01/05", holidayList);
		Assert.assertEquals(3, actual);
		
		holidayList.clear();
		holidayList.add("20180101");
		holidayList.add("20180102");
		holidayList.add("20180103");
		holidayList.add("20180104");
		holidayList.add("20180105");
		holidayList.add("20180106");
		actual = tisWorkDaysService.countWorkDays("2018/01/01", "2018/01/05", holidayList);
		Assert.assertEquals(0, actual);
	}
	
	@Test
	public void workDayCountWithWeekendAndHolidayTest() throws Exception {
		List<String> holidayList = new ArrayList<String>();
		
		holidayList.clear();
		holidayList.add("20180108");
		holidayList.add("20180109");
		int actual = tisWorkDaysService.countWorkDays("2018/01/05", "2018/01/18", holidayList);
		Assert.assertEquals(8, actual);
		
		holidayList.clear();
		holidayList.add("20180107");
		holidayList.add("20180108");
		actual = tisWorkDaysService.countWorkDays("2018/01/05", "2018/01/18", holidayList);
		Assert.assertEquals(9, actual);
		
		holidayList.clear();
		holidayList.add("20180106");
		holidayList.add("20180107");
		actual = tisWorkDaysService.countWorkDays("2018/01/05", "2018/01/18", holidayList);
		Assert.assertEquals(10, actual);
		
		holidayList.clear();
		holidayList.add("20180118");
		holidayList.add("20180119");
		holidayList.add("20180131");
		actual = tisWorkDaysService.countWorkDays("2018/01/05", "2018/01/18", holidayList);
		Assert.assertEquals(9, actual);
	}
}
