package tis.com.financial.salary.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import tis.com.financial.salary.service.SalaryMasterVO;
import tis.com.financial.salary.service.SalaryVO;
import egovframework.com.cmm.service.impl.EgovComAbstractDAO;

@Repository("salaryDAO")
public class SalaryDAO extends EgovComAbstractDAO{
	
	public List<?> allSalaryList(SalaryMasterVO salaryMasterVO) throws Exception{
		return list("salaryDAO.allSalaryList", salaryMasterVO);
	}
	
	public List<?> selectSalaryList(SalaryMasterVO salaryMasterVO) throws Exception{
		return list("salaryDAO.selectSalaryList", salaryMasterVO);
	}
	
	public List<?> salaryView(SalaryVO salaryVO) throws Exception{
		return list("salaryDAO.salaryView", salaryVO);
	}
	
	public List<?> salaryViewExcel(SalaryVO salaryVO) throws Exception{
		return list("salaryDAO.salaryViewExcel", salaryVO);
	}
	
	public List<?> salaryEmplyrDetail(SalaryVO salaryVO) throws Exception{
		return list("salaryDAO.salaryEmplyrDetail", salaryVO);
	}
	
	public List<?> salaryEmplyrDetailExcel(SalaryVO salaryVO) throws Exception{
		return list("salaryDAO.salaryEmplyrDetailExcel", salaryVO);
	}
	
	public List<?> salaryFull(SalaryVO salaryVO) throws Exception{
		return list("salaryDAO.salaryFull", salaryVO);
	}
	
	public void insertExcelDetail(SalaryVO salaryVO) throws Exception{
		insert("salaryDAO.insertExcelDetail", salaryVO);
	}
	
	public void insertExcelMaster(SalaryVO salaryVO) throws Exception{
		insert("salaryDAO.insertExcelMaster", salaryVO);
	}
	
	public void deleteExcelDetail(SalaryVO salaryVO) throws Exception{
		delete("salaryDAO.deleteExcelDetail", salaryVO);
	}
	
	public void deleteExcelMaster(SalaryVO salaryVO) throws Exception{
		delete("salaryDAO.deleteExcelMaster", salaryVO);
	}
	
	public void updateExcelMaster(SalaryMasterVO salaryMasterVO) throws Exception{
		update("salaryDAO.updateExcelMaster", salaryMasterVO);
	}
	
	public int salaryListCnt(SalaryMasterVO salaryMasterVO) {
		return (int) select("BasketDAO.salaryListCnt", salaryMasterVO);
	}

	public int emplSalaryListCnt(SalaryVO salaryVO) {
		return (int) select("BasketDAO.emplSalaryListCnt", salaryVO);
	}
}