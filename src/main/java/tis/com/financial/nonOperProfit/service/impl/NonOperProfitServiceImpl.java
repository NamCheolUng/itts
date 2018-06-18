package tis.com.financial.nonOperProfit.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import tis.com.financial.nonOperProfit.service.NonOperProfitService;
import tis.com.financial.nonOperProfit.service.NonOperVO;

@Service("NonOperProfitService")
public class NonOperProfitServiceImpl implements NonOperProfitService{
	
	/** egovCmtManageIdGnrService */
	@Resource(name = "nonOperIdGnrService")
	private EgovIdGnrService nonOperIdGnrService;
	
	@Resource(name="nonOperProfitDAO")
	private NonOperProfitDAO nonOperProfitDAO;
	
	@Override
	public List<?> loadData(NonOperVO nonOperVO)throws Exception {
		return nonOperProfitDAO.loadData(nonOperVO);
	}

	@Override
	public void updateData(NonOperVO nonOperVO)throws Exception {
		nonOperProfitDAO.updateData(nonOperVO);
	}
	
	@Override
	public void insertData(NonOperVO nonOperVO)throws Exception {
		String otdealId = nonOperIdGnrService.getNextStringId();
		nonOperVO.setOtdealId(otdealId);
		nonOperProfitDAO.insertData(nonOperVO);
	}
	
	@Override
	public void deleteData(NonOperVO nonOperVO)throws Exception {
		nonOperProfitDAO.deleteData(nonOperVO);
	}
	
	@Override
	public void addDefauitData(NonOperVO nonOperVO)throws Exception {
		String otdealId = nonOperIdGnrService.getNextStringId();
		nonOperVO.setOtdealId(otdealId);
		nonOperProfitDAO.addDefauitData(nonOperVO);
	}
}
