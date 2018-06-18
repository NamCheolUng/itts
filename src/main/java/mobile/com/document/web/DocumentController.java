package mobile.com.document.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import mobile.com.document.service.DocumentFileService;
import mobile.com.document.service.DocumentFileVO;
import mobile.com.receipt.service.ReceiptService;
import mobile.com.receipt.service.ReceiptVO;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.EgovFileMngService;
import egovframework.com.cmm.service.EgovFileMngUtil;
import egovframework.com.cmm.service.FileVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.uss.umt.service.EgovUserManageService;
import egovframework.com.uss.umt.service.UserManageVO;

@Controller
public class DocumentController {
	
	// 첨부파일 관련
	@Resource(name="EgovFileMngService")
	private EgovFileMngService fileMngService;
	
	@Resource(name = "EgovFileMngUtil")
	protected EgovFileMngUtil fileUtil;
	
	@Resource(name = "DocumentFileService")
	private DocumentFileService documentFileService;
	
    @Resource(name = "EgovFileMngService")
    private EgovFileMngService fileService;
    
    /** userManageService */
	@Resource(name = "userManageService")
	private EgovUserManageService userManageService;
	
	@Resource(name = "ReceiptService")
	private ReceiptService receiptService;
    
	/*증빙서류페이지*/
	@RequestMapping("/mobile/com/document/documentList.do")
	public String document(@ModelAttribute("documentFileVO") DocumentFileVO documentFileVO, Model model
			,	@ModelAttribute("userManageVO") UserManageVO userManageVO	) throws Exception {
		
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		if (!isAuthenticated) {
			return "redirect:/mobile/com/inc/login.do";
		}
		LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();

		userManageVO = userManageService.selectUser(loginVO.getUniqId());
		model.addAttribute("userManageVO", userManageVO);
		return "/mobile/com/document/documentList";
	}
	
	/*증빙서류저장*/
	@RequestMapping("/mobile/com/document/documentSave.do")
	public String documentSave(@ModelAttribute("userManageVO") UserManageVO userManageVO, 
								final MultipartHttpServletRequest multiRequest,
								@RequestParam(value="selectAtchFileId", required=false) String selectAtchFileId ) throws Exception {
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		if (!isAuthenticated) {
			return "redirect:/mobile/com/inc/login.do";
		}
		LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		userManageVO.setUniqId(loginVO.getUniqId());
		
		List<FileVO> result = null;
		String atchFileId = userManageVO.getAtchFileId();
		final Map<String, MultipartFile> files = multiRequest.getFileMap();

				if (!files.isEmpty()) {
					String tmpFileId="";
					// 기존 첨부 파일에 대한 처리
					if("".equals(atchFileId)) {
						result = fileUtil.parseFileInf(files, "PTR_", 0, atchFileId, "");
						tmpFileId = fileMngService.insertFileInfs(result);
						userManageVO.setAtchFileId(tmpFileId);
					} else {
						FileVO fvo = new FileVO();
						fvo.setAtchFileId(atchFileId);
						int cnt = fileMngService.getMaxFileSN(fvo);
						result = fileUtil.parseFileInf(files, "PTR_", cnt, atchFileId, "");
						fileMngService.updateFileInfs(result);
					}
				}
		userManageService.updateUserFileId(userManageVO);
				
		return "redirect:/mobile/com/document/documentList.do";
	}

/*	등본사본페이지
	@RequestMapping("/mobile/com/document/certified.do")
	public String certified(@ModelAttribute("documentFileVO") DocumentFileVO documentFileVO, Model model) throws Exception {
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		if (!isAuthenticated) {
			return "redirect:/mobile/com/inc/login.do";
		}
		LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		documentFileVO.setEmplNo(loginVO.getEmplyrNo());
		documentFileVO.setFileType("certified");
		documentFileVO = documentFileService.selectDocumentArticle(documentFileVO);
		
		if(documentFileVO != null){
			ReceiptVO rvo = new ReceiptVO();
			rvo.setAtchFileId(documentFileVO.getAtchFileId());
			receiptService.deleteAtchFileDetail(rvo);
			receiptService.deleteAtchFile(rvo);
			
			documentFileService.deleteDocumentArticle(documentFileVO);
		}
		return "/mobile/com/document/certified";
	}
	
	등본사본페이지
	@RequestMapping("/mobile/com/document/certifiedView.do")
	public String certifiedView(@ModelAttribute("documentFileVO") DocumentFileVO documentFileVO, Model model) throws Exception {
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		if (!isAuthenticated) {
			return "redirect:/mobile/com/inc/login.do";
		}
		LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		documentFileVO.setEmplNo(loginVO.getEmplyrNo());
		documentFileVO.setFileType("certified");

		documentFileVO = documentFileService.selectDocumentArticle(documentFileVO);
		model.addAttribute("result", documentFileVO);
		
		
		return "/mobile/com/document/certified";
	}
	
	등본사본유저창에 업데이트
	@RequestMapping("/mobile/com/document/documentUptEmplyr.do")
	public String documentUptEmplyr(@ModelAttribute("documentFileVO") DocumentFileVO documentFileVO, Model model) throws Exception {
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		if (!isAuthenticated) {
			return "redirect:/mobile/com/inc/login.do";
		}
		LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		documentFileVO.setEmplNo(loginVO.getEmplyrNo());
		documentFileVO.setFileType("certified");
		
		UserManageVO userManageVO = new UserManageVO();
		userManageVO.setEmplNo(loginVO.getEmplyrNo());
		
		documentFileVO = (DocumentFileVO) documentFileService.selectDocumentArticle(documentFileVO);
		
		UserManageVO tmpVO = userManageService.selectEmpNo(userManageVO);
		
		if(tmpVO.getAtchFileId() == null || tmpVO.getAtchFileId().isEmpty()){
			userManageVO.setAtchFileId(documentFileVO.getAtchFileId());
			userManageService.updateAtchFileId(userManageVO);			
		}else{
			documentFileVO.setOrginFileId(tmpVO.getAtchFileId());
			documentFileService.updateAtchFileDetail(documentFileVO);
		}
			
		documentFileService.deleteDocumentArticle(documentFileVO);
		
		return "/mobile/com/document/certified";
	}*/
}