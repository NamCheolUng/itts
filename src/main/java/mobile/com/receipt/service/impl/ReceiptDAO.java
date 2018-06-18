package mobile.com.receipt.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import mobile.com.receipt.service.ReceiptVO;
import egovframework.com.cmm.service.FileVO;
import egovframework.com.cmm.service.impl.EgovComAbstractDAO;

@Repository("ReceiptDAO")
public class ReceiptDAO extends EgovComAbstractDAO {
	
    public String insertReceipt(ReceiptVO receiptVO)throws Exception{
        return (String)insert("receiptDAO.insertReceipt", receiptVO);
    }
    
	public List<?> selectReceiptArticle(ReceiptVO receiptVO) throws Exception{
		return list("receiptDAO.selectReceiptArticle", receiptVO);
	}
	
	public ReceiptVO receiptView(ReceiptVO receiptVO) throws Exception{
		return (ReceiptVO)select("receiptDAO.receiptView", receiptVO);
	}
	
    public void updateReceiptArticle(ReceiptVO receiptVO) throws Exception {
	update("receiptDAO.updateReceiptArticle", receiptVO);
    }

    public void deleteAtchFileDetail(ReceiptVO receiptVO) throws Exception {
	delete("receiptDAO.deleteAtchFileDetail", receiptVO);
    }
    
    public void deleteAtchFile(ReceiptVO receiptVO) throws Exception {
    	delete("receiptDAO.deleteAtchFile", receiptVO);
    }
    
    public void deleteReceipt(ReceiptVO receiptVO) throws Exception {
    	delete("receiptDAO.deleteReceipt", receiptVO);
    }
    
    public void deleteAtchFileId(FileVO fileVO) throws Exception {
    	update("receiptDAO.deleteAtchFileId", fileVO);
    }
}