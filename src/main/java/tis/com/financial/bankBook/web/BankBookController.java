package tis.com.financial.bankBook.web;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import tis.com.financial.bankBook.service.TbBankbookmanageService;
import tis.com.financial.bankBook.service.TbBankbookmanageVO;
import tis.com.financial.bankBook.service.TbBankcardmanageService;
import tis.com.financial.bankBook.service.TbBankcardmanageVO;
import egovframework.com.cmm.ComDefaultCodeVO;
import egovframework.com.cmm.service.EgovCmmUseService;
import egovframework.com.cmm.util.EgovUserDetailsHelper;

@Controller
public class BankBookController {
	
	/** cmmUseService */
	@Resource(name = "EgovCmmUseService")
	private EgovCmmUseService cmmUseService;
	
	@Resource(name = "tbBankbookmanageService")
	private TbBankbookmanageService tbBankbookmanageService;

	@Resource(name = "tbBankcardmanageService")
	private TbBankcardmanageService tbBankcardmanageService;
	
	
	/*통장*/
	@RequestMapping(value = "/com/financial/bankBook/bankBook.do")
	public String bankBook(ModelMap model, HttpServletRequest request, @RequestParam("affiliationId") String affiliationId) throws Exception {
		request.getSession().setAttribute("affiliationId", affiliationId);
		
    	ComDefaultCodeVO vo = new ComDefaultCodeVO();
		//횟바구분
		vo.setCodeId("COM103");
		List<?> company_result = cmmUseService.selectCmmCodeDetail(vo);
		
		TbBankbookmanageVO bankBookVO = new TbBankbookmanageVO();
		
		bankBookVO.setCpCode(affiliationId);
		model.addAttribute("company_result", company_result); //회사구분
		model.addAttribute("result_list", tbBankbookmanageService.selectTbBankbookmanageList(bankBookVO));
		
		if(affiliationId.equals("I")){
			return "tis/com/financial/bankBook/bankBookIeetu";
		}else{
			return "tis/com/financial/bankBook/bankBookSmaker";
		}
	}
	
	/*신규통장*/
	@RequestMapping(value = "/com/financial/bankBook/bankBookAdd.do")
	public String bankBookAdd(@ModelAttribute("tbBankbookmanageVO")TbBankbookmanageVO tbBankbookmanageVO, ModelMap model, HttpServletRequest request) throws Exception {
		
    	tbBankbookmanageService.insertTbBankbookmanage(tbBankbookmanageVO);
		return "forward:/com/financial/bankBook/bankBook.do?affiliationId="+tbBankbookmanageVO.getCpCode();
	}
	
	/*통장상세*/
	@RequestMapping(value = "/com/financial/bankBook/bankBookView.do")
	public String bankBookView(@ModelAttribute("tbBankbookmanageVO")TbBankbookmanageVO tbBankbookmanageVO, ModelMap model, HttpServletRequest request,
								@RequestParam("affiliationId") String affiliationId) throws Exception {

    	ComDefaultCodeVO vo = new ComDefaultCodeVO();
		//횟바구분
		vo.setCodeId("COM103");
		List<?> company_result = cmmUseService.selectCmmCodeDetail(vo);
		
		model.addAttribute("company_result", company_result); //회사구분
    	model.addAttribute("tbBankbookmanageVO", tbBankbookmanageService.selectTbBankbookmanage(tbBankbookmanageVO));
    	
		if(affiliationId.equals("I")){
			return "tis/com/financial/bankBook/bankBookViewIeetu";
		}else{
			return "tis/com/financial/bankBook/bankBookViewSmaker";
		}
	}
	
	/*통장수정*/
	@RequestMapping(value = "/com/financial/bankBook/bankBookModify.do")
	public String bankBookModify(@ModelAttribute("tbBankbookmanageVO")TbBankbookmanageVO tbBankbookmanageVO, ModelMap model, HttpServletRequest request,
									@RequestParam("affiliationId") String affiliationId) throws Exception {	
		
    	ComDefaultCodeVO vo = new ComDefaultCodeVO();
		//횟바구분
		vo.setCodeId("COM103");
		List<?> company_result = cmmUseService.selectCmmCodeDetail(vo);
		
		model.addAttribute("company_result", company_result); //회사구분
    	model.addAttribute("tbBankbookmanageVO", tbBankbookmanageService.selectTbBankbookmanage(tbBankbookmanageVO));
    	
		if(affiliationId.equals("I")){
			return "tis/com/financial/bankBook/bankBookModifyIeetu";
		}else{
			return "tis/com/financial/bankBook/bankBookModifySmaker";
		}
	}
	
	/*통장수정저장*/
	@RequestMapping(value = "/com/financial/bankBook/bankBookUpdate.do")
	public String bankBookUpdate(@ModelAttribute("tbBankbookmanageVO")TbBankbookmanageVO tbBankbookmanageVO, ModelMap model, HttpServletRequest request) throws Exception {

    	tbBankbookmanageService.updateTbBankbookmanage(tbBankbookmanageVO);
    	return "forward:/com/financial/bankBook/bankBook.do?affiliationId="+tbBankbookmanageVO.getCpCode();
	}
	
	
	
	
	/*통장삭제*/
	@RequestMapping(value = "/com/financial/bankBook/bankBookDelete.do")
	public String bankBookDelete(@ModelAttribute("tbBankbookmanageVO")TbBankbookmanageVO tbBankbookmanageVO, ModelMap model, HttpServletRequest request,
								@RequestParam("affiliationId") String affiliationId) throws Exception {

    	tbBankbookmanageService.deleteTbBankbookmanage(tbBankbookmanageVO);
		return "redirect:/com/financial/bankBook/bankBook.do?affiliationId="+affiliationId;
	}
	
