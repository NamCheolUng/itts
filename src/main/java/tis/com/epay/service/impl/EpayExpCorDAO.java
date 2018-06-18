package tis.com.epay.service.impl;

import org.springframework.stereotype.Repository;

import tis.com.epay.service.EpayExpCorVO;
import egovframework.com.cmm.service.impl.EgovComAbstractDAO;
import egovframework.rte.psl.dataaccess.util.EgovMap;

@Repository("EpayExpCorDAO")
public class EpayExpCorDAO extends EgovComAbstractDAO{																				

	public Object insertEpayExpCor(EpayExpCorVO epayExpCorVO) throws Exception{
		return insert("EpayExpCorDAO.insertEpayExpCor", epayExpCorVO);
	}
	
	public EpayExpCorVO selectEpayExpCor(EpayExpCorVO epayExpCorVO) throws Exception{
		return (EpayExpCorVO) select("EpayExpCorDAO.selectEpayExpCor", epayExpCorVO);
	}
	
	public EgovMap selectEpayExpCorWithDInfo(EpayExpCorVO epayExpCorVO) throws Exception{
		return (EgovMap) select("EpayExpCorDAO.selectEpayExpCorWithDInfo", epayExpCorVO);
	}
		
	public void updateEpayExpCor(EpayExpCorVO epayExpCorVO) throws Exception{
		update("EpayExpCorDAO.updateEpayExpCor", epayExpCorVO);
	}
	
	public void deleteEpayExpCor(EpayExpCorVO epayExpCorVO) throws Exception{
		delete("EpayExpCorDAO.deleteEpayExpCor", epayExpCorVO);
	}
	
	public void updateManageExpCorAcuntRegistYn(EpayExpCorVO epayExpCorVO) throws Exception{
		update("EpayExpCorDAO.updateManageExpCorAcuntRegistYn", epayExpCorVO);
	}
	
	
}
