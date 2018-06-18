package tis.com.financial.deposit.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import tis.com.financial.deposit.service.DepositVO;
import egovframework.com.cmm.service.impl.EgovComAbstractDAO;

@Repository("depositDAO")
public class DepositDAO extends EgovComAbstractDAO{

	public List<?> loadData(DepositVO depositVO)throws Exception {
		// TODO Auto-generated method stub
		return list("depositDAO.loadData", depositVO);
	}
	
    public void updateData(DepositVO depositVO) throws Exception {
    	update("depositDAO.updateData", depositVO);
    }
    
    public void insertData(DepositVO depositVO) throws Exception {
    	insert("depositDAO.insertData", depositVO);
    }
    
    public void deleteData(DepositVO depositVO) throws Exception {
    	delete("depositDAO.deleteData", depositVO);
    }
    
    public void addDefauitData(DepositVO depositVO) throws Exception {
    	insert("depositDAO.addDefauitData", depositVO);
    }
}