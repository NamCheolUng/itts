package tis.com.financial.profitloss.web;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import tis.com.common.service.TisCodeService;
import tis.com.financial.cost.service.CostSearchVO;
import tis.com.financial.cost.service.CostService;
import tis.com.financial.profitloss.service.ProfitLossSearchVO;
import tis.com.financial.profitloss.service.ProfitLossService;
import tis.com.financial.purchase.service.PurchaseSearchVO;
import tis.com.financial.purchase.service.PurchaseService;
import tis.com.financial.sales.service.SalesSearchVO;
import tis.com.financial.sales.service.SalesService;
import tis.com.task.service.TaskService;
import tis.com.task.service.TaskVO;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;



@Controller
public class ProfitLossController {

	@Resource(name = "profitLossService")
	private ProfitLossService profitLossService;
	
	@Resource(name = "TisCodeService")
	private TisCodeService tisCodeService;

	@Resource(name="taskService")
	private TaskService taskService;
	
	@Resource(name = "salesService")
	private SalesService salesService;
	
	@Resource(name = "purchaseService")
	private PurchaseService purchaseService;

	@Resource(name = "costService")
	private CostService costService;
	
	@RequestMapping("/com/financial/profitLoss/profitLossList.do")
	public String profitLossList(ModelMap model, HttpServletRequest request, ProfitLossSearchVO searchVO) throws Exception {
		if(searchVO.getAffiliationId() != null) {
			request.getSession().setAttribute("affiliationId", searchVO.getAffiliationId());
		}
		
		PaginationInfo paginationInfo = new PaginationInfo();
		
		paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(searchVO.getRecordCountPerPage());
		paginationInfo.setPageSize(searchVO.getPageSize());
		
		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchVO.setPageSize(paginationInfo.getPageSize());
		
		Map<String, Object> map = profitLossService.selectProfitLossList(searchVO);
		int recordCount = (Integer)map.get("resultCount");
		
		paginationInfo.setTotalRecordCount(recordCount);

		List<EgovMap> resultList = (List<EgovMap>)map.get("resultList");
		
		// task 별 인건비 계산
		for(EgovMap taskProfitLossMap : resultList) {
			String taskId = (String)taskProfitLossMap.get("taskId");
			String taskSdate = ((String)taskProfitLossMap.get("taskSdate"));
			String taskEdate = ((String)taskProfitLossMap.get("taskEdate"));
			
			int taskLaborCost = profitLossService.calculateLaborCost(taskId, taskSdate, taskEdate);
			taskProfitLossMap.put("laborExecPrice", taskLaborCost);
			
			long outgoingsTotPrice = ((BigDecimal)taskProfitLossMap.get("outgoingsTotPrice")).longValue();
			taskProfitLossMap.put("outgoingsTotPrice", outgoingsTotPrice + taskLaborCost);
			
			long businessProfit = ((BigDecimal)taskProfitLossMap.get("businessProfit")).longValue();
			taskProfitLossMap.put("businessProfit", businessProfit - taskLaborCost);
		}
		
		model.addAttribute("resultList", resultList);
		model.addAttribute("paginationInfo", paginationInfo);
		model.addAttribute("searchVO", searchVO);
		model.addAttribute("deptList", tisCodeService.selectCodeDetailList("COM101"));
		
		return "tis/com/financial/profitLoss/profitLossList";
	}
	
	@RequestMapping("/com/financial/profitLoss/profitLossDetail.do")
	public String profitLossDetail(ModelMap model, HttpServletRequest request, String taskId, String pageIndex) throws Exception {
		
		TaskVO taskVO = new TaskVO();
		taskVO.setTaskId(taskId);
		
		EgovMap taskDetailVO = (EgovMap)taskService.selectTaskDetail(taskVO);
		model.addAttribute("taskVO", taskDetailVO);

		// 인건비 계산
		ProfitLossSearchVO profitLossVO = new ProfitLossSearchVO();
		profitLossVO.setSearchTaskId(taskId);
		profitLossVO.setRecordCountPerPage(-1);
		List<EgovMap> taskProfitLossList = (List<EgovMap>)profitLossService.selectProfitLossList(profitLossVO).get("resultList");
		EgovMap taskProfitLossMap = (EgovMap)taskProfitLossList.get(0);
		
		String taskSdate = ((String)taskDetailVO.get("taskSdate"));
		String taskEdate = ((String)taskDetailVO.get("taskEdate"));
		int taskLaborCost = profitLossService.calculateLaborCost(taskId, taskSdate, taskEdate);
		taskProfitLossMap.put("laborExecPrice", taskLaborCost);
		
		// 인건비를 반영한 영업이익 다시 계산
		long businessProfit = ((BigDecimal)taskProfitLossMap.get("businessProfit")).longValue();
		taskProfitLossMap.put("businessProfit", businessProfit - taskLaborCost);
		
		model.addAttribute("profitLossList", taskProfitLossList);
		
		// 매출내역
		SalesSearchVO searchVO = new SalesSearchVO();
		searchVO.setSearchTaskId(taskId);
		model.addAttribute("salesList", salesService.selectSalesManageList(searchVO).get("resultList"));
		
		// 매입내역
		PurchaseSearchVO purchaseVO = new PurchaseSearchVO();
		purchaseVO.setSearchTaskId(taskId);
		model.addAttribute("purchaseList", purchaseService.selectPurchaseManageList(purchaseVO).get("resultList"));
		
		// 경비내역
		CostSearchVO costVO = new CostSearchVO();
		costVO.setSearchTaskId(taskId);
		model.addAttribute("costList", costService.selectCostManageList(costVO).get("resultList"));
		
		// 인건비 내역
		List<EgovMap> laborCostList = (List<EgovMap>)profitLossService.selectLaborCostByTask(taskId);
		for(EgovMap laborCostMap : laborCostList) {
			int taskPersonLaborCost = profitLossService.calculateLaborCostByPerson(taskId, taskSdate, taskEdate, (String)laborCostMap.get("emplNo"));
			laborCostMap.put("taskPersonLaborCost", taskPersonLaborCost);
		}
		model.addAttribute("laborCostList", laborCostList);

		model.addAttribute("pageIndex", pageIndex);
		
		return "tis/com/financial/profitLoss/profitLossDetail";
	}
}
