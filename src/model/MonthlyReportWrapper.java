package model;

import java.util.ArrayList;

import javax.xml.bind.annotation.*;

import bean.CartItemBean;

@XmlType(propOrder={"reportMonth", "orderItemList"})
public class MonthlyReportWrapper {
	@XmlElement(name="reportMonth")
	private String month;
	@XmlElement(name="orderItemList")
	private ArrayList<OrderItemWrapper> books;
	
	public MonthlyReportWrapper() {
		super();
	}
	
	public MonthlyReportWrapper(String month, ArrayList<OrderItemWrapper> books) {
		super();
		this.month = month;
		this.books = books;
	}

	public String getMonth() {
		return month;
	}

	public ArrayList<OrderItemWrapper> getBooks() {
		return books;
	}
}
