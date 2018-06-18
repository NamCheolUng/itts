package tis.com.financial.profitloss.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import tis.com.common.service.TisWorkHoursService;
import tis.com.financial.profitloss.service.ProfitLossSalaryVO;
import tis.com.financial.profitloss.service.ProfitLossSearchVO;
import tis.com.financial.profitloss.service.ProfitLossService;
import tis.com.task.service.TaskService;
import tis.com.task.service.TaskVO;


@Service("profitLossService")
public class ProfitLossServiceImpl implements ProfitLossService {

	@Resource(name = "profitLossDAO")
	private ProfitLossDAO profitLossDAO;
	
	@Resource(name = "profitLossService")
	private ProfitLossService profitLossService;
	
	@Resource(name="taskService")
	private TaskService taskService;
	
	@Resource(name = "TisWorkHoursService")
	private TisWorkHoursService tisWorkHoursService;
	
	@Override
	public Map<String, Object> selectProfitLossList(ProfitLossSearchVO vo) throws Exception {
		List<?> result = profitLossDAO.selectProfitLossList(vo);
		int count = profitLossDAO.selectProfitLossListCount(vo);
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("resultList", result);
		map.put("resultCount", new Integer(count));

		return map;
	}

	@Override
	public List<?> selectLaborCostByTask(String taskId) throws Exception {
		return profitLossDAO.selectLaborCostByTask(taskId);
	}

	@Override
	public int selectSalaryDuringTask(ProfitLossSalaryVO vo) throws Exception {
		return profitLossDAO.selectSalaryDuringTask(vo);
	}

	@Override
	public int calculateLaborCost(String taskId, String taskSdate, String taskEdate) throws Exception {
		TaskVO taskVO = new TaskVO();
		taskVO.setTaskId(taskId);
		List<EgovMap> taskPersonList = (List<EgovMap>)taskService.selectTaskPersonList(taskVO);
		
		// task 참여 인원의 인건비 합
		int taskLaborCostSum = 0;
		
		// task 참여 인원 별 인건비 계산
		for(EgovMap taskPersonMap : taskPersonList) {
			taskLaborCostSum += calculateLaborCostByPerson(taskId, taskSdate, taskEdate, (String)taskPersonMap.get("emplNo"));
		}
		
		return taskLaborCostSum;
	}

	@Override
	public int calculateLaborCostByPerson(String taskId, String taskSdate, String taskEdate, String emplNo) throws Exception {
		ProfitLossSalaryVO profitLossSalaryVO = new ProfitLossSalaryVO();
		profitLossSalaryVO.setEmplNo(emplNo);
		profitLossSalaryVO.setTaskId(taskId);
		profitLossSalaryVO.setTaskSdate(taskSdate);
		profitLossSalaryVO.setTaskEdate(taskEdate);
		
		EgovMap taskWorkHourVO = (EgovMap)profitLossService.selectTaskWorkHour(profitLossSalaryVO);
		float taskSchdulTime = ((BigDecimal)taskWorkHourVO.get("taskSchdulTime")).floatValue();
		float totalSchdulTime = ((BigDecimal)taskWorkHourVO.get("totalSchdulTime")).floatValue();
		
		// 현재 task의 일정 시간이 존재하면 시작,종료일에 걸친 다른 task의 일정 시간을 계산
		if(taskSchdulTime > 0) {
			String taskSdateHour = taskSdate + " 09:00";
			List<EgovMap> spannedScheduleForAnotherTaskHead = (List<EgovMap>)profitLossService.selectSpannedScheduleForAnotherTaskHead(profitLossSalaryVO);
			
			// task 시작일에 걸쳐진 다른 task의 일정들의 시간 합산
			for(EgovMap spannedScheduleForAnotherTaskHeadVO : spannedScheduleForAnotherTaskHead) {
				String scheduleEdateHour = (String)spannedScheduleForAnotherTaskHeadVO.get("schdulEdt");
				
				int otherTaskWorkHours = tisWorkHoursService.countWorkHours(taskSdateHour, scheduleEdateHour);
				totalSchdulTime += otherTaskWorkHours;
			}
			
			String taskEdateHour = taskEdate + " 18:00";
			List<EgovMap> spannedScheduleForAnotherTaskTail = (List<EgovMap>)profitLossService.selectSpannedScheduleForAnotherTaskTail(profitLossSalaryVO);
			
			// task 종료일에 걸쳐진 다른 task의 일정들의 시간 합산
			for(EgovMap spannedScheduleForAnotherTaskTailVO : spannedScheduleForAnotherTaskTail) {
				String scheduleSdateHour = (String)spannedScheduleForAnotherTaskTailVO.get("schdulSdt");
				
				int otherTaskWorkHours = tisWorkHoursService.countWorkHours(scheduleSdateHour, taskEdateHour);
				totalSchdulTime += otherTaskWorkHours;
			}
		}

		float taskWorkHourWeight = (totalSchdulTime == 0) ? 0 : taskSchdulTime / totalSchdulTime;
		
		// emplNo의 과제 기간 동안의 인건비
		int taskPersonSalary = profitLossService.selectSalaryDuringTask(profitLossSalaryVO);
		
		// emplNo의 일정 가중치가 적용된 인건비
		return (int)(taskPersonSalary * taskWorkHourWeight);
	}
	
	@Override
	public Object selectTaskWorkHour(ProfitLossSalaryVO vo) throws Exception {
		return profitLossDAO.selectTaskWorkHour(vo);
	}

	@Override
	public List<?> selectSpannedScheduleForAnotherTaskHead(ProfitLossSalaryVO vo) throws Exception {
		return profitLossDAO.selectSpannedScheduleForAnotherTaskHead(vo);
	}

	@Override
	public List<?> selectSpannedScheduleForAnotherTaskTail(ProfitLossSalaryVO vo) throws Exception {
		return profitLossDAO.selectSpannedScheduleForAnotherTaskTail(vo);
	}
}
