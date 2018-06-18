package tis.com.financial.salary.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.list.LazyList;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.Region;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import tis.com.financial.nonOperProfit.service.NonOperVO;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.star.table.CellRangeAddress;

public class SalaryMonthDownload extends AbstractExcelView{
	@Resource(name = "salaryService")
    private SalaryService salaryService;
	
	@SuppressWarnings("deprecation")
	@Override
	protected void buildExcelDocument(Map<String, Object> excel,
			HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setFileNmae("급여_월별전체.xls", request, response);
		
		SalaryVO salaryVO = new SalaryVO();
		
		String divInfo = excel.toString().replaceAll("[{*}]", "");
		
		String[] splitDivInfo = divInfo.split(",");
		String dtDivInfo =  splitDivInfo[0].replaceAll("excel=", "");
		
        HSSFSheet sheet = workbook.createSheet("사원별급여상세");
        sheet.setDefaultColumnWidth(20);
        
		HSSFRow row = sheet.createRow((short)0);
		HSSFCell cell = null;
        // 제목 셀 병합
        sheet.addMergedRegion(new Region((short)0, (short)0, (short)0, (short)4));
        
        CellStyle titleCs = getTitleCellStyle(workbook);
        CellStyle headCs = getHeadCellStyle(workbook);//제목 style
        CellStyle mainCs = getMainCellStyle(workbook);//내용 style
		mainCs.setBorderBottom(CellStyle.BORDER_THIN);
		mainCs.setBorderLeft(CellStyle.BORDER_THIN);
		mainCs.setBorderRight(CellStyle.BORDER_THIN);
		mainCs.setBorderTop(CellStyle.BORDER_THIN);

		cell = getCell(sheet, 0, 0);
		cell.setCellStyle(titleCs);
        setText(cell, "월별급여 상세");
        
		cell = getCell(sheet, 2, 0);
		cell.setCellStyle(headCs);
		setText(cell, "사원번호");
		
		cell = getCell(sheet, 2, 1);
		cell.setCellStyle(headCs);
		setText(cell, "사원이름");
		
		cell = getCell(sheet, 2, 2);
		cell.setCellStyle(headCs);
		setText(cell, "구분");
		
		cell = getCell(sheet, 2, 3);
		cell.setCellStyle(headCs);
		setText(cell, "급여형태");
		
		cell = getCell(sheet, 2, 4);
		cell.setCellStyle(headCs);
		setText(cell, "기본근무시간");
		
		cell = getCell(sheet, 2, 5);
		cell.setCellStyle(headCs);
		setText(cell, "야간근무");
		
		cell = getCell(sheet, 2, 6);
		cell.setCellStyle(headCs);
		setText(cell, "주말근무");
		
		cell = getCell(sheet, 2, 7);
		cell.setCellStyle(headCs);
		setText(cell, "기본월급");
		
		cell = getCell(sheet, 2, 8);
		cell.setCellStyle(headCs);
		setText(cell, "기본일급");
		
		cell = getCell(sheet, 2, 9);
		cell.setCellStyle(headCs);
		setText(cell, "기본시급");
		
		cell = getCell(sheet, 2, 10);
		cell.setCellStyle(headCs);
		setText(cell, "주휴수당");
		
		cell = getCell(sheet, 2, 11);
		cell.setCellStyle(headCs);
		setText(cell, "연장근로수당");
		
		cell = getCell(sheet, 2, 12);
		cell.setCellStyle(headCs);
		setText(cell, "휴일근로수당");
		
		cell = getCell(sheet, 2, 13);
		cell.setCellStyle(headCs);
		setText(cell, "야간근로수당");
		
		cell = getCell(sheet, 2, 14);
		cell.setCellStyle(headCs);
		setText(cell, "상여급");
		
		cell = getCell(sheet, 2, 15);
		cell.setCellStyle(headCs);
		setText(cell, "직급수당");
		
		cell = getCell(sheet, 2, 16);
		cell.setCellStyle(headCs);
		setText(cell, "특근수당");
		
		cell = getCell(sheet, 2, 17);
		cell.setCellStyle(headCs);
		setText(cell, "교통비");
		
		cell = getCell(sheet, 2, 18);
		cell.setCellStyle(headCs);
		setText(cell, "차량유지비");
		
		cell = getCell(sheet, 2, 19);
		cell.setCellStyle(headCs);
		setText(cell, "양육비");
		
		cell = getCell(sheet, 2, 20);
		cell.setCellStyle(headCs);
		setText(cell, "식대");
		
		cell = getCell(sheet, 2, 21);
		cell.setCellStyle(headCs);
		setText(cell, "성과급");
		
		cell = getCell(sheet, 2, 22);
		cell.setCellStyle(headCs);
		setText(cell, "소득세");
		
		cell = getCell(sheet, 2, 23);
		cell.setCellStyle(headCs);
		setText(cell, "주민세");
		
		cell = getCell(sheet, 2, 24);
		cell.setCellStyle(headCs);
		setText(cell, "국민연금");
		
		cell = getCell(sheet, 2, 25);
		cell.setCellStyle(headCs);
		setText(cell, "건강보험");
		
		cell = getCell(sheet, 2, 26);
		cell.setCellStyle(headCs);
		setText(cell, "고용보험");
		
		cell = getCell(sheet, 2, 27);
		cell.setCellStyle(headCs);
		setText(cell, "장기요양");
		
		cell = getCell(sheet, 2, 28);
		cell.setCellStyle(headCs);
		setText(cell, "총증가액");
		
		cell = getCell(sheet, 2, 29);
		cell.setCellStyle(headCs);
		setText(cell, "총차감액");
		
		cell = getCell(sheet, 2, 30);
		cell.setCellStyle(headCs);
		setText(cell, "실지급액");
		
		cell = getCell(sheet, 2, 31);
		cell.setCellStyle(headCs);
		setText(cell, "비고");


		salaryVO.setYmonth(dtDivInfo);
		salaryVO.setAffiliationId(splitDivInfo[1]);
		
		List allSalaryList = salaryService.salaryFull(salaryVO);
		List<Map<String, Object>> listMap = allSalaryList; 
		System.out.println(allSalaryList);
		System.out.println(listMap);
		for(int i=0; i < listMap.size(); i++){
				
				String emplyrNo = listMap.get(i).get("emplyrNo").toString();
				String emplyrNm = listMap.get(i).get("emplyrNm").toString();
				
				String ymonth = listMap.get(i).get("ymonth").toString();
				int nymonth = Integer.parseInt(ymonth);
				
				String payType = listMap.get(i).get("payType").toString();
				
				String basicWorkingTime = listMap.get(i).get("basicWorkingTime").toString();
				int nbasicWorkingTime = Integer.parseInt(basicWorkingTime);
				String nightWorkingTime = listMap.get(i).get("nightWorkingTime").toString();
				int nnightWorkingTime = Integer.parseInt(nightWorkingTime);
				String holidayWorkingTime = listMap.get(i).get("holidayWorkingTime").toString();
				int nholidayWorkingTime = Integer.parseInt(holidayWorkingTime);
				String basicmPay = listMap.get(i).get("basicmPay").toString();
				int nbasicmPay = Integer.parseInt(basicmPay);
				String basicdPay = listMap.get(i).get("basicdPay").toString();
				int nbasicdPay = Integer.parseInt(basicdPay);
				String basichPay = listMap.get(i).get("basichPay").toString();
				int nbasichPay = Integer.parseInt(basichPay);
				String weeklyHolidayPay = listMap.get(i).get("weeklyHolidayPay").toString();
				int nweeklyHolidayPay = Integer.parseInt(weeklyHolidayPay);
				String extendedPay = listMap.get(i).get("extendedPay").toString();
				int nextendedPay = Integer.parseInt(extendedPay);
				String holidayPay = listMap.get(i).get("holidayPay").toString();
				int nholidayPay = Integer.parseInt(holidayPay);
				String nightWorkPay = listMap.get(i).get("nightWorkPay").toString();
				int nnightWorkPay = Integer.parseInt(nightWorkPay);
				String bonus = listMap.get(i).get("bonus").toString();
				int nbonus = Integer.parseInt(bonus);
				String ofcpsPay = listMap.get(i).get("ofcpsPay").toString();
				int nofcpsPay = Integer.parseInt(ofcpsPay);
				String overtimePay = listMap.get(i).get("overtimePay").toString();
				int novertimePay = Integer.parseInt(overtimePay);
				String transporExpe = listMap.get(i).get("transporExpe").toString();
				int ntransporExpe = Integer.parseInt(transporExpe);
				String carMaintenExpe = listMap.get(i).get("carMaintenExpe").toString();
				int ncarMaintenExpe = Integer.parseInt(carMaintenExpe);
				String childRearingExpe = listMap.get(i).get("childRearingExpe").toString();
				int nchildRearingExpe = Integer.parseInt(childRearingExpe);
				String mealExpenses = listMap.get(i).get("mealExpenses").toString();
				int nmealExpenses = Integer.parseInt(mealExpenses);
				String paymentResults = listMap.get(i).get("paymentResults").toString();
				int npaymentResults = Integer.parseInt(paymentResults);
				String incomeTax = listMap.get(i).get("incomeTax").toString();
				int nincomeTax = Integer.parseInt(incomeTax);
				String residenceTax = listMap.get(i).get("residenceTax").toString();
				int nresidenceTax = Integer.parseInt(residenceTax);
				String nationalPension = listMap.get(i).get("nationalPension").toString();
				int nnationalPension = Integer.parseInt(nationalPension);
				String healthInsu = listMap.get(i).get("healthInsu").toString();
				int nhealthInsu = Integer.parseInt(healthInsu);
				String umplInsu = listMap.get(i).get("umplInsu").toString();
				int numplInsu = Integer.parseInt(umplInsu);
				String longCareInsu = listMap.get(i).get("longCareInsu").toString();
				int nlongCareInsu = Integer.parseInt(longCareInsu);
				String totalIncrease = listMap.get(i).get("totalIncrease").toString();
				int ntotalIncrease = Integer.parseInt(totalIncrease);
				String totalReduction = listMap.get(i).get("totalReduction").toString();
				int ntotalReduction = Integer.parseInt(totalReduction);
				String totalPay = listMap.get(i).get("totalPay").toString();
				int ntotalPay = Integer.parseInt(totalPay);
				String rm = listMap.get(i).get("rm").toString();
				
				cell = getCell(sheet, 3+i, 0);
				cell.setCellStyle(mainCs);
				setText(cell, emplyrNo);
				
				cell = getCell(sheet, 3+i, 1);
				cell.setCellStyle(mainCs);
				setText(cell, emplyrNm);
				
				cell = getCell(sheet, 3+i, 2);
				cell.setCellStyle(mainCs);
				cell.setCellValue(nymonth);
				
				cell = getCell(sheet, 3+i, 3);
				cell.setCellStyle(mainCs);
				setText(cell, payType);
				
				cell = getCell(sheet, 3+i, 4);
				cell.setCellStyle(mainCs);
				cell.setCellType(Cell.CELL_TYPE_STRING);
				cell.setCellValue(nbasicWorkingTime);
				
				cell = getCell(sheet, 3+i, 5);
				cell.setCellStyle(mainCs);
				cell.setCellValue(nnightWorkingTime);
				
				cell = getCell(sheet, 3+i, 6);
				cell.setCellStyle(mainCs);
				cell.setCellValue(nholidayWorkingTime);
				
				cell = getCell(sheet, 3+i, 7);
				cell.setCellStyle(mainCs);
				cell.setCellValue(nbasicmPay);
				
				cell = getCell(sheet, 3+i, 8);
				cell.setCellStyle(mainCs);
				cell.setCellValue(nbasicdPay);
				
				cell = getCell(sheet, 3+i, 9);
				cell.setCellStyle(mainCs);
				cell.setCellValue(nbasichPay);
				
				cell = getCell(sheet, 3+i, 10);
				cell.setCellStyle(mainCs);
				cell.setCellValue(nweeklyHolidayPay);
				
				cell = getCell(sheet, 3+i, 11);
				cell.setCellStyle(mainCs);
				cell.setCellValue(nextendedPay);
				
				cell = getCell(sheet, 3+i, 12);
				cell.setCellStyle(mainCs);
				cell.setCellValue(nholidayPay);
				
				cell = getCell(sheet, 3+i, 13);
				cell.setCellStyle(mainCs);
				cell.setCellValue(nnightWorkPay);
				
				cell = getCell(sheet, 3+i, 14);
				cell.setCellStyle(mainCs);
				cell.setCellValue(nbonus);
				
				cell = getCell(sheet, 3+i, 15);
				cell.setCellStyle(mainCs);
				cell.setCellValue(nofcpsPay);
				
				cell = getCell(sheet, 3+i, 16);
				cell.setCellStyle(mainCs);
				cell.setCellValue(novertimePay);
				
				cell = getCell(sheet, 3+i, 17);
				cell.setCellStyle(mainCs);
				cell.setCellValue(ntransporExpe);
				
				cell = getCell(sheet, 3+i, 18);
				cell.setCellStyle(mainCs);
				cell.setCellValue(ncarMaintenExpe);
				
				cell = getCell(sheet, 3+i, 19);
				cell.setCellStyle(mainCs);
				cell.setCellValue(nchildRearingExpe);
				
				cell = getCell(sheet, 3+i, 20);
				cell.setCellStyle(mainCs);
				cell.setCellValue(nmealExpenses);
				
				cell = getCell(sheet, 3+i, 21);
				cell.setCellStyle(mainCs);
				cell.setCellValue(npaymentResults);
				
				cell = getCell(sheet, 3+i, 22);
				cell.setCellStyle(mainCs);
				cell.setCellValue(nincomeTax);
				
				cell = getCell(sheet, 3+i, 23);
				cell.setCellStyle(mainCs);
				cell.setCellValue(nresidenceTax);
				
				cell = getCell(sheet, 3+i, 24);
				cell.setCellStyle(mainCs);
				cell.setCellValue(nnationalPension);
				
				cell = getCell(sheet, 3+i, 25);
				cell.setCellStyle(mainCs);
				cell.setCellValue(nhealthInsu);
				
				cell = getCell(sheet, 3+i, 26);
				cell.setCellStyle(mainCs);
				cell.setCellValue(numplInsu);
				
				cell = getCell(sheet, 3+i, 27);
				cell.setCellStyle(mainCs);
				cell.setCellValue(nlongCareInsu);
				
				cell = getCell(sheet, 3+i, 28);
				cell.setCellStyle(mainCs);
				cell.setCellValue(ntotalIncrease);
				
				cell = getCell(sheet, 3+i, 29);
				cell.setCellStyle(mainCs);
				cell.setCellValue(ntotalReduction);
				
				cell = getCell(sheet, 3+i, 30);
				cell.setCellStyle(mainCs);
				cell.setCellValue(ntotalPay);
				
				cell = getCell(sheet, 3+i, 31);
				cell.setCellStyle(mainCs);
				setText(cell, rm);
		  }
		
		sheet.setColumnWidth(0, 5000);
		sheet.setColumnWidth(1, 5000);
		sheet.setColumnWidth(2, 5000);
		sheet.setColumnWidth(3, 5000);
		sheet.setColumnWidth(4, 5000);
		sheet.setColumnWidth(5, 5000);
		sheet.setColumnWidth(6, 5000);
		sheet.setColumnWidth(7, 5000);
		sheet.setColumnWidth(8, 5000);
		sheet.setColumnWidth(9, 5000);
		sheet.setColumnWidth(10, 5000);
		sheet.setColumnWidth(11, 5000);
		sheet.setColumnWidth(12, 5000);
		sheet.setColumnWidth(13, 5000);
		sheet.setColumnWidth(14, 5000);
		sheet.setColumnWidth(15, 5000);
		sheet.setColumnWidth(16, 5000);
		sheet.setColumnWidth(17, 5000);
		sheet.setColumnWidth(18, 5000);
		sheet.setColumnWidth(19, 5000);
		sheet.setColumnWidth(20, 5000);
		sheet.setColumnWidth(21, 5000);
		sheet.setColumnWidth(22, 5000);
		sheet.setColumnWidth(23, 5000);
		sheet.setColumnWidth(24, 5000);
		sheet.setColumnWidth(25, 5000);
		sheet.setColumnWidth(26, 5000);
		sheet.setColumnWidth(27, 5000);
		sheet.setColumnWidth(28, 5000);
		sheet.setColumnWidth(29, 5000);
		sheet.setColumnWidth(30, 5000);
		sheet.setColumnWidth(31, 5000);

	}
	
