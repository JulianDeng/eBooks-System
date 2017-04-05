package bean;

import java.util.ArrayList;

public class CartBean {
	private ArrayList<CartItemBean> items;
	private int total;
	
	public CartBean(){
		this.items = new ArrayList<CartItemBean>();
		this.total = 0;
	}

	public CartBean(CartItemBean items) {
		this.items = new ArrayList<CartItemBean>();
		this.items.add(items);
	}

	public ArrayList<CartItemBean> getItems() {
		return items;
	}
	
	public CartItemBean getItemByIndex(int index) {
		return items.get(index);
	}
	
	public int getTotal(){
		return this.total;
	}
	
	public void setTotal(int amount){
		this.total = amount;
	}
	
	public int contains(CartItemBean item) {
		int result = -1;
		for(int i=0; i<items.size(); i++){
			if(items.get(i).getItem().equals(item.getItem())){
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
	 * @param item
	 * @return
	 */
	public void addItems(CartItemBean item) {
		if(this.contains(item) != -1){
			this.addOneExistItemByBid(this.items.get(this.contains(item)).getItem().getBid());
		}else{
			this.items.add(item);			
		}
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
	
	public void updateItemByid(String bid, int quantity){
		for(CartItemBean cItem : this.items){
			if(cItem.getItem().getBid().equals(bid)){
				cItem.setQuantity(quantity);
			}
		}
	}
	
	
	public void computeTotal(){
		this.total = 0;
		for(CartItemBean cItem : this.items){
			this.total += cItem.getPrice();
		}
	}
	
}
