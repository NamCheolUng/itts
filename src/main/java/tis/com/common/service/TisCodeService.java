package tis.com.common.service;

import java.util.List;

public interface TisCodeService {
	
	public List<?> selectCodeDetailList(String codeId) throws Exception;
	
	public List<?> selectCodeDetailByVOList(TisCodeVO vo) throws Exception;
	
	public String selectCodeNameOfCodeDetail(String codeId, String subCodeId) throws Exception;
}
