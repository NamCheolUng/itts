package mobile.com.receipt.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import mobile.com.receipt.service.ReceiptService;
import mobile.com.receipt.service.ReceiptVO;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import tis.com.common.service.TisCodeService;
import tis.com.epay.service.EpayDraftInfoService;
import tis.com.epay.service.EpayTaskPersonVO;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.EgovFileMngService;
import egovframework.com.cmm.service.EgovFileMngUtil;
import egovframework.com.cmm.service.FileVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;

@Controller
public class ReceiptController {
	
	@Resource(name = "ReceiptService")
	private ReceiptService receiptService;
	
	// 첨부파일 관련
	@Resource(name="EgovFileMngService")
	private EgovFileMngService fileMngService;
	
	@Resource(name = "EgovFileMngUtil")
	protected EgovFileMngUtil fileUtil;
	
	@Resource(name = "epayDraftInfoService")
	private EpayDraftInfoService epayDraftInfoService;
	
	@Resource(name = "TisCodeService")
	private TisCodeService tisCodeService;
	
	/*영수증저장화면*/
	@RequestMapping("/mobile/com/receipt/receiptInsert.do")
	public String receiptInsert(Model model) throws Exception {
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		
		if (!isAuthenticated) {
			return "redirect:/mobile/com/inc/login.do";
		}
		
		EpayTaskPersonVO epayTaskPersonVO = new EpayTaskPersonVO();
		epayTaskPersonVO.setEmplNo(loginVO.getEmplyrNo());
		epayTaskPersonVO.setSttus("1");

		model.addAttribute("taskList", this.epayDraftInfoService.selectTaskList(epayTaskPersonVO)); // 기안자가 과제 담당자로 속한 과제 목록 전달
		model.addAttribute("expSubjList", this.tisCodeService.selectCodeDetailList("COM501")); // 계정과목 코드 조회

		return "/mobile/com/receipt/receiptInsert";
	}
	
	/*영수증저장*/
	@RequestMapping("/mobile/com/receipt/receiptSave.do")
	public String receiptSave( @ModelAttribute("receiptVO") ReceiptVO receiptVO,
								final MultipartHttpServletRequest multiRequest	) throws Exception {
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		if (!isAuthenticated) {
			return "redirect:/mobile/com/inc/login.do";
		}
		
		//첨부파일 저장
		List<FileVO> result = null;
		String atchFileId = "";
		final Map<String, MultipartFile> files = multiRequest.getFileMap();
				
		if (!files.isEmpty()) {
			result = fileUtil.parseFileInf(files, "RCT_", 0, "", "Globals.fileStorePathcallingCard");
			atchFileId = fileMngService.insertFileInfs(result);

		}	
		receiptVO.setAtchFileId(atchFileId);
		receiptService.insertReceipt(receiptVO);
		
		return "redirect:/mobile/com/receipt/receiptList.do";
	}
	
	/*영수증리스트화면*/
	@RequestMapping("/mobile/com/receipt/receiptList.do")
	public String receiptList( @ModelAttribute("receiptVO") ReceiptVO receiptVO, Model model) throws Exception {
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		
		if (!isAuthenticated) {
			return "redirect:/mobile/com/inc/login.do";
		}
		receiptVO.setEmplNo(loginVO.getEmplyrNo());
		List<?> result = receiptService.selectReceiptArticle(receiptVO);
		model.addAttribute("result", result);
		
		EpayTaskPersonVO epayTaskPersonVO = new EpayTaskPersonVO();
		epayTaskPersonVO.setEmplNo(loginVO.getEmplyrNo());
		epayTaskPersonVO.setSttus("1");

		model.addAttribute("taskList", this.epayDraftInfoService.selectTaskList(epayTaskPersonVO)); // 기안자가 과제 담당자로 속한 과제 목록 전달
		model.addAttribute("expSubjList", this.tisCodeService.selectCodeDetailList("COM501")); // 계정과목 코드 조회
		model.addAttribute("receiptVO", receiptVO);
		
		return "/mobile/com/receipt/receiptList";
	}
	
