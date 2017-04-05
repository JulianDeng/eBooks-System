package model;

import java.util.ArrayList;

import javax.xml.bind.annotation.*;

import bean.CartItemBean;

@XmlType(propOrder={"month", "books"})
public class MonthlyVisitReportWrapper {
	@XmlElement(name="reportMonth")
	private String month;
	@XmlElement(name="orderItemList")
	private ArrayList<OrderItemWrapper> books;
	
	public MonthlyVisitReportWrapper() {
		super();
	}
	
	public MonthlyVisitReportWrapper(String month, ArrayList<OrderItemWrapper> books) {
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
