package tis.com.epay.service.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import tis.com.common.service.impl.ExcelRead;
import tis.com.common.service.impl.ExcelReadOption;
import tis.com.epay.service.EpayApprHistVO;
import tis.com.epay.service.EpayApprLineVO;
import tis.com.epay.service.EpayApprLnCfgTmpVO;
import tis.com.epay.service.EpayApprLnCfgVO;
import tis.com.epay.service.EpayCnsulVO;
import tis.com.epay.service.EpayDocFormVO;
import tis.com.epay.service.EpayDraftInfoService;
import tis.com.epay.service.EpayDraftInfoVO;
import tis.com.epay.service.EpayDraftVO;
import tis.com.epay.service.EpayExpCorHistVO;
import tis.com.epay.service.EpayExpCorVO;
import tis.com.epay.service.EpayExpHistVO;
import tis.com.epay.service.EpayExpVO;
import tis.com.epay.service.EpayHolidayVO;
import tis.com.epay.service.EpayTaskPersonVO;
import tis.com.financial.purchase.service.PurchaseService;
import tis.com.financial.purchase.service.PurchaseVO;
import tis.com.financial.sales.service.SalesService;
import tis.com.financial.sales.service.SalesVO;
import egovframework.com.cmm.service.EgovFileMngService;
import egovframework.com.cmm.service.EgovFileMngUtil;
import egovframework.com.cmm.service.FileVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import egovframework.rte.psl.dataaccess.util.EgovMap;

@Service("epayDraftInfoService")
public class EpayDraftInfoServiceImpl extends EgovAbstractServiceImpl implements EpayDraftInfoService{

	@Resource(name = "EgovFileMngService")
	private EgovFileMngService fileMngService;
	
	@Resource(name = "EpayDraftInfoDAO")
	private EpayDraftInfoDAO epayDraftInfoDao;
	
	@Resource(name = "EpayApprHistDAO")
	private EpayApprHistDAO epayApprHistDao;
	
	@Resource(name = "EpayDocFormDAO")
	private EpayDocFormDAO epayDocFormDao;
	
	@Resource(name = "EpayDraftDAO")
	private EpayDraftDAO epayDraftDao;
	
	@Resource(name = "EpayApprLineDAO")
	private EpayApprLineDAO epayApprLineDao;
	
	@Resource(name = "EpayApprLnCfgDAO")
	private EpayApprLnCfgDAO epayApprLnCfgDao;
	
	@Resource(name = "EpayExpHistDAO")
	private EpayExpHistDAO epayExpHistDao;
	
	@Resource(name = "EpayExpCorHistDAO")
	private EpayExpCorHistDAO epayExpCorHistDao;
	
	@Resource(name = "EpayTaskDAO")
	private EpayTaskDAO epayTaskDao;
	
	@Resource(name = "EpayExpDAO")
	private EpayExpDAO epayExpDAO;
	
	@Resource(name = "EpayExpCorDAO")
	private EpayExpCorDAO epayExpCorDAO;
	
	@Resource(name ="EpayCnsulDAO")
	private EpayCnsulDAO epayCnsulDAO;
	
	@Resource(name ="EpayHolidayDAO")
	private EpayHolidayDAO epayHolidayDAO;
	
	@Resource(name = "egovEpayDraftIdGnrService")
	private EgovIdGnrService draftIdGnrService; // draftIdGnrService.getNextStringId();
	
	@Resource(name = "salesService")
	private SalesService salesService;
	
	@Resource(name = "purchaseService")
	private PurchaseService purchaseService;
	
    @Resource(name = "EgovFileMngUtil")
    private EgovFileMngUtil fileUtil;
    
	@Resource(name = "egovEpayApprHistIdGnrService")
	private EgovIdGnrService epayApprHistIdGnrService;
    
    private static final String FILE_KEY_STR = "EXPCOR_";
    private static final String FILE_KEY_STR_HOLIDAY = "HOL_";
	
	@Override
	public List<EpayDraftInfoVO> selectEpayDraftInfoList(
			EpayDraftInfoVO epayDraftInfoVO) throws Exception {
		// TODO Auto-generated method stub
		return this.epayDraftInfoDao.selectEpayDraftInfoList(epayDraftInfoVO);
	}

	@Override
	public Integer epayDraftInfoListCount(EpayDraftInfoVO epayDraftInfoVO)
			throws Exception {
		// TODO Auto-generated method stub
		return this.epayDraftInfoDao.epayDraftInfoListCount(epayDraftInfoVO);
	}

