package tis.com.manager.managerHoliday.web;

import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import tis.com.manager.managerHoliday.service.TbAllvcatnmanageService;
import tis.com.manager.managerHoliday.service.TbAllvcatnmanageVO;
import egovframework.com.cmm.ComDefaultCodeVO;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.EgovCmmUseService;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.uss.ion.vct.service.EgovVcatnManageService;
import egovframework.com.uss.ion.vct.service.IndvdlYrycManage;
import egovframework.com.uss.ion.vct.service.VcatnManage;
import egovframework.com.uss.ion.vct.service.VcatnManageVO;
import egovframework.com.utl.fcc.service.EgovDateUtil;

@Controller
public class ManagerHolidayController {
	
	@Resource(name = "tbAllvcatnmanageService")
	private TbAllvcatnmanageService tbAllvcatnmanageService;
	
	@Resource(name = "egovVcatnManageService")
	private EgovVcatnManageService egovVcatnManageService;
	
	/** cmmUseService */
	@Resource(name = "EgovCmmUseService")
	private EgovCmmUseService cmmUseService;
	
	/*연차리스트*/
	@RequestMapping(value = "/com/manager/managerHoliday/managerHolidayList.do")
	public String manageHolidayList(@ModelAttribute("indvdlYrycManage") IndvdlYrycManage indvdlYrycManage, ModelMap model, HttpServletRequest request) throws Exception {
    	
    	String nowYear = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
    	if(indvdlYrycManage.getOccrrncYear() == null || indvdlYrycManage.getOccrrncYear().isEmpty()){
    		model.addAttribute("nowYear", nowYear);
    		indvdlYrycManage.setOccrrncYear(nowYear);
    	}else{
    		model.addAttribute("nowYear", indvdlYrycManage.getOccrrncYear());
    	}
    	
    	List<IndvdlYrycManage> years = (List<IndvdlYrycManage>) egovVcatnManageService.selectVacationAllUserYearList();
    	boolean checkYear = false;
    	for (int i = 0; i < years.size(); i++) {
    		if(years.get(i).getOccrrncYear().equals(nowYear))
    			checkYear = true;
    	}
    	if(!checkYear){
    		IndvdlYrycManage tmpvo = new IndvdlYrycManage();
    		tmpvo.setOccrrncYear(nowYear);
    		years.add(tmpvo);
    	}
    	ComDefaultCodeVO vo = new ComDefaultCodeVO();

    	//새로 입사 한사람 있는지 확인후 테이블 생성
    	egovVcatnManageService.insertUserDefaultVacationCnt();
    	
		//부서 조회
		vo.setCodeId("COM101");
		List<?> depart_result = cmmUseService.selectCmmCodeDetail(vo);
    	
		if(indvdlYrycManage.getOrgnztId() != null && !indvdlYrycManage.getOrgnztId().isEmpty())
			model.addAttribute("depart", indvdlYrycManage.getOrgnztId());
		else
			model.addAttribute("depart", "");
		
		model.addAttribute("depart_result", depart_result);
    	model.addAttribute("yearList", years);	
    	model.addAttribute("resultList", egovVcatnManageService.selectIndvdlYrycManageList(indvdlYrycManage));    	
    	
		return "tis/com/manager/managerHoliday/managerHolidayList";
	}
	
	/*연차상세보기*/
	@RequestMapping(value = "/com/manager/managerHoliday/managerHolidayDetail.do")
	public String managerHolidayDetail(@ModelAttribute("indvdlYrycManage") IndvdlYrycManage indvdlYrycManage, ModelMap model, HttpServletRequest request) throws Exception {

    	VcatnManageVO vcatnManageVO = new VcatnManageVO();
    	vcatnManageVO.setUsid(indvdlYrycManage.getUsid());  
    	vcatnManageVO.setApplcntId(indvdlYrycManage.getUsid());
    	    	
    	if(indvdlYrycManage.getOccrrncYear() == null || indvdlYrycManage.getOccrrncYear().isEmpty()){
    		model.addAttribute("nowYear", Calendar.getInstance().get(Calendar.YEAR));
    		indvdlYrycManage.setOccrrncYear(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));    		
    	}else{
    		model.addAttribute("nowYear", indvdlYrycManage.getOccrrncYear());
    	}
    	
    	vcatnManageVO.setOccrrncYear(indvdlYrycManage.getOccrrncYear());
    	
    	ComDefaultCodeVO vo = new ComDefaultCodeVO();
		vo.setCodeId("COM056");
        model.addAttribute("vcatnSeCode", cmmUseService.selectCmmCodeDetail(vo));
        
    	model.addAttribute("yearList", egovVcatnManageService.selectVacationYearList(indvdlYrycManage));    	
    	model.addAttribute("vacCnt", egovVcatnManageService.selectIndvdlYrycManage(vcatnManageVO));
    	model.addAttribute("vacList", egovVcatnManageService.selectVcatnUserList(vcatnManageVO));

