package tis.com.financial.deposit.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import tis.com.financial.deposit.service.DepositService;
import tis.com.financial.deposit.service.DepositVO;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;

@Service("DepositService")
public class DepositServiceImpl implements DepositService{
	
	/** egovCmtManageIdGnrService */
	@Resource(name = "depositIdGnrService")
	private EgovIdGnrService depositIdGnrService;
	
	@Resource(name="depositDAO")
	private DepositDAO depositDAO;
	
	@Override
	public List<?> loadData(DepositVO depositVO)throws Exception {
		return depositDAO.loadData(depositVO);
	}

	@Override
	public void updateData(DepositVO depositVO)throws Exception {
		depositDAO.updateData(depositVO);
	}
	
	@Override
	public void insertData(DepositVO depositVO)throws Exception {
		String depositId = depositIdGnrService.getNextStringId();
		depositVO.setDepositId(depositId);
		depositDAO.insertData(depositVO);
	}
	
	@Override
	public void deleteData(DepositVO depositVO)throws Exception {
		depositDAO.deleteData(depositVO);
	}
	
	@Override
	public void addDefauitData(DepositVO depositVO)throws Exception {
		String depositId = depositIdGnrService.getNextStringId();
		depositVO.setDepositId(depositId);
		depositDAO.addDefauitData(depositVO);
	}
}
