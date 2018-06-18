package tis.com.business.bbs.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springmodules.validation.commons.DefaultBeanValidator;

import com.google.gson.Gson;

import egovframework.com.cmm.ComDefaultCodeVO;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.EgovCmmUseService;
import egovframework.com.cmm.service.EgovFileMngUtil;
import egovframework.com.cmm.service.FileVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.cop.bbs.service.Board;
import egovframework.com.cop.bbs.service.BoardMaster;
import egovframework.com.cop.bbs.service.BoardMasterVO;
import egovframework.com.cop.bbs.service.BoardVO;
import egovframework.com.cop.bbs.service.EgovBBSAttributeManageService;
import egovframework.com.cop.bbs.service.EgovBBSManageService;
import egovframework.com.cop.smt.sim.service.IndvdlSchdulManageVO;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import egovframework.com.cmm.service.EgovFileMngService;

@Controller
public class BbsController {
	
	@Resource(name = "EgovBBSAttributeManageService")
    private EgovBBSAttributeManageService bbsAttrbService;
	
	@Resource(name = "propertiesService")
    protected EgovPropertyService propertyService;

	@Resource(name = "EgovBBSManageService")
    private EgovBBSManageService bbsMngService;
	
    @Resource(name = "EgovFileMngUtil")
    private EgovFileMngUtil fileUtil;
    
    @Resource(name = "EgovFileMngService")
    private EgovFileMngService fileMngService;

	@Resource(name = "EgovCmmUseService")
	private EgovCmmUseService cmmUseService;
	
    @Autowired
    private DefaultBeanValidator beanValidator;
    
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
    
