package tis.com.task.web;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import tis.com.common.service.TisCodeService;
import egovframework.com.cmm.ComDefaultCodeVO;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.EgovCmmUseService;
import egovframework.com.cmm.service.EgovFileMngService;
import egovframework.com.cmm.service.EgovFileMngUtil;
import egovframework.com.cmm.service.FileVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.cop.bbs.service.Board;
import egovframework.com.cop.bbs.service.BoardMaster;
import egovframework.com.cop.bbs.service.BoardMasterVO;
import egovframework.com.cop.bbs.service.BoardVO;
import egovframework.com.cop.bbs.service.EgovBBSAttributeManageService;
import egovframework.com.cop.bbs.service.EgovBBSManageService;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

@Controller
public class TaskDocumentController {
	
	@Resource(name = "EgovBBSAttributeManageService")
    private EgovBBSAttributeManageService bbsAttrbService;
	
	@Resource(name = "EgovBBSManageService")
    private EgovBBSManageService bbsMngService;
	
	/** cmmUseService */
	@Resource(name = "EgovCmmUseService")
	private EgovCmmUseService cmmUseService;
	
	@Resource(name = "EgovFileMngUtil")
    private EgovFileMngUtil fileUtil;
	
	@Resource(name = "EgovFileMngService")
    private EgovFileMngService fileMngService;
	
	@Resource(name="TisCodeService")
	private TisCodeService tisCodeService;
	 /**
     * XSS 방지 처리.
     * 
     * @param data
     * @return
     */
    protected String unscript(String data) {
        if (data == null || data.trim().equals("")) {
            return "";
        }
        
        String ret = data;
        
        ret = ret.replaceAll("<(S|s)(C|c)(R|r)(I|i)(P|p)(T|t)", "&lt;script");
        ret = ret.replaceAll("</(S|s)(C|c)(R|r)(I|i)(P|p)(T|t)", "&lt;/script");
        
        ret = ret.replaceAll("<(O|o)(B|b)(J|j)(E|e)(C|c)(T|t)", "&lt;object");
        ret = ret.replaceAll("</(O|o)(B|b)(J|j)(E|e)(C|c)(T|t)", "&lt;/object");
        
        ret = ret.replaceAll("<(A|a)(P|p)(P|p)(L|l)(E|e)(T|t)", "&lt;applet");
        ret = ret.replaceAll("</(A|a)(P|p)(P|p)(L|l)(E|e)(T|t)", "&lt;/applet");
        
        ret = ret.replaceAll("<(E|e)(M|m)(B|b)(E|e)(D|d)", "&lt;embed");
        ret = ret.replaceAll("</(E|e)(M|m)(B|b)(E|e)(D|d)", "&lt;embed");
        
        ret = ret.replaceAll("<(F|f)(O|o)(R|r)(M|m)", "&lt;form");
        ret = ret.replaceAll("</(F|f)(O|o)(R|r)(M|m)", "&lt;form");

        return ret;
    }
	
	
	
	/*문서양식리스트로 이동*/
	//bbsId=BBSMSTR_000000000031
	@RequestMapping(value = "/com/task/document/taskDocumentList.do")
	public String taskDocumentList(@ModelAttribute("searchVO") BoardVO boardVO, ModelMap model) throws Exception {
		// 미인증 사용자에 대한 보안처리
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		if (!isAuthenticated) {
			return "redirect:/";
		}
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		boardVO.setBbsId(boardVO.getBbsId());
		boardVO.setBbsNm(boardVO.getBbsNm());

		BoardMasterVO vo = new BoardMasterVO();
		
		vo.setBbsId(boardVO.getBbsId());
		
		boardVO.setPageUnit(5000);
		boardVO.setPageSize(1);

		PaginationInfo paginationInfo = new PaginationInfo();
		
		paginationInfo.setCurrentPageNo(boardVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(boardVO.getPageUnit());
		paginationInfo.setPageSize(boardVO.getPageSize());

		boardVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		boardVO.setLastIndex(paginationInfo.getLastRecordIndex());
		boardVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());		
		
		BoardMasterVO master = bbsAttrbService.selectBBSMasterInf(vo);

		List<EgovMap> deptList = (List<EgovMap>) tisCodeService.selectCodeDetailList("COM101");
		if(boardVO.getOrgnztNm() == null ){
			
			for(int i=0;i<deptList.size();i++){
				EgovMap tmp  = deptList.get(i);
				if(user.getOrgnztId().equals(tmp.get("code").toString()) ){
					boardVO.setOrgnztNm(tmp.get("codeNm").toString());
				}
			}			
		}
		Map<String, Object> map = bbsMngService.selectBoardArticles(boardVO, master.getBbsAttrbCode());
		int totCnt = Integer.parseInt((String)map.get("resultCnt"));

		model.addAttribute("deptList", deptList);
		model.addAttribute("resultList", map.get("resultList"));
		model.addAttribute("resultCnt", map.get("resultCnt"));
		model.addAttribute("boardVO", boardVO);
		model.addAttribute("brdMstrVO", master);
		
		return "tis/com/task/document/taskDocumentList";
	}	
	
