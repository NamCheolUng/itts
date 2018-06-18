package mobile.com.receipt.service.impl;

import java.util.List;

import javax.annotation.Resource;

import mobile.com.receipt.service.ReceiptService;
import mobile.com.receipt.service.ReceiptVO;

import org.springframework.stereotype.Service;

import egovframework.com.cmm.service.FileVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;

@Service("ReceiptService")
public class ReceiptServiceImpl extends EgovAbstractServiceImpl implements ReceiptService {
	/** egovCmtManageIdGnrService */
	@Resource(name = "exphIdGnrService")
	private EgovIdGnrService exphIdGnrService;
	
	@Resource(name = "ReceiptDAO")
    private ReceiptDAO receiptDAO;
		
	@Override
	public String insertReceipt(ReceiptVO receiptVO) throws Exception {
		String histId = exphIdGnrService.getNextStringId();
		receiptVO.setExpHistId(histId);
		
		String result = receiptDAO.insertReceipt(receiptVO);
		return result;
	}
	
	@Override
	public List<?> selectReceiptArticle(ReceiptVO receiptVO) throws Exception{
		return receiptDAO.selectReceiptArticle(receiptVO);
	}
	
	@Override
	public ReceiptVO receiptView(ReceiptVO receiptVO) throws Exception{
		return receiptDAO.receiptView(receiptVO);
	}
	
	@Override
	public void updateReceiptArticle(ReceiptVO receiptVO) throws Exception{
		receiptDAO.updateReceiptArticle(receiptVO);
	}
	
	@Override
	public void deleteAtchFileDetail(ReceiptVO receiptVO) throws Exception{
		receiptDAO.deleteAtchFileDetail(receiptVO);
	}
	
	@Override 
	public void deleteAtchFile(ReceiptVO receiptVO) throws Exception{
		receiptDAO.deleteAtchFile(receiptVO);
	}
	
	@Override
	public void deleteReceipt(ReceiptVO receiptVO) throws Exception{
		receiptDAO.deleteReceipt(receiptVO);
	}
	
	@Override
	public void deleteAtchFileId(FileVO fileVO) throws Exception{
		receiptDAO.deleteAtchFileId(fileVO);
	}
}