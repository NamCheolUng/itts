package tis.com.epay.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import tis.com.epay.service.EpayApprLineVO;
import tis.com.epay.service.EpayApprLnCfgVO;
import egovframework.com.cmm.service.impl.EgovComAbstractDAO;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;

@Repository("EpayApprLineDAO")
public class EpayApprLineDAO extends EgovComAbstractDAO{

	@Resource(name = "egovEpayApprLineIdGnrService")
	private EgovIdGnrService egovEpayApprLineIdGnrService;
	
	public List<EpayApprLnCfgVO> selectEpayApprLnListByOrgzntId(String orgzntId) throws Exception{
		return (List<EpayApprLnCfgVO>) list("EpayApprLineDAO.selectEpayApprLnListByOrgzntId", orgzntId);
	}
	
	public List<EpayApprLineVO> selectEpayApprLineList() throws Exception{
		return (List<EpayApprLineVO>) list("EpayApprLineDAO.selectEpayApprLine");
	}
	
	public EpayApprLineVO insertEpayApprLine(EpayApprLineVO epayApprLineVO) throws Exception{
		epayApprLineVO.setApprLineId(this.egovEpayApprLineIdGnrService.getNextStringId());
		insert("EpayApprLineDAO.insertEpayApprLine", epayApprLineVO);
		return epayApprLineVO;
	}
	public void deleteEpayApprLine(EpayApprLineVO epayApprLineVO) throws Exception{
		delete("EpayApprLineDAO.deleteEpayApprLine", epayApprLineVO);
	}
}
