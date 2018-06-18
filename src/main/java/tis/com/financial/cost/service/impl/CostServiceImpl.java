package tis.com.financial.cost.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import tis.com.financial.cost.service.CostSearchVO;
import tis.com.financial.cost.service.CostService;
import tis.com.financial.cost.service.CostVO;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import egovframework.rte.psl.dataaccess.util.EgovMap;

@Service("costService")
public class CostServiceImpl implements CostService{

	@Resource(name="costDAO")
	private CostDAO costDAO;

	@Resource(name = "tisCostIdGnrService")
	private EgovIdGnrService tisCostIdGnrService;

	@Override
	public Map<String, Object> selectCostManageList(CostSearchVO vo) throws Exception {
		List<?> result = costDAO.selectCostManageList(vo);
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("resultList", result);

		return map;
	}

	@Override
	public void insertCost(List<CostVO> costVOList) throws Exception {
		// TODO Auto-generated method stub
		
		for(int i=0;i<costVOList.size();i++){
			CostVO tmp  =  costVOList.get(i);
			if(tmp != null && tmp.getExpTotPrice() != null && tmp.getExpTotPrice() != BigDecimal.ZERO ){
				tmp.setCostId(tisCostIdGnrService.getNextStringId());
				costDAO.insertCost(tmp);
			}
		}
		
	}

	@Override
	public EgovMap selectCost(String costId) throws Exception {
		return costDAO.selectCost(costId);
	}

	@Override
	public void updateCost(CostVO vo) throws Exception {
		costDAO.updateCost(vo);
	}

	@Override
	public Map<String, Object> selectCostManageApprovalList(CostSearchVO vo) throws Exception {
		if(vo.getSearchCondition().isEmpty()) {
			vo.setSearchCondition("USER_NM");
		}

		List<?> result = costDAO.selectCostManageApprovalList(vo);
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("resultList", result);

		return map;
	}

	@Override
	public void updateCostPayApproval(CostVO vo) throws Exception {
		costDAO.updateCostPayApproval(vo);
	}
}