	private void setText(HSSFCell cell, int totalIncrease) {
		// TODO Auto-generated method stub
		
	}

	public static void setFileNmae(String fileName, HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException
	{
		String userAgent = req.getHeader("User-Agent");
		
		if(userAgent.indexOf("MSIE") > -1 || userAgent.indexOf("Trident") > -1){//ie10 or ie11
			fileName = URLEncoder.encode(fileName, "utf-8");
		}else{
			fileName = new String(fileName.getBytes("utf-8"), "iso-8859-1");
		}
		  
		resp.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
		resp.setHeader("Content-Transfer-Encoding", "binary");
	}

	public static CellStyle getTitleCellStyle(HSSFWorkbook wb) {
		CellStyle cs = wb.createCellStyle();

		Font font = wb.createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		font.setFontHeight((short) (18 * 20));

		cs.setFont(font);

		setCommonStyle(cs);

		HSSFPalette palette = wb.getCustomPalette();
		HSSFColor hssfColor = null;
		hssfColor = palette.findColor((byte) 215, (byte) 228, (byte) 188);
		if (hssfColor == null) {
			palette.setColorAtIndex(HSSFColor.RED.index, (byte) 196, (byte) 189, (byte) 151);
			hssfColor = palette.getColor(HSSFColor.RED.index);
		}
		cs.setFillForegroundColor(hssfColor.getIndex());
		cs.setFillPattern(CellStyle.SOLID_FOREGROUND);

		setTextAlign(cs, CellStyle.ALIGN_CENTER);

		return cs;
	}
	
	public static CellStyle getTitleCellStyle_left(HSSFWorkbook wb) {
		CellStyle cs = wb.createCellStyle();

		Font font = wb.createFont();
		font.setFontHeight((short) (18 * 10));
		font.setFontName("맑은 고딕");

		cs.setFont(font);
		
		setCommonStyle(cs);
		cs.setBorderBottom(CellStyle.BORDER_THIN);

		setBackgroundColor(wb, cs, HSSFColor.LAVENDER.index, (byte) 184, (byte) 204, (byte) 228);
		setTextAlign(cs, CellStyle.ALIGN_CENTER);

		return cs;
	}

	/*
	 * 사선
	 */
	public static CellStyle getDiagonalCellStyle(HSSFWorkbook wb) {
		CellStyle cs = wb.createCellStyle();

		Font font = wb.createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		font.setFontHeight((short) (10 * 20));

		cs.setFont(font);
		cs.setWrapText(true);

		setCommonStyle(cs);
		setBackgroundColor(wb, cs, HSSFColor.AQUA.index, (byte) 216, (byte) 228, (byte) 188);

		return cs;
	}

	/*
	 * 항목
	 */
	public static CellStyle getHeadCellStyle(HSSFWorkbook wb) {
		CellStyle cs = wb.createCellStyle();

		Font font = wb.createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		font.setFontHeight((short) (10 * 20));

		cs.setFont(font);
		cs.setWrapText(true);
		

		setCommonStyle(cs);

		setBackgroundColor(wb, cs, HSSFColor.AQUA.index, (byte) 216, (byte) 228, (byte) 188);
		setTextAlign(cs, CellStyle.ALIGN_CENTER);
		return cs;
	}

	/*
	 * 내용
	 */
	public static CellStyle getMainCellStyle(HSSFWorkbook wb) {
		CellStyle cs = wb.createCellStyle();

		cs.setWrapText(true);

		setCommonStyle(cs);

		//setBackgroundColor(wb, cs, HSSFColor.LAVENDER.index, (byte) 127, (byte) 171, (byte) 189);
		setTextAlign(cs, CellStyle.ALIGN_CENTER);
		return cs;
	}
	

	/*
	 * 배경색 설정
	 */
	public static void setBackgroundColor(HSSFWorkbook wb, CellStyle cs, short index, byte r, byte g, byte b) {

		HSSFPalette palette = wb.getCustomPalette();
		HSSFColor hssfColor = null;
		hssfColor = palette.findColor((byte) 215, (byte) 228, (byte) 188);
		if (hssfColor == null) {
			palette.setColorAtIndex(index, r, g, b);
			hssfColor = palette.getColor(index);
		}
		cs.setFillForegroundColor(hssfColor.getIndex());
		cs.setFillPattern(CellStyle.SOLID_FOREGROUND);

	}

	/*
	 * 총합계
	 */
	public static CellStyle getTotalCellStyle(HSSFWorkbook wb) {
		CellStyle cs = wb.createCellStyle();
		cs.setWrapText(true);

		setCommonStyle(cs);
		setBackgroundColor(wb, cs, HSSFColor.YELLOW.index, (byte) 255, (byte) 255, (byte) 0);
		setTextAlign(cs, CellStyle.ALIGN_CENTER);

		return cs;
	}
	
	/*
	 * 총합계 (mtcc) 부분 
	 */
	public static CellStyle getTotalNameCellStyle(HSSFWorkbook wb) {
		CellStyle cs = wb.createCellStyle();
		cs.setWrapText(true);

		setCommonStyle(cs);
		setBackgroundColor(wb, cs, HSSFColor.WHITE.index, (byte) 255, (byte) 255, (byte) 255);
		setTextAlign(cs, CellStyle.ALIGN_CENTER);

		return cs;
	}

	/*
	 * 공통 스타일 설정
	 */
	public static void setCommonStyle(CellStyle cs) {
		cs.setBorderTop(CellStyle.BORDER_MEDIUM);
		cs.setBorderBottom(CellStyle.BORDER_MEDIUM);
		cs.setBorderLeft(CellStyle.BORDER_MEDIUM);
		cs.setBorderRight(CellStyle.BORDER_MEDIUM);
		cs.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
	}
	

	/*
	 * 글자 정렬 설정
	 */
	public static void setTextAlign(CellStyle cs, short align) {
		cs.setAlignment(align);
	}
}
