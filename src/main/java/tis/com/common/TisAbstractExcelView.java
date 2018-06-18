package tis.com.common;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.springframework.web.servlet.view.document.AbstractExcelView;

public class TisAbstractExcelView extends AbstractExcelView {
	
	protected HSSFCell getCell(HSSFSheet sheet, int row, int col, CellStyle style) {
		return getCell(sheet, row, col, style, -1);
	}
	
	protected HSSFCell getCell(HSSFSheet sheet, int row, int col, int colWidth) {
		return getCell(sheet, row, col, null, colWidth);
	}
	
	protected HSSFCell getCell(HSSFSheet sheet, int row, int col, CellStyle style, int colWidth) {
		HSSFCell cell = getCell(sheet, row, col);
		
		if(style != null) {
			cell.setCellStyle(style);
		}
		
		if(colWidth > -1) {
			sheet.setColumnWidth(col, colWidth);
		}
		
		return cell;
	}

	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	}
}
