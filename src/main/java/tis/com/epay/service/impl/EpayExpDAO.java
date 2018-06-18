package tis.com.epay.service.impl;

import org.springframework.stereotype.Repository;

import tis.com.epay.service.EpayDraftVO;
import tis.com.epay.service.EpayExpVO;
import egovframework.com.cmm.service.impl.EgovComAbstractDAO;
import egovframework.rte.psl.dataaccess.util.EgovMap;

@Repository("EpayExpDAO")
public class EpayExpDAO extends EgovComAbstractDAO{

	public Object insertEpayExp(EpayExpVO epayExpVO) throws Exception{
		return insert("EpayExpDAO.insertEpayExp", epayExpVO);
	}
	
	public EpayExpVO selectEpayExp(EpayExpVO epayExpVO) throws Exception{
		return (EpayExpVO) select("EpayExpDAO.selectEpayExp", epayExpVO);
	}
	
	public EgovMap selectEpayExpWithDInfo(EpayExpVO epayExpVO) throws Exception{
		return (EgovMap) select("EpayExpDAO.selectEpayExpWithDInfo", epayExpVO);
	}
	
	public void updateEpayExp(EpayExpVO epayExpVO) throws Exception{
		update("EpayExpDAO.updateEpayExp", epayExpVO);
	}
	
	public void deleteEpayExp(EpayExpVO epayExpVO) throws Exception{
		delete("EpayExpDAO.deleteEpayExp", epayExpVO);
	}
	
	public void updateManageExpAcuntRegistYn(EpayExpVO epayExpVO) throws Exception{
		update("EpayDraftInfoDAO.updateManageExpAcuntRegistYn", epayExpVO);
	}
}
