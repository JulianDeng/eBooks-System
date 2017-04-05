package bean;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder={"orderID", "uid", "status", "address"})
public class OrderBean {
	
	private ArrayList<CartItemBean> items;
	@XmlElement(name="orderId")
	private int orderID;
	@XmlElement
	private String status;
	@XmlElement
	private AddressBean address;
	@XmlElement(name="userId")
	private int uid;
	
	
	public OrderBean(){
		
	}
	
	public OrderBean(ArrayList<CartItemBean> items, int orderID, String status, AddressBean address, int uid) {
		this.items = items;
		this.orderID = orderID;
		this.status = status;
		this.address = address;
		this.uid = uid;
	}

	public ArrayList<CartItemBean> getItems() {
		return items;
	}

	public boolean addItems(CartItemBean items) {
		return this.items.add(items);
	}
	
	public boolean deleteItems(CartItemBean items) {
		return this.items.remove(items);
	}
	@XmlTransient
	public int getOrderID() {
		return orderID;
	}
	@XmlTransient
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	@XmlTransient
	public AddressBean getAddress() {
		return address;
	}
	@XmlTransient
	public int getUid() {
		return uid;
	}
	
}
