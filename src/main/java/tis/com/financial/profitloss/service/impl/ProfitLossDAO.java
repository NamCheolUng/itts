package tis.com.financial.profitloss.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import tis.com.financial.profitloss.service.ProfitLossSalaryVO;
import tis.com.financial.profitloss.service.ProfitLossSearchVO;
import egovframework.com.cmm.service.impl.EgovComAbstractDAO;
import egovframework.rte.psl.dataaccess.util.EgovMap;


@Repository("profitLossDAO")
public class ProfitLossDAO extends EgovComAbstractDAO {

	public List<?> selectProfitLossList(ProfitLossSearchVO vo) throws Exception {
		return list("profitLossDAO.selectProfitLossList", vo);
	}
	
	public int selectProfitLossListCount(ProfitLossSearchVO vo) throws Exception {
		return (Integer) select("profitLossDAO.selectProfitLossListCount", vo);
	}

	public List<?> selectLaborCostByTask(String taskId) throws Exception {
		return list("profitLossDAO.selectLaborCostByTask", taskId);
	}

	public int selectSalaryDuringTask(ProfitLossSalaryVO vo) throws Exception {
		Object obj = select("profitLossDAO.selectSalaryDuringTask", vo);
		return (Integer)(obj != null ? obj : 0);
	}

	public Object selectTaskWorkHour(ProfitLossSalaryVO vo) throws Exception {
		return select("profitLossDAO.selectTaskWorkHour", vo);
	}
	
	public List<?> selectSpannedScheduleForAnotherTaskHead(ProfitLossSalaryVO vo) throws Exception {
		return list("profitLossDAO.selectSpannedScheduleForAnotherTaskHead", vo);
	}

	public List<?> selectSpannedScheduleForAnotherTaskTail(ProfitLossSalaryVO vo) throws Exception {
		return list("profitLossDAO.selectSpannedScheduleForAnotherTaskTail", vo);
	}
}
