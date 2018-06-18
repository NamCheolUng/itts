package mobile.com.document.service.impl;

import java.util.List;
import javax.annotation.Resource;
import mobile.com.document.service.DocumentFileService;
import mobile.com.document.service.DocumentFileVO;
import org.springframework.stereotype.Service;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service("DocumentFileService")
public class DocumentFileServiceImpl extends EgovAbstractServiceImpl implements DocumentFileService {
	
	@Resource(name = "DocumentFileDAO")
    private DocumentFileDAO documentFileDAO;
	
	@Override
	public DocumentFileVO selectDocumentArticle(DocumentFileVO documentFileVO) throws Exception{
		return documentFileDAO.selectDocumentArticle(documentFileVO);
	}

	@Override
	public String insertfile(DocumentFileVO documentFileVO) throws Exception {
		String result = documentFileDAO.insertfile(documentFileVO);
		return result;
	}
	
	public void deleteDocumentArticle(DocumentFileVO documentFileVO) throws Exception{
		documentFileDAO.deleteDocumentArticle(documentFileVO);
	}
	
	public void updateAtchFileDetail(DocumentFileVO documentFileVO) throws Exception{
		documentFileDAO.updateAtchFileDetail(documentFileVO);
	}
	
	
}