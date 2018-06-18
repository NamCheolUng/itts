package mobile.com.callingCard.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import mobile.com.callingCard.service.CallingCardFileService;
import mobile.com.callingCard.service.CallingCardFileVO;
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
import egovframework.com.cop.ncm.service.EgovNcrdManageService;
import egovframework.com.cop.ncm.service.NameCardVO;
import egovframework.com.uss.umt.service.UserManageVO;

@Controller
public class CallingCardController {
	@Resource(name = "CallingCardFileService")
	private CallingCardFileService callingCardFileService;
	
	// 첨부파일 관련
	@Resource(name="EgovFileMngService")
	private EgovFileMngService fileMngService;
	
	@Resource(name = "EgovFileMngUtil")
	protected EgovFileMngUtil fileUtil;
	
    @Resource(name = "EgovFileMngService")
    private EgovFileMngService fileService;
    
    @Resource(name = "EgovNcrdManageService")
    private EgovNcrdManageService ncrdService;
    
    @Resource(name = "ReceiptService")
	private ReceiptService receiptService;
    
	/*명함*/
	@RequestMapping("/mobile/com/callingCard/callingCard.do")
	public String callingCard(@ModelAttribute("callingCardFileVO") CallingCardFileVO callingCardFileVO, Model model) throws Exception {
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		if (!isAuthenticated) {
			return "redirect:/mobile/com/inc/login.do";
		}
		LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		callingCardFileVO.setEmplNo(loginVO.getEmplyrNo());
		callingCardFileVO.setFileType("callingCard");
		callingCardFileVO = callingCardFileService.selectCallingCardArticle(callingCardFileVO);
		//model.addAttribute("result", callingCardFileVO);

		if(callingCardFileVO != null){
			ReceiptVO rvo = new ReceiptVO();
			rvo.setAtchFileId(callingCardFileVO.getAtchFileId());
			receiptService.deleteAtchFileDetail(rvo);
			receiptService.deleteAtchFile(rvo);
			
			callingCardFileService.deleteCallingCardArticle(callingCardFileVO);
			callingCardFileVO.setAtchFileId(null);
		}
		NameCardVO nvo = new NameCardVO();
		nvo.setEmplyrId(loginVO.getUniqId());
		model.addAttribute("ncrd", ncrdService.selectNcrdItemListOfUser(nvo));
		
		model.addAttribute("callingCardFileVO", callingCardFileVO);
		return "/mobile/com/callingCard/callingCard";
	}
	
	/*명함증명서류저장*/
	@RequestMapping("/mobile/com/callingCard/callingCardSave.do")
	public String documentSave(@ModelAttribute("ncrdVO") NameCardVO ncrdVO, 
								final MultipartHttpServletRequest multiRequest) throws Exception {
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		if (!isAuthenticated) {
			return "redirect:/mobile/com/inc/login.do";
		}
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
		ncrdService.updateNcrdFileId(ncrdVO);	
		return "redirect:/mobile/com/callingCard/callingCard.do";
	}
	
	
}