		return "tis/com/manager/managerHoliday/managerHolidayDetail";
	}
	
	/*연차수정페이지*/
	@RequestMapping(value = "/com/manager/managerHoliday/managerHolidayModify.do")
	public String managerHolidayModify(HttpServletRequest request) throws Exception {
		return "tis/com/manager/managerHoliday/managerHolidayModify";
	}
	
	/*전체사원 연차관리*/
	@RequestMapping(value = "/com/manager/managerHoliday/managerAllHoliday.do")
	public String managerAllHoliday(@ModelAttribute("tbAllvcatnmanageVO") TbAllvcatnmanageVO tbAllvcatnmanageVO, ModelMap model, HttpServletRequest request) throws Exception {
    	
    	String nowYear = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
    	if(tbAllvcatnmanageVO.getOccrrncYear() == null || tbAllvcatnmanageVO.getOccrrncYear().isEmpty()){
    		model.addAttribute("nowYear", nowYear);
    		tbAllvcatnmanageVO.setOccrrncYear(nowYear);
    	}else{
    		model.addAttribute("nowYear", tbAllvcatnmanageVO.getOccrrncYear());
    	}
    	
    	List<TbAllvcatnmanageVO> years = (List<TbAllvcatnmanageVO>) tbAllvcatnmanageService.selectTbAllvcatnmanageYearList();
    	boolean checkYear = false;
    	for (int i = 0; i < years.size(); i++) {
    		if(years.get(i).getOccrrncYear().equals(nowYear))
    			checkYear = true;
    	}
    	if(!checkYear){
    		TbAllvcatnmanageVO tmpvo = new TbAllvcatnmanageVO();
    		tmpvo.setOccrrncYear(nowYear);
    		years.add(tmpvo);
    	}
    	
    	model.addAttribute("yearList", years);
    	model.addAttribute("resultList", tbAllvcatnmanageService.selectTbAllvcatnmanageList(tbAllvcatnmanageVO));
    	
		return "tis/com/manager/managerHoliday/managerAllHoliday";
	}
	
	/*전체사원 연차관리*/
	@RequestMapping(value = "/com/manager/managerHoliday/managerAllHolidayAdd.do")
	public String managerAllHolidayAdd(@ModelAttribute("tbAllvcatnmanageVO") TbAllvcatnmanageVO tbAllvcatnmanageVO, ModelMap model, HttpServletRequest request) throws Exception {	
    	
    	LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
    	tbAllvcatnmanageVO.setOccrrncYear(tbAllvcatnmanageVO.getBgnde().substring(0, 4));
    	tbAllvcatnmanageVO.setApplcntId(user.getUniqId());
    	tbAllvcatnmanageVO.setVcatnSe("01");
    	tbAllvcatnmanageVO.setNoonSe("0");
    	
    	tbAllvcatnmanageService.insertTbAllvcatnmanage(tbAllvcatnmanageVO);
    	//새로 입사 한사람 있는지 확인후 테이블 생성
    	//egovVcatnManageService.insertUserDefaultVacationCnt();
    	
    	//개인별 연차 갯수 조절
    	VcatnManage vcatnManage = new VcatnManage();
    	vcatnManage.setBgnde(tbAllvcatnmanageVO.getBgnde());
    	vcatnManage.setEndde(tbAllvcatnmanageVO.getEndde());
    	vcatnManage.setLastUpdusrId(tbAllvcatnmanageVO.getApplcntId());
    	vcatnManage.setOccrrncYear(tbAllvcatnmanageVO.getOccrrncYear());
    	egovVcatnManageService.updateAllVacationUse(vcatnManage);
    	
    	//개인별 연차 상세내용 추가
    	vcatnManage.setNoonSe("0");
    	vcatnManage.setVcatnSe("01");
    	vcatnManage.setFrstRegisterId(tbAllvcatnmanageVO.getApplcntId());
    	vcatnManage.setVcatnResn(tbAllvcatnmanageVO.getVcatnResn());
    	vcatnManage.setReqstDe(EgovDateUtil.getToday());
    	egovVcatnManageService.insertVcatnManageAll(vcatnManage);
    	
		return "forward:/com/manager/managerHoliday/managerAllHoliday.do";
	}
	
	
	/*전체사원 연차관리*/
	@RequestMapping(value = "/com/manager/managerHoliday/managerAllHolidayDelete.do")
	public String managerAllHolidayDelete(@ModelAttribute("tbAllvcatnmanageVO") TbAllvcatnmanageVO tbAllvcatnmanageVO, ModelMap model, HttpServletRequest request) throws Exception {
    	
    	for(int i=0; i < tbAllvcatnmanageVO.getAllvcatnmanageList().size(); i++){			
    		TbAllvcatnmanageVO tmpVO = tbAllvcatnmanageVO.getAllvcatnmanageList().get(i);
			if(tmpVO.getChk() != null && tmpVO.getChk().equals("on")){
				tbAllvcatnmanageService.deleteTbAllvcatnmanage(tmpVO);				
			}			
		}
		return "forward:/com/manager/managerHoliday/managerAllHoliday.do";
	}
	
	
}