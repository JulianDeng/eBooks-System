package bean;

import java.util.ArrayList;

public class OrderBean {
	private ArrayList<CartItemBean> items;
	private int orderID;
	private String status;
	private AddressBean address;
	private int uid;
	
	public OrderBean(){
		
	}
	
	public OrderBean(ArrayList<CartItemBean> items, int orderID, String status, AddressBean address, int uid) {
		super();
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

	public int getOrderID() {
		return orderID;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public AddressBean getAddress() {
		return address;
	}

	public int getUid() {
		return uid;
	}
	
}
