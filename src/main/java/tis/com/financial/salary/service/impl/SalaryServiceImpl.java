package tis.com.financial.salary.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import tis.com.financial.salary.service.SalaryMasterVO;
import tis.com.financial.salary.service.SalaryService;
import tis.com.financial.salary.service.SalaryVO;

@Service("salaryService")
public class SalaryServiceImpl implements SalaryService{

	@Resource(name="salaryDAO")
	private SalaryDAO salaryDAO;
	
	@Override
	public List<?> allSalaryList(SalaryMasterVO salaryMasterVO) throws Exception{
		return salaryDAO.allSalaryList(salaryMasterVO);
	}
	
	@Override
	public List<?> selectSalaryList(SalaryMasterVO salaryMasterVO) throws Exception{
		return salaryDAO.selectSalaryList(salaryMasterVO);
	}
	
	@Override
	public List<?> salaryView(SalaryVO salaryVO) throws Exception{
		return salaryDAO.salaryView(salaryVO);
	}
	
	@Override
	public List<?> salaryViewExcel(SalaryVO salaryVO) throws Exception{
		return salaryDAO.salaryViewExcel(salaryVO);
	}
	
	@Override
	public List<?> salaryEmplyrDetail(SalaryVO salaryVO) throws Exception{
		return salaryDAO.salaryEmplyrDetail(salaryVO);
	}
	
	@Override
	public List<?> salaryEmplyrDetailExcel(SalaryVO salaryVO) throws Exception{
		return salaryDAO.salaryEmplyrDetailExcel(salaryVO);
	}
	
	@Override
	public List<?> salaryFull(SalaryVO salaryVO) throws Exception{
		return salaryDAO.salaryFull(salaryVO);
	}
	
	@Override
	public void insertExcelDetail(SalaryVO salaryVO) throws Exception{
		salaryDAO.insertExcelDetail(salaryVO);
	}
	
	@Override
	public void insertExcelMaster(SalaryVO salaryVO) throws Exception{
		salaryDAO.insertExcelMaster(salaryVO);
	}
	
	@Override
	public void deleteExcelDetail(SalaryVO salaryVO) throws Exception{
		salaryDAO.deleteExcelDetail(salaryVO);
	}
	
	@Override
	public void deleteExcelMaster(SalaryVO salaryVO) throws Exception{
		salaryDAO.deleteExcelMaster(salaryVO);
	}
	
	@Override
	public void updateExcelMaster(SalaryMasterVO salaryMasterVO) throws Exception{
		salaryDAO.updateExcelMaster(salaryMasterVO);
	}
	
	@Override
	public int salaryListCnt(SalaryMasterVO salaryMasterVO) throws Exception{
		return salaryDAO.salaryListCnt(salaryMasterVO);
	}
	
	@Override
	public int emplSalaryListCnt(SalaryVO salaryVO) throws Exception{
		return salaryDAO.emplSalaryListCnt(salaryVO);
	}
}