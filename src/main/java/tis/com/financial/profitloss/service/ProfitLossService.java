package tis.com.financial.profitloss.service;

import java.util.List;
import java.util.Map;

public interface ProfitLossService {
	
	public Map<String, Object> selectProfitLossList(ProfitLossSearchVO vo) throws Exception;

	public List<?> selectLaborCostByTask(String taskId) throws Exception;

	public int selectSalaryDuringTask(ProfitLossSalaryVO vo) throws Exception;
	
	public int calculateLaborCost(String taskId, String taskSdate, String taskEdate) throws Exception;
	
	public int calculateLaborCostByPerson(String taskId, String taskSdate, String taskEdate, String emplNo) throws Exception;

	public Object selectTaskWorkHour(ProfitLossSalaryVO vo) throws Exception;
	
	public List<?> selectSpannedScheduleForAnotherTaskHead(ProfitLossSalaryVO vo) throws Exception;

	public List<?> selectSpannedScheduleForAnotherTaskTail(ProfitLossSalaryVO vo) throws Exception;
}
