package model;

import java.util.*;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import bean.OrderBean;

@XmlRootElement(name="orderList")
public class OrderWrapper {
	@XmlElement(name="orderItem")
	private ArrayList<OrderBean> items;

	public OrderWrapper() {
		super();
	}
	
	public OrderWrapper(ArrayList<OrderBean> items) {
		super();
		this.items = items;
	}

	public ArrayList<OrderBean> getItems() {
		return items;
	}
}
