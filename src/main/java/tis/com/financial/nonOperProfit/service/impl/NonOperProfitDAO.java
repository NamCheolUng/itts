package tis.com.financial.nonOperProfit.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import tis.com.financial.nonOperProfit.service.NonOperVO;
import egovframework.com.cmm.service.impl.EgovComAbstractDAO;

@Repository("nonOperProfitDAO")
public class NonOperProfitDAO extends EgovComAbstractDAO{

	public List<?> loadData(NonOperVO nonOperVO)throws Exception {
		// TODO Auto-generated method stub
		return list("nonOperProfitDAO.loadData", nonOperVO);
	}
	
    public void updateData(NonOperVO nonOperVO) throws Exception {
    	update("nonOperProfitDAO.updateData", nonOperVO);
    }
    
    public void insertData(NonOperVO nonOperVO) throws Exception {
    	insert("nonOperProfitDAO.insertData", nonOperVO);
    }
    
    public void deleteData(NonOperVO nonOperVO) throws Exception {
    	delete("nonOperProfitDAO.deleteData", nonOperVO);
    }
    
    public void addDefauitData(NonOperVO nonOperVO) throws Exception {
    	insert("nonOperProfitDAO.addDefauitData", nonOperVO);
    }
}