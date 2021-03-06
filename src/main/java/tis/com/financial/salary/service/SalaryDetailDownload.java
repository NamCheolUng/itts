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

public class SalaryDetailDownload extends AbstractExcelView{
	@Resource(name = "salaryService")
    private SalaryService salaryService;
	
	@SuppressWarnings("deprecation")
	@Override
	protected void buildExcelDocument(Map<String, Object> excel,
			HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		List allSalaryList = null;
		
		SalaryVO salaryVO = new SalaryVO();
		
		String divInfo = excel.toString().replaceAll("[{*}]", "");
		
		String[] splitDivInfo = divInfo.split(",");
		String dtDivInfo =  splitDivInfo[0].replaceAll("excel=", "");
		String[] splitDtDivInfo = dtDivInfo.split("/");
		
		String yyyy = splitDivInfo[3].substring(0,4);
		String mm = splitDivInfo[3].substring(4,6);
		String cpName;
		
		if(splitDivInfo[1].equals("I")){
			cpName= "이튜";
		}else{
			cpName="에스메이커";
		}
		
		salaryVO.setAffiliationId(splitDivInfo[1]);
		salaryVO.setYmonth(splitDivInfo[3]);
		
		setFileNmae(yyyy+"년"+mm+"월 급여내역("+cpName+").xls", request, response);
        HSSFSheet sheet = workbook.createSheet(yyyy+mm);
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
        setText(cell, yyyy+"년"+mm+"월 급여내역("+cpName+")");
        
		cell = getCell(sheet, 2, 0);
		cell.setCellStyle(headCs);
        setText(cell, "사원번호");
        
		cell = getCell(sheet, 2, 1);
		cell.setCellStyle(headCs);
		setText(cell, "사원명");
		
		cell = getCell(sheet, 2, 2);
		cell.setCellStyle(headCs);
		setText(cell, "기본근무시간");
		
		cell = getCell(sheet, 2, 3);
		cell.setCellStyle(headCs);
		setText(cell, "야간근무");
		
		cell = getCell(sheet, 2, 4);
		cell.setCellStyle(headCs);
		setText(cell, "주말근무");
		
		cell = getCell(sheet, 2, 5);
		cell.setCellStyle(headCs);
		setText(cell, "지급총액");
		
		cell = getCell(sheet, 2, 6);
		cell.setCellStyle(headCs);
		setText(cell, "공제총액");
		
		cell = getCell(sheet, 2, 7);
		cell.setCellStyle(headCs);
		setText(cell, "실지급액");
		
		cell = getCell(sheet, 2, 8);
		cell.setCellStyle(headCs);
		setText(cell, "비고");

		for(int i=0; i < splitDtDivInfo.length; i++){
				salaryVO.setEmplyrNo(splitDtDivInfo[i]);
				allSalaryList = salaryService.salaryViewExcel(salaryVO);

				List<Map<String, Object>> listMap = allSalaryList; 

				String emplyrNo = listMap.get(0).get("emplyrNo").toString();
				String emplyrNm = listMap.get(0).get("emplyrNm").toString();
				
				String basicWorkingTime = listMap.get(0).get("basicWorkingTime").toString();
				int nbasicWorkingTime = Integer.parseInt(basicWorkingTime);
				String nightWorkingTime = listMap.get(0).get("nightWorkingTime").toString();
				int nnightWorkingTime = Integer.parseInt(nightWorkingTime);
				String holidayWorkingTime = listMap.get(0).get("holidayWorkingTime").toString();
				int nholidayWorkingTime = Integer.parseInt(holidayWorkingTime);
				String totalIncrease  = listMap.get(0).get("totalIncrease").toString();
				int ntotalIncrease = Integer.parseInt(totalIncrease);
				String totalReduction = listMap.get(0).get("totalReduction").toString();
				int ntotalReduction = Integer.parseInt(totalReduction);
				String totalPay = listMap.get(0).get("totalPay").toString();
				int ntotalPay = Integer.parseInt(totalPay);
				
				String rm = listMap.get(0).get("rm").toString();
	
				cell = getCell(sheet, 3+i, 0);
				cell.setCellStyle(mainCs);
				setText(cell, emplyrNo);
				
				cell = getCell(sheet, 3+i, 1);
				cell.setCellStyle(mainCs);
				setText(cell, emplyrNm);
				
				cell = getCell(sheet, 3+i, 2);
				cell.setCellStyle(mainCs);
				cell.setCellValue(nbasicWorkingTime);
				
				cell = getCell(sheet, 3+i, 3);
				cell.setCellStyle(mainCs);
				cell.setCellValue(nnightWorkingTime);
				
				cell = getCell(sheet, 3+i, 4);
				cell.setCellStyle(mainCs);
				cell.setCellValue(nholidayWorkingTime);
				
				cell = getCell(sheet, 3+i, 5);
				cell.setCellStyle(mainCs);
				cell.setCellValue(ntotalIncrease);
				
				cell = getCell(sheet, 3+i, 6);
				cell.setCellStyle(mainCs);
				cell.setCellValue(ntotalReduction);
				
				cell = getCell(sheet, 3+i, 7);
				cell.setCellStyle(mainCs);
				cell.setCellValue(ntotalPay);
				
				cell = getCell(sheet, 3+i, 8);
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
