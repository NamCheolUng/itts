package tis.com.financial.salary.web;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import tis.com.financial.salary.service.SalaryMasterVO;
import tis.com.financial.salary.service.SalaryService;
import tis.com.financial.salary.service.SalaryVO;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.sun.star.io.IOException;

import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

@Controller
public class SalaryController {
	@Resource(name = "salaryService")
    private SalaryService salaryService;
	
	/*급여대장*/
	@RequestMapping(value = "/com/financial/salary/salaryList.do")
	public String salaryList(Model model, @RequestParam("affiliationId") String affiliationId,HttpServletRequest request,
			@ModelAttribute("salaryMasterVO") SalaryMasterVO salaryMasterVO,
			@RequestParam(value = "selectListCnt", defaultValue= "10")int selectListCnt) throws Exception {
		
		request.getSession().setAttribute("affiliationId", affiliationId);
		
		salaryMasterVO.setAffiliationId(affiliationId);
		PaginationInfo paginationInfo = new PaginationInfo();

		paginationInfo.setCurrentPageNo(salaryMasterVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(selectListCnt);
		paginationInfo.setPageSize(salaryMasterVO.getPageSize());
		
		salaryMasterVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		salaryMasterVO.setLastIndex(paginationInfo.getLastRecordIndex());
		salaryMasterVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		int totCnt = salaryService.salaryListCnt(salaryMasterVO);
		
		String sdate = salaryMasterVO.getStartDate();
		String edate = salaryMasterVO.getEndDate();
		paginationInfo.setTotalRecordCount(totCnt);
		
		List<?> allSalaryList = salaryService.allSalaryList(salaryMasterVO);
		
		model.addAttribute("resultList", allSalaryList);
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("sdate", sdate);
		model.addAttribute("edate", edate);
		if(affiliationId.equals("I")){
			return "tis/com/financial/salary/salaryListIeetu";}
		else{
			return "tis/com/financial/salary/salaryListSmaker";}
		
	}
	
	/*급여상세내역*/
	@RequestMapping(value = "/com/financial/salary/salaryDetail.do")
	public String salaryDetail(@ModelAttribute("salaryVO") SalaryVO salaryVO, Model model,
							   @RequestParam("affiliationId") String affiliationId, HttpServletRequest request) throws Exception {
		List<?> salaryDetail = salaryService.salaryView(salaryVO);
		
		model.addAttribute("salaryDetail",salaryDetail);
		model.addAttribute("salaryVO",salaryVO);
		if(salaryVO.getAffiliationId().equals("I")){
			return "tis/com/financial/salary/salaryDetailIeetu";}
		else{
			return "tis/com/financial/salary/salaryDetailSmaker";}
	}
	
	/*사원별급여내역*/
	@RequestMapping(value = "/com/financial/salary/salaryDetailEmplyr.do")
	public String salaryDetailEmplyr(@ModelAttribute("salaryVO") SalaryVO salaryVO, Model model,
									@RequestParam("affili") String affili, HttpServletRequest request,
									@RequestParam(value = "selectListCnt", defaultValue= "10")int selectListCnt) throws Exception {
		salaryVO.setAffiliationId(affili);
		
		PaginationInfo paginationInfo = new PaginationInfo();

		paginationInfo.setCurrentPageNo(salaryVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(selectListCnt);
		paginationInfo.setPageSize(salaryVO.getPageSize());
		
		salaryVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		salaryVO.setLastIndex(paginationInfo.getLastRecordIndex());
		salaryVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		int totCnt = salaryService.emplSalaryListCnt(salaryVO);
		
		paginationInfo.setTotalRecordCount(totCnt);
			
		String sdate = salaryVO.getStartDate();
		String edate = salaryVO.getEndDate();
		
		List<?> salaryEmplyrDetail = salaryService.salaryEmplyrDetail(salaryVO);
		
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("resultList",salaryEmplyrDetail);
		model.addAttribute("salaryVO",salaryVO);
		
		model.addAttribute("sdate", sdate);
		model.addAttribute("edate", edate);
		
		if(affili.equals("I")){
			return "tis/com/financial/salary/salaryDetailEmplyrIeetu";}
		else{
			return "tis/com/financial/salary/salaryDetailEmplyrSmaker";}
	}
	
	/*엑셀업로드*/
	@RequestMapping(value = "/com/financial/salary/salaryExcelUpload.do")
	public String excelUpload(MultipartHttpServletRequest req, @ModelAttribute("salaryVO") SalaryVO salaryVO,
								@RequestParam("extension") String extension,
								@RequestParam("affiliationId") String affiliationId, HttpServletRequest request) throws Exception{
		MultipartFile file = req.getFile("excel");
		if (file != null && file.getSize() > 0) {
			try {
				if(extension.equals("xlsx")){
				XSSFWorkbook wb = new XSSFWorkbook(file.getInputStream());
				XSSFSheet sheet = wb.getSheetAt(0);
				
				int last = sheet.getLastRowNum();
				
				salaryVO.setAffiliationId(affiliationId);
				salaryService.deleteExcelDetail(salaryVO);
				salaryService.deleteExcelMaster(salaryVO);
				salaryService.insertExcelMaster(salaryVO);
				
				SalaryMasterVO salaryMasterVO = new SalaryMasterVO();
				
				for(int i=3; i<=last; i++){
					Row row = sheet.getRow(i);
					SalaryVO salaryExcelVO = new SalaryVO();		
					salaryExcelVO.setEmplyrNo(row.getCell(0, Row.CREATE_NULL_AS_BLANK).getStringCellValue());	
					salaryExcelVO.setEmplyrNm(row.getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
						
						int parseDt = (int) row.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
						String stringDt = String.valueOf(parseDt);
						salaryExcelVO.setYmonth(stringDt);
						
						salaryExcelVO.setPayType(row.getCell(3, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
								
						salaryExcelVO.setBasicWorkingTime((int) row.getCell(4, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
						salaryExcelVO.setNightWorkingTime((int) row.getCell(5, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
						salaryExcelVO.setHolidayWorkingTime((int) row.getCell(6, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
						salaryExcelVO.setBasicmPay((int) row.getCell(7, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
						salaryExcelVO.setBasicdPay((int) row.getCell(8, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
						salaryExcelVO.setBasichPay((int) row.getCell(9, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
						salaryExcelVO.setWeeklyHolidayPay((int) row.getCell(10, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
						salaryExcelVO.setExtendedPay((int) row.getCell(11, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
						salaryExcelVO.setHolidayPay((int) row.getCell(12, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
						salaryExcelVO.setNightWorkPay((int) row.getCell(13, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
						salaryExcelVO.setBonus((int) row.getCell(14, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
						salaryExcelVO.setOfcpsPay((int) row.getCell(15, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
						salaryExcelVO.setOvertimePay((int) row.getCell(16, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
						salaryExcelVO.setTransporExpe((int) row.getCell(17, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
						salaryExcelVO.setCarMaintenExpe((int) row.getCell(18, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
						salaryExcelVO.setChildRearingExpe((int) row.getCell(19, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
						salaryExcelVO.setMealExpenses((int) row.getCell(20, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
						salaryExcelVO.setPaymentResults((int) row.getCell(21, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
						salaryExcelVO.setIncomeTax((int) row.getCell(22, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
						salaryExcelVO.setResidenceTax((int) row.getCell(23, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
						salaryExcelVO.setNationalPension((int) row.getCell(24, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
						salaryExcelVO.setHealthInsu((int) row.getCell(25, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
						salaryExcelVO.setUmplInsu((int) row.getCell(26, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
						salaryExcelVO.setLongCareInsu((int) row.getCell(27, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
						salaryExcelVO.setTotalIncrease((int) row.getCell(28, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
						salaryExcelVO.setTotalReduction((int) row.getCell(29, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
						salaryExcelVO.setTotalPay((int) row.getCell(30, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());

						salaryExcelVO.setRm(row.getCell(31, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
						salaryExcelVO.setAffiliationId(affiliationId);
						
						salaryService.insertExcelDetail(salaryExcelVO);
						
						salaryMasterVO.setTotalIncrease(salaryMasterVO.getTotalIncrease()+ salaryExcelVO.getTotalIncrease() );
						salaryMasterVO.setTotalReduction(salaryMasterVO.getTotalReduction()+ salaryExcelVO.getTotalReduction() );
						salaryMasterVO.setTotalPay(salaryMasterVO.getTotalPay()+ salaryExcelVO.getTotalPay() );
				}				
					salaryMasterVO.setAffiliationId(salaryVO.getAffiliationId());
				    salaryMasterVO.setYmonth(salaryVO.getYmonth());
					salaryService.updateExcelMaster(salaryMasterVO);
					
				}else if(extension.equals("xls")){
					
					Workbook wb = new HSSFWorkbook(file.getInputStream());
					Sheet sheet = wb.getSheetAt(0);
					
					int last = sheet.getLastRowNum();
					
					salaryVO.setAffiliationId(affiliationId);
					salaryService.deleteExcelDetail(salaryVO);
					salaryService.deleteExcelMaster(salaryVO);
					salaryService.insertExcelMaster(salaryVO);
					
					SalaryMasterVO salaryMasterVO = new SalaryMasterVO();
					
					for(int i=3; i<=last; i++){
						Row row = sheet.getRow(i);
						SalaryVO salaryExcelVO = new SalaryVO();		
						salaryExcelVO.setEmplyrNo(row.getCell(0, Row.CREATE_NULL_AS_BLANK).getStringCellValue());	
						salaryExcelVO.setEmplyrNm(row.getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
							
							int parseDt = (int) row.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
							String stringDt = String.valueOf(parseDt);
							salaryExcelVO.setYmonth(stringDt);
							
							salaryExcelVO.setPayType(row.getCell(3, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
									
							salaryExcelVO.setBasicWorkingTime((int) row.getCell(4, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
							salaryExcelVO.setNightWorkingTime((int) row.getCell(5, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
							salaryExcelVO.setHolidayWorkingTime((int) row.getCell(6, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
							salaryExcelVO.setBasicmPay((int) row.getCell(7, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
							salaryExcelVO.setBasicdPay((int) row.getCell(8, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
							salaryExcelVO.setBasichPay((int) row.getCell(9, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
							salaryExcelVO.setWeeklyHolidayPay((int) row.getCell(10, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
							salaryExcelVO.setExtendedPay((int) row.getCell(11, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
							salaryExcelVO.setHolidayPay((int) row.getCell(12, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
							salaryExcelVO.setNightWorkPay((int) row.getCell(13, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
							salaryExcelVO.setBonus((int) row.getCell(14, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
							salaryExcelVO.setOfcpsPay((int) row.getCell(15, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
							salaryExcelVO.setOvertimePay((int) row.getCell(16, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
							salaryExcelVO.setTransporExpe((int) row.getCell(17, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
							salaryExcelVO.setCarMaintenExpe((int) row.getCell(18, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
							salaryExcelVO.setChildRearingExpe((int) row.getCell(19, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
							salaryExcelVO.setMealExpenses((int) row.getCell(20, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
							salaryExcelVO.setPaymentResults((int) row.getCell(21, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
							salaryExcelVO.setIncomeTax((int) row.getCell(22, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
							salaryExcelVO.setResidenceTax((int) row.getCell(23, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
							salaryExcelVO.setNationalPension((int) row.getCell(24, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
							salaryExcelVO.setHealthInsu((int) row.getCell(25, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
							salaryExcelVO.setUmplInsu((int) row.getCell(26, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
							salaryExcelVO.setLongCareInsu((int) row.getCell(27, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
							salaryExcelVO.setTotalIncrease((int) row.getCell(28, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
							salaryExcelVO.setTotalReduction((int) row.getCell(29, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
							salaryExcelVO.setTotalPay((int) row.getCell(30, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());

							salaryExcelVO.setRm(row.getCell(31, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
							salaryExcelVO.setAffiliationId(affiliationId);
							
							salaryService.insertExcelDetail(salaryExcelVO);
							
							salaryMasterVO.setTotalIncrease(salaryMasterVO.getTotalIncrease()+ salaryExcelVO.getTotalIncrease() );
							salaryMasterVO.setTotalReduction(salaryMasterVO.getTotalReduction()+ salaryExcelVO.getTotalReduction() );
							salaryMasterVO.setTotalPay(salaryMasterVO.getTotalPay()+ salaryExcelVO.getTotalPay() );
					}
							salaryMasterVO.setAffiliationId(salaryVO.getAffiliationId());
						    salaryMasterVO.setYmonth(salaryVO.getYmonth());
							salaryService.updateExcelMaster(salaryMasterVO);
				}
			} catch (IllegalStateException e) {
				e.printStackTrace();
			}
		}
		System.out.println(affiliationId);
		return "redirect:/com/financial/salary/salaryList.do?affiliationId="+affiliationId;
	}
	
	/*리스트엑셀다운로드*/
	@RequestMapping(value = "/com/financial/salary/downloadExcel.do")
	public ModelAndView downloadExcel(ModelMap model,
			@RequestParam("affiliationId") String affiliationId,
			@RequestParam("ymonth") String ymonth, HttpServletRequest request) throws Exception{
		
		return new ModelAndView("salaryDownload", "excel", ymonth+","+affiliationId);
	}
	
	/*디테일엑셀다운로드*/
	@RequestMapping(value = "/com/financial/salary/downloadDetailExcel.do")
	public ModelAndView downloadDetailExcel(ModelMap model,
			@RequestParam("affiliationId") String affiliationId,
			@RequestParam("emplyrNo") String emplyrNo,
			@RequestParam("ymonth") String ymonth, HttpServletRequest request) throws Exception{

		return new ModelAndView("salaryDetailDownload", "excel", emplyrNo+","+affiliationId+","+ymonth);
	}
	
	/*디테일사원엑셀다운로드*/
	@RequestMapping(value = "/com/financial/salary/downloadDetailEmplExcel.do")
	public ModelAndView downloadDetailEmplExcel(ModelMap model,
			@RequestParam("affiliationId") String affiliationId,
			@RequestParam("ymonth") String ymonth,
			@RequestParam("emplyrNm") String emplyrNm,
			@RequestParam("emplyrNo") String emplyrNo, HttpServletRequest request) throws Exception{
		
		return new ModelAndView("salaryDetailEmplDownload", "excel", ymonth+","+affiliationId+","+emplyrNo+","+emplyrNm);
	}
	
	/*월별상세급여 엑셀다운*/
	@RequestMapping(value = "/com/financial/salary/salaryMonthDownload.do")
	public ModelAndView salaryMonthDownload(ModelMap model,
			@RequestParam("affiliationId") String affiliationId,
			@RequestParam("ymonth") String ymonth, HttpServletRequest request) throws Exception{
		
		return new ModelAndView("salaryMonthDownload", "excel", ymonth+","+affiliationId);
	}
	
	/*엑셀양식 다운*/
	@RequestMapping("/com/epay/draft/excelFormDownload.do")
	public ModelAndView excelFormDownload(ModelMap model) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		return new ModelAndView("salaryFormDownload", "accessInfoList", map);
	}
}