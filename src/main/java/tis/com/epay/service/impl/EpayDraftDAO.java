package tis.com.epay.service.impl;

import org.springframework.stereotype.Repository;

import tis.com.epay.service.EpayDraftVO;
import egovframework.com.cmm.service.impl.EgovComAbstractDAO;

@Repository("EpayDraftDAO")
public class EpayDraftDAO extends EgovComAbstractDAO{

	public EpayDraftVO selectEpayDraft(EpayDraftVO epayDraftVO) throws Exception{
		return (EpayDraftVO) select("EpayDraftDAO.selectEpayDraft", epayDraftVO);
	}
	
	public Object insertEpayDraft(EpayDraftVO epayDraftVO) throws Exception{
		return insert("EpayDraftDAO.insertEpayDraft", epayDraftVO);
	}
}
