package tis.com.epay.web;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import tis.com.common.service.TisCodeService;
import tis.com.epay.service.EpayApprLineVO;
import tis.com.epay.service.EpayApprLnCfgVO;
import tis.com.epay.service.EpayDraftInfoService;
import tis.com.epay.service.EpayPopupService;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;

@Controller
public class EpayPopupController {

	@Resource(name = "epayDraftInfoService")
	private EpayDraftInfoService epayDraftInfoService;
	
	@Resource(name = "epayPopupService")
	private EpayPopupService epayPopupService;
	
	@Resource(name = "TisCodeService")
	private TisCodeService tisCodeService;
	
	@RequestMapping("/com/epay/popup/epayApprLnPopup.do")
	public String epayApprLnPopup(Model model)throws Exception{
	
		// 미인증 사용자에 대한 보안처리
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		Boolean isAuthenticated = false;
		
		model.addAttribute("userVO", user);
		model.addAttribute("deptList", this.tisCodeService.selectCodeDetailList("COM101"));
		model.addAttribute("userList", this.epayDraftInfoService.selectEmplyrListForOrgnztChart());
		model.addAttribute("apprLineList", this.epayDraftInfoService.selectEpayApprLineList()); 
		
		return "/tis/com/epay/draft/popup/epayApprLnPopup";
	}
	
	@RequestMapping("/com/epay/popup/epayOrgnztInfo.do")
	public ModelAndView epayOrgnztInfo(ModelMap model) throws Exception {
		
		Map<String,Object> map = new HashMap<String,Object>();		
		
		map.put("deptList", this.tisCodeService.selectCodeDetailList("COM101"));
		map.put("userList", this.epayDraftInfoService.selectEmplyrListForOrgnztChart());
		
		return new ModelAndView("ajaxMainView", map);
	}
	
	@RequestMapping("/com/epay/popup/epayOrgnztInfoForEmplyr.do")
	public ModelAndView epayOrgnztInfoForEmplyr(String emplNo, ModelMap model) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();		
		map.put("userInfo", this.epayDraftInfoService.selectEmplyrForOrgnztChart(emplNo));
		return new ModelAndView("ajaxMainView", map);
	}
	
	@RequestMapping("/com/epay/popup/epayApprLnCfgList.do")
	public ModelAndView epayApprLnCfgList(String apprLineId, ModelMap model) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();		
		
		EpayApprLnCfgVO epayApprLnCfgVO = new EpayApprLnCfgVO();
		epayApprLnCfgVO.setApprLineId(apprLineId);
		
		map.put("apprLnCfgList", this.epayDraftInfoService.selectEpayApprLn(apprLineId));// selectEpayApprLnCfgList(epayApprLnCfgVO));
		
		return new ModelAndView("ajaxMainView", map);
	}
	
	@RequestMapping("/com/epay/popup/saveFavoriteItem.do")
	public ModelAndView saveFavoriteItem(EpayApprLineVO epayApprLineVO, EpayApprLnCfgVO epayApprLnCfgVO, ModelMap model) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();		
		
		if(epayApprLnCfgVO.getApprLnList() != null){
			this.epayPopupService.saveFavoriteItem(epayApprLineVO, epayApprLnCfgVO);
		}
		
		return new ModelAndView("ajaxMainView", map);
	}
	
	@RequestMapping("/com/epay/popup/removeFavoriteItem.do")
	public ModelAndView removeFavoriteItem(EpayApprLineVO epayApprLineVO, EpayApprLnCfgVO epayApprLnCfgVO, ModelMap model) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();		
			
		if(epayApprLineVO.getApprLineId() != null && epayApprLnCfgVO.getApprLineId() != null){
				
			this.epayPopupService.removeFavoriteItem(epayApprLineVO, epayApprLnCfgVO);	
		}
		
		return new ModelAndView("ajaxMainView", map);
	}
	
	@RequestMapping("/com/epay/popup/registApprLn.do")
	public ModelAndView registApprLn(EpayApprLnCfgVO epayApprLnCfgVO, ModelMap model) throws Exception {
		Map<String,Object> map = new HashMap<String,Object>();		
			
		if(epayApprLnCfgVO.getApprLnList() != null){
			map.put("apprLnList", epayApprLnCfgVO.getApprLnList());
		}
			
		return new ModelAndView("ajaxMainView", map);
	}
}
