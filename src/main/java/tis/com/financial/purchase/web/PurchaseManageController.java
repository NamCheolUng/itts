package tis.com.financial.purchase.web;

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
import tis.com.financial.bankBook.service.TbBankbookmanageService;
import tis.com.financial.purchase.service.PurchaseDetailVO;
import tis.com.financial.purchase.service.PurchaseSearchVO;
import tis.com.financial.purchase.service.PurchaseService;
import tis.com.financial.purchase.service.PurchaseVO;
import tis.com.task.service.TaskService;
import tis.com.task.service.TaskVO;
import egovframework.rte.psl.dataaccess.util.EgovMap;



@Controller
public class PurchaseManageController {

	@Resource(name = "purchaseService")
	private PurchaseService purchaseService;
	
	@Resource(name = "TisCodeService")
	private TisCodeService tisCodeService;
	
	@Resource(name = "tisUtilService")
	private TisUtilService tisUtilService;
	
	@Resource(name="taskService")
	private TaskService taskService;
	
	@Resource(name = "tbBankbookmanageService")
	private TbBankbookmanageService tbBankbookmanageService;
	
	@RequestMapping("/com/financial/purchase/purchaseManageList.do")
	public String purchaseManageList(ModelMap model, HttpServletRequest request, PurchaseSearchVO searchVO) throws Exception {
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
		
		Map<String, Object> map = purchaseService.selectPurchaseManageList(searchVO);
		
		model.addAttribute("resultList", map.get("resultList"));
		model.addAttribute("searchVO", searchVO);
		model.addAttribute("deptList", tisCodeService.selectCodeDetailList("COM101"));
		model.addAttribute("adbkList", taskService.selectCompanyList(new TaskVO()));
		
		return "tis/com/financial/purchase/purchaseManageList";
	}
	
	@RequestMapping("/com/financial/purchase/purchaseManageListExcelDownload.do")
	public ModelAndView purchaseManageListExcelDownload(ModelMap model, HttpServletRequest request, PurchaseSearchVO searchVO) throws Exception {
		if(searchVO.getSearchOrderSdate() == null || searchVO.getSearchOrderEdate() == null) {
			String firstDayOnMonth = tisUtilService.getFirstDayOfMonth(new Date());
			String lastDayOnMonth = tisUtilService.getLastDayOfMonth(new Date());
			
			searchVO.setSearchOrderSdate(firstDayOnMonth);
			searchVO.setSearchOrderEdate(lastDayOnMonth);
		}
		
		searchVO.setAffiliationId((String)request.getSession().getAttribute("affiliationId"));
		
		if(searchVO.getSearchCondition() == null || searchVO.getSearchCondition().isEmpty()) {
			searchVO.setSearchCondition("PRCS_PLACE");
		}
		
		Map<String, Object> map = purchaseService.selectPurchaseManageList(searchVO);
		
		model.addAttribute("resultList", map.get("resultList"));
		
		return new ModelAndView("purchaseManageListExcelDownloadView");
	}
	
	@RequestMapping("/com/financial/purchase/purchaseManageView.do")
	public String purchaseManageView(ModelMap model, HttpServletRequest request, PurchaseVO purchaseVO) throws Exception {
		
		EgovMap purchaseMap = purchaseService.selectPurchase(purchaseVO.getPurchaseId());
		model.put("purchaseVO", purchaseMap);
		
		List<EgovMap> purchaseDetailMap = (List<EgovMap>)purchaseService.selectPurchaseDetailList(purchaseVO.getPurchaseId());
		model.put("purchaseDetailVO", purchaseDetailMap);
		
		model.addAttribute("tradeSeptList", tisCodeService.selectCodeDetailList("COM502"));
		model.addAttribute("payPointList", tisCodeService.selectCodeDetailList("COM505"));
		model.addAttribute("payWayList", tisCodeService.selectCodeDetailList("COM506"));
		
		return "/tis/com/financial/purchase/purchaseManageWrite";
	}
	
