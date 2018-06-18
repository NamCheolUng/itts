package tis.com.financial.sales.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import tis.com.financial.sales.service.SalesDetailVO;
import tis.com.financial.sales.service.SalesSearchVO;
import tis.com.financial.sales.service.SalesVO;
import egovframework.com.cmm.service.impl.EgovComAbstractDAO;
import egovframework.rte.psl.dataaccess.util.EgovMap;


@Repository("salesDAO")
public class SalesDAO extends EgovComAbstractDAO {

	public List<?> selectSalesManageList(SalesSearchVO vo) throws Exception {
		return list("salesDAO.selectSalesManageList", vo);
	}

	public void insertSales(SalesVO vo) throws Exception {
		insert("salesDAO.insertSales", vo);
	}

	public void insertSalesDetail(SalesDetailVO vo) throws Exception {
		insert("salesDAO.insertSalesDetail", vo);
	}

	public List<?> selectDraftSalesList(String draftInfoId) throws Exception {
		return list("salesDAO.selectDraftSalesList", draftInfoId);
	}

	public EgovMap selectSales(String salesId) throws Exception {
		return (EgovMap) select("salesDAO.selectSales", salesId);
	}

	public List<?> selectSalesDetailList(String salesId) throws Exception {
		return list("salesDAO.selectSalesDetailList", salesId);
	}

	public void updateSales(SalesVO vo) throws Exception {
		update("salesDAO.updateSales", vo);
	}
	
	public void deleteSales(String salesId) throws Exception {
		delete("salesDAO.deleteSales", salesId);
	}	

	public void deleteSalesByDraftInfoId(SalesVO salesVO) throws Exception {
		delete("salesDAO.deleteSalesByDraftInfoId", salesVO);
	}
	
	public void deleteSalesDetail(String salesId) throws Exception {
		delete("salesDAO.deleteSalesDetail", salesId);
	}

	public void updateSalesFinancialRegistration(SalesVO salesVO) throws Exception {
		update("salesDAO.updateSalesFinancialRegistration", salesVO);
	}
}
