package tis.com.epay.service.impl;

import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Repository;

import tis.com.epay.service.EpayApprHistVO;
import egovframework.com.cmm.service.impl.EgovComAbstractDAO;
import egovframework.rte.psl.dataaccess.util.EgovMap;

@Repository("EpayApprHistDAO")
public class EpayApprHistDAO extends EgovComAbstractDAO{

	public List<EpayApprHistVO> selectEpayApprHistList(EpayApprHistVO epayApprHistVO) throws Exception{
		return (List<EpayApprHistVO>) list("EpayDraftInfoDAO.selectEpayApprHist", epayApprHistVO);
	}
	
	public Object insertEpayApprHist(EpayApprHistVO epayApprHistVO) throws Exception{
		return insert("EpayApprHistDAO.insertEpayApprHist", epayApprHistVO);
	}
	
	public void insertEpayApprHistList(List<?> epayApprHistVOList) throws Exception{
		Iterator<?> itr = epayApprHistVOList.iterator();
		EpayApprHistVO epayApprHistVO;
		
		while(itr.hasNext()){
			epayApprHistVO = (EpayApprHistVO) itr.next();
			
			if(!epayApprHistVO.getApprHistId().isEmpty() && !epayApprHistVO.getDraftInfoId().isEmpty()){
				insert("EpayApprHistDAO.insertEpayApprHist", epayApprHistVO);
			}
		}
	}
	
	public void updateEpayApprHist(EpayApprHistVO epayApprHistVO) throws Exception{
		update("EpayApprHistDAO.updateEpayApprHist", epayApprHistVO);
	}
	
	public void epayApprHist(EpayApprHistVO epayApprHistVO) throws Exception{
		update("EpayApprHistDAO.epayApprHist", epayApprHistVO);
	}
	
	public EgovMap epayApprHistNextEmplNo(EpayApprHistVO epayApprHistVO) throws Exception{
		return (EgovMap) select("EpayApprHistDAO.epayApprHistNextEmplNo", epayApprHistVO);
	}
	
	public void deleteApprHist(EpayApprHistVO epayApprHistVO) throws Exception{
		delete("EpayApprHistDAO.deleteEpayApprHist", epayApprHistVO);
	}
	
	// 임시 저장용 결재이력 데이터 추가
	public Object insertEpayApprHistTemp(EpayApprHistVO epayApprHistVO) throws Exception{
		return insert("EpayApprHistDAO.insertEpayApprHistTemp", epayApprHistVO);
	}
	
	public void deleteApprHistTemp(EpayApprHistVO epayApprHistVO) throws Exception{
		delete("EpayApprHistDAO.deleteEpayApprHistTemp", epayApprHistVO);
	}
	
	public void updateEpayApprHistTemp(EpayApprHistVO epayApprHistVO) throws Exception{
		update("EpayApprHistDAO.updateEpayApprHistTemp", epayApprHistVO);
	}
	
	public List<EpayApprHistVO> selectEpayApprHistListTemp(EpayApprHistVO epayApprHistVO) throws Exception{
		return (List<EpayApprHistVO>) list("EpayDraftInfoDAO.selectEpayApprHistTemp", epayApprHistVO);
	}
	
}
