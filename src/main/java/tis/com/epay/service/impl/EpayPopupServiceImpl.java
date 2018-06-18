package tis.com.epay.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import tis.com.epay.service.EpayApprLineVO;
import tis.com.epay.service.EpayApprLnCfgVO;
import tis.com.epay.service.EpayDraftInfoService;
import tis.com.epay.service.EpayDraftInfoVO;
import tis.com.epay.service.EpayPopupService;
import egovframework.com.cmm.service.EgovFileMngService;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service("epayPopupService")
public class EpayPopupServiceImpl extends EgovAbstractServiceImpl implements EpayPopupService{

	@Resource(name = "EpayApprLineDAO")
	private EpayApprLineDAO epayApprLineDao;
	
	@Resource(name = "EpayApprLnCfgDAO")
	private EpayApprLnCfgDAO epayApprLnCfgDao;	

	@Override
	public void saveFavoriteItem(EpayApprLineVO epayApprLineVO, EpayApprLnCfgVO epayApprLnCfgVO) throws Exception {
			
		EpayApprLineVO resultVO = this.epayApprLineDao.insertEpayApprLine(epayApprLineVO);
		
		if(epayApprLnCfgVO.getApprLnList() != null){
			for (EpayApprLnCfgVO vo: epayApprLnCfgVO.getApprLnList()){
				epayApprLnCfgVO.setApprLineId(resultVO.getApprLineId());
				epayApprLnCfgVO.setApprOrdr(vo.getApprOrdr());
				epayApprLnCfgVO.setEmplNo(vo.getEmplNo());
				epayApprLnCfgVO.setApprTy(vo.getApprTy());
				epayApprLnCfgVO.setPosition(vo.getPosition());
				this.epayApprLnCfgDao.insertEpayApprLnCfg(epayApprLnCfgVO);
			}
		}
	}

	@Override
	public void removeFavoriteItem(EpayApprLineVO epayApprLineVO,
			EpayApprLnCfgVO epayApprLnCfgVO) throws Exception {

		this.epayApprLnCfgDao.deleteEpayApprLnCfg(epayApprLnCfgVO);
		this.epayApprLineDao.deleteEpayApprLine(epayApprLineVO);
	}
}
