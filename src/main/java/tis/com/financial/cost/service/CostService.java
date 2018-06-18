package tis.com.financial.cost.service;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.util.EgovMap;

public interface CostService {
	public Map<String, Object> selectCostManageList(CostSearchVO searchVO) throws Exception;
	
	public void insertCost(List<CostVO> costVOList) throws Exception;

	public EgovMap selectCost(String costId) throws Exception;

	public void updateCost(CostVO costVO) throws Exception;

	public Map<String, Object> selectCostManageApprovalList(CostSearchVO searchVO) throws Exception;

	public void updateCostPayApproval(CostVO vo) throws Exception;
}
