package tis.com.epay.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import tis.com.financial.purchase.service.PurchaseVO;
import tis.com.financial.sales.service.SalesVO;
import egovframework.rte.psl.dataaccess.util.EgovMap;

public interface EpayDraftInfoService {

	public List<EpayDraftInfoVO> selectEpayDraftInfoList(EpayDraftInfoVO epayDraftInfoVO) throws Exception;
	
	public Integer epayDraftInfoListCount(EpayDraftInfoVO epayDraftInfoVO) throws Exception;
	
	public EpayDraftInfoVO selectEpayDraftInfo(EpayDraftInfoVO epayDraftInfoVO) throws Exception;
	
	public void updateEpayDraftInfo(EpayDraftInfoVO epayDraftInfoVO) throws Exception;
	
	public void deleteEpayDraftInfo(EpayDraftInfoVO epayDraftInfoVO) throws Exception;
	
	public void insertEpayDraftInfo(EpayDraftInfoVO epayDraftInfoVO) throws Exception;
	
	public List<EpayApprHistVO> selectEpayApprHistList(EpayApprHistVO epayApprHistVO) throws Exception;
	
	public void insertEpayApprHist(EpayApprHistVO epayApprHistVO) throws Exception;
	
	public List<EpayDocFormVO> selectEpayDocFormList(EpayDocFormVO epayDocFormVO) throws Exception;
	
	public EpayDraftVO selectEpayDraft(EpayDraftVO epayDraftVO) throws Exception;
	
	public void insertEpayDraft(EpayDraftVO epayDraftVO) throws Exception;
	
	public List<EpayApprLnCfgVO> selectEpayApprLnCfgList(EpayApprLnCfgVO epayApprLnCfgVO) throws Exception;
	
	public List<EpayApprLnCfgVO> selectEpayApprLnListByOrgzntId(String orgzntId) throws Exception;
	
	public List<?> selectTaskList(EpayTaskPersonVO epayTaskPersonVO) throws Exception; // 과제담당자가 속한 과제 목록 조회
	
	public EpayExpVO selectEpayExp(EpayExpVO epayExpVO) throws Exception;
	
	public EpayExpCorVO selectEpayExpCor(EpayExpCorVO epayExpCorVO) throws Exception;
	
	public void deleteEpayExp(EpayExpVO epayExpVO) throws Exception;
	
	public EgovMap selectEpayExpWithDInfo(EpayExpVO epayExpVO) throws Exception;
	
	public List<?> selectEpayExpHistList(EpayExpHistVO epayExpHistVO) throws Exception;
	
	public List<?> selectEpayExpCorHistList(EpayExpCorHistVO epayExpCorHistVO) throws Exception;
	
	public List<EpayApprLineVO> selectEpayApprLineList() throws Exception;
	
	public void insertEpayExp(EpayExpVO epayExpVO) throws Exception;
	
	public void updateEpayExp(EpayExpVO epayExpVO) throws Exception;
	
	public void updateEpayExpHist(EpayExpHistVO epayExpHistVO) throws Exception;
	
	public void insertEpayCnsul(EpayCnsulVO epayCnsulVO) throws Exception;
	
	public String selectEpayMaxDocNo(EpayDraftInfoVO epayDraftInfoVO) throws Exception;
	
	public EgovMap selectEpayCnsul(EpayCnsulVO epayCnsulVO) throws Exception;
	
	public EgovMap selectEpayCnsulWithDInfo(EpayCnsulVO epayCnsulVO) throws Exception;
	
	public void deleteEpayCnsul(EpayCnsulVO epayCnsulVO) throws Exception;
	
	public void deleteEpayCnsulSales(SalesVO salesVO) throws Exception;
	
	public void deleteEpayCnsulPurchase(PurchaseVO purchaseVO) throws Exception;
	
	public void updateEpayCnsul(EpayCnsulVO epayCnsulVO) throws Exception;
	
	public void updateEpayCnsulSales(EpayCnsulVO epayCnsulVO) throws Exception;
	