	@Override
	public EpayDraftInfoVO selectEpayDraftInfo(EpayDraftInfoVO epayDraftInfoVO)
			throws Exception {
		// TODO Auto-generated method stub
		return this.epayDraftInfoDao.selectEpayDraftInfo(epayDraftInfoVO);
	}
	
	@Override
	public void updateEpayDraftInfo(EpayDraftInfoVO epayDraftInfoVO)
			throws Exception {
		// TODO Auto-generated method stub
		this.epayDraftInfoDao.updateEpayDraftInfo(epayDraftInfoVO);
	}

	@Override
	public void deleteEpayDraftInfo(EpayDraftInfoVO epayDraftInfoVO)
			throws Exception {
		// TODO Auto-generated method stub
		this.epayDraftInfoDao.deleteEpayDraftInfo(epayDraftInfoVO);
	}

	@Override
	public void insertEpayDraftInfo(EpayDraftInfoVO epayDraftInfoVO)
			throws Exception {
		// TODO Auto-generated method stub
		this.epayDraftInfoDao.insertEpayDraftInfo(epayDraftInfoVO);
	}

	@Override
	public List<EpayApprHistVO> selectEpayApprHistList(EpayApprHistVO epayApprHistVO)
			throws Exception {
		// TODO Auto-generated method stub
		return this.epayApprHistDao.selectEpayApprHistList(epayApprHistVO);
	}

	@Override
	public void insertEpayApprHist(EpayApprHistVO epayApprHistVO) throws Exception {
		// TODO Auto-generated method stub
		this.epayApprHistDao.insertEpayApprHist(epayApprHistVO);
	}

	@Override
	public List<EpayDocFormVO> selectEpayDocFormList(EpayDocFormVO epayDocFormVO)
			throws Exception {
		// TODO Auto-generated method stub
		return this.epayDocFormDao.selectEpayDocFormList(epayDocFormVO);
	}

	@Override
	public EpayDraftVO selectEpayDraft(EpayDraftVO epayDraftVO) throws Exception {
		// TODO Auto-generated method stub
		return this.epayDraftDao.selectEpayDraft(epayDraftVO);
	}

	@Override
	public void insertEpayDraft(EpayDraftVO epayDraftVO) throws Exception {
		// TODO Auto-generated method stub
		this.epayDraftDao.insertEpayDraft(epayDraftVO);
	}

	@Override
	public List<EpayApprLnCfgVO> selectEpayApprLnCfgList(
			EpayApprLnCfgVO epayApprLnCfgVO) throws Exception {
		// TODO Auto-generated method stub
		return this.epayApprLnCfgDao.selectEpayApprLnCfgList(epayApprLnCfgVO);
	}

	@Override
	public List<EpayApprLnCfgVO> selectEpayApprLnListByOrgzntId(String orgzntId)
			throws Exception {
		// TODO Auto-generated method stub
		return this.epayApprLineDao.selectEpayApprLnListByOrgzntId(orgzntId);
	}

	@Override
	public List<?> selectTaskList(EpayTaskPersonVO epayTaskPersonVO) throws Exception {
		return this.epayTaskDao.selectEpayTaskList(epayTaskPersonVO);
	}

	@Override
	public List<?> selectEpayExpHistList(EpayExpHistVO epayExpHistVO) throws Exception {
		return this.epayExpHistDao.selectEpayExpHistList(epayExpHistVO);
	}
	
	@Override
	public List<?> selectEpayExpCorHistList(EpayExpCorHistVO epayExpCorHistVO) throws Exception {
		return this.epayExpCorHistDao.selectEpayExpCorHistList(epayExpCorHistVO);
	}

	@Override
	public List<EpayApprLineVO> selectEpayApprLineList() throws Exception {
		return this.epayApprLineDao.selectEpayApprLineList();
	}

	@Override
	public void insertEpayExp(EpayExpVO epayExpVO) throws Exception {
		
		// Decimal 입력 관련 천단위 구분값 제거
		String expTotPrice = epayExpVO.getExpTotPrice();
		
		if(!expTotPrice.equals("") && !expTotPrice.equals("0")){
			expTotPrice = expTotPrice.replaceAll(",","");
			epayExpVO.setExpTotPrice(expTotPrice);
		}
		
		this.epayExpDAO.insertEpayExp(epayExpVO);
	}

	@Override
	public void updateEpayExpHist(EpayExpHistVO epayExpHistVO) throws Exception {
		// TODO Auto-generated method stub
		this.epayExpHistDao.updateEpayExpHist(epayExpHistVO);
	}

