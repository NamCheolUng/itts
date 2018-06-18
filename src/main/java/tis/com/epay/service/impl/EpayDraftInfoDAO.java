package tis.com.epay.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import tis.com.epay.service.EpayDraftInfoVO;
import egovframework.com.cmm.service.impl.EgovComAbstractDAO;
import egovframework.rte.psl.dataaccess.util.EgovMap;

@Repository("EpayDraftInfoDAO")
public class EpayDraftInfoDAO extends EgovComAbstractDAO{

	public List<EpayDraftInfoVO> selectEpayDraftInfoList(EpayDraftInfoVO epayDraftInfoVO) throws Exception{
		return (List<EpayDraftInfoVO>) list("EpayDraftInfoDAO.selectEpayDraftInfoList", epayDraftInfoVO);
	}

	public int epayDraftInfoListCount(EpayDraftInfoVO epayDraftInfoVO) throws Exception{
		return (Integer) select("EpayDraftInfoDAO.epayDraftInfoListCount", epayDraftInfoVO);
	}
	
	public EpayDraftInfoVO selectEpayDraftInfo(EpayDraftInfoVO epayDraftInfoVO) throws Exception{
		return (EpayDraftInfoVO) select("EpayDraftInfoDAO.selectEpayDraftInfo", epayDraftInfoVO);
	}
	
	public Object insertEpayDraftInfo(EpayDraftInfoVO epayDraftInfoVO) throws Exception{
		return insert("EpayDraftInfoDAO.insertEpayDraftInfo", epayDraftInfoVO);
	}
	
	public void updateEpayDraftInfo(EpayDraftInfoVO epayDraftInfoVO) throws Exception{
		update("EpayDraftInfoDAO.updateEpayDraftInfo", epayDraftInfoVO);
	}	
	
	public void deleteEpayDraftInfo(EpayDraftInfoVO epayDraftInfoVO) throws Exception{
		delete("EpayDraftInfoDAO.deleteEpayDraftInfo", epayDraftInfoVO);
	}

	public String selectEpayMaxDocNo(EpayDraftInfoVO epayDraftInfoVO) throws Exception{
		return (String) select("EpayDraftInfoDAO.selectEpayMaxDocNo", epayDraftInfoVO);
	}
	
	public List<?> selectManageEpayDraftInfoList(EpayDraftInfoVO epayDraftInfoVO) throws Exception{
		return list("EpayDraftInfoDAO.selectManageEpayDraftInfoList", epayDraftInfoVO);
	}
	
	public int selectManageEpayDraftInfoListCount(EpayDraftInfoVO epayDraftInfoVO) throws Exception{
		return (Integer) select("EpayDraftInfoDAO.selectManageEpayDraftInfoListCount", epayDraftInfoVO);
	}
	
	public void updateManageEpayApprSttus(EpayDraftInfoVO epayDraftInfoVO) throws Exception{
		update("EpayDraftInfoDAO.updateManageEpayApprSttus", epayDraftInfoVO);
	}
	
	public List<?> selectEmplyrList() throws Exception{
		return list("EpayDraftInfoDAO.selectEmplyrList");
	}
	
	public List<?> selectEmplyrListForOrgnztChart() throws Exception{
		return list("EpayDraftInfoDAO.selectEmplyrListForOrgnztChart");
	}
	
	public EgovMap selectEmplyrForOrgnztChart(String emplNo) throws Exception{
		return (EgovMap)select("EpayDraftInfoDAO.selectEmplyrForOrgnztChart", emplNo);
	}
	
	public List<?> selectEpayApprLn(String apprLineId) throws Exception{
		return list("EpayDraftInfoDAO.selectEpayApprLn", apprLineId);
	}
	
	public EgovMap selectEpayDraftInfoForSlack(EpayDraftInfoVO epayDraftInfoVO) throws Exception{
		return (EgovMap) select("EpayDraftInfoDAO.selectEpayDraftInfoForSlack", epayDraftInfoVO);
	}
}