	/*일반문서양식 수정페이지*/
	@RequestMapping(value = "/com/task/document/taskDocumentModify.do")
	public String taskDocumentModify(@ModelAttribute("boardVO") BoardVO boardVO, ModelMap model) throws Exception {
		// 미인증 사용자에 대한 보안처리
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		if (!isAuthenticated) {
			return "redirect:/";
		}
		BoardMasterVO master = new BoardMasterVO();
		BoardMasterVO vo = new BoardMasterVO();
		vo.setBbsId(boardVO.getBbsId());
		master = bbsAttrbService.selectBBSMasterInf(vo);
		model.addAttribute("brdMstrVO", master);
				
		boardVO.setPlusCount(true);
		boardVO = bbsMngService.selectBoardArticle(boardVO);
		
		//부서 조회
		ComDefaultCodeVO codevo = new ComDefaultCodeVO();
		codevo.setCodeId("COM101");
		List<?> depart_result = cmmUseService.selectCmmCodeDetail(codevo);
		model.addAttribute("depart_result", depart_result); //부서구분	
		model.addAttribute("boardVO", boardVO);		
		
		return "tis/com/task/document/taskDocumentModify";
	}
	
	/*일반문서양식 등록페이지*/
	@RequestMapping(value = "/com/task/document/taskDocumentInsert.do")
	public String taskDocumentInsert(@ModelAttribute("searchVO") BoardVO boardVO, ModelMap model) throws Exception {
		// 미인증 사용자에 대한 보안처리
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		if (!isAuthenticated) {
			return "redirect:/";
		}
		
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		BoardMasterVO bdMstr = new BoardMasterVO();

		BoardMasterVO vo = new BoardMasterVO();
		vo.setBbsId(boardVO.getBbsId());
		vo.setUniqId(user.getUniqId());

		bdMstr = bbsAttrbService.selectBBSMasterInf(vo);
		
		ComDefaultCodeVO codevo = new ComDefaultCodeVO();

		//부서 조회
		codevo.setCodeId("COM101");
		List<?> depart_result = cmmUseService.selectCmmCodeDetail(codevo);
    	model.addAttribute("depart_result", depart_result); //부서구분		
		
		model.addAttribute("bdMstr", bdMstr);
		model.addAttribute("brdMstrVO", bdMstr);

		return "tis/com/task/document/taskDocumentInsert";
	}
	
	/*일반문서양식 등록페이지*/
	@RequestMapping(value = "/com/task/document/taskDocumentView.do")
	public String taskDocumentView(@ModelAttribute("boardVO") BoardVO boardVO, ModelMap model) throws Exception {
		// 미인증 사용자에 대한 보안처리
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		if (!isAuthenticated) {
			return "redirect:/";
		}
		BoardMasterVO master = new BoardMasterVO();
		BoardMasterVO vo = new BoardMasterVO();
		vo.setBbsId(boardVO.getBbsId());
		master = bbsAttrbService.selectBBSMasterInf(vo);
		model.addAttribute("brdMstrVO", master);
				
		boardVO.setPlusCount(true);
		boardVO = bbsMngService.selectBoardArticle(boardVO);
				
		model.addAttribute("boardVO", boardVO);

		return "tis/com/task/document/taskDocumentView";
	}
	
	
	/*일반문서양식 저장*/
	@RequestMapping(value = "/com/task/document/taskDocumentAdd.do")
	public String taskDocumentAdd(final MultipartHttpServletRequest multiRequest, @ModelAttribute("searchVO") BoardVO boardVO,
		    @ModelAttribute("bdMstr") BoardMaster bdMstr, @ModelAttribute("board") Board board, BindingResult bindingResult, SessionStatus status,
		    ModelMap model) throws Exception {
			
		// 미인증 사용자에 대한 보안처리
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		if (!isAuthenticated) {
			return "redirect:/";
		}
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();

		if (isAuthenticated) {
		    List<FileVO> result = null;
		    String atchFileId = "";
		    
		    final Map<String, MultipartFile> files = multiRequest.getFileMap();
		    if (!files.isEmpty()) {
				result = fileUtil.parseFileInf(files, "BBS_", 0, "", "");
				atchFileId = fileMngService.insertFileInfs(result);
		    }
		    board.setAtchFileId(atchFileId);
		    board.setFrstRegisterId(user.getUniqId());
		    board.setBbsId(board.getBbsId());
		    
		    //board.setNtcrNm("");	// dummy 오류 수정 (익명이 아닌 경우 validator 처리를 위해 dummy로 지정됨)
		    board.setPassword("");	// dummy 오류 수정 (익명이 아닌 경우 validator 처리를 위해 dummy로 지정됨)
		    
		    board.setNtcrId(user.getId()); //게시물 통계 집계를 위해 등록자 ID 저장
		    board.setNtcrNm(user.getName()); //게시물 통계 집계를 위해 등록자 Name 저장
		    
		    board.setNttCn(unscript(board.getNttCn()));	// XSS 방지
		    board.setNttCn2(unscript(board.getNttCn2()));	// XSS 방지
		    Random rand = new Random();
		    String rNum = Integer.toString((rand.nextInt(99999999)+1000)).substring(0,2);
		    SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat ( "yyyyMM", Locale.KOREA );
		    Date currentTime = new Date ();
		    String mTime = mSimpleDateFormat.format ( currentTime );
		    String docNo = board.getOrgnztNm()+"-"+mTime+rNum;
		    board.setDocNo(docNo);
		    bbsMngService.insertBoardArticle(board);
		}

		//status.setComplete();
		//String url  = "?bbsId="+boardVO.getBbsId();
		return "redirect:/com/task/document/taskDocumentList.do?bbsId="+board.getBbsId();
	}	
	