	@Override
	public EpayExpVO selectEpayExp(EpayExpVO epayExpVO) throws Exception {
		// TODO Auto-generated method stub
		return this.epayExpDAO.selectEpayExp(epayExpVO);
	}
	
	@Override
	public void updateEpayExp(EpayExpVO epayExpVO) throws Exception {
		// TODO Auto-generated method stub
		String expTotPrice = epayExpVO.getExpTotPrice();
		
		if(!expTotPrice.equals("") && !expTotPrice.equals("0")){
			expTotPrice = expTotPrice.replaceAll(",","");
			epayExpVO.setExpTotPrice(expTotPrice);
		}
		
		this.epayExpDAO.updateEpayExp(epayExpVO);
	}

	@Override
	public void deleteEpayExp(EpayExpVO epayExpVO) throws Exception {
		// TODO Auto-generated method stub
		this.epayExpDAO.deleteEpayExp(epayExpVO);
	}

	@Override
	public EgovMap selectEpayExpWithDInfo(EpayExpVO epayExpVO) throws Exception {
		// TODO Auto-generated method stub
		return this.epayExpDAO.selectEpayExpWithDInfo(epayExpVO);
	}
	
	

	@Override
	public EpayExpCorVO selectEpayExpCor(EpayExpCorVO epayExpCorVO)
			throws Exception {
		// TODO Auto-generated method stub
		return this.epayExpCorDAO.selectEpayExpCor(epayExpCorVO);
	}

	@Override
	public void insertEpayCnsul(EpayCnsulVO epayCnsulVO) throws Exception {
		// TODO Auto-generated method stub
		this.epayCnsulDAO.insertEpayCnsul(epayCnsulVO);
	}

	@Override
	public String selectEpayMaxDocNo(EpayDraftInfoVO epayDraftInfoVO) throws Exception {
		// TODO Auto-generated method stub
		return this.epayDraftInfoDao.selectEpayMaxDocNo(epayDraftInfoVO);
	}

	@Override
	public EgovMap selectEpayCnsul(EpayCnsulVO epayCnsulVO) throws Exception {
		// TODO Auto-generated method stub
		return (EgovMap) this.epayCnsulDAO.selectEpayCnsul(epayCnsulVO);
	}

	@Override
	public EgovMap selectEpayCnsulWithDInfo(EpayCnsulVO epayCnsulVO)
			throws Exception {
		// TODO Auto-generated method stub
		return (EgovMap) this.epayCnsulDAO.selectEpayCnsulWithDInfo(epayCnsulVO);
	}

	@Override
	public void deleteEpayCnsul(EpayCnsulVO epayCnsulVO) throws Exception {
		// TODO Auto-generated method stub
		this.epayCnsulDAO.deleteEpayCnsul(epayCnsulVO);
	}

	@Override
	public void deleteEpayCnsulSales(SalesVO salesVO) throws Exception {
		// TODO Auto-generated method stub
		this.epayCnsulDAO.deleteEpayCnsulSales(salesVO);
	}

	@Override
	public void deleteEpayCnsulPurchase(PurchaseVO purchaseVO) throws Exception {
		// TODO Auto-generated method stub
		this.epayCnsulDAO.deleteEpayCnsulPurchase(purchaseVO);
	}

	@Override
	public void updateEpayCnsul(EpayCnsulVO epayCnsulVO) throws Exception {
		// TODO Auto-generated method stub
		this.epayCnsulDAO.updateEpayCnsul(epayCnsulVO);
	}

	@Override
	public void updateEpayCnsulSales(EpayCnsulVO epayCnsulVO) throws Exception {
		// TODO Auto-generated method stub
		this.epayCnsulDAO.updateEpayCnsulSales(epayCnsulVO);
	}

	@Override
	public void updateEpayCnsulPurchase(EpayCnsulVO epayCnsulVO)
			throws Exception {
		// TODO Auto-generated method stub
		this.epayCnsulDAO.updateEpayCnsulPurchase(epayCnsulVO);
	}
	
	@Override
	public Map<String, Object> selectManageEpayDraftInfoList(EpayDraftInfoVO epayDraftInfoVO) throws Exception {
		List<?> result = epayDraftInfoDao.selectManageEpayDraftInfoList(epayDraftInfoVO);
		int count = epayDraftInfoDao.selectManageEpayDraftInfoListCount(epayDraftInfoVO);
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("resultList", result);
		map.put("resultCount", new Integer(count));
		
		return map;
	}

