package bean;

import java.util.ArrayList;

public class CartBean {
	private ArrayList<CartItemBean> items;
	
	public CartBean(){
		super();
		this.items = new ArrayList<CartItemBean>();

	}

	public CartBean(CartItemBean items) {
		super();
		this.items = new ArrayList<CartItemBean>();
		this.items.add(items);
	}

	public ArrayList<CartItemBean> getItems() {
		return items;
	}
	
	public CartItemBean getItemByIndex(int index) {
		return items.get(index);
	}
	
	public int contains(CartItemBean item) {
		int result = -1;
		for(int i=0; i<items.size(); i++){
			if(items.get(i).equals(item)){
				result=i;
				break;
			}
		}
		return result;
	}

	public int size() {
		return this.items.size();
	}
	
	public void addOneExistItemByBid(String bid) {
		int size = this.items.size();
		for(int i=0; i<size; i++){
			if(this.items.get(i).getItem().getBid().equals(bid)){
				this.items.get(i).addQuantity(1);
				return;
			}
		}
	}
	public void removeOneExistItemByBid(String bid) {
		int size = this.items.size();
		for(int i=0; i<size; i++){
			if(this.items.get(i).getItem().getBid().equals(bid)){
				this.items.get(i).minusQuantity(1);
				return;
			}
		}
	}
	
	/**
	 * If this book already inside shopping cart, return false, else return true 
	 * @param items
	 * @return
	 */
	public boolean addItems(CartItemBean items) {
		int size = this.items.size();
		for(int i=0; i<size; i++){
			//if this book already inside shopping cart
			if(this.items.get(i).getItem().isEqual(items.getItem())){
				//this.items.get(i).addQuantity(items.getQuantity());
				return false;
			}
		}
		this.items.add(items);
		return true;
	}
	
	public void deleteItemByIndex(int index) {
		if(index < items.size() && index >= 0){
			this.items.remove(index);
		}
	}
	
	public void deleteItem(CartItemBean item) {
		this.items.remove(item);
	}
	
	public void deleteItemByBid(String bid) {
		int size = this.items.size();
		for(int i=0; i<size; i++){
			if(this.items.get(i).getItem().getBid().equals(bid)){
				this.items.remove(i);
				return;
			}
		}
	}
	
}
