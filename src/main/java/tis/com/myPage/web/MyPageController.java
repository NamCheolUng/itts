package tis.com.myPage.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import tis.com.common.service.DefaultVO;
import tis.com.common.service.TisCodeService;
import tis.com.myPage.service.MyPageService;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.cop.bbs.service.BoardMasterVO;
import egovframework.com.cop.bbs.service.BoardVO;
import egovframework.com.cop.bbs.service.EgovBBSAttributeManageService;
import egovframework.com.cop.bbs.service.EgovBBSManageService;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

@Controller
public class MyPageController {
	
	@Resource(name = "egovMessageSource")
	EgovMessageSource egovMessageSource;
	
	@Resource(name = "myPageService")
	private MyPageService myPageService;
	
	@Resource(name="TisCodeService")
	private TisCodeService tisCodeService;
	
	@Resource(name = "EgovBBSAttributeManageService")
    private EgovBBSAttributeManageService bbsAttrbService;
	
	@Resource(name = "EgovBBSManageService")
    private EgovBBSManageService bbsMngService;
	
	@RequestMapping(value = "/com/inc/myPage.do")
	public String myPage(@ModelAttribute("searchVO") DefaultVO defaultVO, Model model) throws Exception {
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		
		if (!isAuthenticated) {
			model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
			return "tis/com/inc/login";
		}
		
		//공지사항
		BoardVO boardVO = new BoardVO();
		boardVO.setBbsId("BBSMSTR_000000000001");
		BoardMasterVO mastervo = new BoardMasterVO();
		
		mastervo.setBbsId(boardVO.getBbsId());
		BoardMasterVO master = bbsAttrbService.selectBBSMasterInf(mastervo);

		boardVO.setPageUnit(4);
		boardVO.setPageSize(1);
		boardVO.setFirstIndex(0);
		boardVO.setLastIndex(4);
		boardVO.setRecordCountPerPage(4);

		Map<String, Object> map = bbsMngService.selectBoardArticles(boardVO, master.getBbsAttrbCode());	
		model.addAttribute("boardList", map.get("resultList"));
		
		DefaultVO vo = new DefaultVO();
		vo.setSearchCondition(user.getEmplyrNo());
		model.addAttribute("allCnt", myPageService.selectPrgrsAllCnt(vo));
		defaultVO.setEmplyrId(user.getEmplyrNo());
		String tmp = "";
		if(defaultVO.getSearchCondition() != null && !defaultVO.getSearchCondition().isEmpty()){				
			if("Y".equals(defaultVO.getSearchCondition())){
				defaultVO.setSearchCondition(user.getEmplyrNo());
				defaultVO.setSearchCondition2("Y");
				model.addAttribute("schdulList", myPageService.selectMySchduleList(defaultVO));
				model.addAttribute("leftSchdulList", myPageService.selectSchdulList(defaultVO));
				return "tis/com/myPage/mypage";
			}else{
				String []arr  = defaultVO.getSearchCondition().split(",");			
				if(arr.length > 1){
					for(int i=0;i<arr.length;i++){
						if(arr.length -1 == i){
							tmp = tmp +"'" +arr[i] +"'" ;
						}else{
							tmp = tmp +"'" +arr[i] +"'," ;
						}
						
					}
					defaultVO.setSearchCondition(tmp);
					
				}else{
					defaultVO.setSearchCondition("'"+defaultVO.getSearchCondition()+"'");
				}
			}							
		}else{
			if(defaultVO.getSearchKeyword() != null && !"Y".equals(defaultVO.getSearchKeyword())){
				List<EgovMap> mapList = (List<EgovMap>) tisCodeService.selectCodeDetailList("COM101");
				for(int i=0; i<mapList.size();i++){
					EgovMap tmpMap = mapList.get(i);
					if(mapList.size()-1 == i){
						tmp = tmp +"'" +tmpMap.get("code").toString()+"'" ;	
					}else{
						tmp = tmp +"'" +tmpMap.get("code").toString()+"'," ;	
					}
					 
				}				
			}else{
				tmp = tmp +"'nodata'" ;	
			}			
			defaultVO.setSearchCondition(tmp);
		}		
		
		model.addAttribute("leftSchdulList", myPageService.selectSchdulList(defaultVO));
		model.addAttribute("schdulList", myPageService.selectMyPageScheduler(defaultVO));
		return "tis/com/myPage/mypage";
	}
}