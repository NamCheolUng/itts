package tis.com.epay.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import tis.com.epay.service.EpayApprInfoVO;
import egovframework.com.cmm.service.impl.EgovComAbstractDAO;
import egovframework.rte.psl.dataaccess.util.EgovMap;

@Repository("EpayApprInfoDAO")
public class EpayApprInfoDAO extends EgovComAbstractDAO{

	public List<EgovMap> selectEpayApprInfoList(EpayApprInfoVO epayApprInfoVO) throws Exception{
		return (List<EgovMap>) list("EpayApprInfoDAO.selectEpayApprInfoList", epayApprInfoVO);
	}
	public int epayApprInfoListCount(EpayApprInfoVO epayApprInfoVO) throws Exception{
		return (Integer) select("EpayApprInfoDAO.epayApprInfoListCount", epayApprInfoVO);
	}
	public int epayApprWaitingCount(EpayApprInfoVO epayApprInfoVO) throws Exception{
		return (Integer) select("EpayApprInfoDAO.epayApprWaitingCount", epayApprInfoVO);
	}
}