	/*영수증상세보기*/
	@RequestMapping("/mobile/com/receipt/receiptView.do")
	public String receiptView( @ModelAttribute("receiptVO") ReceiptVO receiptVO, Model model ) throws Exception {
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		if (!isAuthenticated) {
			return "redirect:/mobile/com/inc/login.do";
		}	
		
		receiptVO = receiptService.receiptView(receiptVO);
		
		model.addAttribute("receiptVO",receiptVO);
		
		EpayTaskPersonVO epayTaskPersonVO = new EpayTaskPersonVO();
		epayTaskPersonVO.setEmplNo(loginVO.getEmplyrNo());
		epayTaskPersonVO.setSttus("1");

		model.addAttribute("taskList", this.epayDraftInfoService.selectTaskList(epayTaskPersonVO)); // 기안자가 과제 담당자로 속한 과제 목록 전달
		model.addAttribute("expSubjList", this.tisCodeService.selectCodeDetailList("COM501")); // 계정과목 코드 조회
		
		return "/mobile/com/receipt/receiptView";
	}
	
	/*영수증수정페이지*/
	@RequestMapping("/mobile/com/receipt/receiptModify.do")
	public String receiptModify( @ModelAttribute("receiptVO") ReceiptVO receiptVO, Model model ) throws Exception {
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		if (!isAuthenticated) {
			return "redirect:/mobile/com/inc/login.do";
		}
		
		receiptVO = receiptService.receiptView(receiptVO);
		
		model.addAttribute("receiptVO",receiptVO);
		
		EpayTaskPersonVO epayTaskPersonVO = new EpayTaskPersonVO();
		epayTaskPersonVO.setEmplNo(loginVO.getEmplyrNo());
		epayTaskPersonVO.setSttus("1");

		model.addAttribute("taskList", this.epayDraftInfoService.selectTaskList(epayTaskPersonVO)); // 기안자가 과제 담당자로 속한 과제 목록 전달
		model.addAttribute("expSubjList", this.tisCodeService.selectCodeDetailList("COM501")); // 계정과목 코드 조회
		
		model.addAttribute("receiptVO", receiptVO);
		return "/mobile/com/receipt/receiptModify";
	}
	
	/*영수증수정*/
	@RequestMapping("/mobile/com/receipt/receiptUpdate.do")
	public String receiptUpdate( @ModelAttribute("receiptVO") ReceiptVO receiptVO, Model model, final MultipartHttpServletRequest multiRequest ) throws Exception {
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		if (!isAuthenticated) {
			return "redirect:/mobile/com/inc/login.do";
		}
		LoginVO loginVO = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		final Map<String, MultipartFile> files = multiRequest.getFileMap();
		String atchFileId = receiptVO.getAtchFileId();
		
		List<FileVO> result = null;
		// 첨부 파일이 있을 때만 동작
				if (!files.isEmpty()) {
					String tmpFileId="";
					// 기존 첨부 파일에 대한 처리
					if("".equals(atchFileId)) {
						result = fileUtil.parseFileInf(files, "RCT_", 0, "", "Globals.fileStorePathcallingCard");
						tmpFileId = fileMngService.insertFileInfs(result);
						receiptVO.setAtchFileId(tmpFileId);
					} else {
						FileVO fvo = new FileVO();
						fvo.setAtchFileId(atchFileId);					
						result = fileUtil.parseFileInf(files, "RCT_", 0, atchFileId, "Globals.fileStorePathcallingCard");
						
						fileMngService.updateFileInfsMobile(result);
					}
				}
		receiptVO.setEmplNo(loginVO.getEmplyrNo());
		receiptService.updateReceiptArticle(receiptVO);
		
		return "forward:/mobile/com/receipt/receiptView.do";
	}
	
	/*영수증삭제*/
	@RequestMapping("/mobile/com/receipt/receiptDelete.do")
	public String receiptDelete( @ModelAttribute("receiptVO") ReceiptVO receiptVO, Model model ) throws Exception {
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		if (!isAuthenticated) {
			return "redirect:/mobile/com/inc/login.do";
		}
		
		/*첨부파일제거*/
		receiptService.deleteAtchFileDetail(receiptVO);
		receiptService.deleteAtchFile(receiptVO);
		
		/*지출명세서기록 삭제*/
		receiptService.deleteReceipt(receiptVO);
		return "redirect:/mobile/com/receipt/receiptList.do";
	}
}