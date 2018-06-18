package tis.com.business.item.web;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import tis.com.business.item.service.TbItemmanageService;
import tis.com.business.item.service.TbItemmanageVO;
import tis.com.financial.bankBook.service.TbBankcardmanageService;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

@Controller
public class ItemController {

	@Resource(name = "tbItemmanageService")
	private TbItemmanageService tbItemmanageService;

	@Resource(name = "tbBankcardmanageService")
	private TbBankcardmanageService tbBankcardmanageService;
	
	/*아이템리스트*/
	@RequestMapping(value = "/com/business/item/itemList.do")
	public String itemList(@ModelAttribute("searchVO") TbItemmanageVO searchVO, ModelMap model) throws Exception {		
    	
    	model.addAttribute("bgndt", searchVO.getBgndt());
    	model.addAttribute("enddt", searchVO.getEnddt());
    	
    	if(!searchVO.getBgndt().isEmpty()){
    		searchVO.setBgndt(searchVO.getBgndt().replace("/", "-"));
    		if(searchVO.getBgndt().isEmpty())
    			searchVO.setEnddt(searchVO.getBgndt());
    		searchVO.setEnddt(searchVO.getEnddt().replace("/", "-"));
    	}
    	
    	/** EgovPropertyService */
    	searchVO.setPageUnit(searchVO.getPageUnit());
    	searchVO.setPageSize(searchVO.getPageSize());

		/** pageing */
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(searchVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(15);
		paginationInfo.setPageSize(searchVO.getPageSize());

		searchVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		searchVO.setLastIndex(paginationInfo.getLastRecordIndex());
		searchVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		List<?> itemList = tbItemmanageService.selectTbItemmanageList(searchVO);
		model.addAttribute("resultList", itemList);
		
		List<?> itemNm = tbItemmanageService.selectTbItemNameList();
		model.addAttribute("itemNmList", itemNm);
		
		int totCnt = tbItemmanageService.selectTbItemmanageListTotCnt(searchVO);
		paginationInfo.setTotalRecordCount(totCnt);
		model.addAttribute("paginationInfo", paginationInfo);	
		model.addAttribute("emplyr_list", tbBankcardmanageService.selectEmplyrList()); //사원이름
		model.addAttribute("tbItemmanageVO", searchVO);
		return "tis/com/business/item/itemList";
	}
	
	/*아이템등록*/
	@RequestMapping(value = "/com/business/item/itemAdd.do")
	public String itemAdd(@ModelAttribute("tbItemmanageVO") TbItemmanageVO tbItemmanageVO, ModelMap model) throws Exception {
		
    	tbItemmanageVO.setItemCode(tbItemmanageService.selectGetItemID());
    	
    	tbItemmanageService.insertTbItemmanage(tbItemmanageVO);
		
		return "redirect:/com/business/item/itemList.do";
	}		

	/*아이템상세보기*/
	@RequestMapping(value = "/com/business/item/itemView.do")
	public String itemView(@ModelAttribute("tbItemmanageVO") TbItemmanageVO tbItemmanageVO, ModelMap model) throws Exception {

    	model.addAttribute("tbItemmanageVO", tbItemmanageService.selectTbItemmanage(tbItemmanageVO));
    	model.addAttribute("emplyr_list", tbBankcardmanageService.selectEmplyrList()); //사원이름
		return "tis/com/business/item/itemView";
	}
	
	/*아이템삭제*/
	@RequestMapping(value = "/com/business/item/itemDelete.do")
	public String itemDelete(@ModelAttribute("tbItemmanageVO") TbItemmanageVO tbItemmanageVO, ModelMap model) throws Exception {

    	tbItemmanageService.deleteTbItemmanage(tbItemmanageVO);
		
		return "redirect:/com/business/item/itemList.do";
	}
	
	/*아이템수정*/
	@RequestMapping(value = "/com/business/item/itemModify.do")
	public String itemModify(@ModelAttribute("tbItemmanageVO") TbItemmanageVO tbItemmanageVO, ModelMap model) throws Exception {

    	List<?> itemNm = tbItemmanageService.selectTbItemNameList();
		model.addAttribute("itemNmList", itemNm);
    	model.addAttribute("tbItemmanageVO", tbItemmanageService.selectTbItemmanage(tbItemmanageVO));
    	model.addAttribute("emplyr_list", tbBankcardmanageService.selectEmplyrList()); //사원이름
		return "tis/com/business/item/itemModify";
	}	
	
	/*아이템수정 저장*/
	@RequestMapping(value = "/com/business/item/itemUpdate.do")
	public String itemUpdate(@ModelAttribute("tbItemmanageVO") TbItemmanageVO tbItemmanageVO, ModelMap model) throws Exception {

    	tbItemmanageService.updateTbItemmanage(tbItemmanageVO);
		
		return "redirect:/com/business/item/itemList.do";
	}	
	
	
}
