package tis.com.financial.purchase.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import tis.com.financial.purchase.service.PurchaseDetailVO;
import tis.com.financial.purchase.service.PurchaseSearchVO;
import tis.com.financial.purchase.service.PurchaseService;
import tis.com.financial.purchase.service.PurchaseVO;
import egovframework.com.cmm.service.EgovFileMngService;
import egovframework.com.cmm.service.EgovFileMngUtil;
import egovframework.com.cmm.service.FileVO;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import egovframework.rte.psl.dataaccess.util.EgovMap;


@Service("purchaseService")
public class PurchaseServiceImpl implements PurchaseService {

	@Resource(name = "purchaseDAO")
	private PurchaseDAO purchaseDAO;

	@Resource(name = "tisPurchaseIdGnrService")
	private EgovIdGnrService tisPurchaseIdGnrService;
	
	@Resource(name = "tisPurchaseDetailIdGnrService")
	private EgovIdGnrService tisPurchaseDetailIdGnrService;
	
    @Resource(name = "EgovFileMngUtil")
    private EgovFileMngUtil fileUtil;

    @Resource(name = "EgovFileMngService")
    private EgovFileMngService fileMngService;
    
    private static final String FILE_KEY_STR = "PURCHASE_";
    
	@Override
	public Map<String, Object> selectPurchaseManageList(PurchaseSearchVO vo) throws Exception {
		List<?> result = purchaseDAO.selectPurchaseManageList(vo);
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("resultList", result);

		return map;
	}

	@Override
	public void insertPurchase(PurchaseVO purchaseVO, List<PurchaseDetailVO> purchaseDetailVOList, MultipartHttpServletRequest multiRequest) throws Exception {
		String salesId = this.tisPurchaseIdGnrService.getNextStringId();
		purchaseVO.setPurchaseId(salesId);
		
		List<FileVO> result = null;
		String atchFileId = "";
		
	    Map<String, MultipartFile> files = null;
	    
	    if(multiRequest != null){
	    	files = multiRequest.getFileMap();
	    	
	    	if (!files.isEmpty()) {
				result = fileUtil.parseFileInf(files, FILE_KEY_STR, 0, "", "");
				atchFileId = fileMngService.insertFileInfs(result);
		    }
		    purchaseVO.setAtchFileId(atchFileId);
	    }
	    
	    purchaseDAO.insertPurchase(purchaseVO);
		
		if(purchaseDetailVOList != null) {
			for(PurchaseDetailVO vo : purchaseDetailVOList) {
				vo.setPurchaseId(purchaseVO.getPurchaseId());
				
				String purchaseDetailId = this.tisPurchaseDetailIdGnrService.getNextStringId();
				vo.setPurchaseDetailId(purchaseDetailId);
				
				purchaseDAO.insertPurchaseDetail(vo);
			}
		}
	}

	@Override
	public List<?> selectDraftPurchaseList(String draftInfoId) throws Exception {
		return purchaseDAO.selectDraftPurchaseList(draftInfoId);
	}

	@Override
	public EgovMap selectPurchase(String purchaseId) throws Exception {
		return purchaseDAO.selectPurchase(purchaseId);
	}

	@Override
	public List<?> selectPurchaseDetailList(String purchaseId) throws Exception {
		return purchaseDAO.selectPurchaseDetailList(purchaseId);
	}

	@Override
	public void updateDraftPurchase(PurchaseVO purchaseVO, List<PurchaseDetailVO> purchaseDetailVOList, MultipartHttpServletRequest multiRequest) throws Exception {
		List<FileVO> result = null;
		String atchFileId = purchaseVO.getAtchFileId();
		final Map<String, MultipartFile> files = multiRequest.getFileMap();
		if (!files.isEmpty()) {
			if("".equals(atchFileId)) {
				result = fileUtil.parseFileInf(files, FILE_KEY_STR, 0, atchFileId, "");
				String tmpFileId = fileMngService.insertFileInfs(result);
				purchaseVO.setAtchFileId(tmpFileId);
			} else {
				FileVO fvo = new FileVO();
				fvo.setAtchFileId(atchFileId);
				int cnt = fileMngService.getMaxFileSN(fvo);
				result = fileUtil.parseFileInf(files, FILE_KEY_STR, cnt, atchFileId, "");
				fileMngService.updateFileInfs(result);
			}
		}
	    
		purchaseDAO.updatePurchase(purchaseVO);
		
		purchaseDAO.deletePurchaseDetail(purchaseVO.getPurchaseId());
		
		if(purchaseDetailVOList != null) {
			for(PurchaseDetailVO vo : purchaseDetailVOList) {
				vo.setPurchaseId(purchaseVO.getPurchaseId());
				
				String salesDetailId = this.tisPurchaseDetailIdGnrService.getNextStringId();
				vo.setPurchaseDetailId(salesDetailId);
				
				purchaseDAO.insertPurchaseDetail(vo);
			}
		}
	}

	@Override
	public Map<String, Object> selectPurchaseManageApprovalList(PurchaseSearchVO searchVO) throws Exception {
		if(searchVO.getSearchCondition().isEmpty()) {
			searchVO.setSearchCondition("ADBK_ID");
		}

		List<?> result = purchaseDAO.selectPurchaseManageApprovalList(searchVO);
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("resultList", result);

		return map;
	}

	@Override
	public void updatePurchasePayApproval(PurchaseVO vo) throws Exception {
		purchaseDAO.updatePurchasePayApproval(vo);
	}

	@Override
	public void updatePurchaseFinancialRegistration(PurchaseVO purchaseVO) throws Exception {
		purchaseDAO.updatePurchaseFinancialRegistration(purchaseVO);
	}

	@Override
	public void deleteDraftPurchase(List<EgovMap> pruchaseVOList)
			throws Exception {
		if(pruchaseVOList != null ){
			for(EgovMap pvo : pruchaseVOList){
				
				String atchFileId = (String)pvo.get("atchFileId"); 
				if (atchFileId != null && !atchFileId.isEmpty()) {
					
					FileVO fvo = new FileVO();
					fvo.setAtchFileId(atchFileId);
					fileMngService.deleteAllFileInf(fvo);
				}
				
				String purchaseId = (String)pvo.get("purchaseId");
				
				if(purchaseId != null && purchaseId != ""){
					this.purchaseDAO.deletePurchase(purchaseId);
					this.purchaseDAO.deletePurchaseDetail(purchaseId);	
				}
			}
		}
	}
	
	
}
