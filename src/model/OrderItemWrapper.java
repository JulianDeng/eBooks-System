package model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import bean.BookBean;

/**
 * Book items which contain quantity and total price and date of this order
 * This is the class for generate report
 * @author Administrator
 *
 */
@XmlType(propOrder={"date", "bookInfo"})
public class OrderItemWrapper {
	@XmlElement(name="bookInfo")
	private BookBean item;
	@XmlElement
	private String date;
	
	public OrderItemWrapper() {
		super();
	}
	public OrderItemWrapper(BookBean item, String date) {
		super();
		this.item = item;
		this.date = date;
	}
	
	public BookBean getItem() {
		return item;
	}
	
	public String getDate() {
		return date;
	}
	
}
