package mobile.com.receipt.service;

import java.util.List;

import egovframework.com.cmm.service.FileVO;
import mobile.com.receipt.service.ReceiptVO;

public interface ReceiptService {
	/*지출명세서 등록*/
	public String insertReceipt(ReceiptVO receiptVO) throws Exception;
	
	/*지출명세서 목록 조회*/
	List<?> selectReceiptArticle(ReceiptVO receiptVO) throws Exception;
	
	/*지출명세서 상세보기*/
	public ReceiptVO receiptView(ReceiptVO receiptVO) throws Exception;
	
	/*지출명세서 수정*/
	public void updateReceiptArticle(ReceiptVO receiptVO) throws Exception;
	
	/*지출명세서 삭제시 첨부파일 삭제(디테일 테이블*/
	public void deleteAtchFileDetail(ReceiptVO receiptVO) throws Exception;
	
	/*지출명세서 삭제시 첨부파일 삭제(마스터 테이블)*/
	public void deleteAtchFile(ReceiptVO receiptVO) throws Exception;
	
	/*지출명세서 삭제*/
	public void deleteReceipt(ReceiptVO receiptVO) throws Exception;
	
	/*첨부파일 삭제시 첨부파일ID제거*/
	public void deleteAtchFileId(FileVO fileVO) throws Exception;
}