package bean;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Book items which contain quantity and total price
 * It will be stored on CartBean and MonthReportBean as single book item
 * @author Administrator
 *
 */

public class CartItemBean {
	private BookBean item;
	private int quantity;
	private int price;
  
	
	public CartItemBean() {
		super();
	}
	public CartItemBean(BookBean item, int quantity) {
		super();
		this.item = item;
		this.quantity = quantity;
		this.price = item.getPrice()*quantity;
	}
	
	public BookBean getItem() {
		return item;
	}
	public void setItem(BookBean item) {
		this.item = item;
		this.price = item.getPrice()*this.quantity;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
		this.price = this.item.getPrice()*quantity;
	}
	public void addQuantity(int quantity) {
		this.quantity += quantity;
		this.price = this.item.getPrice()*this.quantity;
	}
	public void minusQuantity(int quantity) {
		this.quantity -= quantity;
		if(this.quantity < 0){
			this.quantity = 0;
			this.price = 0;
		}else{
			this.price = this.item.getPrice()*this.quantity;
		}
	}
	
	
	public int getPrice() {
		return price;
	}
	
}
