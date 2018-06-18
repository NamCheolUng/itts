package tis.com.financial.cost.service;

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
import tis.com.financial.purchase.service.PurchaseSearchVO;
import egovframework.rte.fdl.excel.download.ExcelFileName;
import egovframework.rte.fdl.excel.download.ExcelStyle;
import egovframework.rte.psl.dataaccess.util.EgovMap;

public class CostManageApprovalListExcelDownloadView extends TisAbstractExcelView {
	
	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook workbook,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		List<?> list = (List<?>) model.get("resultList");
		CostSearchVO searchVO = (CostSearchVO) model.get("searchVO");
		
		ExcelFileName.setFileNmae("경비정산.xls", request, response);
		
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
		setText(getCell(sheet, row, 0, mainCs, -1), "경비정산");
		sheet.addMergedRegion(new CellRangeAddress(row, row, 0, 8));
		
		row++;
		
		if(searchVO.getSearchCondition().equals("USER_NM")) {
			setText(getCell(sheet, row, col++, headCs, 3000), "담당자");
			setText(getCell(sheet, row, col++, headCs, 3000), "사용일");
			setText(getCell(sheet, row, col++, headCs, 10000), "과제명");
			setText(getCell(sheet, row, col++, headCs, 7000), "부서");
			setText(getCell(sheet, row, col++, headCs, 7000), "상품/서비스명");
			setText(getCell(sheet, row, col++, headCs, 4000), "지출금액");
			setText(getCell(sheet, row, col++, headCs, 3000), "결제 예정일");
			setText(getCell(sheet, row, col++, headCs, 3000), "결제일");
			setText(getCell(sheet, row, col++, headCs), "결제승인");
		} else if(searchVO.getSearchCondition().equals("TASK_ID")) {
			setText(getCell(sheet, row, col++, headCs, 10000), "과제명");
			setText(getCell(sheet, row, col++, headCs, 3000), "담당자");
			setText(getCell(sheet, row, col++, headCs, 3000), "사용일");
			setText(getCell(sheet, row, col++, headCs, 7000), "부서");
			setText(getCell(sheet, row, col++, headCs, 7000), "상품/서비스명");
			setText(getCell(sheet, row, col++, headCs, 4000), "지출금액");
			setText(getCell(sheet, row, col++, headCs, 3000), "결제 예정일");
			setText(getCell(sheet, row, col++, headCs, 3000), "결제일");
			setText(getCell(sheet, row, col++, headCs), "결제승인");
		} else if(searchVO.getSearchCondition().equals("DEPT_CODE")) {
			setText(getCell(sheet, row, col++, headCs, 7000), "부서");
			setText(getCell(sheet, row, col++, headCs, 3000), "담당자");
			setText(getCell(sheet, row, col++, headCs, 10000), "과제명");
			setText(getCell(sheet, row, col++, headCs, 3000), "사용일");
			setText(getCell(sheet, row, col++, headCs, 7000), "상품/서비스명");
			setText(getCell(sheet, row, col++, headCs, 4000), "지출금액");
			setText(getCell(sheet, row, col++, headCs, 3000), "결제 예정일");
			setText(getCell(sheet, row, col++, headCs, 3000), "결제일");
			setText(getCell(sheet, row, col++, headCs), "결제승인");
		}
		
		row++;
		col = 0;
		
		EgovMap sumVO = (EgovMap) list.get(0);
		
		setText(getCell(sheet, row, col++, headCs), "총계");
		setText(getCell(sheet, row, col++, headCs), "");
		setText(getCell(sheet, row, col++, headCs), "");
		setText(getCell(sheet, row, col++, headCs), "");
		setText(getCell(sheet, row, col++, headCs), "");
		setText(getCell(sheet, row, col++, headCs), TisExcelUtil.bigDecimalToCurrency(sumVO.get("expTotPrice")));
		setText(getCell(sheet, row, col++, headCs), "");
		setText(getCell(sheet, row, col++, headCs), "");
		setText(getCell(sheet, row, col++, headCs), "");
		
		sheet.addMergedRegion(new CellRangeAddress(row, row, 0, 4));
		
		row++;
		
