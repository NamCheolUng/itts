package tis.com.epay.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import tis.com.epay.service.EpayExpCorHistVO;
import egovframework.com.cmm.service.impl.EgovComAbstractDAO;

@Repository("EpayExpCorHistDAO")
public class EpayExpCorHistDAO extends EgovComAbstractDAO{

	public List<?> selectEpayExpCorHistList(EpayExpCorHistVO epayExpCorHistVO) throws Exception{
		return list("EpayExpCorHistDAO.selectEpayExpCorHistList", epayExpCorHistVO);
	}
	
	public void insertEpayExpCorHist(EpayExpCorHistVO epayExpCorHistVO) throws Exception{
		insert("EpayExpCorHistDAO.insertEpayExpCorHist", epayExpCorHistVO);
	}
	
	public void updateEpayExpCorHist(EpayExpCorHistVO epayExpCorHistVO) throws Exception{
		update("EpayExpCorHistDAO.updateEpayExpCorHist", epayExpCorHistVO);
	}
	
	public void deleteEpayExpCorHist(EpayExpCorHistVO epayExpCorHistVO) throws Exception{
		delete("EpayExpCorHistDAO.deleteEpayExpCorHist", epayExpCorHistVO);
	}
}