	public void updateEpayCnsulPurchase(EpayCnsulVO epayCnsulVO) throws Exception;

	public Map<String, Object> selectManageEpayDraftInfoList(EpayDraftInfoVO epayDraftInfoVO) throws Exception;

	public void updateManageEpayApprSttus(EpayDraftInfoVO epayDraftInfoVO) throws Exception;

	public void registerToFinancial(EpayDraftInfoVO epayDraftInfoVO) throws Exception;

	public void updateManageExpAcuntRegistYn(EpayExpVO epayExpVO) throws Exception;
	
	public void updateManageExpCorAcuntRegistYn(EpayExpCorVO epayExpCorVO) throws Exception;
	
	public List<?> selectEmplyrList() throws Exception;
	
	public List<?> selectEpayTaskAllList() throws Exception;
	
	public EgovMap selectEpayTaskForPerson(String emplNo) throws Exception;
	
	public void insertEpayExpCor(EpayExpCorVO epayExpCorVO, MultipartHttpServletRequest multiRequest) throws Exception;
	
	public void updateEpayExpCor(EpayExpCorVO epayExpCorVO, MultipartHttpServletRequest multiRequest) throws Exception;
	
	public void deleteEpayExpCor(EpayExpCorVO epayExpCorVO) throws Exception;
	
	public void insertEpayExpCorHist(EpayExpCorHistVO epayExpCorHistVO) throws Exception;
	
	public void updateEpayExpCorHist(EpayExpCorHistVO epayExpCorHistVO) throws Exception;
	
	public void deleteEpayExpCorHist(EpayExpCorHistVO epayExpCorHistVO) throws Exception;
	
	public EgovMap selectEpayExpCorWithDInfo(EpayExpCorVO epayExpCorVO) throws Exception;
	
	public void MakingEpayApprLn(List<EpayApprLnCfgVO> apprLnCfgVOList, String emplNo, String draftInfoId) throws Exception;
	
	public List<EpayExpCorHistVO> uploadEpayExpCorHistExcel(File destFile) throws Exception;
	
	public EgovMap selectEpayApprLnCfgChk(EpayApprLnCfgVO epayApprLnCfgVO) throws Exception;
	
	public List<?> selectEmplyrListForOrgnztChart() throws Exception;
	
	public EgovMap selectEmplyrForOrgnztChart(String emplNo) throws Exception;
	
	public List<?> selectEpayApprLn(String apprLineId) throws Exception;
	
	public void insertEpayApprLnCfg(EpayApprLnCfgVO epayApprLnCfgVO) throws Exception;
	
	public EpayApprLnCfgVO selectEpayApprLnCfgTmpList(EpayApprLnCfgTmpVO epayApprLnCfgTmpVO) throws Exception;
	
	public void insertEpayApprLnCfgTmp(String draftInfoId, EpayApprLnCfgVO epayApprLnCfgVO) throws Exception;
	
	public void updateEpayApprLnCfgTmp(String draftInfoId, EpayApprLnCfgVO epayApprLnCfgVO) throws Exception;
	
	public void deleteEpayApprLnCfgTmp(EpayApprLnCfgTmpVO epayApprLnCfgTmpVO) throws Exception;
	
	public EgovMap selectEpayHoliday(EpayHolidayVO epayHolidayVO) throws Exception;
	
	public EgovMap selectEpayHolidayWithDInfo(EpayHolidayVO epayHolidayVO) throws Exception;
	
	public void insertEpayHoliday(EpayHolidayVO epayHolidayVO, MultipartHttpServletRequest multiRequest) throws Exception;
	
	public void updateEpayHoliday(EpayHolidayVO epayHolidayVO, MultipartHttpServletRequest multiRequest) throws Exception;
	
	public void deleteEpayHoliday(EpayHolidayVO epayHolidayVO) throws Exception;
	
	public void insertEpayManagerHoliday(EpayHolidayVO epayHolidayVO) throws Exception;
	
	public EgovMap selectEpayDraftInfoForSlack(EpayDraftInfoVO epayDraftInfoVO) throws Exception;
}
