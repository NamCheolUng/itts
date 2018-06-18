package tis.com.financial.purchase.service;

import java.util.List;

import tis.com.common.service.DefaultVO;


public class PurchaseDetailVO extends DefaultVO {
	
	/** 매입상세ID */
	private String purchaseDetailId;
	
	/** 매입ID */
	private String purchaseId;
	
	/** 품명 */
	private String itemNm;
	
	/** 수량 */
	private int qty;
	
	/** 단가 */
	private int price;
	
	/** 공급가액(수량*단가) */
	private int splyPrice;
	
	/** 세액 */
	private String surtax;
	
	private List<PurchaseDetailVO> list;

	public String getPurchaseDetailId() {
		return purchaseDetailId;
	}

	public void setPurchaseDetailId(String purchaseDetailId) {
		this.purchaseDetailId = purchaseDetailId;
	}

	public String getPurchaseId() {
		return purchaseId;
	}

	public void setPurchaseId(String purchaseId) {
		this.purchaseId = purchaseId;
	}

	public String getItemNm() {
		return itemNm;
	}

	public void setItemNm(String itemNm) {
		this.itemNm = itemNm;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getSplyPrice() {
		return splyPrice;
	}

	public void setSplyPrice(int splyPrice) {
		this.splyPrice = splyPrice;
	}

	public String getSurtax() {
		return surtax;
	}

	public void setSurtax(String surtax) {
		this.surtax = surtax;
	}

	public List<PurchaseDetailVO> getList() {
		return list;
	}

	public void setList(List<PurchaseDetailVO> list) {
		this.list = list;
	}
}