	/*일반문서양식 저장*/
	@RequestMapping(value = "/com/task/document/taskDocumentSave.do")
	public String taskDocumentSave(final MultipartHttpServletRequest multiRequest, @ModelAttribute("searchVO") BoardVO boardVO,
		    @ModelAttribute("bdMstr") BoardMaster bdMstr, @ModelAttribute("board") Board board, BindingResult bindingResult, SessionStatus status,
		    ModelMap model) throws Exception {
			
		// 미인증 사용자에 대한 보안처리
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		if (!isAuthenticated) {
			return "redirect:/";
		}
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();

		if (isAuthenticated) {
		    List<FileVO> result = null;
		    String atchFileId = boardVO.getAtchFileId();
		    
		    final Map<String, MultipartFile> files = multiRequest.getFileMap();
		 // 첨부 파일이 있을 때만 동작
			if (!files.isEmpty()) {
				String tmpFileId="";
				// 기존 첨부 파일에 대한 처리
				if("".equals(atchFileId)) {
					result = fileUtil.parseFileInf(files, "BBS_", 0, atchFileId, "");
					tmpFileId = fileMngService.insertFileInfs(result);
					board.setAtchFileId(tmpFileId);
				} else {
					FileVO fvo = new FileVO();
					fvo.setAtchFileId(atchFileId);
					int cnt = fileMngService.getMaxFileSN(fvo);
					result = fileUtil.parseFileInf(files, "BBS_", cnt, atchFileId, "");
					fileMngService.updateFileInfs(result);
				}
			}

		    board.setBbsId(board.getBbsId());
		    
		    //board.setNtcrNm("");	// dummy 오류 수정 (익명이 아닌 경우 validator 처리를 위해 dummy로 지정됨)
		    board.setPassword("");	// dummy 오류 수정 (익명이 아닌 경우 validator 처리를 위해 dummy로 지정됨)
		    
		    board.setNtcrId(user.getId()); //게시물 통계 집계를 위해 등록자 ID 저장
		    board.setNtcrNm(user.getName()); //게시물 통계 집계를 위해 등록자 Name 저장
		    
		    board.setNttCn(unscript(board.getNttCn()));	// XSS 방지
		    board.setNttCn2(unscript(board.getNttCn2()));	// XSS 방지
		    
		    bbsMngService.updateBoardArticle(board);
		}

		//status.setComplete();
		//String url  = "?bbsId="+boardVO.getBbsId();
		return "forward:/com/task/document/taskDocumentList.do";
	}	
    @RequestMapping("/com/task/document/taskDocumentDelete.do")
    public String deleteBoardArticle(@ModelAttribute("searchVO") BoardVO boardVO, @ModelAttribute("board") Board board,
	    @ModelAttribute("bdMstr") BoardMaster bdMstr, ModelMap model) throws Exception {
	
	LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
	Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

	if (isAuthenticated) {
	    board.setLastUpdusrId(user.getUniqId());
	    
	    bbsMngService.deleteBoardArticle(board);
	}

	return "redirect:/com/task/document/taskDocumentList.do?bbsId="+board.getBbsId();
    }
	
	/*자동문서양식 등록페이지*/
	@RequestMapping(value = "/com/task/document/taskAutoDocumentInsert.do")
	public String taskAutoDocumentInsert() throws Exception {
		return "tis/com/task/document/taskAutoDocumentInsert";
	}
}