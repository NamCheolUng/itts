package tis.com.epay.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import tis.com.epay.service.EpayApprHistVO;
import tis.com.epay.service.EpayApprInfoService;
import tis.com.epay.service.EpayApprInfoVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.psl.dataaccess.util.EgovMap;

@Service("epayApprInfoService")
public class EpayApprInfoServiceImpl extends EgovAbstractServiceImpl implements EpayApprInfoService{

	@Resource(name = "EpayApprInfoDAO")
	private EpayApprInfoDAO epayApprInfoDao;
	
	@Resource(name = "EpayApprHistDAO")
	private EpayApprHistDAO epayApprHistDao;
	
	@Override
	public List<EgovMap> selectEpayApprInfoList(
			EpayApprInfoVO epayApprInfoVO) throws Exception {
		// TODO Auto-generated method stub
		return this.epayApprInfoDao.selectEpayApprInfoList(epayApprInfoVO);
	}

	@Override
	public Integer epayApprInfoListCount(EpayApprInfoVO epayApprInfoVO)
			throws Exception {
		// TODO Auto-generated method stub
		return this.epayApprInfoDao.epayApprInfoListCount(epayApprInfoVO);
	}

	@Override
	public void updateEpayApprHist(EpayApprHistVO epayApprHistVO)
			throws Exception {
		// TODO Auto-generated method stub
		this.epayApprHistDao.updateEpayApprHist(epayApprHistVO);
	}

	@Override
	public void epayApprHist(EpayApprHistVO epayApprHistVO) throws Exception {
		// TODO Auto-generated method stub
		this.epayApprHistDao.epayApprHist(epayApprHistVO);
	}

	@Override
	public EgovMap epayApprHistNextEmplNo(EpayApprHistVO epayApprHistVO)
			throws Exception {
		return (EgovMap) this.epayApprHistDao.epayApprHistNextEmplNo(epayApprHistVO);
	}

	@Override
	public Integer epayApprWaitingCount(EpayApprInfoVO epayApprInfoVO) throws Exception {
		// TODO Auto-generated method stub
		return this.epayApprInfoDao.epayApprWaitingCount(epayApprInfoVO);
	}

	@Override
	public void epayApprReturn(EpayApprHistVO epayApprHistVO) throws Exception {
		// TODO Auto-generated method stub
		// 현재 결재순서 이상의 결재이력을 삭제함. 
		this.epayApprHistDao.deleteApprHist(epayApprHistVO);
	}
	
}
