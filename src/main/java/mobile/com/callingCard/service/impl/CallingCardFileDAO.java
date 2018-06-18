package mobile.com.callingCard.service.impl;

import java.util.List;

import mobile.com.callingCard.service.CallingCardFileVO;

import org.springframework.stereotype.Repository;

import egovframework.com.cmm.service.impl.EgovComAbstractDAO;

@Repository("CallingCardFileDAO")
public class CallingCardFileDAO extends EgovComAbstractDAO {
	
	public CallingCardFileVO selectCallingCardArticle(CallingCardFileVO callingCardFileVO) throws Exception{
		return (CallingCardFileVO) select("callingCardFileDAO.selectCallingCardArticle", callingCardFileVO);
	}
	
	public void deleteCallingCardArticle(CallingCardFileVO callingCardFileVO) throws Exception{
		delete("callingCardFileDAO.deleteCallingCardArticle",callingCardFileVO);
	}
	
    public String insertfile(CallingCardFileVO callingCardFileVO)throws Exception{
        return (String)insert("callingCardFileDAO.insertfile", callingCardFileVO);
    }
    
	public List<?> selectCallingCardName(CallingCardFileVO callingCardFileVO) throws Exception{
		return list("callingCardFileDAO.selectCallingCardName", callingCardFileVO);
	}
}