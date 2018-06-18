package tis.com.epay.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import tis.com.epay.service.EpayDocFormVO;
import egovframework.com.cmm.service.impl.EgovComAbstractDAO;

@Repository("EpayDocFormDAO")
public class EpayDocFormDAO extends EgovComAbstractDAO{

	public List<EpayDocFormVO> selectEpayDocFormList(EpayDocFormVO epayDocFormVO) throws Exception{
		return (List<EpayDocFormVO>) list("EpayDocFormDAO.selectEpayDocForm", epayDocFormVO);
	}
}
