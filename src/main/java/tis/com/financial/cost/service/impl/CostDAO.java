package tis.com.financial.cost.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tis.com.financial.cost.service.CostSearchVO;
import tis.com.financial.cost.service.CostVO;
import egovframework.com.cmm.service.impl.EgovComAbstractDAO;
import egovframework.rte.psl.dataaccess.util.EgovMap;

@Repository("costDAO")
public class CostDAO extends EgovComAbstractDAO{
	
	public void insertCost(CostVO costVO) throws Exception{
		insert("costDAO.insertCost", costVO);
	}
	
	public List<?> selectCostManageList(CostSearchVO vo) throws Exception {
		return list("costDAO.selectCostManageList", vo);
	}

	public EgovMap selectCost(String costId) throws Exception {
		return (EgovMap) select("costDAO.selectCost", costId);
	}

	public void updateCost(CostVO vo) throws Exception {
		update("costDAO.updateCost", vo);
	}

	public List<?> selectCostManageApprovalList(CostSearchVO vo) throws Exception {
		return list("costDAO.selectCostManageApprovalList", vo);
	}

	public void updateCostPayApproval(CostVO vo) throws Exception {
		update("costDAO.updateCostPayApproval", vo);
	}
}
