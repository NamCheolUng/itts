package tis.com.financial.sales.web;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import tis.com.common.TisUtil;
import tis.com.common.service.TisCodeService;
import tis.com.common.service.TisUtilService;
import tis.com.financial.sales.service.SalesDetailVO;
import tis.com.financial.sales.service.SalesSearchVO;
import tis.com.financial.sales.service.SalesService;
import tis.com.financial.sales.service.SalesVO;
import tis.com.task.service.TaskService;
import tis.com.task.service.TaskVO;
import egovframework.rte.psl.dataaccess.util.EgovMap;



@Controller
public class SalesManageController {
	
	@Resource(name = "salesService")
	private SalesService salesService;
	
	@Resource(name = "TisCodeService")
	private TisCodeService tisCodeService;
	
	@Resource(name = "tisUtilService")
	private TisUtilService tisUtilService;
	
	@Resource(name="taskService")
	private TaskService taskService;
	
	@RequestMapping("/com/financial/sales/salesManageList.do")
	public String salesManageList(ModelMap model, HttpServletRequest request, SalesSearchVO searchVO) throws Exception {
		if(searchVO.getAffiliationId() != null) {
			request.getSession().setAttribute("affiliationId", searchVO.getAffiliationId());
		}
		
		searchVO.setAffiliationId((String)request.getSession().getAttribute("affiliationId"));
		
		if(searchVO.getSearchOrderSdate() == null || searchVO.getSearchOrderEdate() == null) {
			String firstDayOnMonth = tisUtilService.getFirstDayOfMonth(new Date());
			String lastDayOnMonth = tisUtilService.getLastDayOfMonth(new Date());
			
			searchVO.setSearchOrderSdate(firstDayOnMonth);
			searchVO.setSearchOrderEdate(lastDayOnMonth);
		}
		
		Map<String, Object> map = salesService.selectSalesManageList(searchVO);
		
		model.addAttribute("resultList", map.get("resultList"));
		model.addAttribute("searchVO", searchVO);
		model.addAttribute("deptList", tisCodeService.selectCodeDetailList("COM101"));
		model.addAttribute("adbkList", taskService.selectCompanyList(new TaskVO()));
		
		return "tis/com/financial/sales/salesManageList";
	}
	
	@RequestMapping("/com/financial/sales/salesManageListExcelDownload.do")
	public ModelAndView salesManageListExcelDownload(SalesSearchVO searchVO, ModelMap model) throws Exception {
		Map<String, Object> map = salesService.selectSalesManageList(searchVO);
		
		model.addAttribute("resultList", map.get("resultList"));
		
		return new ModelAndView("salesManageListExcelDownloadView");
	}
	
	@RequestMapping("/com/financial/sales/salesManageView.do")
	public String salesManageView(ModelMap model, HttpServletRequest request, SalesVO salesVO) throws Exception {
		
		EgovMap salesMap = salesService.selectSales(salesVO.getSalesId());
		model.put("salesVO", salesMap);
		
		List<EgovMap> salesDetailMap = (List<EgovMap>)salesService.selectSalesDetailList(salesVO.getSalesId());
		model.put("salesDetailVO", salesDetailMap);
		
		model.addAttribute("tradeSeptList", tisCodeService.selectCodeDetailList("COM502"));
		model.addAttribute("payPointList", tisCodeService.selectCodeDetailList("COM505"));
		model.addAttribute("payWayList", tisCodeService.selectCodeDetailList("COM506"));
		
		return "/tis/com/financial/sales/salesManageWrite";
	}
	
	@RequestMapping("/com/financial/sales/salesManageWriteUpdate.do")
	public String salesManageWriteUpdate(ModelMap model,
			HttpServletRequest request,
			final MultipartHttpServletRequest multiRequest,
			SalesVO salesVO,
			SalesDetailVO salesDetailVO) throws Exception {
		
		if(!TisUtil.isNumeric(salesVO.getSalesExecPrice())) {
			salesVO.setSalesExecPrice("0");
		}
		
		if(!TisUtil.isNumeric(salesVO.getSalesExecSurtax())) {
			salesVO.setSalesExecSurtax("0");
		}
		
		if(!TisUtil.isNumeric(salesVO.getSalesExecTotPrice())) {
			salesVO.setSalesExecTotPrice("0");
		}
		
		salesService.updateDraftSales(salesVO, salesDetailVO.getList(), multiRequest);
		
		return "redirect:/com/financial/sales/salesManageList.do";
	}
}