	@Override
	public void updateManageEpayApprSttus(EpayDraftInfoVO epayDraftInfoVO)
			throws Exception {
		// TODO Auto-generated method stub
		epayDraftInfoDao.updateManageEpayApprSttus(epayDraftInfoVO);
	}

	@Override
	public void registerToFinancial(EpayDraftInfoVO epayDraftInfoVO) throws Exception {
		SalesVO salesVO = new SalesVO();
		salesVO.setDraftInfoId(epayDraftInfoVO.getDraftInfoId());
		salesVO.setAffiliationId(epayDraftInfoVO.getAffiliationId());
		salesService.updateSalesFinancialRegistration(salesVO);
		
		PurchaseVO purchaseVO = new PurchaseVO();
		purchaseVO.setDraftInfoId(epayDraftInfoVO.getDraftInfoId());
		purchaseVO.setAffiliationId(epayDraftInfoVO.getAffiliationId());
		purchaseService.updatePurchaseFinancialRegistration(purchaseVO);
		
		EpayDraftInfoVO epayDraftInfoUpdateVO = new EpayDraftInfoVO();
		epayDraftInfoUpdateVO.setDraftInfoId(epayDraftInfoVO.getDraftInfoId());
		epayDraftInfoUpdateVO.setApprSttus("5"); // 문서등록
		epayDraftInfoDao.updateEpayDraftInfo(epayDraftInfoUpdateVO);
	}

	@Override
	public void updateManageExpAcuntRegistYn(EpayExpVO epayExpVO)
			throws Exception {
		// TODO Auto-generated method stub
		epayExpDAO.updateManageExpAcuntRegistYn(epayExpVO);
	}

	@Override
	public List<?> selectEmplyrList() throws Exception {
		// TODO Auto-generated method stub
		return this.epayDraftInfoDao.selectEmplyrList();
	}

	
	
	@Override
	public void updateManageExpCorAcuntRegistYn(EpayExpCorVO epayExpCorVO)
			throws Exception {
		this.epayExpCorDAO.updateManageExpCorAcuntRegistYn(epayExpCorVO);
	}

	@Override
	public List<?> selectEpayTaskAllList() throws Exception {
		// TODO Auto-generated method stub
		return this.epayTaskDao.selectEpayTaskAllList();
	}

	@Override
	public EgovMap selectEpayTaskForPerson(String emplNo) throws Exception {
		// TODO Auto-generated method stub
		return this.epayTaskDao.selectEpayTaskForPerson(emplNo);
	}

	@Override
	public void insertEpayExpCor(
			EpayExpCorVO epayExpCorVO
			, MultipartHttpServletRequest multiRequest
			) throws Exception {
		
		List<FileVO> result = null;
		String atchFileId = "";
	    final Map<String, MultipartFile> files = multiRequest.getFileMap();
	    if (!files.isEmpty()) {
			result = fileUtil.parseFileInf(files, FILE_KEY_STR, 0, "", "");
			atchFileId = fileMngService.insertFileInfs(result);
	    }
	    epayExpCorVO.setAtchFileId(atchFileId);
	    
		this.epayExpCorDAO.insertEpayExpCor(epayExpCorVO);
	}

	@Override
	public void updateEpayExpCor(
			EpayExpCorVO epayExpCorVO
			, MultipartHttpServletRequest multiRequest
			) throws Exception {
		
		List<FileVO> result = null;
		
		String atchFileId = epayExpCorVO.getAtchFileId();
		
		final Map<String, MultipartFile> files = multiRequest.getFileMap();
		if (!files.isEmpty()) {
			if("".equals(atchFileId)) {
				result = fileUtil.parseFileInf(files, FILE_KEY_STR, 0, atchFileId, "");
				String tmpFileId = fileMngService.insertFileInfs(result);
				epayExpCorVO.setAtchFileId(tmpFileId);
			} else {
				FileVO fvo = new FileVO();
				fvo.setAtchFileId(atchFileId);
				int cnt = fileMngService.getMaxFileSN(fvo);
				result = fileUtil.parseFileInf(files, FILE_KEY_STR, cnt, atchFileId, "");
				fileMngService.updateFileInfs(result);
			}
		}
		
		this.epayExpCorDAO.updateEpayExpCor(epayExpCorVO);
	}

