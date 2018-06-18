package tis.com.financial.laterSettle.service;

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

public class LaterSettleParkingDownload extends AbstractExcelView{
	@Resource(name = "laterSettleService")
    private LaterSettleService laterSettleService;
	
	@SuppressWarnings("deprecation")
	@Override
	protected void buildExcelDocument(Map<String, Object> excel,
			HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setFileNmae("후불정산_주차비.xls", request, response);
		
		List allSalaryList = null;
		
		ParkingVO parkingVO = new ParkingVO();
		
		String divInfo = excel.toString().replaceAll("[{*}]", "");
		
		String[] splitDivInfo = divInfo.split(",");
		String dtDivInfo =  splitDivInfo[0].replaceAll("excel=", "");
		String[] splitDtDivInfo = dtDivInfo.split("/");	
		
		parkingVO.setAffiliationId(splitDivInfo[1]);
		
        HSSFSheet sheet = workbook.createSheet("주차비 관리");
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
        setText(cell, "주차비 관리");
        
		cell = getCell(sheet, 2, 0);
		cell.setCellStyle(headCs);
        setText(cell, "이름");
        
		cell = getCell(sheet, 2, 1);
		cell.setCellStyle(headCs);
		setText(cell, "차량번호");
		
		cell = getCell(sheet, 2, 2);
		cell.setCellStyle(headCs);
		setText(cell, "최근연장일자");
		
		cell = getCell(sheet, 2, 3);
		cell.setCellStyle(headCs);
		setText(cell, "구분");
		
		cell = getCell(sheet, 2, 4);
		cell.setCellStyle(headCs);
		setText(cell, "금액(원)");
		
		cell = getCell(sheet, 2, 5);
		cell.setCellStyle(headCs);
		setText(cell, "최근정산일");
		
		cell = getCell(sheet, 2, 6);
		cell.setCellStyle(headCs);
		setText(cell, "만료일");
		
		cell = getCell(sheet, 2, 7);
		cell.setCellStyle(headCs);
		setText(cell, "비고");

		for(int i=0; i < splitDtDivInfo.length; i++){
			parkingVO.setCarNumb(splitDtDivInfo[i]);
				allSalaryList = laterSettleService.selectParkingExpensesListExcel(parkingVO);

				List<Map<String, Object>> listMap = allSalaryList; 
				
				String emplNm = listMap.get(0).get("emplNm").toString();
				String carNumb = listMap.get(0).get("carNumb").toString();
				String extensionDt = listMap.get(0).get("extensionDt").toString();
				String carDiv = listMap.get(0).get("carDiv").toString();
				String cost = listMap.get(0).get("cost").toString();
				int ncost = Integer.parseInt(cost);
				String paymentDt = listMap.get(0).get("paymentDt").toString();
				String expirationDt = listMap.get(0).get("expirationDt").toString();
				String rm = listMap.get(0).get("rm").toString();

				cell = getCell(sheet, 3+i, 0);
				cell.setCellStyle(mainCs);
				setText(cell, emplNm);
				
				cell = getCell(sheet, 3+i, 1);
				cell.setCellStyle(mainCs);
				setText(cell, carNumb);
				
				cell = getCell(sheet, 3+i, 2);
				cell.setCellStyle(mainCs);
				setText(cell, extensionDt);
				
				cell = getCell(sheet, 3+i, 3);
				cell.setCellStyle(mainCs);
				setText(cell, carDiv);
				
				cell = getCell(sheet, 3+i, 4);
				cell.setCellStyle(mainCs);
				cell.setCellValue(ncost);
				
				cell = getCell(sheet, 3+i, 5);
				cell.setCellStyle(mainCs);
				setText(cell, paymentDt);
				
				cell = getCell(sheet, 3+i, 6);
				cell.setCellStyle(mainCs);
				setText(cell, expirationDt);
				
				cell = getCell(sheet, 3+i, 7);
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

	
	/* * 사선*/
	 
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

	
	 /** 항목*/
	 
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

	
	 /** 내용*/
	 
	public static CellStyle getMainCellStyle(HSSFWorkbook wb) {
		CellStyle cs = wb.createCellStyle();

		cs.setWrapText(true);

		setCommonStyle(cs);

		//setBackgroundColor(wb, cs, HSSFColor.LAVENDER.index, (byte) 127, (byte) 171, (byte) 189);
		setTextAlign(cs, CellStyle.ALIGN_CENTER);
		return cs;
	}
	

	
	/* * 배경색 설정*/
	 
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

	
	 /** 총합계*/
	 
	public static CellStyle getTotalCellStyle(HSSFWorkbook wb) {
		CellStyle cs = wb.createCellStyle();
		cs.setWrapText(true);

		setCommonStyle(cs);
		setBackgroundColor(wb, cs, HSSFColor.YELLOW.index, (byte) 255, (byte) 255, (byte) 0);
		setTextAlign(cs, CellStyle.ALIGN_CENTER);

		return cs;
	}
	
	
	/* * 총합계 (mtcc) 부분 */
	 
	public static CellStyle getTotalNameCellStyle(HSSFWorkbook wb) {
		CellStyle cs = wb.createCellStyle();
		cs.setWrapText(true);

		setCommonStyle(cs);
		setBackgroundColor(wb, cs, HSSFColor.WHITE.index, (byte) 255, (byte) 255, (byte) 255);
		setTextAlign(cs, CellStyle.ALIGN_CENTER);

		return cs;
	}

	
	 /** 공통 스타일 설정*/
	 
	public static void setCommonStyle(CellStyle cs) {
		cs.setBorderTop(CellStyle.BORDER_MEDIUM);
		cs.setBorderBottom(CellStyle.BORDER_MEDIUM);
		cs.setBorderLeft(CellStyle.BORDER_MEDIUM);
		cs.setBorderRight(CellStyle.BORDER_MEDIUM);
		cs.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
	}
	

	
	 /** 글자 정렬 설정*/
	 
	public static void setTextAlign(CellStyle cs, short align) {
		cs.setAlignment(align);
	}
}