	/*공지사항리스트*/
	//?bbsId=BBSMSTR_000000000001
	@RequestMapping(value = "/com/business/bbs/noticeList.do")
	public String noticeList(@ModelAttribute("searchVO") BoardVO boardVO, ModelMap model) throws Exception {

		boardVO.setBbsId(boardVO.getBbsId());
		boardVO.setBbsNm(boardVO.getBbsNm());

		BoardMasterVO vo = new BoardMasterVO();
		
		vo.setBbsId(boardVO.getBbsId());
		//vo.setUniqId(user.getUniqId());
		
		BoardMasterVO master = bbsAttrbService.selectBBSMasterInf(vo);

		boardVO.setPageUnit(propertyService.getInt("pageUnit"));
		boardVO.setPageSize(propertyService.getInt("pageSize"));

		PaginationInfo paginationInfo = new PaginationInfo();
		
		paginationInfo.setCurrentPageNo(boardVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(boardVO.getPageUnit());
		paginationInfo.setPageSize(boardVO.getPageSize());

		boardVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		boardVO.setLastIndex(paginationInfo.getLastRecordIndex());
		boardVO.setRecordCountPerPage(15);

		Map<String, Object> map = bbsMngService.selectBoardArticles(boardVO, master.getBbsAttrbCode());
		int totCnt = Integer.parseInt((String)map.get("resultCnt"));
		
		paginationInfo.setTotalRecordCount(totCnt);

		model.addAttribute("resultList", map.get("resultList"));
		model.addAttribute("resultCnt", map.get("resultCnt"));
		model.addAttribute("boardVO", boardVO);
		model.addAttribute("brdMstrVO", master);
		model.addAttribute("paginationInfo", paginationInfo);

		//return "egovframework/com/cop/bbs/EgovNoticeList";
		
		return "tis/com/business/bbs/noticeList";
	}

	@RequestMapping(value="/com/business/bbs/noticeCnt.do")
	public String noticeCnt(@RequestParam("leftmenu") String leftmenu, ModelMap model) throws Exception {
		
		model.addAttribute("newCnt", bbsMngService.noticeCnt());
		model.addAttribute("leftmenu", leftmenu);		
    	return "tis/com/inc/newBoard";
	}
	
	/*공지사항상세보기*/
	@RequestMapping(value = "/com/business/bbs/noticeView.do")
	public String noticeView( BoardVO boardVO , Model model) throws Exception {

		BoardMasterVO master = new BoardMasterVO();
		BoardMasterVO vo = new BoardMasterVO();
		vo.setBbsId(boardVO.getBbsId());
		master = bbsAttrbService.selectBBSMasterInf(vo);
		model.addAttribute("brdMstrVO", master);
		
		boardVO.setPlusCount(true);
		boardVO = bbsMngService.selectBoardArticle(boardVO);
		
		model.addAttribute("boardVO", boardVO);
		
		return "tis/com/business/bbs/noticeView";
	}
	
	/*공지사항등록페이지*/
	@RequestMapping(value = "/com/business/bbs/noticeInsert.do")
	public String noticeInsert(BoardVO boardVO, Model model) throws Exception {

		BoardMasterVO master = new BoardMasterVO();
		BoardMasterVO vo = new BoardMasterVO();
		vo.setBbsId(boardVO.getBbsId());
		master = bbsAttrbService.selectBBSMasterInf(vo);

		// ----------------------------
		// 기본 BBS template 지정
		// ----------------------------
		if (master.getTmplatCours() == null || master.getTmplatCours().equals("")) {
			master.setTmplatCours("/css/egovframework/com/cop/tpl/egovBaseTemplate.css");
		}
		
		model.addAttribute("bdMstr", master);
		model.addAttribute("bbsId", boardVO.getBbsId());
		
		return "tis/com/business/bbs/noticeInsert";
	}

    /*공지사항저장*/
	@RequestMapping("/com/business/bbs/noticeSave.do")
   public String noticeSave(final MultipartHttpServletRequest multiRequest, @ModelAttribute("searchVO") BoardVO boardVO,
	    @ModelAttribute("bdMstr") BoardMaster bdMstr, @ModelAttribute("board") Board board, BindingResult bindingResult, SessionStatus status,
	    ModelMap model) throws Exception {

	LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();

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
	    
	    bbsMngService.insertBoardArticle(board);

	//status.setComplete();
	String url  = "?bbsId="+boardVO.getBbsId();
	return "redirect:/com/business/bbs/noticeList.do"+url;
   }
	
	/*공지사항수정페이지*/
	@RequestMapping(value = "/com/business/bbs/noticeModify.do")
	public String noticeModify(@ModelAttribute("searchVO") BoardVO boardVO, @ModelAttribute("board") BoardVO vo, ModelMap model) throws Exception {
		
		BoardMaster master = new BoardMaster();
		BoardMasterVO bmvo = new BoardMasterVO();
		BoardVO bdvo = new BoardVO();

		vo.setBbsId(boardVO.getBbsId());

		master.setBbsId(boardVO.getBbsId());
		bmvo = bbsAttrbService.selectBBSMasterInf(master);
		bdvo = bbsMngService.selectBoardArticle(boardVO);

		model.addAttribute("result", bdvo);
		model.addAttribute("bdMstr", bmvo);

		// ----------------------------
		// 기본 BBS template 지정
		// ----------------------------
		if (bmvo.getTmplatCours() == null || bmvo.getTmplatCours().equals("")) {
			bmvo.setTmplatCours("/css/egovframework/com/cop/tpl/egovBaseTemplate.css");
		}

		model.addAttribute("brdMstrVO", bmvo);
		// //-----------------------------
		
		return "tis/com/business/bbs/noticeModify";
	}

	/*공지사항수정*/
	@RequestMapping("/com/business/bbs/noticeUpdate.do")
	public String noticeUpdate(final MultipartHttpServletRequest multiRequest
							  , @ModelAttribute("searchVO") BoardVO boardVO
							  , @ModelAttribute("bdMstr") BoardMaster bdMstr
							  , @ModelAttribute("board") Board board
							  , @ModelAttribute("indvdlSchdulManageVO") IndvdlSchdulManageVO indvdlSchdulManageVO
						  	  , BindingResult bindingResult
							  , ModelMap model
							  , SessionStatus status) throws Exception {

		List<FileVO> result = null;
		String atchFileId = boardVO.getAtchFileId();

		beanValidator.validate(board, bindingResult);
		if (bindingResult.hasErrors()) {
			BoardMaster master = new BoardMaster();
			BoardMasterVO bmvo = new BoardMasterVO();
			BoardVO bdvo = new BoardVO();

			master.setBbsId(boardVO.getBbsId());

			bmvo = bbsAttrbService.selectBBSMasterInf(master);
			bdvo = bbsMngService.selectBoardArticle(boardVO);

			model.addAttribute("result", bdvo);
			model.addAttribute("bdMstr", bmvo);

			return "dgstat/bbs/comBbsModify";
		}

		if(board.getFrstRegisterPnttm() == null || board.getFrstRegisterPnttm().equals("") ){
			board.setFrstRegisterPnttm(new java.text.SimpleDateFormat("yyyyMMdd").format(new java.util.Date()));
		}
		
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

			board.setNttCn(unscript(board.getNttCn())); // XSS 방지
			bbsMngService.updateBoardArticle(board);

		String url  = "?bbsId="+boardVO.getBbsId();
		return "forward:/com/business/bbs/noticeView.do";
	}
	
//	공지사항글삭제
	@RequestMapping("/com/business/bbs/noticeDelete.do")
	public String comBbsDelete(@ModelAttribute("searchVO") BoardVO boardVO
							  , @ModelAttribute("board") Board board
							  , @ModelAttribute("bdMstr") BoardMaster bdMstr
							  , @ModelAttribute("indvdlSchdulManageVO") IndvdlSchdulManageVO indvdlSchdulManageVO
							  , ModelMap model) throws Exception {
		
		bbsMngService.deleteBoardArticle(board);
		String url  = "?bbsId="+boardVO.getBbsId();
		return "redirect:/com/business/bbs/noticeList.do"+url;
	}
	/*취업규칙상세보기*/
	//?bbsId=BBSMSTR_000000000011
	@RequestMapping(value = "/com/business/bbs/emplyrRuleList.do")
	public String emplyrRuleList( BoardVO boardVO , Model model) throws Exception {
		
		BoardMasterVO master = new BoardMasterVO();
		BoardMasterVO vo = new BoardMasterVO();
		vo.setBbsId(boardVO.getBbsId());
		master = bbsAttrbService.selectBBSMasterInf(vo);
		model.addAttribute("brdMstrVO", master);
		
		boardVO.setPlusCount(true);
		boardVO = bbsMngService.selectBoardArticle(boardVO);
		
		model.addAttribute("boardVO", boardVO);
		

		return "tis/com/business/bbs/emplyrRuleList";
	}
	
	/*취업규칙등록페이지*/
    @RequestMapping("/com/business/bbs/emplyrRuleInsert.do")
    public String emplyrRuleInsert(@ModelAttribute("searchVO") BoardVO boardVO, ModelMap model) throws Exception {
		
	BoardMasterVO bdMstr = new BoardMasterVO();

	    BoardMasterVO vo = new BoardMasterVO();
	    vo.setBbsId(boardVO.getBbsId());
	    vo.setUniqId("ANONYMOUS");

	    bdMstr = bbsAttrbService.selectBBSMasterInf(vo);
	    model.addAttribute("bdMstr", bdMstr);

	//----------------------------
	// 기본 BBS template 지정 
	//----------------------------
	if (bdMstr.getTmplatCours() == null || bdMstr.getTmplatCours().equals("")) {
	    bdMstr.setTmplatCours("/css/egovframework/com/cop/tpl/egovBaseTemplate.css");
	}

	model.addAttribute("brdMstrVO", bdMstr);
	////-----------------------------
	
	model.addAttribute("anonymous", "true");
	
	//부서 조회
	ComDefaultCodeVO cvo = new ComDefaultCodeVO();
	cvo.setCodeId("COM101");
	List<?> depart_result = cmmUseService.selectCmmCodeDetail(cvo);
	
	model.addAttribute("depart_result", depart_result); //부서구분
	
	return "tis/com/business/bbs/emplyrRuleInsert";
    }
    
     /*취업규칙저장*/
	@RequestMapping("/com/business/bbs/emplyrRuleSave.do")
    public String emplyrRuleSave(final MultipartHttpServletRequest multiRequest, @ModelAttribute("searchVO") BoardVO boardVO,
	    @ModelAttribute("bdMstr") BoardMaster bdMstr, @ModelAttribute("board") Board board, BindingResult bindingResult, SessionStatus status,
	    ModelMap model) throws Exception {
		
	LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();

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
	    
	    bbsMngService.insertBoardArticle(board);

	//status.setComplete();
	String url  = "?bbsId="+boardVO.getBbsId();
	return "redirect:/com/business/bbs/emplyrRuleList.do"+url;
    }
    
	/*취업규칙수정페이지*/
	@RequestMapping(value = "/com/business/bbs/emplyrRuleModify.do")
	public String emplyrRuleModify(@ModelAttribute("searchVO") BoardVO boardVO, @ModelAttribute("board") BoardVO vo, ModelMap model) throws Exception {
		
		BoardMaster master = new BoardMaster();
		BoardMasterVO bmvo = new BoardMasterVO();
		BoardVO bdvo = new BoardVO();

		vo.setBbsId(boardVO.getBbsId());

		master.setBbsId(boardVO.getBbsId());
		bmvo = bbsAttrbService.selectBBSMasterInf(master);
		bdvo = bbsMngService.selectBoardArticle(boardVO);

		model.addAttribute("result", bdvo);
		model.addAttribute("bdMstr", bmvo);

		ComDefaultCodeVO cvo = new ComDefaultCodeVO();
		
		//부서 조회
		cvo.setCodeId("COM101");
		List<?> depart_result = cmmUseService.selectCmmCodeDetail(cvo);
		
		model.addAttribute("depart_result", depart_result); //부서구분
		// ----------------------------
		// 기본 BBS template 지정
		// ----------------------------
		if (bmvo.getTmplatCours() == null || bmvo.getTmplatCours().equals("")) {
			bmvo.setTmplatCours("/css/egovframework/com/cop/tpl/egovBaseTemplate.css");
		}

		model.addAttribute("brdMstrVO", bmvo);
		// //-----------------------------
		
		
		return "tis/com/business/bbs/emplyrRuleModify";
	}
	
	/*취업규칙수정*/
	@RequestMapping(value = "/com/business/bbs/emplyrRuleUpdate.do")
	public String emplyrRuleUpdate(final MultipartHttpServletRequest multiRequest
			  , @ModelAttribute("searchVO") BoardVO boardVO
			  , @ModelAttribute("bdMstr") BoardMaster bdMstr
			  , @ModelAttribute("board") Board board
			  , @ModelAttribute("indvdlSchdulManageVO") IndvdlSchdulManageVO indvdlSchdulManageVO
		  	  , BindingResult bindingResult
			  , ModelMap model
			  , SessionStatus status) throws Exception {
			
			List<FileVO> result = null;
			String atchFileId = boardVO.getAtchFileId();
			
			beanValidator.validate(board, bindingResult);
			if (bindingResult.hasErrors()) {
			BoardMaster master = new BoardMaster();
			BoardMasterVO bmvo = new BoardMasterVO();
			BoardVO bdvo = new BoardVO();
			
			master.setBbsId(boardVO.getBbsId());
			
			bmvo = bbsAttrbService.selectBBSMasterInf(master);
			bdvo = bbsMngService.selectBoardArticle(boardVO);
			
			model.addAttribute("result", bdvo);
			model.addAttribute("bdMstr", bmvo);
			
			return "dgstat/bbs/comBbsModify";
			}
			
			if(board.getFrstRegisterPnttm() == null || board.getFrstRegisterPnttm().equals("") ){
			board.setFrstRegisterPnttm(new java.text.SimpleDateFormat("yyyyMMdd").format(new java.util.Date()));
			}
			
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
			
			board.setNttCn(unscript(board.getNttCn())); // XSS 방지
			bbsMngService.updateBoardArticle(board);
			
			String url  = "?bbsId="+boardVO.getBbsId();
			return "redirect:/com/business/bbs/emplyrRuleList.do"+url;

		}
	
	/*성희롱예방교육상세보기*/
	@RequestMapping(value = "/com/business/bbs/sexualList.do")
	public String sexualList( BoardVO boardVO , Model model) throws Exception {
		
		BoardMasterVO master = new BoardMasterVO();
		BoardMasterVO vo = new BoardMasterVO();
		vo.setBbsId(boardVO.getBbsId());
		master = bbsAttrbService.selectBBSMasterInf(vo);
		model.addAttribute("brdMstrVO", master);
		
		boardVO.setPlusCount(true);
		boardVO = bbsMngService.selectBoardArticle(boardVO);
		
		model.addAttribute("boardVO", boardVO);
		
		return "tis/com/business/bbs/sexualList";
	}
	
	/*성희롱예방교육등록페이지*/
    @RequestMapping("/com/business/bbs/sexualInsert.do")
    public String sexualInsert(@ModelAttribute("searchVO") BoardVO boardVO, ModelMap model) throws Exception {
		
	BoardMasterVO bdMstr = new BoardMasterVO();

	    BoardMasterVO vo = new BoardMasterVO();
	    vo.setBbsId(boardVO.getBbsId());
	    vo.setUniqId("ANONYMOUS");

	    bdMstr = bbsAttrbService.selectBBSMasterInf(vo);
	    model.addAttribute("bdMstr", bdMstr);

	//----------------------------
	// 기본 BBS template 지정 
	//----------------------------
	if (bdMstr.getTmplatCours() == null || bdMstr.getTmplatCours().equals("")) {
	    bdMstr.setTmplatCours("/css/egovframework/com/cop/tpl/egovBaseTemplate.css");
	}

	model.addAttribute("brdMstrVO", bdMstr);
	////-----------------------------
	
	model.addAttribute("anonymous", "true");
	
	//부서 조회
	ComDefaultCodeVO cvo = new ComDefaultCodeVO();
	cvo.setCodeId("COM101");
	List<?> depart_result = cmmUseService.selectCmmCodeDetail(cvo);
	
	model.addAttribute("depart_result", depart_result); //부서구분
	
	return "tis/com/business/bbs/sexualInsert";
    }
    
    /*성희롱예방교육저장*/
	@RequestMapping("/com/business/bbs/sexualSave.do")
   public String sexualSave(final MultipartHttpServletRequest multiRequest, @ModelAttribute("searchVO") BoardVO boardVO,
	    @ModelAttribute("bdMstr") BoardMaster bdMstr, @ModelAttribute("board") Board board, BindingResult bindingResult, SessionStatus status,
	    ModelMap model) throws Exception {
		
	LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();

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
	    
	    bbsMngService.insertBoardArticle(board);

	//status.setComplete();
	String url  = "?bbsId="+boardVO.getBbsId();
	return "redirect:/com/business/bbs/sexualList.do"+url;
   }
	
	/*성희롱예방교육수정페이지*/
	@RequestMapping(value = "/com/business/bbs/sexualModify.do")
	public String sexualModify(@ModelAttribute("searchVO") BoardVO boardVO, @ModelAttribute("board") BoardVO vo, ModelMap model) throws Exception {
		
		BoardMaster master = new BoardMaster();
		BoardMasterVO bmvo = new BoardMasterVO();
		BoardVO bdvo = new BoardVO();

		vo.setBbsId(boardVO.getBbsId());

		master.setBbsId(boardVO.getBbsId());
		bmvo = bbsAttrbService.selectBBSMasterInf(master);
		bdvo = bbsMngService.selectBoardArticle(boardVO);

		model.addAttribute("result", bdvo);
		model.addAttribute("bdMstr", bmvo);

		ComDefaultCodeVO cvo = new ComDefaultCodeVO();
		
		//부서 조회
		cvo.setCodeId("COM101");
		List<?> depart_result = cmmUseService.selectCmmCodeDetail(cvo);
		
		model.addAttribute("depart_result", depart_result); //부서구분
		// ----------------------------
		// 기본 BBS template 지정
		// ----------------------------
		if (bmvo.getTmplatCours() == null || bmvo.getTmplatCours().equals("")) {
			bmvo.setTmplatCours("/css/egovframework/com/cop/tpl/egovBaseTemplate.css");
		}

		model.addAttribute("brdMstrVO", bmvo);
		// //-----------------------------
		
		
		return "tis/com/business/bbs/sexualModify";
	}
	
	/*취업규칙수정*/
	@RequestMapping(value = "/com/business/bbs/sexualUpdate.do")
	public String sexualUpdate(final MultipartHttpServletRequest multiRequest
			  , @ModelAttribute("searchVO") BoardVO boardVO
			  , @ModelAttribute("bdMstr") BoardMaster bdMstr
			  , @ModelAttribute("board") Board board
			  , @ModelAttribute("indvdlSchdulManageVO") IndvdlSchdulManageVO indvdlSchdulManageVO
		  	  , BindingResult bindingResult
			  , ModelMap model
			  , SessionStatus status) throws Exception {
			
			List<FileVO> result = null;
			String atchFileId = boardVO.getAtchFileId();
			
			beanValidator.validate(board, bindingResult);
			if (bindingResult.hasErrors()) {
			BoardMaster master = new BoardMaster();
			BoardMasterVO bmvo = new BoardMasterVO();
			BoardVO bdvo = new BoardVO();
			
			master.setBbsId(boardVO.getBbsId());
			
			bmvo = bbsAttrbService.selectBBSMasterInf(master);
			bdvo = bbsMngService.selectBoardArticle(boardVO);
			
			model.addAttribute("result", bdvo);
			model.addAttribute("bdMstr", bmvo);
			
			return "dgstat/bbs/comBbsModify";
			}
			
			if(board.getFrstRegisterPnttm() == null || board.getFrstRegisterPnttm().equals("") ){
			board.setFrstRegisterPnttm(new java.text.SimpleDateFormat("yyyyMMdd").format(new java.util.Date()));
			}
			
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
			
			board.setNttCn(unscript(board.getNttCn())); // XSS 방지
			bbsMngService.updateBoardArticle(board);
			
			String url  = "?bbsId="+boardVO.getBbsId();
			return "redirect:/com/business/bbs/sexualList.do"+url;

		}

}