	@Override
	public void deleteEpayExpCor(EpayExpCorVO epayExpCorVO) throws Exception {
		
		String atchFileId = epayExpCorVO.getAtchFileId();
		
		if (!atchFileId.isEmpty()) {
			FileVO fvo = new FileVO();
			fvo.setAtchFileId(atchFileId);
			fileMngService.deleteAllFileInf(fvo);
		}
		
		String expcorStatId = epayExpCorVO.getExpcorStatId();
		
		if(expcorStatId != null && expcorStatId != ""){
			this.epayExpCorDAO.deleteEpayExpCor(epayExpCorVO);
			EpayExpCorHistVO epayExpCorHistVO = new EpayExpCorHistVO();
			epayExpCorHistVO.setExpcorStatId(expcorStatId);
			this.epayExpCorHistDao.deleteEpayExpCorHist(epayExpCorHistVO);
		}		
	}

	@Override
	public void insertEpayExpCorHist(EpayExpCorHistVO epayExpCorHistVO)
			throws Exception {
		this.epayExpCorHistDao.insertEpayExpCorHist(epayExpCorHistVO);
	}
	
	@Override
	public void updateEpayExpCorHist(EpayExpCorHistVO epayExpCorHistVO)
			throws Exception {
		this.epayExpCorHistDao.updateEpayExpCorHist(epayExpCorHistVO);
	}
	
	

	@Override
	public void deleteEpayExpCorHist(EpayExpCorHistVO epayExpCorHistVO)
			throws Exception {
		this.epayExpCorHistDao.deleteEpayExpCorHist(epayExpCorHistVO);
	}

	@Override
	public EgovMap selectEpayExpCorWithDInfo(EpayExpCorVO epayExpCorVO)
			throws Exception {
		return this.epayExpCorDAO.selectEpayExpCorWithDInfo(epayExpCorVO);
	}
	
