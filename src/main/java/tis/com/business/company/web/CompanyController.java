package tis.com.business.company.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import egovframework.com.cmm.ComDefaultCodeVO;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.EgovCmmUseService;
import egovframework.com.cmm.service.EgovFileMngService;
import egovframework.com.cmm.service.EgovFileMngUtil;
import egovframework.com.cmm.service.FileVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.cop.adb.service.AdressBookUser;
import egovframework.com.cop.adb.service.AdressBookUserVO;
import egovframework.com.cop.adb.service.AdressBookVO;
import egovframework.com.cop.adb.service.EgovAdressBookService;
import egovframework.com.cop.ncm.service.EgovNcrdManageService;
import egovframework.com.cop.ncm.service.NameCardVO;
import egovframework.com.cop.smt.dsm.service.DiaryManageVO;
import egovframework.com.cop.smt.dsm.service.EgovDiaryManageService;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

@Controller
public class CompanyController {
	
	
	@Resource(name = "egovMessageSource")
	EgovMessageSource egovMessageSource;
	
	@Resource(name = "EgovNcrdManageService")
    private EgovNcrdManageService ncrdService;

    @Resource(name = "propertiesService")
    protected EgovPropertyService propertyService;
    
    @Resource(name = "EgovAdressBookService")
    private EgovAdressBookService adbkService;
    
    @Resource(name = "EgovFileMngUtil")
	protected EgovFileMngUtil fileUtil;

	// 첨부파일 관련
	@Resource(name="EgovFileMngService")
	private EgovFileMngService fileMngService;
	
	@Resource(name = "EgovCmmUseService")
    private EgovCmmUseService cmmUseService;
	
	@Resource(name = "egovDiaryManageService")
	private EgovDiaryManageService egovDiaryManageService;
	
	/*업체리스트*/
	@RequestMapping(value = "/com/business/company/companyList.do")
	public String companyList(@ModelAttribute("searchVO") AdressBookUserVO adbkUserVO, ModelMap model) throws Exception {	

    	adbkUserVO.setPageUnit(propertyService.getInt("pageUnit"));
        adbkUserVO.setPageSize(propertyService.getInt("pageSize"));

        PaginationInfo paginationInfo = new PaginationInfo();

        paginationInfo.setCurrentPageNo(adbkUserVO.getPageIndex());
        paginationInfo.setRecordCountPerPage(15);
        paginationInfo.setPageSize(adbkUserVO.getPageSize());

        adbkUserVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
        adbkUserVO.setLastIndex(paginationInfo.getLastRecordIndex());
        adbkUserVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

        model.addAttribute("resultList", adbkService.selectCompanyListist(adbkUserVO));
        
        int totCnt = adbkService.selectCompanyLististCnt(adbkUserVO);
        paginationInfo.setTotalRecordCount(totCnt);

        model.addAttribute("resultCnt", totCnt);
        model.addAttribute("paginationInfo", paginationInfo); 	
        model.addAttribute("adbkUserVO", adbkUserVO);
		
		return "tis/com/business/company/companyList";
	}
	
	/*업체상세보기*/
	@RequestMapping(value = "/com/business/company/companyView.do")
	public String companyView(@ModelAttribute("adbkUserVO") AdressBookUserVO adbkUserVO, ModelMap model) throws Exception {
		
    	model.addAttribute("adbkUserVO", adbkService.selectUserDetail(adbkUserVO));
		
		return "tis/com/business/company/companyView";
	}
	
	/*업체등록*/
	@RequestMapping(value = "/com/business/company/companyInsert.do")
	public String companyInsert(ModelMap model) throws Exception {
		
    	ComDefaultCodeVO vo = new ComDefaultCodeVO();
    	
    	vo.setCodeId("COM401");    	
    	model.addAttribute("seCode", cmmUseService.selectCmmCodeDetail(vo));
    	
		return "tis/com/business/company/companyInsert";
	}
	
	/*업체등록저장*/
	@RequestMapping(value = "/com/business/company/companyAdd.do")
	public String companyAdd(@ModelAttribute("adbkUserVO") AdressBookUserVO adbkUserVO, ModelMap model
			,final MultipartHttpServletRequest multiRequest) throws Exception {
		
    	LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
    	
    	List<FileVO> result = null;
		String atchFileId = "";
		
		final Map<String, MultipartFile> files = multiRequest.getFileMap();

		// 첨부 파일이 있을 때만 동작
		if (!files.isEmpty()) {
			result = fileUtil.parseFileInf(files, "PTR_", 0, atchFileId, "");
			atchFileId = fileMngService.insertFileInfs(result);			
		}    	
		adbkUserVO.setAtchFileId(atchFileId);
		adbkUserVO.setEmplyrId(user.getUniqId());
    	adbkService.insertAdressBookUser(adbkUserVO);		
		return "redirect:/com/business/company/companyList.do";
	}	
	
