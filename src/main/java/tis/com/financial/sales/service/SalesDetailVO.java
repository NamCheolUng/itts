package tis.com.financial.sales.service;

import java.util.List;

import tis.com.common.service.DefaultVO;


public class SalesDetailVO extends DefaultVO {
	
	/** 매출상세ID */
	private String salesDetailId;
	
	/** 매출ID */
	private String salesId;
	
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
	
	private List<SalesDetailVO> list;

	public String getSalesDetailId() {
		return salesDetailId;
	}

	public void setSalesDetailId(String salesDetailId) {
		this.salesDetailId = salesDetailId;
	}

	public String getSalesId() {
		return salesId;
	}

	public void setSalesId(String salesId) {
		this.salesId = salesId;
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

	public List<SalesDetailVO> getList() {
		return list;
	}

	public void setList(List<SalesDetailVO> list) {
		this.list = list;
	}
}
