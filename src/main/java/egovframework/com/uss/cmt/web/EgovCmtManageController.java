package egovframework.com.uss.cmt.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.annotation.IncludedInfo;
import egovframework.com.cmm.util.EgovHttpRequestHelper;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.uss.cmt.service.CmtDefaultVO;
import egovframework.com.uss.cmt.service.CmtManageVO;
import egovframework.com.uss.cmt.service.EgovCmtManageService;
import egovframework.com.utl.fcc.service.EgovDateUtil;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import egovframework.rte.fdl.property.EgovPropertyService;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springmodules.validation.commons.DefaultBeanValidator;

import tis.com.task.service.TaskVO;

/**
 * 업무사용자관련 요청을  비지니스 클래스로 전달하고 처리된결과를  해당
 * 웹 화면으로 전달하는  Controller를 정의한다
 * @author 표준프레임워크 개발팀
 * @since 2014.08.29
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2014.08.29  개발팀          최초 생성
 *
 * </pre>
 */

@Controller
public class EgovCmtManageController {

	/** cmtManageService */
	@Resource(name = "cmtManageService")
	private EgovCmtManageService cmtManageService;

	/** EgovPropertyService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	/** DefaultBeanValidator beanValidator */
	@Autowired
	private DefaultBeanValidator beanValidator;

	/** egovCmtManageIdGnrService */
	@Resource(name = "egovCmtManageIdGnrService")
	private EgovIdGnrService idgenService;

	private LoginVO user;

	/**
	 * 출근 정보를 등록한다.
	 * @param cmtManageVO 사용자등록정보
	 * @param bindingResult 입력값검증용 bindingResult
	 * @param model 화면모델
	 * @return forward:/uss/cmt/EgovCmtMange.do
	 * @throws Exception
	 */
	@RequestMapping(value = "/uss/cmt/EgovCmtWrkStartInsert.do")
	public ModelAndView insertWrkStartCmtInfo(Model model) throws Exception {
		
		Map<String,Object> map = new HashMap<String,Object>();
		String ip = EgovHttpRequestHelper.getRequestIp();
		if(!ip.startsWith("172.16.1") && !ip.startsWith("0:0:0")){
			map.put("result","Error");
			return new ModelAndView("ajaxMainView",map);
		}
		user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		
		CmtManageVO cmtManageVO = new CmtManageVO();

		// login data
		if (user.getUniqId() != null)
			cmtManageVO.setEmplyrId(user.getUniqId());
		if (user.getOrgnztId() != null)
			cmtManageVO.setOrgnztId(user.getOrgnztId());
		cmtManageVO.setWrktDt(EgovDateUtil.getToday());
		String result;
		try{
			result = cmtManageService.insertWrkStartCmtInfo(cmtManageVO);
		}catch(Exception ex){
			map.put("result","fail");
			return new ModelAndView("ajaxMainView",map);
		}		
				
		map.put("result","OK");
		return new ModelAndView("ajaxMainView",map);
	}
	
	/**
	 * 퇴근 정보를 등록한다.
	 * @param cmtManageVO 사용자등록정보
	 * @param bindingResult 입력값검증용 bindingResult
	 * @param model 화면모델
	 * @return forward:/uss/cmt/EgovCmtMange.do
	 * @throws Exception
	 */
	@RequestMapping(value = "/uss/cmt/EgovCmtWrkEndInsert.do")
	public ModelAndView insertWrkEndCmtInfo(@ModelAttribute("cmtManageVO") CmtManageVO cmtManageVO, BindingResult bindingResult, Model model) throws Exception {
		//사용자정보
		user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		
		if (user.getUniqId() != null)
			cmtManageVO.setEmplyrId(user.getUniqId());
		if (user.getOrgnztId() != null)
			cmtManageVO.setOrgnztId(user.getOrgnztId());

		cmtManageVO.setWrktDt(EgovDateUtil.getToday());

		cmtManageService.insertWrkEndCmtInfo(cmtManageVO);

		Map<String,Object> map = new HashMap<String,Object>();
		map.put("result","OK");
		return new ModelAndView("ajaxMainView",map);
	}

	/**
	 * 출퇴근목록을 조회한다. (paging)
	 * @param userSearchVO 검색조건정보
	 * @param model 화면모델
	 * @return cmm/uss/umt/EgovCmtManageList
	 * @throws Exception
	 */
	@IncludedInfo(name = "출퇴근관리", order = 950, gid = 50)
	@RequestMapping(value = "/uss/cmt/EgovCmtManageList.do")
	public String selectUserCmtList(@ModelAttribute("cmtSearchVO") CmtDefaultVO cmtSearchVO, ModelMap model) throws Exception {

		List<?> cmtManageList = cmtManageService.selectCmtInfoList(cmtSearchVO);
		model.addAttribute("resultList", cmtManageList);

		return "egovframework/com/uss/cmt/EgovCmtManageList";
	}

	/**
	 * 출장 정보를 등록한다.
	 * @param cmtManageVO 사용자등록정보
	 * @param bindingResult 입력값검증용 bindingResult
	 * @param model 화면모델
	 * @return forward:/uss/cmt/EgovCmtMange.do
	 * @throws Exception
	 */
	@RequestMapping(value = "/uss/cmt/EgovCmtOutWrkInsert.do")
	public ModelAndView EgovCmtOutWrkInsert(@ModelAttribute("cmtManageVO") CmtManageVO cmtManageVO, BindingResult bindingResult, Model model) throws Exception {
		
		Map<String,Object> map = new HashMap<String,Object>();
		String ip = EgovHttpRequestHelper.getRequestIp();
		
		if(!ip.startsWith("172.16.1") && !ip.startsWith("0:0:0")){
			map.put("result","Error");
			return new ModelAndView("ajaxMainView",map);
		}
		
/*		Date current = new Date();	
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		Date d1 = f.parse(cmtManageVO.getOutWrkStime() + ":00"); 
		Date d2 = f.parse(f.format(current)); 
		
		//long diff = d2.getTime() - d1.getTime(); 
		if(d2.after(d1)){  //현재의 날보다 그전날 일경우 에러
			map.put("result","Error");
			System.out.println(d1);
			System.out.println(d2);
			return new ModelAndView("ajaxMainView",map);
		}*/
		
		cmtManageVO.setOutWrkStime(cmtManageVO.getOutWrkStime()+":00");
		cmtManageVO.setOutWrkEtime(cmtManageVO.getOutWrkEtime()+":00");
		
		user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

		// login data
		if (user.getUniqId() != null)
			cmtManageVO.setEmplyrId(user.getUniqId());
		if (user.getOrgnztId() != null)
			cmtManageVO.setOrgnztId(user.getOrgnztId());
		cmtManageVO.setWrktDt(EgovDateUtil.getToday());
		
		try{
			cmtManageService.insertOutWrkStartCmtInfo(cmtManageVO);
		}catch(Exception ex){
			map.put("result","fail");
			return new ModelAndView("ajaxMainView",map);
		}		
				
		map.put("result","OK");
		return new ModelAndView("ajaxMainView",map);
	}
	
	
	
	
}
