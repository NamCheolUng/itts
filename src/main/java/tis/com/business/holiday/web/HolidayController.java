package tis.com.business.holiday.web;

import java.util.Calendar;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import egovframework.com.cmm.ComDefaultCodeVO;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.EgovCmmUseService;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.uss.ion.vct.service.EgovVcatnManageService;
import egovframework.com.uss.ion.vct.service.IndvdlYrycManage;
import egovframework.com.uss.ion.vct.service.VcatnManageVO;

@Controller
public class HolidayController {
	
	@Resource(name = "egovVcatnManageService")
    private EgovVcatnManageService egovVcatnManageService;
	
	/** cmmUseService */
	@Resource(name = "EgovCmmUseService")
	private EgovCmmUseService cmmUseService;
	
	/*연차리스트*/
	@RequestMapping(value = "/com/business/holiday/holidayList.do")
	public String holidayList(@ModelAttribute("indvdlYrycManage")IndvdlYrycManage indvdlYrycManage, ModelMap model) throws Exception {
    	
    	LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
    	VcatnManageVO vcatnManageVO = new VcatnManageVO();
    	vcatnManageVO.setUsid(user.getUniqId());    	
    	indvdlYrycManage.setUsid(user.getUniqId());
    	vcatnManageVO.setApplcntId(indvdlYrycManage.getUsid());
    	
    	if(indvdlYrycManage.getOccrrncYear() == null || indvdlYrycManage.getOccrrncYear().isEmpty()){
    		model.addAttribute("nowYear", Calendar.getInstance().get(Calendar.YEAR));
    		indvdlYrycManage.setOccrrncYear(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));    		
    	}else{
    		model.addAttribute("nowYear", indvdlYrycManage.getOccrrncYear());
    	}
    	vcatnManageVO.setOccrrncYear(indvdlYrycManage.getOccrrncYear());
    	model.addAttribute("yearList", egovVcatnManageService.selectVacationYearList(indvdlYrycManage));    	
    	model.addAttribute("vacCnt", egovVcatnManageService.selectIndvdlYrycManage(vcatnManageVO));
    	model.addAttribute("vacList", egovVcatnManageService.selectVcatnUserList(vcatnManageVO));
    	
    /*	
    	VcatnManageVO vcatnManageVO1 = egovVcatnManageService.selectIndvdlYrycManage(user.getUniqId());
    	vcatnManageVO1.setApplcntId(user.getUniqId());
    	vcatnManageVO1.setApplcntNm(user.getName());
    	vcatnManageVO1.setOrgnztNm(user.getOrgnztNm());
    	vcatnManageVO1.setUsid(user.getUniqId());
    	model.addAttribute("vcatnManageVO",  vcatnManageVO1); 
    	
    	*/
      	
    	//model.addAttribute("resultList", cmtManageService.selectUserCmtList(cmtManageVO));


    	ComDefaultCodeVO vo = new ComDefaultCodeVO();
		vo.setCodeId("COM056");
        model.addAttribute("vcatnSeCode",    cmmUseService.selectCmmCodeDetail(vo));
    	
		return "tis/com/business/holiday/holidayList";
	}
}