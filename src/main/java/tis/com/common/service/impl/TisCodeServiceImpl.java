package tis.com.common.service.impl;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import tis.com.common.service.TisCodeService;
import tis.com.common.service.TisCodeVO;
import egovframework.com.cmm.service.FileVO;
import egovframework.rte.psl.dataaccess.util.EgovMap;

@Service("TisCodeService")
public class TisCodeServiceImpl implements TisCodeService {

	@Resource(name = "TisCodeDAO")
	private TisCodeDAO tisCodeDAO;

	@Override
	public List<?> selectCodeDetailList(String codeId) throws Exception {
		return tisCodeDAO.selectCodeDetailList(codeId);
	}

	@Override
	public List<?> selectCodeDetailByVOList(TisCodeVO vo) throws Exception {
		return tisCodeDAO.selectCodeDetailByVOList(vo);
	}

	@Override
	public String selectCodeNameOfCodeDetail(String codeId, String subCodeId)
			throws Exception {
		// TODO Auto-generated method stub

		List<?> codeList = this.tisCodeDAO.selectCodeDetailList(codeId);
		
		String codeNm = null;
		
		Iterator<?> iter = codeList.iterator();
		while (iter.hasNext()) {
			
			EgovMap tmp = (EgovMap)iter.next();
			
			if(tmp.get("code").equals(subCodeId)){
				codeNm = tmp.get("codeNm").toString();
				break;
			}
		}
		
		return codeNm;
	}
	
	
}
