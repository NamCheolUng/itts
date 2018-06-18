package mobile.com.document.service.impl;

import java.util.List;

import mobile.com.document.service.DocumentFileVO;

import org.springframework.stereotype.Repository;

import egovframework.com.cmm.service.impl.EgovComAbstractDAO;

@Repository("DocumentFileDAO")
public class DocumentFileDAO extends EgovComAbstractDAO {
	
	public DocumentFileVO selectDocumentArticle(DocumentFileVO documentFileVO) throws Exception{
		return (DocumentFileVO) select("documentFileDAO.selectDocumentArticle", documentFileVO);
	}
	
    public String insertfile(DocumentFileVO documentFileVO)throws Exception{
        return (String)insert("documentFileDAO.insertfile", documentFileVO);
    }
    
    public void deleteDocumentArticle(DocumentFileVO documentFileVO) throws Exception{
    	delete("documentFileDAO.deleteDocumentArticle", documentFileVO);		
	}
    
    public void updateAtchFileDetail(DocumentFileVO documentFileVO) throws Exception{
    	update("documentFileDAO.updateAtchFileDetail", documentFileVO);		
	}
    
}