	/*업체수정저장*/
	@RequestMapping(value = "/com/business/company/companyUpdate.do")
	public String companyUpdate(@ModelAttribute("adbkUserVO") AdressBookUserVO adbkUserVO, ModelMap model
			,final MultipartHttpServletRequest multiRequest) throws Exception {	

    	List<FileVO> result = null;
		String atchFileId = adbkUserVO.getAtchFileId();
		
		final Map<String, MultipartFile> files = multiRequest.getFileMap();

		// 첨부 파일이 있을 때만 동작
		if (!files.isEmpty()) {
			String tmpFileId="";
			// 기존 첨부 파일에 대한 처리
			if("".equals(atchFileId)) {
				result = fileUtil.parseFileInf(files, "PTR_", 0, atchFileId, "");
				tmpFileId = fileMngService.insertFileInfs(result);
				adbkUserVO.setAtchFileId(tmpFileId);
			} else {
				FileVO fvo = new FileVO();
				fvo.setAtchFileId(atchFileId);
				int cnt = fileMngService.getMaxFileSN(fvo);
				result = fileUtil.parseFileInf(files, "PTR_", cnt, atchFileId, "");
				fileMngService.updateFileInfs(result);
			}
		}    	
    	
    	adbkService.updateAdressBookUser(adbkUserVO);		
		return "forward:/com/business/company/companyView.do";
	}	
	
	
	/*업체수정*/
	@RequestMapping(value = "/com/business/company/companyModify.do")
	public String companyModify(@ModelAttribute("adbkUserVO") AdressBookUserVO adbkUserVO, ModelMap model) throws Exception {	
		
    	ComDefaultCodeVO vo = new ComDefaultCodeVO();
    	
    	vo.setCodeId("COM401");
    	
    	model.addAttribute("seCode", cmmUseService.selectCmmCodeDetail(vo));
    	model.addAttribute("adbkUserVO", adbkService.selectUserDetail(adbkUserVO));
		
		return "tis/com/business/company/companyModify";
	}
	
	/*업체사원리스트*/
	@RequestMapping(value = "/com/business/company/companyEmplyrList.do")
	public String companyEmplyrList(@ModelAttribute("searchVO") NameCardVO ncrdVO, ModelMap model) throws Exception {
    	
    	LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
    	
    	ncrdVO.setPageUnit(propertyService.getInt("pageUnit"));
    	ncrdVO.setPageSize(propertyService.getInt("pageSize"));

    	PaginationInfo paginationInfo = new PaginationInfo();
    	
    	paginationInfo.setCurrentPageNo(ncrdVO.getPageIndex());
    	paginationInfo.setRecordCountPerPage(15);
    	paginationInfo.setPageSize(ncrdVO.getPageSize());

    	ncrdVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
    	ncrdVO.setLastIndex(paginationInfo.getLastRecordIndex());
    	ncrdVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

    	ncrdVO.setEmplyrId(user.getUniqId());
    	
    	Map<String, Object> map = ncrdService.selectNcrdItems(ncrdVO);
    	int totCnt = Integer.parseInt((String)map.get("resultCnt"));
    	paginationInfo.setTotalRecordCount(totCnt);

    	model.addAttribute("ncrdVO", ncrdVO);
    	model.addAttribute("resultList", map.get("resultList"));
    	model.addAttribute("resultCnt", map.get("resultCnt"));
    	model.addAttribute("paginationInfo", paginationInfo);    	
    	
		return "tis/com/business/company/companyEmplyrList";
	}
	
	/*업체사원상세보기*/
	@RequestMapping(value = "/com/business/company/companyEmplyrView.do")
	public String companyEmplyrView(@ModelAttribute("ncrdVO") NameCardVO ncrdVO,@ModelAttribute("diaryManageVO") DiaryManageVO diaryManageVO, ModelMap model) throws Exception {
		
    	NameCardVO vo = ncrdService.selectNcrdItem(ncrdVO);
    	model.addAttribute("ncrdVO", vo);		
    	model.addAttribute("diary", egovDiaryManageService.selectDiaryCmng(diaryManageVO));
		return "tis/com/business/company/companyEmplyrView";
	}
	
	/*업체사원등록*/
	@RequestMapping(value = "/com/business/company/companyEmplyrInsert.do")
	public String companyEmplyrInsert(ModelMap model) throws Exception {
    	
    	model.addAttribute("adbkcode",adbkService.selectAdbkCodeList());
		
		return "tis/com/business/company/companyEmplyrInsert";
	}
	
