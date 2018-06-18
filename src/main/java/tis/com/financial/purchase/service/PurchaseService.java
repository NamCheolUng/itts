package tis.com.financial.purchase.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import egovframework.rte.psl.dataaccess.util.EgovMap;

public interface PurchaseService {
	
	public Map<String, Object> selectPurchaseManageList(PurchaseSearchVO vo) throws Exception;

	public void insertPurchase(PurchaseVO purchaseVO, List<PurchaseDetailVO> purchaseDetailVOList, MultipartHttpServletRequest multiRequest) throws Exception;

	public List<?> selectDraftPurchaseList(String draftInfoId) throws Exception;

	public EgovMap selectPurchase(String purchaseId) throws Exception;

	public List<?> selectPurchaseDetailList(String purchaseId) throws Exception;

	public void updateDraftPurchase(PurchaseVO purchaseVO, List<PurchaseDetailVO> list, MultipartHttpServletRequest multiRequest) throws Exception;

	public Map<String, Object> selectPurchaseManageApprovalList(PurchaseSearchVO searchVO) throws Exception;

	public void updatePurchasePayApproval(PurchaseVO vo) throws Exception;

	public void updatePurchaseFinancialRegistration(PurchaseVO purchaseVO) throws Exception;
	
	public void deleteDraftPurchase(List<EgovMap> pruchaseVOList) throws Exception;
}
