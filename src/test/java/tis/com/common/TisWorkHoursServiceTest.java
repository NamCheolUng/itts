package tis.com.common;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import tis.com.common.service.TisWorkHoursService;
import tis.com.common.service.impl.TisWorkHoursServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class TisWorkHoursServiceTest {
	
	@InjectMocks
	private TisWorkHoursService tisWorkHoursService = new TisWorkHoursServiceImpl();
	
	@Before
	public void setup() {
	}

	@Test
	public void workHoursCountWithinOneDay() throws Exception {
		int actual = tisWorkHoursService.countWorkHours("2018/02/12 09:00", "2018/02/12 10:00", null);
		Assert.assertEquals(1, actual);
		
		actual = tisWorkHoursService.countWorkHours("2018/02/12 00:00", "2018/02/12 03:00", null);
		Assert.assertEquals(3, actual);
		
		actual = tisWorkHoursService.countWorkHours("2018/02/12 19:00", "2018/02/13 00:00", null);
		Assert.assertEquals(5, actual);
	}
	
	@Test
	public void workHoursCountMoreThanOneDayWithoutHolidayTest() throws Exception {
		int actual = tisWorkHoursService.countWorkHours("2018/02/12 09:00", "2018/02/13 10:00", null);
		Assert.assertEquals(10, actual);
		
		actual = tisWorkHoursService.countWorkHours("2018/02/12 17:00", "2018/02/14 10:00", null);
		Assert.assertEquals(11, actual);
		
		actual = tisWorkHoursService.countWorkHours("2018/02/12 09:00", "2018/02/13 18:00", null);
		Assert.assertEquals(18, actual);
	}
	
	@Test
	public void workHoursCountMoreThanOneDayWithHolidayTest() throws Exception {
		List<String> holidayList = new ArrayList<String>();
		
		holidayList.clear();
		holidayList.add("20180213");
		int actual = tisWorkHoursService.countWorkHours("2018/02/12 17:00", "2018/02/14 10:00", holidayList);
		Assert.assertEquals(2, actual);
		
		holidayList.clear();
		holidayList.add("20180213");
		holidayList.add("20180214");
		actual = tisWorkHoursService.countWorkHours("2018/02/12 17:00", "2018/02/16 10:00", holidayList);
		Assert.assertEquals(11, actual);
		
		holidayList.clear();
		actual = tisWorkHoursService.countWorkHours("2018/02/09 17:00", "2018/02/12 10:00", holidayList);
		Assert.assertEquals(2, actual);
		
		holidayList.clear();
		holidayList.add("20180212");
		actual = tisWorkHoursService.countWorkHours("2018/02/09 17:00", "2018/02/13 10:00", holidayList);
		Assert.assertEquals(2, actual);
		
		holidayList.clear();
		holidayList.add("20180227");
		actual = tisWorkHoursService.countWorkHours("2018/02/27 09:00", "2018/03/01 18:00", holidayList);
		Assert.assertEquals(18, actual);
		
		holidayList.clear();
		holidayList.add("20180228");
		actual = tisWorkHoursService.countWorkHours("2018/02/26 09:00", "2018/02/28 16:00", holidayList);
		Assert.assertEquals(18, actual);

	}
}