	/*카드*/
	@RequestMapping(value = "/com/financial/bankBook/card.do")
	public String card(ModelMap model, HttpServletRequest request, @RequestParam("affiliationId") String affiliationId ) throws Exception {
		
    	ComDefaultCodeVO vo = new ComDefaultCodeVO();
		//횟바구분
		vo.setCodeId("COM103");
		List<?> company_result = cmmUseService.selectCmmCodeDetail(vo);
		TbBankcardmanageVO crdVO = new TbBankcardmanageVO();
		
		crdVO.setCpCode(affiliationId);
		
		model.addAttribute("emplyr_list", tbBankcardmanageService.selectEmplyrList()); //사원이름
		model.addAttribute("company_result", company_result); //회사구분
		model.addAttribute("result_list", tbBankcardmanageService.selectTbBankcardmanageList(crdVO));	
		model.addAttribute("bank_list", tbBankbookmanageService.selectTbBankbookUseList());	
		
		if(affiliationId.equals("I")){
			return "tis/com/financial/bankBook/cardIeetu";
		}else{
			return "tis/com/financial/bankBook/cardSmaker";
		}
	}
	
	/*신규카드*/
	@RequestMapping(value = "/com/financial/bankBook/cardAdd.do")
	public String cardAdd(@ModelAttribute("tbBankcardmanageVO")TbBankcardmanageVO tbBankcardmanageVO, ModelMap model, HttpServletRequest request) throws Exception {
		
    	tbBankcardmanageService.insertTbBankcardmanage(tbBankcardmanageVO);
    	model.addAttribute("emplyr_list", tbBankcardmanageService.selectEmplyrList()); //사원이름
		return "forward:/com/financial/bankBook/card.do?affiliationId="+tbBankcardmanageVO.getCpCode();
	}
	
	
	/*카드상세*/
	@RequestMapping(value = "/com/financial/bankBook/cardView.do")
	public String cardView(@ModelAttribute("tbBankcardmanageVO")TbBankcardmanageVO tbBankcardmanageVO, ModelMap model, HttpServletRequest request,
							@RequestParam("affiliationId") String affiliationId) throws Exception {

    	ComDefaultCodeVO vo = new ComDefaultCodeVO();
		//횟바구분
		vo.setCodeId("COM103");
		List<?> company_result = cmmUseService.selectCmmCodeDetail(vo);
		
		model.addAttribute("company_result", company_result); //회사구분
    	model.addAttribute("tbBankcardmanageVO", tbBankcardmanageService.selectTbBankcardmanage(tbBankcardmanageVO));
    	model.addAttribute("emplyr_list", tbBankcardmanageService.selectEmplyrList()); //사원이름
    	
		if(affiliationId.equals("I")){
			return "tis/com/financial/bankBook/cardViewIeetu";
		}else{
			return "tis/com/financial/bankBook/cardViewSmaker";
		}
	}
	
	/*카드수정*/
	@RequestMapping(value = "/com/financial/bankBook/cardModify.do")
	public String cardModify(@ModelAttribute("tbBankcardmanageVO")TbBankcardmanageVO tbBankcardmanageVO, ModelMap model, HttpServletRequest request,
							@RequestParam("affiliationId") String affiliationId ) throws Exception {		
		
    	ComDefaultCodeVO vo = new ComDefaultCodeVO();
		//횟바구분
		vo.setCodeId("COM103");
		List<?> company_result = cmmUseService.selectCmmCodeDetail(vo);
		
		model.addAttribute("company_result", company_result); //회사구분
    	model.addAttribute("tbBankcardmanageVO", tbBankcardmanageService.selectTbBankcardmanage(tbBankcardmanageVO));		
    	model.addAttribute("emplyr_list", tbBankcardmanageService.selectEmplyrList()); //사원이름
    	model.addAttribute("bank_list", tbBankbookmanageService.selectTbBankbookUseList());	
    	
		if(affiliationId.equals("I")){
			return "tis/com/financial/bankBook/cardModifyIeetu";
		}else{
			return "tis/com/financial/bankBook/cardModifySmaker";
		}
	}
	
	/*카드수정저장*/
	@RequestMapping(value = "/com/financial/bankBook/cardUpdate.do")
	public String cardUpdate(@ModelAttribute("tbBankcardmanageVO")TbBankcardmanageVO tbBankcardmanageVO, ModelMap model, HttpServletRequest request) throws Exception {		
		
    	tbBankcardmanageService.updateTbBankcardmanage(tbBankcardmanageVO);
		return "forward:/com/financial/bankBook/card.do";
	}
	
	/*카드수정저장*/
	@RequestMapping(value = "/com/financial/bankBook/cardDelete.do")
	public String cardDelete(@ModelAttribute("tbBankcardmanageVO")TbBankcardmanageVO tbBankcardmanageVO, ModelMap model, HttpServletRequest request) throws Exception {

    	tbBankcardmanageService.deleteTbBankcardmanage(tbBankcardmanageVO);
		return "forward:/com/financial/bankBook/card.do";
	}	
	
}