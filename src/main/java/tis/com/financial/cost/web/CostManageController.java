package tis.com.financial.cost.web;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import tis.com.common.service.TisCodeService;
import tis.com.common.service.TisUtilService;
import tis.com.financial.bankBook.service.TbBankbookmanageService;
import tis.com.financial.cost.service.CostSearchVO;
import tis.com.financial.cost.service.CostService;
import tis.com.financial.cost.service.CostVO;
import egovframework.rte.psl.dataaccess.util.EgovMap;



@Controller
public class CostManageController {

	@Resource(name = "costService")
	private CostService costService;
	
	@Resource(name = "TisCodeService")
	private TisCodeService tisCodeService;
	
	@Resource(name = "tisUtilService")
	private TisUtilService tisUtilService;
	
	@Resource(name = "tbBankbookmanageService")
	private TbBankbookmanageService tbBankbookmanageService;
	
	@RequestMapping("/com/financial/cost/costManageList.do")
	public String costManageList(ModelMap model, HttpServletRequest request, CostSearchVO searchVO) throws Exception {
		if(searchVO.getAffiliationId() != null) {
			request.getSession().setAttribute("affiliationId", searchVO.getAffiliationId());
		}
		
		searchVO.setAffiliationId((String)request.getSession().getAttribute("affiliationId"));
		
		if(searchVO.getSearchExpSdate() == null || searchVO.getSearchExpEdate() == null) {
			String firstDayOnMonth = tisUtilService.getFirstDayOfMonth(new Date());
			String lastDayOnMonth = tisUtilService.getLastDayOfMonth(new Date());
			
			searchVO.setSearchExpSdate(firstDayOnMonth);
			searchVO.setSearchExpEdate(lastDayOnMonth);
		}
		
		Map<String, Object> map = costService.selectCostManageList(searchVO);
		
		model.addAttribute("resultList", map.get("resultList"));
		model.addAttribute("searchVO", searchVO);
		model.addAttribute("deptList", tisCodeService.selectCodeDetailList("COM101"));
		
		return "tis/com/financial/cost/costManageList";
	}
	
	@RequestMapping("/com/financial/cost/costManageListExcelDownload.do")
	public ModelAndView costManageListExcelDownload(ModelMap model, HttpServletRequest request, CostSearchVO searchVO) throws Exception {
		if(searchVO.getSearchExpSdate() == null || searchVO.getSearchExpEdate() == null) {
			String firstDayOnMonth = tisUtilService.getFirstDayOfMonth(new Date());
			String lastDayOnMonth = tisUtilService.getLastDayOfMonth(new Date());
			
			searchVO.setSearchExpSdate(firstDayOnMonth);
			searchVO.setSearchExpEdate(lastDayOnMonth);
		}
		
		searchVO.setAffiliationId((String)request.getSession().getAttribute("affiliationId"));
		
		if(searchVO.getSearchCondition() == null || searchVO.getSearchCondition().isEmpty()) {
			searchVO.setSearchCondition("USER_NM");
		}
		
		Map<String, Object> map = costService.selectCostManageList(searchVO);
		
		model.addAttribute("resultList", map.get("resultList"));
		
		return new ModelAndView("costManageListExcelDownloadView");
	}
	
	@RequestMapping("/com/financial/cost/costManageView.do")
	public String costManageView(ModelMap model, HttpServletRequest request, CostVO costVO) throws Exception {
		EgovMap costMap = costService.selectCost(costVO.getCostId());
		model.addAttribute("costVO", costMap);
		
		model.addAttribute("deptList", tisCodeService.selectCodeDetailList("COM101"));
		
		return "/tis/com/financial/cost/costManageWrite";
	}
	
	@RequestMapping("/com/financial/cost/costManageWriteUpdate.do")
	public String costManageWriteUpdate(ModelMap model, CostVO costVO) throws Exception {
		costService.updateCost(costVO);
		
		return "redirect:/com/financial/cost/costManageList.do";
	}
	
	@RequestMapping("/com/financial/cost/costManageApprovalList.do")
	public String costManageApprovalList(ModelMap model, HttpServletRequest request, CostSearchVO searchVO) throws Exception {
		if(searchVO.getSearchExpSdate() == null || searchVO.getSearchExpEdate() == null) {
			String firstDayOnMonth = tisUtilService.getFirstDayOfMonth(new Date());
			String lastDayOnMonth = tisUtilService.getLastDayOfMonth(new Date());
			
			searchVO.setSearchExpSdate(firstDayOnMonth);
			searchVO.setSearchExpEdate(lastDayOnMonth);
		}
		
		searchVO.setAffiliationId((String)request.getSession().getAttribute("affiliationId"));
		
		if(searchVO.getSearchCondition() == null || searchVO.getSearchCondition().isEmpty()) {
			searchVO.setSearchCondition("USER_NM");
		}
		
		Map<String, Object> map = costService.selectCostManageApprovalList(searchVO);
		
		model.addAttribute("resultList", map.get("resultList"));
		model.addAttribute("searchVO", searchVO);
		
		// 통잔잔액
		model.addAttribute("accountBalance", tbBankbookmanageService.selectTbBankbookmanageByCpCode(searchVO.getAffiliationId()));
		
		return "/tis/com/financial/cost/costManageApprovalList";
	}
	
	@RequestMapping("/com/financial/cost/costManageApprovalListExcelDownload.do")
	public ModelAndView costManageApprovalListExcelDownload(ModelMap model, HttpServletRequest request, CostSearchVO searchVO) throws Exception {
		if(searchVO.getSearchExpSdate() == null || searchVO.getSearchExpEdate() == null) {
			String firstDayOnMonth = tisUtilService.getFirstDayOfMonth(new Date());
			String lastDayOnMonth = tisUtilService.getLastDayOfMonth(new Date());
			
			searchVO.setSearchExpSdate(firstDayOnMonth);
			searchVO.setSearchExpEdate(lastDayOnMonth);
		}
		
		searchVO.setAffiliationId((String)request.getSession().getAttribute("affiliationId"));
		
		if(searchVO.getSearchCondition() == null || searchVO.getSearchCondition().isEmpty()) {
			searchVO.setSearchCondition("USER_NM");
		}
		
		Map<String, Object> map = costService.selectCostManageApprovalList(searchVO);
		
		model.addAttribute("resultList", map.get("resultList"));
		model.addAttribute("searchVO", searchVO);
		
		return new ModelAndView("costManageApprovalListExcelDownloadView");
	}
	
	@RequestMapping("/com/financial/cost/costManageApproval.do")
	public ModelAndView costManageApproval(ModelMap model, HttpServletRequest request, CostVO vo) throws Exception {
		costService.updateCostPayApproval(vo);
		
		return new ModelAndView("ajaxMainView");
	}
}