	@RequestMapping("/com/financial/purchase/purchaseManageWriteUpdate.do")
	public String purchaseManageWriteUpdate(ModelMap model,
			HttpServletRequest request,
			final MultipartHttpServletRequest multiRequest,
			PurchaseVO purchaseVO,
			PurchaseDetailVO purchaseDetailVO) throws Exception {
		
		if(!TisUtil.isNumeric(purchaseVO.getPrcsExecPrice())) {
			purchaseVO.setPrcsExecPrice("0");
		}
		
		if(!TisUtil.isNumeric(purchaseVO.getPrcsExecSurtax())) {
			purchaseVO.setPrcsExecSurtax("0");
		}
		
		if(!TisUtil.isNumeric(purchaseVO.getPrcsExecTotPrice())) {
			purchaseVO.setPrcsExecTotPrice("0");
		}
		
		purchaseService.updateDraftPurchase(purchaseVO, purchaseDetailVO.getList(), multiRequest);
		
		return "redirect:/com/financial/purchase/purchaseManageList.do";
	}
	
	@RequestMapping("/com/financial/purchase/purchaseManageApprovalList.do")
	public String purchaseManageApprovalList(ModelMap model, HttpServletRequest request, PurchaseSearchVO searchVO) throws Exception {
		if(searchVO.getSearchOrderSdate() == null || searchVO.getSearchOrderEdate() == null) {
			String firstDayOnMonth = tisUtilService.getFirstDayOfMonth(new Date());
			String lastDayOnMonth = tisUtilService.getLastDayOfMonth(new Date());
			
			searchVO.setSearchOrderSdate(firstDayOnMonth);
			searchVO.setSearchOrderEdate(lastDayOnMonth);
		}
		
		searchVO.setAffiliationId((String)request.getSession().getAttribute("affiliationId"));
		
		if(searchVO.getSearchCondition() == null || searchVO.getSearchCondition().isEmpty()) {
			searchVO.setSearchCondition("PRCS_PLACE");
		}
		
		Map<String, Object> map = purchaseService.selectPurchaseManageApprovalList(searchVO);
		
		model.addAttribute("resultList", map.get("resultList"));
		model.addAttribute("searchVO", searchVO);
		
		// 통잔잔액
		model.addAttribute("accountBalance", tbBankbookmanageService.selectTbBankbookmanageByCpCode(searchVO.getAffiliationId()));
		
		return "/tis/com/financial/purchase/purchaseManageApprovalList";
	}
	
	@RequestMapping("/com/financial/purchase/purchaseManageApprovalListExcelDownload.do")
	public ModelAndView purchaseManageApprovalListExcelDownload(ModelMap model, HttpServletRequest request, PurchaseSearchVO searchVO) throws Exception {
		if(searchVO.getSearchOrderSdate() == null || searchVO.getSearchOrderEdate() == null) {
			String firstDayOnMonth = tisUtilService.getFirstDayOfMonth(new Date());
			String lastDayOnMonth = tisUtilService.getLastDayOfMonth(new Date());
			
			searchVO.setSearchOrderSdate(firstDayOnMonth);
			searchVO.setSearchOrderEdate(lastDayOnMonth);
		}
		
		searchVO.setAffiliationId((String)request.getSession().getAttribute("affiliationId"));
		
		if(searchVO.getSearchCondition() == null || searchVO.getSearchCondition().isEmpty()) {
			searchVO.setSearchCondition("PRCS_PLACE");
		}
		
		Map<String, Object> map = purchaseService.selectPurchaseManageApprovalList(searchVO);
		
		model.addAttribute("resultList", map.get("resultList"));
		model.addAttribute("searchVO", searchVO);
		
		return new ModelAndView("purchaseManageApprovalListExcelDownloadView");
	}
	
	@RequestMapping("/com/financial/purchase/purchaseManageApproval.do")
	public ModelAndView purchaseManageApproval(ModelMap model, HttpServletRequest request, PurchaseVO vo) throws Exception {
		purchaseService.updatePurchasePayApproval(vo);
		
		return new ModelAndView("ajaxMainView");
	}
}
