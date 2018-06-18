package tis.com.financial.sales.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;

import tis.com.common.TisAbstractExcelView;
import tis.com.common.TisExcelUtil;
import egovframework.rte.fdl.excel.download.ExcelFileName;
import egovframework.rte.fdl.excel.download.ExcelStyle;
import egovframework.rte.psl.dataaccess.util.EgovMap;

public class SalesManageListExcelDownloadView extends TisAbstractExcelView {
	
	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook workbook,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		List<?> list = (List<?>) model.get("resultList");
		
		ExcelFileName.setFileNmae("매출목록.xls", request, response);
		
		HSSFSheet sheet = workbook.createSheet("sheet1");
		HSSFCell cell = null;
		int rowIdx  = 0;
		int cellIdx = 0;

		CellStyle headCs = ExcelStyle.getHeadCellStyle(workbook);//제목 style
		CellStyle mainCs = ExcelStyle.getMainCellStyle(workbook);//내용 style
		mainCs.setBorderBottom(CellStyle.BORDER_THIN);
		mainCs.setBorderLeft(CellStyle.BORDER_THIN);
		mainCs.setBorderRight(CellStyle.BORDER_THIN);
		mainCs.setBorderTop(CellStyle.BORDER_THIN);
		
		setText(getCell(sheet, rowIdx, cellIdx++, headCs, 3000), "발주일");
		setText(getCell(sheet, rowIdx, cellIdx++, headCs, 3000), "주매출부");
		setText(getCell(sheet, rowIdx, cellIdx++, headCs, 10000), "과제명(건명)");
		setText(getCell(sheet, rowIdx, cellIdx++, headCs, 7000), "상품/서비스");
		setText(getCell(sheet, rowIdx, cellIdx++, headCs, 7000), "매출처");
		setText(getCell(sheet, rowIdx, cellIdx++, headCs, 4000), "매출금액(계획)");
		setText(getCell(sheet, rowIdx, cellIdx++, headCs, 4000), "VAT(계획)");
		setText(getCell(sheet, rowIdx, cellIdx++, headCs, 4000), "Total(계획)");
		setText(getCell(sheet, rowIdx, cellIdx++, headCs, 4000), "매출금액(집행)");
		setText(getCell(sheet, rowIdx, cellIdx++, headCs, 4000), "VAT(집행)");
		setText(getCell(sheet, rowIdx, cellIdx++, headCs, 4000), "Total(집행)");
		setText(getCell(sheet, rowIdx, cellIdx++, headCs, 3000), "결제예정일");
		setText(getCell(sheet, rowIdx, cellIdx++, headCs, 3000), "결제일");
		setText(getCell(sheet, rowIdx, cellIdx++, headCs), "결제여부");
		setText(getCell(sheet, rowIdx, cellIdx++, headCs, 3000), "세금계산서발행일");
		setText(getCell(sheet, rowIdx, cellIdx++, headCs), "거래구분");
		
		rowIdx++;
		
		for(int i=0; i<list.size();i++){
			cellIdx = 0;
			EgovMap vo = (EgovMap) list.get(i);
			
			setText(getCell(sheet, rowIdx, cellIdx++, mainCs), (String)vo.get("orderDt"));
			setText(getCell(sheet, rowIdx, cellIdx++, mainCs), (String)vo.get("deptNm"));
			setText(getCell(sheet, rowIdx, cellIdx++, mainCs), (String)vo.get("taskNm"));
			setText(getCell(sheet, rowIdx, cellIdx++, mainCs), (String)vo.get("prdtNm"));
			setText(getCell(sheet, rowIdx, cellIdx++, mainCs), (String)vo.get("cmpnyNm"));
			setText(getCell(sheet, rowIdx, cellIdx++, mainCs), TisExcelUtil.bigDecimalToCurrency(vo.get("salesPlanPrice")));
			setText(getCell(sheet, rowIdx, cellIdx++, mainCs), TisExcelUtil.bigDecimalToCurrency(vo.get("salesPlanSurtax")));
			setText(getCell(sheet, rowIdx, cellIdx++, mainCs), TisExcelUtil.bigDecimalToCurrency(vo.get("salesPlanTotPrice")));
			setText(getCell(sheet, rowIdx, cellIdx++, mainCs), TisExcelUtil.bigDecimalToCurrency(vo.get("salesExecPrice")));
			setText(getCell(sheet, rowIdx, cellIdx++, mainCs), TisExcelUtil.bigDecimalToCurrency(vo.get("salesExecSurtax")));
			setText(getCell(sheet, rowIdx, cellIdx++, mainCs), TisExcelUtil.bigDecimalToCurrency(vo.get("salesExecTotPrice")));
			setText(getCell(sheet, rowIdx, cellIdx++, mainCs), (String)vo.get("payPlanDate"));
			setText(getCell(sheet, rowIdx, cellIdx++, mainCs), (String)vo.get("payDate"));
			setText(getCell(sheet, rowIdx, cellIdx++, mainCs), (String)vo.get("payAt"));
			setText(getCell(sheet, rowIdx, cellIdx++, mainCs), (String)vo.get("taxPlanDate"));
			setText(getCell(sheet, rowIdx, cellIdx++, mainCs), (String)vo.get("tradeSeptNm"));
			
			rowIdx++;	
		}
	}
}
