package tis.com.financial.salary.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.Region;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.sun.star.table.CellRangeAddress;

public class SalaryFormDownload extends AbstractExcelView{

	@SuppressWarnings("deprecation")
	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		setFileNmae("급여내역_양식.xls", request, response);
		
		HSSFCell cell = null;
 
        HSSFSheet sheet = workbook.createSheet("급여내역");
        sheet.setDefaultColumnWidth(20);
        
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
		setText(cell, "사원번호*");
		
		cell = getCell(sheet, 2, 1);
		cell.setCellStyle(headCs);
		setText(cell, "사원이름*");
		
		cell = getCell(sheet, 2, 2);
		cell.setCellStyle(headCs);
		setText(cell, "구분*");
		
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
		setText(cell, "총증가액*");
		
		cell = getCell(sheet, 2, 29);
		cell.setCellStyle(headCs);
		setText(cell, "총차감액*");
		
		cell = getCell(sheet, 2, 30);
		cell.setCellStyle(headCs);
		setText(cell, "실지급액*");
		
		cell = getCell(sheet, 2, 31);
		cell.setCellStyle(headCs);
		setText(cell, "비고");
		
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
		sheet.setColumnWidth(31, 10000);
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
