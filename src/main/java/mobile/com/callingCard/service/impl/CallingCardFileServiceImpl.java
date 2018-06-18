package mobile.com.callingCard.service.impl;

import java.util.List;

import javax.annotation.Resource;

import mobile.com.callingCard.service.CallingCardFileService;
import mobile.com.callingCard.service.CallingCardFileVO;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service("CallingCardFileService")
public class CallingCardFileServiceImpl extends EgovAbstractServiceImpl implements CallingCardFileService {
	
	@Resource(name = "CallingCardFileDAO")
    private CallingCardFileDAO callingCardFileDAO;
	
	@Override
	public CallingCardFileVO selectCallingCardArticle(CallingCardFileVO callingCardFileVO) throws Exception{
		return callingCardFileDAO.selectCallingCardArticle(callingCardFileVO);
	}
	
	public void deleteCallingCardArticle(CallingCardFileVO callingCardFileVO) throws Exception{
		callingCardFileDAO.deleteCallingCardArticle(callingCardFileVO);
	}
	
	@Override
	public String insertfile(CallingCardFileVO callingCardFileVO) throws Exception {
		String result = callingCardFileDAO.insertfile(callingCardFileVO);
		return result;
	}
	
	@Override
	public List<?> selectCallingCardName(CallingCardFileVO callingCardFileVO) throws Exception{
		return callingCardFileDAO.selectCallingCardName(callingCardFileVO);
	}
	
}