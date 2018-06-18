package tis.com.financial.sales.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import tis.com.financial.sales.service.SalesDetailVO;
import tis.com.financial.sales.service.SalesSearchVO;
import tis.com.financial.sales.service.SalesService;
import tis.com.financial.sales.service.SalesVO;
import egovframework.com.cmm.service.EgovFileMngService;
import egovframework.com.cmm.service.EgovFileMngUtil;
import egovframework.com.cmm.service.FileVO;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import egovframework.rte.psl.dataaccess.util.EgovMap;


@Service("salesService")
public class SalesServiceImpl implements SalesService {

	@Resource(name = "salesDAO")
	private SalesDAO salesDAO;
	
	@Resource(name = "egovSalesIdGnrService")
	private EgovIdGnrService egovSalesIdGnrService;
	
	@Resource(name = "egovSalesDetailIdGnrService")
	private EgovIdGnrService egovSalesDetailIdGnrService;
	
    @Resource(name = "EgovFileMngUtil")
    private EgovFileMngUtil fileUtil;

    @Resource(name = "EgovFileMngService")
    private EgovFileMngService fileMngService;
    
    private static final String FILE_KEY_STR = "SALES_";

	@Override
	public Map<String, Object> selectSalesManageList(SalesSearchVO vo) throws Exception {
		List<?> result = salesDAO.selectSalesManageList(vo);
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("resultList", result);

		return map;
	}

	@Override
	public void insertSales(SalesVO salesVO, List<SalesDetailVO> salesDetailVOList, final MultipartHttpServletRequest multiRequest) throws Exception {
		String salesId = this.egovSalesIdGnrService.getNextStringId();
		salesVO.setSalesId(salesId);
		
		List<FileVO> result = null;
		String atchFileId = "";
	    final Map<String, MultipartFile> files = multiRequest.getFileMap();
	    if (!files.isEmpty()) {
			result = fileUtil.parseFileInf(files, FILE_KEY_STR, 0, "", "");
			atchFileId = fileMngService.insertFileInfs(result);
	    }
	    salesVO.setAtchFileId(atchFileId);
	    
		salesDAO.insertSales(salesVO);
		
		if(salesDetailVOList != null) {
			for(SalesDetailVO vo : salesDetailVOList) {
				vo.setSalesId(salesVO.getSalesId());
				
				String salesDetailId = this.egovSalesDetailIdGnrService.getNextStringId();
				vo.setSalesDetailId(salesDetailId);
				
				salesDAO.insertSalesDetail(vo);
			}
		}
	}

	@Override
	public List<?> selectDraftSalesList(String draftInfoId) throws Exception {
		return salesDAO.selectDraftSalesList(draftInfoId);
	}

	@Override
	public EgovMap selectSales(String salesId) throws Exception {
		return salesDAO.selectSales(salesId);
	}

	@Override
	public List<?> selectSalesDetailList(String salesId) throws Exception {
		return salesDAO.selectSalesDetailList(salesId);
	}

	@Override
	public void updateDraftSales(SalesVO salesVO, List<SalesDetailVO> salesDetailVOList, MultipartHttpServletRequest multiRequest) throws Exception {
		List<FileVO> result = null;
		String atchFileId = salesVO.getAtchFileId();
		final Map<String, MultipartFile> files = multiRequest.getFileMap();
		if (!files.isEmpty()) {
			if("".equals(atchFileId)) {
				result = fileUtil.parseFileInf(files, FILE_KEY_STR, 0, atchFileId, "");
				String tmpFileId = fileMngService.insertFileInfs(result);
				salesVO.setAtchFileId(tmpFileId);
			} else {
				FileVO fvo = new FileVO();
				fvo.setAtchFileId(atchFileId);
				int cnt = fileMngService.getMaxFileSN(fvo);
				result = fileUtil.parseFileInf(files, FILE_KEY_STR, cnt, atchFileId, "");
				fileMngService.updateFileInfs(result);
			}
		}
	    
		salesDAO.updateSales(salesVO);
		
		salesDAO.deleteSalesDetail(salesVO.getSalesId());
		
		if(salesDetailVOList != null) {
			for(SalesDetailVO vo : salesDetailVOList) {
				vo.setSalesId(salesVO.getSalesId());
				
				String salesDetailId = this.egovSalesDetailIdGnrService.getNextStringId();
				vo.setSalesDetailId(salesDetailId);
				
				salesDAO.insertSalesDetail(vo);
			}
		}
	}

	@Override
	public void updateSalesFinancialRegistration(SalesVO salesVO) throws Exception {
		salesDAO.updateSalesFinancialRegistration(salesVO);
	}

	@Override
	public void deleteDraftSales(List<EgovMap> salesVOList) throws Exception {
		
		if(salesVOList != null){
			for(EgovMap svo : salesVOList){
				
				String atchFileId = (String)svo.get("atchFileId"); 
				if (atchFileId != null && !atchFileId.isEmpty()) {
					
					FileVO fvo = new FileVO();
					fvo.setAtchFileId(atchFileId);
					fileMngService.deleteAllFileInf(fvo);
				}
				
				String salesId = (String)svo.get("salesId");
				
				if(salesId != null){
					this.salesDAO.deleteSales(salesId);
					this.salesDAO.deleteSalesDetail(salesId);	
				}
			}
		}
	}

	@Override
	public void deleteSalesByDraftInfoId(SalesVO salesVO) throws Exception {
		// TODO Auto-generated method stub
		this.salesDAO.deleteSalesByDraftInfoId(salesVO);
	}
	
	
}
