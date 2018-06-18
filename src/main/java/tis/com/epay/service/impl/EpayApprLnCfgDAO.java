package tis.com.epay.service.impl;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import tis.com.epay.service.EpayApprLnCfgTmpVO;
import tis.com.epay.service.EpayApprLnCfgVO;
import egovframework.com.cmm.service.impl.EgovComAbstractDAO;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import egovframework.rte.psl.dataaccess.util.EgovMap;

@Repository("EpayApprLnCfgDAO")
public class EpayApprLnCfgDAO extends EgovComAbstractDAO{

	@Resource(name = "egovEpayApprLnCfgIdGnrService")
	private EgovIdGnrService egovEpayApprLnCfgIdGnrService;
	
	public List<EpayApprLnCfgVO> selectEpayApprLnCfgList(EpayApprLnCfgVO epayApprLnCfgVO) throws Exception{
		return (List<EpayApprLnCfgVO>) list("EpayApprLnCfgDAO.selectEpayApprLnCfgList", epayApprLnCfgVO);
	}
	
	public EgovMap selectEpayApprLnCfgChk(EpayApprLnCfgVO epayApprLnCfgVO) throws Exception{
		return (EgovMap) select("EpayApprLnCfgDAO.selectEpayApprLnCfgChk", epayApprLnCfgVO);
	}
	
	public void insertEpayApprLnCfg(EpayApprLnCfgVO epayApprLnCfgVO) throws Exception{
		
		epayApprLnCfgVO.setApprLnCfgId(this.egovEpayApprLnCfgIdGnrService.getNextStringId()); 
		
		insert("EpayApprLnCfgDAO.insertEpayApprLnCfg", epayApprLnCfgVO);
	}
	
	public void deleteEpayApprLnCfg(EpayApprLnCfgVO epayApprLnCfgVO) throws Exception{
		delete("EpayApprLnCfgDAO.deleteEpayApprLnCfg", epayApprLnCfgVO);
	}
	
	public List<EpayApprLnCfgTmpVO> selectEpayApprLnCfgTmpList(EpayApprLnCfgTmpVO epayApprLnCfgTmpVO) throws Exception{
		return (List<EpayApprLnCfgTmpVO>) list("EpayApprLnCfgDAO.selectEpayApprLnCfgTmpList", epayApprLnCfgTmpVO);
	}
	
	public void insertEpayApprLnCfgTmp(String draftInfoId, EpayApprLnCfgVO epayApprLnCfgVO) throws Exception{
		
		List<EpayApprLnCfgVO> apprLnCfgVOList = epayApprLnCfgVO.getApprLnList();
		
		if(apprLnCfgVOList.size() > 0){
			
			Iterator<?> itr = apprLnCfgVOList.iterator();
			EpayApprLnCfgVO tmpApprLnCfgVO = null;
			
			while(itr.hasNext()){
				
				tmpApprLnCfgVO = (EpayApprLnCfgVO)itr.next();
				
				EpayApprLnCfgTmpVO epayApprLnCfgTmpVO = new EpayApprLnCfgTmpVO();
				epayApprLnCfgTmpVO.setDraftInfoId(draftInfoId);
				epayApprLnCfgTmpVO.setApprOrdr(tmpApprLnCfgVO.getApprOrdr());
				epayApprLnCfgTmpVO.setApprTy(tmpApprLnCfgVO.getApprTy());
				epayApprLnCfgTmpVO.setEmplNo(tmpApprLnCfgVO.getEmplNo());
				epayApprLnCfgTmpVO.setPosition(tmpApprLnCfgVO.getPosition());
				epayApprLnCfgTmpVO.setUserNm(tmpApprLnCfgVO.getUserNm());
				
				insert("EpayApprLnCfgDAO.insertEpayApprLnCfgTmp", epayApprLnCfgTmpVO);
			}
		}
	}
	
	public void deleteEpayApprLnCfgTmp(EpayApprLnCfgTmpVO epayApprLnCfgTmpVO) throws Exception{
		delete("EpayApprLnCfgDAO.deleteEpayApprLnCfgTmp", epayApprLnCfgTmpVO);
	}
	
	public void updateEpayApprLnCfgTmp(String draftInfoId, EpayApprLnCfgVO epayApprLnCfgVO) throws Exception{
		
		List<EpayApprLnCfgVO> apprLnCfgVOList = epayApprLnCfgVO.getApprLnList();
		
		if(apprLnCfgVOList.size() > 0){
			
			EpayApprLnCfgTmpVO delApprLnCfgTmpVO = new EpayApprLnCfgTmpVO();
			delApprLnCfgTmpVO.setDraftInfoId(draftInfoId);
			
			this.deleteEpayApprLnCfgTmp(delApprLnCfgTmpVO);
			this.insertEpayApprLnCfgTmp(draftInfoId, epayApprLnCfgVO);
		}
	}
}
