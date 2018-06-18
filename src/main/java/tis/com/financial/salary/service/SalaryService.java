package tis.com.financial.salary.service;

import java.util.List;

public interface SalaryService {

	/*급여 리스트 전체 조회*/
	List<?> allSalaryList(SalaryMasterVO salaryMasterVO) throws Exception;
	
	/*급여 리스트 선택 조회*/
	List<?> selectSalaryList(SalaryMasterVO salaryMasterVO) throws Exception;
	
	/*급여 상세보기*/
	List<?> salaryView(SalaryVO salaryVO) throws Exception;
	
	/*급여 상세보기*/
	List<?> salaryViewExcel(SalaryVO salaryVO) throws Exception;
	
	/*사원별 급여 조회*/
	List<?> salaryEmplyrDetail(SalaryVO salaryVO) throws Exception;
	
	/*사원별 급여 조회*/
	List<?> salaryEmplyrDetailExcel(SalaryVO salaryVO) throws Exception;
	
	/*전체사원 조회*/
	List<?> salaryFull(SalaryVO salaryVO) throws Exception;
	
	/*엑셀디테일 업로드*/
	public void insertExcelDetail(SalaryVO salaryVO) throws Exception;
	
	/*엑셀마스터 업로드*/
	public void insertExcelMaster(SalaryVO salaryVO) throws Exception;
	
	/*엑셀디테일 삭제*/
	public void deleteExcelDetail(SalaryVO salaryVO) throws Exception;
	
	/*엑셀마스터 삭제*/
	public void deleteExcelMaster(SalaryVO salaryVO) throws Exception;
	
	/*엑셀마스터 총금액 업데이트*/
	public void updateExcelMaster(SalaryMasterVO salaryMasterVO) throws Exception;

	/*급여리스트 총갯수*/
	int salaryListCnt(SalaryMasterVO salaryMasterVO) throws Exception;
	
	/*사원별 급여리스트 총갯수*/
	int emplSalaryListCnt(SalaryVO salaryVO) throws Exception;
}
