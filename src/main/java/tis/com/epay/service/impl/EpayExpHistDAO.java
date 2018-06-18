package tis.com.epay.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import tis.com.epay.service.EpayApprHistVO;
import tis.com.epay.service.EpayDraftVO;
import tis.com.epay.service.EpayExpHistVO;
import tis.com.task.service.TaskVO;
import egovframework.com.cmm.service.impl.EgovComAbstractDAO;

@Repository("EpayExpHistDAO")
public class EpayExpHistDAO extends EgovComAbstractDAO{

	public List<?> selectEpayExpHistList(EpayExpHistVO epayExpHistVO) throws Exception{
		return list("EpayExpHistDAO.selectEpayExpHistList", epayExpHistVO);
	}
	
	public void updateEpayExpHist(EpayExpHistVO epayExpHistVO) throws Exception{
		update("EpayExpHistDAO.updateEpayExpHist", epayExpHistVO);
	}
}
