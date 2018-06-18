package mobile.com.callingCard.service;

import java.util.List;

public interface CallingCardFileService {
	/*본인증빙서류 조회*/
	public CallingCardFileVO selectCallingCardArticle(CallingCardFileVO callingCardFileVO) throws Exception;
	
	/*증빙서류등록*/
	public String insertfile(CallingCardFileVO callingCardFileVO) throws Exception;
	
	/*담당자이름 조회*/
	List<?> selectCallingCardName(CallingCardFileVO callingCardFileVO) throws Exception;
	
	public void deleteCallingCardArticle(CallingCardFileVO callingCardFileVO) throws Exception;
	
}