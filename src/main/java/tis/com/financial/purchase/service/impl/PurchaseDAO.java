package tis.com.financial.purchase.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import tis.com.financial.purchase.service.PurchaseDetailVO;
import tis.com.financial.purchase.service.PurchaseSearchVO;
import tis.com.financial.purchase.service.PurchaseVO;
import egovframework.com.cmm.service.impl.EgovComAbstractDAO;
import egovframework.rte.psl.dataaccess.util.EgovMap;


@Repository("purchaseDAO")
public class PurchaseDAO extends EgovComAbstractDAO {

	public List<?> selectPurchaseManageList(PurchaseSearchVO vo) throws Exception {
		return list("purchaseDAO.selectPurchaseManageList", vo);
	}

	public void insertPurchase(PurchaseVO vo) throws Exception {
		insert("purchaseDAO.insertPurchase", vo);
	}

	public void insertPurchaseDetail(PurchaseDetailVO vo) throws Exception {
		insert("purchaseDAO.insertPurchaseDetail", vo);
	}

	public List<?> selectDraftPurchaseList(String draftInfoId) throws Exception {
		return list("purchaseDAO.selectDraftPurchaseList", draftInfoId);
	}

	public EgovMap selectPurchase(String purchaseId) throws Exception {
		return (EgovMap) select("purchaseDAO.selectPurchase", purchaseId);
	}

	public List<?> selectPurchaseDetailList(String purchaseId) throws Exception {
		return list("purchaseDAO.selectPurchaseDetailList", purchaseId);
	}

	public void updatePurchase(PurchaseVO vo) throws Exception {
		update("purchaseDAO.updatePurchase", vo);
	}

	public void deletePurchaseDetail(String purchaseId) throws Exception {
		update("purchaseDAO.deletePurchaseDetail", purchaseId);
	}

	public List<?> selectPurchaseManageApprovalList(PurchaseSearchVO searchVO) throws Exception {
		return list("purchaseDAO.selectPurchaseManageApprovalList", searchVO);
	}

	public void updatePurchasePayApproval(PurchaseVO vo) throws Exception {
		update("purchaseDAO.updatePurchasePayApproval", vo);
	}

	public void updatePurchaseFinancialRegistration(PurchaseVO purchaseVO) {
		update("purchaseDAO.updatePurchaseFinancialRegistration", purchaseVO);
	}
	
	public void deletePurchase(String purchaseId) throws Exception {
		delete("purchaseDAO.deletePurchase", purchaseId);
	}
}
