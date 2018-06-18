package tis.com.financial.periodicSettle.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import tis.com.financial.periodicSettle.service.PeriodicSettleVO;
import egovframework.com.cmm.service.impl.EgovComAbstractDAO;

@Repository("periodicSettleDAO")
public class PeriodicSettleDAO extends EgovComAbstractDAO{

	public List<?> loadData_E(PeriodicSettleVO periodicSettleVO)throws Exception {
		return list("periodicSettleDAO.loadData_E", periodicSettleVO);
	}
	
	public List<?> loadData_P(PeriodicSettleVO periodicSettleVO)throws Exception {
		return list("periodicSettleDAO.loadData_P", periodicSettleVO);
	}
	
    public void updateData(PeriodicSettleVO periodicSettleVO) throws Exception {
    	update("periodicSettleDAO.updateData", periodicSettleVO);
    }
    
    public void insertData(PeriodicSettleVO periodicSettleVO) throws Exception {
    	insert("periodicSettleDAO.insertData", periodicSettleVO);
    }
    
    public void deleteData(PeriodicSettleVO periodicSettleVO) throws Exception {
    	delete("periodicSettleDAO.deleteData", periodicSettleVO);
    }
    
    public void addDefauitData(PeriodicSettleVO periodicSettleVO) throws Exception {
    	insert("periodicSettleDAO.addDefauitData", periodicSettleVO);
    }
}