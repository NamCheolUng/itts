package tis.com.financial.sales.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import egovframework.rte.psl.dataaccess.util.EgovMap;

public interface SalesService {
	
	public Map<String, Object> selectSalesManageList(SalesSearchVO vo) throws Exception;
	
	public void insertSales(SalesVO salesVO, List<SalesDetailVO> salesDetailVOList, final MultipartHttpServletRequest multiRequest) throws Exception;

	public List<?> selectDraftSalesList(String draftInfoId) throws Exception;

	public EgovMap selectSales(String salesId) throws Exception;

	public List<?> selectSalesDetailList(String salesId) throws Exception;

	public void updateDraftSales(SalesVO salesVO, List<SalesDetailVO> salesDetailVOList, MultipartHttpServletRequest multiRequest) throws Exception;
	
	public void deleteDraftSales(List<EgovMap> salesVOList) throws Exception;

	public void updateSalesFinancialRegistration(SalesVO salesVO) throws Exception;
	
	public void deleteSalesByDraftInfoId(SalesVO salesVO) throws Exception;
}
