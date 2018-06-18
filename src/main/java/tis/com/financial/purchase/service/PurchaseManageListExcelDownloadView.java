package tis.com.financial.purchase.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.CellStyle;

import tis.com.common.TisAbstractExcelView;
import tis.com.common.TisExcelUtil;
import egovframework.rte.fdl.excel.download.ExcelFileName;
import egovframework.rte.fdl.excel.download.ExcelStyle;
import egovframework.rte.psl.dataaccess.util.EgovMap;

public class PurchaseManageListExcelDownloadView extends TisAbstractExcelView {
	
	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook workbook,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		List<?> list = (List<?>) model.get("resultList");
		
		ExcelFileName.setFileNmae("매입목록.xls", request, response);
		
		HSSFSheet sheet = workbook.createSheet("sheet1");
		int row  = 0;
		int col = 0;

		CellStyle headCs = ExcelStyle.getHeadCellStyle(workbook);//제목 style
		CellStyle mainCs = ExcelStyle.getMainCellStyle(workbook);//내용 style
		mainCs.setBorderBottom(CellStyle.BORDER_THIN);
		mainCs.setBorderLeft(CellStyle.BORDER_THIN);
		mainCs.setBorderRight(CellStyle.BORDER_THIN);
		mainCs.setBorderTop(CellStyle.BORDER_THIN);
		
		// 타이틀
		setText(getCell(sheet, row, 0, mainCs, -1), "매입");
		sheet.addMergedRegion(new CellRangeAddress(row, row, 0, 15));
		
		row++;
		
		setText(getCell(sheet, row, col++, headCs, 3000), "발주일");
		setText(getCell(sheet, row, col++, headCs, 3000), "주입출부");
		setText(getCell(sheet, row, col++, headCs, 10000), "과제명(건명)");
		setText(getCell(sheet, row, col++, headCs, 7000), "상품/서비스");
		setText(getCell(sheet, row, col++, headCs, 7000), "매입처");
		setText(getCell(sheet, row, col++, headCs, 4000), "매입금액(계획)");
		setText(getCell(sheet, row, col++, headCs, 4000), "VAT(계획)");
		setText(getCell(sheet, row, col++, headCs, 4000), "Total(계획)");
		setText(getCell(sheet, row, col++, headCs, 4000), "매입금액(집행)");
		setText(getCell(sheet, row, col++, headCs, 4000), "VAT(집행)");
		setText(getCell(sheet, row, col++, headCs, 4000), "Total(집행)");
		setText(getCell(sheet, row, col++, headCs, 3000), "결제예정일");
		setText(getCell(sheet, row, col++, headCs, 3000), "결제일");
		setText(getCell(sheet, row, col++, headCs), "결제여부");
		setText(getCell(sheet, row, col++, headCs, 3000), "세금계산서발행일");
		setText(getCell(sheet, row, col++, headCs), "거래구분");
		
		row++;
		
		for(int i=0; i<list.size();i++){
			col = 0;
			EgovMap vo = (EgovMap) list.get(i);
			
			setText(getCell(sheet, row, col++, mainCs), (String)vo.get("orderDt"));
			setText(getCell(sheet, row, col++, mainCs), (String)vo.get("deptNm"));
			setText(getCell(sheet, row, col++, mainCs), (String)vo.get("taskNm"));
			setText(getCell(sheet, row, col++, mainCs), (String)vo.get("prdtNm"));
			setText(getCell(sheet, row, col++, mainCs), (String)vo.get("cmpnyNm"));
			setText(getCell(sheet, row, col++, mainCs), TisExcelUtil.bigDecimalToCurrency(vo.get("prcsPlanPrice")));
			setText(getCell(sheet, row, col++, mainCs), TisExcelUtil.bigDecimalToCurrency(vo.get("prcsPlanSurtax")));
			setText(getCell(sheet, row, col++, mainCs), TisExcelUtil.bigDecimalToCurrency(vo.get("prcsPlanTotPrice")));
			setText(getCell(sheet, row, col++, mainCs), TisExcelUtil.bigDecimalToCurrency(vo.get("prcsExecPrice")));
			setText(getCell(sheet, row, col++, mainCs), TisExcelUtil.bigDecimalToCurrency(vo.get("prcsExecSurtax")));
			setText(getCell(sheet, row, col++, mainCs), TisExcelUtil.bigDecimalToCurrency(vo.get("prcsExecTotPrice")));
			setText(getCell(sheet, row, col++, mainCs), (String)vo.get("payPlanDate"));
			setText(getCell(sheet, row, col++, mainCs), (String)vo.get("payDate"));
			setText(getCell(sheet, row, col++, mainCs), (String)vo.get("payAt"));
			setText(getCell(sheet, row, col++, mainCs), (String)vo.get("taxPlanDate"));
			setText(getCell(sheet, row, col++, mainCs), (String)vo.get("tradeSeptNm"));
			
			row++;	
		}
	}
}
