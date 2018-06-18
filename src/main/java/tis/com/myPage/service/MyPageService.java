package tis.com.myPage.service;

import java.util.List;

import tis.com.common.service.DefaultVO;
import egovframework.rte.psl.dataaccess.util.EgovMap;

public interface MyPageService {

	public List<?> selectMyPageScheduler(DefaultVO defaultVO) throws Exception;
	
	public List<?> selectMySchduleList(DefaultVO defaultVO) throws Exception;
	
	public List<?> selectSchdulList(DefaultVO defaultVO) throws Exception;
	
	public EgovMap selectPrgrsAllCnt(DefaultVO defaultVO) throws Exception;
}
