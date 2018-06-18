package tis.com.financial.periodicSettle.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import tis.com.financial.periodicSettle.service.PeriodicSettleService;
import tis.com.financial.periodicSettle.service.PeriodicSettleVO;

@Service("periodicSettleService")
public class PeriodicSettleServiceImpl implements PeriodicSettleService{
	
	/** egovCmtManageIdGnrService */
	@Resource(name = "periodicSettleIdGnrService")
	private EgovIdGnrService periodicSettleIdGnrService;
	
	@Resource(name="periodicSettleDAO")
	private PeriodicSettleDAO periodicSettleDAO;
	
	@Override
	public List<?> loadData_E(PeriodicSettleVO periodicSettleVO)throws Exception {
		return periodicSettleDAO.loadData_E(periodicSettleVO);
	}
	
	@Override
	public List<?> loadData_P(PeriodicSettleVO periodicSettleVO)throws Exception {
		return periodicSettleDAO.loadData_P(periodicSettleVO);
	}
	
	@Override
	public void updateData(PeriodicSettleVO periodicSettleVO)throws Exception {
		periodicSettleDAO.updateData(periodicSettleVO);
	}
	
	@Override
	public void insertData(PeriodicSettleVO periodicSettleVO)throws Exception {
		String calculId = periodicSettleIdGnrService.getNextStringId();
		periodicSettleVO.setCalculId(calculId);
		periodicSettleDAO.insertData(periodicSettleVO);
	}
	
	@Override
	public void deleteData(PeriodicSettleVO periodicSettleVO)throws Exception {
		periodicSettleDAO.deleteData(periodicSettleVO);
	}
	
	@Override
	public void addDefauitData(PeriodicSettleVO periodicSettleVO)throws Exception {
		String calculId = periodicSettleIdGnrService.getNextStringId();
		periodicSettleVO.setCalculId(calculId);
		periodicSettleDAO.addDefauitData(periodicSettleVO);
	}
}