	@Override
	public void MakingEpayApprLn(List<EpayApprLnCfgVO> apprLnCfgVOList, String emplNo, String draftInfoId)
			throws Exception {

		// 결재이력용 날짜 포맷
		SimpleDateFormat sDtApprHistFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
		Calendar cal3 = Calendar.getInstance();	
		String today3 = sDtApprHistFmt.format(cal3.getTime()); // 결재 처리 날짜 생성
		
		EpayApprLnCfgVO flag = null;
		int inputIdx = -1;
		boolean isInput = false;
		boolean isIdx = false;
		
		
		// 조회된 결재라인 정보를 결재이력 테이블에 삽입
		Iterator<?> itr = apprLnCfgVOList.iterator();
		EpayApprLnCfgVO epayApprLnCfgVO = null;
		
		// 결재 라인 구성 목록 중에 기안자가 포함되어 있는 지 체크
		while(itr.hasNext()){
			epayApprLnCfgVO = (EpayApprLnCfgVO)itr.next();
			
			if(epayApprLnCfgVO != null){
				if(epayApprLnCfgVO.getEmplNo().equals(emplNo)){
					flag = epayApprLnCfgVO;
					break;
				}
			}
		}
		
		if(flag != null){ // 결재라인 목록에 결재자가 포함되어 있을 경우(예외처리)
			
			itr = apprLnCfgVOList.iterator(); // 초기화
			
			while(itr.hasNext()){ 
				epayApprLnCfgVO = (EpayApprLnCfgVO)itr.next();
										
				if(epayApprLnCfgVO != null){
							
						
					if(epayApprLnCfgVO.getApprOrdr().equals(flag.getApprOrdr())){
						
						String apprHistId = this.epayApprHistIdGnrService.getNextStringId(); // 기안이력 ID 발급
						EpayApprHistVO epayApprHistVO = new EpayApprHistVO();
						epayApprHistVO.setApprHistId(apprHistId);   // 결재이력 ID
						epayApprHistVO.setDraftInfoId(draftInfoId); // 기안정보 ID
						epayApprHistVO.setApprOrdr(epayApprLnCfgVO.getApprOrdr()); // 결재순서
						epayApprHistVO.setEmplNo(epayApprLnCfgVO.getEmplNo()); // 결재자 ID
						epayApprHistVO.setApprTy(epayApprLnCfgVO.getApprTy()); // 결재유형
						epayApprHistVO.setPostn(epayApprLnCfgVO.getPosition()); // 결재라인구성 구분
										
						epayApprHistVO.setApprState("1"); // 기안자 본인은 결재상태 기본값 승인(1)로 설정
						epayApprHistVO.setApprTm(today3);
						
						epayApprHistVO.setEmplNo(emplNo);
						epayApprHistVO.setPostn("담당");
						
						this.insertEpayApprHist(epayApprHistVO); // 기안이력 정보 DB 등록
						
						isInput = true;
						inputIdx = Integer.parseInt(epayApprLnCfgVO.getApprOrdr()) + 1; 
					}
					
					if(isInput && epayApprLnCfgVO.getApprOrdr().equals(String.valueOf(inputIdx))){
						
						String apprHistId = this.epayApprHistIdGnrService.getNextStringId(); // 기안이력 ID 발급
						EpayApprHistVO epayApprHistVO = new EpayApprHistVO();
						epayApprHistVO.setApprHistId(apprHistId);   // 결재이력 ID
						epayApprHistVO.setDraftInfoId(draftInfoId); // 기안정보 ID
						epayApprHistVO.setApprOrdr(epayApprLnCfgVO.getApprOrdr()); // 결재순서
						epayApprHistVO.setEmplNo(epayApprLnCfgVO.getEmplNo()); // 결재자 ID
						epayApprHistVO.setApprTy(epayApprLnCfgVO.getApprTy()); // 결재유형
						epayApprHistVO.setPostn(epayApprLnCfgVO.getPosition()); // 결재라인구성 구분
						
						epayApprHistVO.setApprState("0");
						
						this.insertEpayApprHist(epayApprHistVO); // 기안이력 정보 DB 등록
						
						isIdx = true;
					}
					
					if(isInput && isIdx && Integer.parseInt(epayApprLnCfgVO.getApprOrdr()) > inputIdx){
						String apprHistId = this.epayApprHistIdGnrService.getNextStringId(); // 기안이력 ID 발급
						EpayApprHistVO epayApprHistVO = new EpayApprHistVO();
						epayApprHistVO.setApprHistId(apprHistId);   // 결재이력 ID
						epayApprHistVO.setDraftInfoId(draftInfoId); // 기안정보 ID
						epayApprHistVO.setApprOrdr(epayApprLnCfgVO.getApprOrdr()); // 결재순서
						epayApprHistVO.setEmplNo(epayApprLnCfgVO.getEmplNo()); // 결재자 ID
						epayApprHistVO.setApprTy(epayApprLnCfgVO.getApprTy()); // 결재유형
						epayApprHistVO.setPostn(epayApprLnCfgVO.getPosition()); // 결재라인구성 구분
						
						this.insertEpayApprHist(epayApprHistVO); // 기안이력 정보 DB 등록
					}
				}
			} // End while(itr.hasNext()){
					
		}else{
					
			// 기안 이력에 추가할 기안자(담당)의 결재 라인 정보를 생성 
			EpayApprLnCfgVO tmpEpayApprLnCfgVO = new EpayApprLnCfgVO();
			tmpEpayApprLnCfgVO.setApprOrdr("1");
			tmpEpayApprLnCfgVO.setEmplNo(emplNo);
			tmpEpayApprLnCfgVO.setPosition("담당");
					
			apprLnCfgVOList.add(tmpEpayApprLnCfgVO);
					
			itr = apprLnCfgVOList.iterator(); // 초기화
					
			while(itr.hasNext()){ 
				epayApprLnCfgVO = (EpayApprLnCfgVO)itr.next();
										
				if(epayApprLnCfgVO != null){
					
					String apprHistId = this.epayApprHistIdGnrService.getNextStringId(); // 기안이력 ID 발급
					EpayApprHistVO epayApprHistVO = new EpayApprHistVO();
					epayApprHistVO.setApprHistId(apprHistId);   // 결재이력 ID
					epayApprHistVO.setDraftInfoId(draftInfoId); // 기안정보 ID
					epayApprHistVO.setApprOrdr(epayApprLnCfgVO.getApprOrdr()); // 결재순서
					epayApprHistVO.setEmplNo(epayApprLnCfgVO.getEmplNo()); // 결재자 ID
					epayApprHistVO.setApprTy(epayApprLnCfgVO.getApprTy()); // 결재유형
					epayApprHistVO.setPostn(epayApprLnCfgVO.getPosition()); // 결재라인구성 구분
											
					if(epayApprLnCfgVO.getApprOrdr().equals("1")){
						epayApprHistVO.setEmplNo(emplNo);
						epayApprHistVO.setApprState("1"); // 기안자 본인은 결재상태 기본값 승인(1)로 설정
						epayApprHistVO.setApprTm(today3);
					}
					else if(epayApprLnCfgVO.getApprOrdr().equals("2")){
						epayApprHistVO.setApprState("0");
					}
							
					this.insertEpayApprHist(epayApprHistVO); // 기안이력 정보 DB 등록
				}
			} // end internel while
		}// End else
				
		// 품의서에 소속된 결재라인 임시 저장 데이터 삭제(존재 할 경우)
		EpayApprLnCfgTmpVO epayApprLnCfgTmpVO = new EpayApprLnCfgTmpVO();
		epayApprLnCfgTmpVO.setDraftInfoId(draftInfoId);
		this.deleteEpayApprLnCfgTmp(epayApprLnCfgTmpVO);
	}