		for(int i=0; i<list.size();i++){
			col = 0;
			EgovMap vo = (EgovMap) list.get(i);
			
			String expDate = (String)vo.get("expDate");
			
			// 리스트 그룹
			if(expDate.equals("0000/00/00")) {
				if(searchVO.getSearchCondition().equals("USER_NM")) {
					setText(getCell(sheet, row, col++, mainCs), (String)vo.get("userNm"));
					setText(getCell(sheet, row, col++, mainCs), "-");
					setText(getCell(sheet, row, col++, mainCs), "-");
					setText(getCell(sheet, row, col++, mainCs), (String)vo.get("deptNm"));
					setText(getCell(sheet, row, col++, mainCs), (String)vo.get("prdtNm"));
					setText(getCell(sheet, row, col++, mainCs), TisExcelUtil.bigDecimalToCurrency(vo.get("expTotPrice")));
					setText(getCell(sheet, row, col++, mainCs), "-");
					setText(getCell(sheet, row, col++, mainCs), "-");
					setText(getCell(sheet, row, col++, mainCs), "-");
				} else if(searchVO.getSearchCondition().equals("TASK_ID")) {
					setText(getCell(sheet, row, col++, mainCs), (String)vo.get("taskNm"));
					setText(getCell(sheet, row, col++, mainCs), "-");
					setText(getCell(sheet, row, col++, mainCs), "-");
					setText(getCell(sheet, row, col++, mainCs), (String)vo.get("deptNm"));
					setText(getCell(sheet, row, col++, mainCs), (String)vo.get("prdtNm"));
					setText(getCell(sheet, row, col++, mainCs), TisExcelUtil.bigDecimalToCurrency(vo.get("expTotPrice")));
					setText(getCell(sheet, row, col++, mainCs), "-");
					setText(getCell(sheet, row, col++, mainCs), "-");
					setText(getCell(sheet, row, col++, mainCs), "-");
				} else if(searchVO.getSearchCondition().equals("DEPT_CODE")) {
					setText(getCell(sheet, row, col++, mainCs), (String)vo.get("deptNm"));
					setText(getCell(sheet, row, col++, mainCs), "-");
					setText(getCell(sheet, row, col++, mainCs), "-");
					setText(getCell(sheet, row, col++, mainCs), "-");
					setText(getCell(sheet, row, col++, mainCs), (String)vo.get("prdtNm"));
					setText(getCell(sheet, row, col++, mainCs), TisExcelUtil.bigDecimalToCurrency(vo.get("expTotPrice")));
					setText(getCell(sheet, row, col++, mainCs), "-");
					setText(getCell(sheet, row, col++, mainCs), "-");
					setText(getCell(sheet, row, col++, mainCs), "-");
				}
			
				row++;
			} else if(!expDate.equals("0000/00/00") && !expDate.equals("9999/99/99")){ // 리스트 아이템
				if(searchVO.getSearchCondition().equals("USER_NM")) {
					setText(getCell(sheet, row, col++, mainCs), (String)vo.get("userNm"));
					setText(getCell(sheet, row, col++, mainCs), (String)vo.get("expDate"));
					setText(getCell(sheet, row, col++, mainCs), (String)vo.get("taskNm"));
					setText(getCell(sheet, row, col++, mainCs), (String)vo.get("deptNm"));
					setText(getCell(sheet, row, col++, mainCs), (String)vo.get("prdtNm"));
					setText(getCell(sheet, row, col++, mainCs), TisExcelUtil.bigDecimalToCurrency(vo.get("expTotPrice")));
					setText(getCell(sheet, row, col++, mainCs), (String)vo.get("payPlanDate"));
					setText(getCell(sheet, row, col++, mainCs), (String)vo.get("payDate"));
					setText(getCell(sheet, row, col++, mainCs), (String)vo.get("payApproval"));
				} else if(searchVO.getSearchCondition().equals("TASK_ID")) {
					setText(getCell(sheet, row, col++, mainCs), (String)vo.get("taskNm"));
					setText(getCell(sheet, row, col++, mainCs), (String)vo.get("userNm"));
					setText(getCell(sheet, row, col++, mainCs), (String)vo.get("expDate"));
					setText(getCell(sheet, row, col++, mainCs), (String)vo.get("deptNm"));
					setText(getCell(sheet, row, col++, mainCs), (String)vo.get("prdtNm"));
					setText(getCell(sheet, row, col++, mainCs), TisExcelUtil.bigDecimalToCurrency(vo.get("expTotPrice")));
					setText(getCell(sheet, row, col++, mainCs), (String)vo.get("payPlanDate"));
					setText(getCell(sheet, row, col++, mainCs), (String)vo.get("payDate"));
					setText(getCell(sheet, row, col++, mainCs), (String)vo.get("payApproval"));
				} else if(searchVO.getSearchCondition().equals("DEPT_CODE")) {
					setText(getCell(sheet, row, col++, mainCs), (String)vo.get("deptNm"));
					setText(getCell(sheet, row, col++, mainCs), (String)vo.get("userNm"));
					setText(getCell(sheet, row, col++, mainCs), (String)vo.get("taskNm"));
					setText(getCell(sheet, row, col++, mainCs), (String)vo.get("expDate"));
					setText(getCell(sheet, row, col++, mainCs), (String)vo.get("prdtNm"));
					setText(getCell(sheet, row, col++, mainCs), TisExcelUtil.bigDecimalToCurrency(vo.get("expTotPrice")));
					setText(getCell(sheet, row, col++, mainCs), (String)vo.get("payPlanDate"));
					setText(getCell(sheet, row, col++, mainCs), (String)vo.get("payDate"));
					setText(getCell(sheet, row, col++, mainCs), (String)vo.get("payApproval"));
				}
			
				row++;				
			}
		}
	}
}
