package mobile.com.document.service;

import java.util.List;

public interface DocumentFileService {
	/*본인증빙서류 조회*/
	public DocumentFileVO selectDocumentArticle(DocumentFileVO documentFileVO) throws Exception;
	
	/*증빙서류등록*/
	public String insertfile(DocumentFileVO documentFileVO) throws Exception;
	
	//초기화를 위한 해당 첨부파일 삭제
	public void deleteDocumentArticle(DocumentFileVO documentFileVO) throws Exception;
	
	public void updateAtchFileDetail(DocumentFileVO documentFileVO) throws Exception;
	
	
}