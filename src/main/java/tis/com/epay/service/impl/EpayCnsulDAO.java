package tis.com.epay.service.impl;

import org.springframework.stereotype.Repository;

import tis.com.epay.service.EpayCnsulVO;
import tis.com.epay.service.EpayExpVO;
import tis.com.financial.purchase.service.PurchaseVO;
import tis.com.financial.sales.service.SalesVO;
import egovframework.com.cmm.service.impl.EgovComAbstractDAO;
import egovframework.rte.psl.dataaccess.util.EgovMap;

@Repository("EpayCnsulDAO")
public class EpayCnsulDAO extends EgovComAbstractDAO{

	public Object insertEpayCnsul(EpayCnsulVO epayCnsulVO) throws Exception{
		return insert("EpayCnsulDAO.insertEpayCnsul", epayCnsulVO);
	}
	
	public EgovMap selectEpayCnsul(EpayCnsulVO epayCnsulVO) throws Exception{
		return (EgovMap) select("EpayCnsulDAO.selectEpayCnsul", epayCnsulVO);
	}
	
	public EgovMap selectEpayCnsulWithDInfo(EpayCnsulVO epayCnsulVO) throws Exception{
		return (EgovMap) select("EpayCnsulDAO.selectEpayCnsulWithDInfo", epayCnsulVO);
	}
	
	public void deleteEpayCnsul(EpayCnsulVO epayCnsulVO) throws Exception{
		delete("EpayCnsulDAO.deleteEpayCnsul", epayCnsulVO);
	}
	
	public void deleteEpayCnsulSales(SalesVO salesVO) throws Exception{
		delete("EpayCnsulDAO.deleteEpayCnsul", salesVO);
	}
	
	public void deleteEpayCnsulPurchase(PurchaseVO purchaseVO) throws Exception{
		delete("EpayCnsulDAO.deleteEpayCnsulPurchase", purchaseVO);
	}
	
	public void updateEpayCnsul(EpayCnsulVO epayCnsulVO) throws Exception{
		update("EpayCnsulDAO.updateEpayCnsul", epayCnsulVO);
	}

	public void updateEpayCnsulSales(EpayCnsulVO epayCnsulVO) throws Exception{
		update("EpayCnsulDAO.updateEpayCnsulSales", epayCnsulVO);
	}
	
	public void updateEpayCnsulPurchase(EpayCnsulVO epayCnsulVO) throws Exception{
		update("EpayCnsulDAO.updateEpayCnsulPurchase", epayCnsulVO);
	}
}