	@Override
	public List<EpayExpCorHistVO> uploadEpayExpCorHistExcel(File destFile) throws Exception {

		List<EpayExpCorHistVO> epayExpCorHistVOList = new ArrayList<EpayExpCorHistVO>();
		
		ExcelReadOption excelReadOption = new ExcelReadOption();
        excelReadOption.setFilePath(destFile.getAbsolutePath());
        excelReadOption.setOutputColumns("A","B","C","D","E");
        excelReadOption.setStartRow(4);
        
        final String COL_CARD_NUM = "A";
        final String COL_ACCPT_DT = "B";
        final String COL_EXP_PLACE = "C";
        final String COL_PRICE = "D";
        final String COL_RM = "E";
		
        List<Map<String, String>> excelContent = ExcelRead.read(excelReadOption);
        
        for(Map<String, String> article : excelContent){
        	
        	EpayExpCorHistVO epayExpCorHistVO = new EpayExpCorHistVO();
        	epayExpCorHistVO.setCardNum(article.get(COL_CARD_NUM));
        	epayExpCorHistVO.setAccptDt(article.get(COL_ACCPT_DT));
        	epayExpCorHistVO.setExpPlace(article.get(COL_EXP_PLACE));
        	epayExpCorHistVO.setPrice(article.get(COL_PRICE));
        	epayExpCorHistVO.setRm(article.get(COL_RM));
        	epayExpCorHistVOList.add(epayExpCorHistVO);
        }
        
        return epayExpCorHistVOList;
	}

	@Override
	public EgovMap selectEpayApprLnCfgChk(EpayApprLnCfgVO epayApprLnCfgVO)
			throws Exception {
		return (EgovMap)this.epayApprLnCfgDao.selectEpayApprLnCfgChk(epayApprLnCfgVO);
	}

	@Override
	public List<?> selectEmplyrListForOrgnztChart() throws Exception {
		return this.epayDraftInfoDao.selectEmplyrListForOrgnztChart();
	}

	@Override
	public EgovMap selectEmplyrForOrgnztChart(String emplNo) throws Exception {
		return this.epayDraftInfoDao.selectEmplyrForOrgnztChart(emplNo);
	}

	@Override
	public List<?> selectEpayApprLn(String apprLineId) throws Exception {
		return this.epayDraftInfoDao.selectEpayApprLn(apprLineId);
	}

	@Override
	public void insertEpayApprLnCfg(EpayApprLnCfgVO epayApprLnCfgVO)
			throws Exception {
		this.epayApprLnCfgDao.insertEpayApprLnCfg(epayApprLnCfgVO);
	}

	@Override
	public void insertEpayApprLnCfgTmp(String draftInfoId,
			EpayApprLnCfgVO epayApprLnCfgVO) throws Exception {

		this.epayApprLnCfgDao.insertEpayApprLnCfgTmp(draftInfoId, epayApprLnCfgVO);
	}

	@Override
	public EpayApprLnCfgVO selectEpayApprLnCfgTmpList(EpayApprLnCfgTmpVO epayApprLnCfgTmpVO)
			throws Exception {
		
		List<?> apprLnCfgTmpVOList = this.epayApprLnCfgDao.selectEpayApprLnCfgTmpList(epayApprLnCfgTmpVO);
		
		EpayApprLnCfgVO epayApprLnCfgVO = new EpayApprLnCfgVO();
		
		List<EpayApprLnCfgVO> apprLnList = new ArrayList<EpayApprLnCfgVO>();
		
		if(apprLnCfgTmpVOList != null && apprLnCfgTmpVOList.size() > 0){

			Iterator<?> itr = apprLnCfgTmpVOList.iterator();
			while(itr.hasNext()){
				EpayApprLnCfgTmpVO tmpEpayApprLnCfgTmpVO = (EpayApprLnCfgTmpVO)itr.next();
				EpayApprLnCfgVO tmpEpayApprLnCfgVO = new EpayApprLnCfgVO();
				
				tmpEpayApprLnCfgVO.setApprOrdr(tmpEpayApprLnCfgTmpVO.getApprOrdr());
				tmpEpayApprLnCfgVO.setApprTy(tmpEpayApprLnCfgTmpVO.getApprTy());
				tmpEpayApprLnCfgVO.setEmplNo(tmpEpayApprLnCfgTmpVO.getEmplNo());
				tmpEpayApprLnCfgVO.setPosition(tmpEpayApprLnCfgTmpVO.getPosition());
				tmpEpayApprLnCfgVO.setUserNm(tmpEpayApprLnCfgTmpVO.getUserNm());
				
				apprLnList.add(tmpEpayApprLnCfgVO);
			}
		}
		
		epayApprLnCfgVO.setApprLnList(apprLnList);
		
		return epayApprLnCfgVO;
	}

