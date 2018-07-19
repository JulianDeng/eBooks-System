package model;

import java.util.*;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import bean.OrderBean;

@XmlRootElement(name="orderList")
@XmlAccessorType(XmlAccessType.FIELD)
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