	/*업체사원수정저장*/
	@RequestMapping(value = "/com/business/company/companyEmplyrAdd.do")
	public String companyEmplyrAdd(@ModelAttribute("ncrdVO") NameCardVO ncrdVO, ModelMap model
			,final MultipartHttpServletRequest multiRequest ) throws Exception {
    	
    	LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
    	
    	List<FileVO> result = null;
		String atchFileId = "";
		
		final Map<String, MultipartFile> files = multiRequest.getFileMap();

		// 첨부 파일이 있을 때만 동작
		if (!files.isEmpty()) {
			result = fileUtil.parseFileInf(files, "PTR_", 0, atchFileId, "");
			atchFileId = fileMngService.insertFileInfs(result);			
		}
		ncrdVO.setAtchFileId(atchFileId);
		
		if(ncrdVO.getAdbkId().equals(ncrdVO.getCmpnyNm()))
			ncrdVO.setAdbkId("");
		AdressBookUser abUser = new AdressBookUser();
		if(ncrdVO.getAdbkId() == null || ncrdVO.getAdbkId().isEmpty() ){
			//업체관리 추가			
			abUser.setCmpnyNm(ncrdVO.getCmpnyNm());
			abUser.setEntrprsSeCode(ncrdVO.getIdntfcNo());
			abUser.setEmplyrId(user.getUniqId());
			adbkService.insertAdressBookUser(abUser);
			ncrdVO.setAdbkId(abUser.getAdbkId());
		}		
		
		ncrdVO.setFrstRegisterId(user.getUniqId());
		ncrdService.insertNcrdItem(ncrdVO);
		return "redirect:/com/business/company/companyEmplyrList.do";
	}
		
	
	
	/*업체사원수정*/
	@RequestMapping(value = "/com/business/company/companyEmplyrModify.do")
	public String companyEmplyrModify(@ModelAttribute("ncrdVO") NameCardVO ncrdVO, ModelMap model) throws Exception {
    	
    	model.addAttribute("adbkcode",adbkService.selectAdbkCodeList());
		
    	NameCardVO vo = ncrdService.selectNcrdItem(ncrdVO);
    	model.addAttribute("ncrdVO", vo);		
		
		return "tis/com/business/company/companyEmplyrModify";
	}
	
	/*업체사원수정저장*/
	@RequestMapping(value = "/com/business/company/companyEmplyrUpdate.do")
	public String companyEmplyrUpdate(@ModelAttribute("ncrdVO") NameCardVO ncrdVO, ModelMap model
			,final MultipartHttpServletRequest multiRequest ) throws Exception {

    	LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
    	
    	List<FileVO> result = null;
		String atchFileId = ncrdVO.getAtchFileId();
		
		final Map<String, MultipartFile> files = multiRequest.getFileMap();

		// 첨부 파일이 있을 때만 동작
		if (!files.isEmpty()) {
			String tmpFileId="";
			// 기존 첨부 파일에 대한 처리
			if("".equals(atchFileId)) {
				result = fileUtil.parseFileInf(files, "PTR_", 0, atchFileId, "");
				tmpFileId = fileMngService.insertFileInfs(result);
				ncrdVO.setAtchFileId(tmpFileId);
			} else {
				FileVO fvo = new FileVO();
				fvo.setAtchFileId(atchFileId);
				result = fileUtil.parseFileInf(files, "PTR_", 0, atchFileId, "");
				fileMngService.updateFileInfsMobile(result);
			}
		}
    	
		if(ncrdVO.getAdbkId().equals(ncrdVO.getCmpnyNm()))
			ncrdVO.setAdbkId("");
		AdressBookUser abUser = new AdressBookUser();
		if(ncrdVO.getAdbkId() == null || ncrdVO.getAdbkId().isEmpty() ){
			//업체관리 추가			
			abUser.setCmpnyNm(ncrdVO.getCmpnyNm());
			abUser.setEntrprsSeCode(ncrdVO.getIdntfcNo());
			abUser.setEmplyrId(user.getUniqId());
			adbkService.insertAdressBookUser(abUser);
		}
		
		/*ncrdVO.setAdbkId(abUser.getAdbkId());*/
		ncrdVO.setLastUpdusrId(user.getUniqId());
		ncrdService.updateNcrdItem(ncrdVO);		
		
		return "forward:/com/business/company/companyEmplyrView.do";
	}
	
	@RequestMapping(value = "/com/business/company/companySearch.do", produces = "application/json; charset=utf8")
	public ModelAndView companySearch(@RequestParam("adbkId")String adbkId, Model model) throws Exception {
		AdressBookVO adbVO = new AdressBookVO();
		adbVO.setAdbkId(adbkId);
		String seCode = adbkService.selectAdbkEntrprsSeCode(adbVO);
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("result",seCode);
		return new ModelAndView("ajaxMainView",map);
	}
	
	
	@RequestMapping("/com/business/company/taskSchduleDiary.do")
	public ModelAndView taskSchduleDiary(@ModelAttribute("diaryManageVO") DiaryManageVO diaryManageVO, Model model) throws Exception{
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		
		if (!isAuthenticated) {
			model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
			//return "tis/com/inc/login";
		}		
		Map<String,Object> map  = new  HashMap<String,Object>();
	
		 map.put("result",egovDiaryManageService.selectDiaryCmng(diaryManageVO));
		return new ModelAndView("ajaxMainView",map);
	}
	
}