	@Override
	public void updateEpayApprLnCfgTmp(String draftInfoId,
			EpayApprLnCfgVO epayApprLnCfgVO) throws Exception {
		this.epayApprLnCfgDao.updateEpayApprLnCfgTmp(draftInfoId, epayApprLnCfgVO);
	}

	@Override
	public void deleteEpayApprLnCfgTmp(EpayApprLnCfgTmpVO epayApprLnCfgTmpVO)
			throws Exception {
		this.epayApprLnCfgDao.deleteEpayApprLnCfgTmp(epayApprLnCfgTmpVO);
	}

	@Override
	public EgovMap selectEpayHoliday(EpayHolidayVO epayHolidayVO)
			throws Exception {
		return this.epayHolidayDAO.selectEpayHoliday(epayHolidayVO);
	}

	@Override
	public EgovMap selectEpayHolidayWithDInfo(EpayHolidayVO epayHolidayVO)
			throws Exception {
		return this.epayHolidayDAO.selectEpayHolidayWithDInfo(epayHolidayVO);
	}
	
	@Override
	public void insertEpayHoliday(
			EpayHolidayVO epayHolidayVO
			, MultipartHttpServletRequest multiRequest
			) throws Exception {
		
		List<FileVO> result = null;
		String atchFileId = "";
	    final Map<String, MultipartFile> files = multiRequest.getFileMap();
	    if (!files.isEmpty()) {
			result = fileUtil.parseFileInf(files, FILE_KEY_STR_HOLIDAY, 0, "", "");
			atchFileId = fileMngService.insertFileInfs(result);
	    }
	    epayHolidayVO.setAtchFileId(atchFileId);
	    
	    this.epayHolidayDAO.insertEpayHoliday(epayHolidayVO);
	}
	
	@Override
	public void updateEpayHoliday(
			EpayHolidayVO epayHolidayVO
			, MultipartHttpServletRequest multiRequest
			) throws Exception {
		
		List<FileVO> result = null;
		
		String atchFileId = epayHolidayVO.getAtchFileId();
		
		final Map<String, MultipartFile> files = multiRequest.getFileMap();
		if (!files.isEmpty()) {
			if("".equals(atchFileId)) {
				result = fileUtil.parseFileInf(files, FILE_KEY_STR_HOLIDAY, 0, atchFileId, "");
				String tmpFileId = fileMngService.insertFileInfs(result);
				epayHolidayVO.setAtchFileId(tmpFileId);
			} else {
				FileVO fvo = new FileVO();
				fvo.setAtchFileId(atchFileId);
				int cnt = fileMngService.getMaxFileSN(fvo);
				result = fileUtil.parseFileInf(files, FILE_KEY_STR_HOLIDAY, cnt, atchFileId, "");
				fileMngService.updateFileInfs(result);
			}
		}
		
		this.epayHolidayDAO.updateEpayHoliday(epayHolidayVO);
	}

	@Override
	public void deleteEpayHoliday(EpayHolidayVO epayHolidayVO) throws Exception {
		this.epayHolidayDAO.deleteEpayHoliday(epayHolidayVO);
	}

	@Override
	public void insertEpayManagerHoliday(EpayHolidayVO epayHolidayVO)
			throws Exception {
		this.epayHolidayDAO.insertEpayManagerHoliday(epayHolidayVO);
		
	}

	@Override
	public EgovMap selectEpayDraftInfoForSlack(EpayDraftInfoVO epayDraftInfoVO)
			throws Exception {
		return this.epayDraftInfoDao.selectEpayDraftInfoForSlack(epayDraftInfoVO);
	}
	
}
