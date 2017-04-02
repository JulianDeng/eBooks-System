package bean;

import java.util.ArrayList;

public class CartBean {
	private ArrayList<CartItemBean> items;

	public CartBean(){
		super();
	}

	public CartBean(CartItemBean items) {
		super();
		this.items = new ArrayList<CartItemBean>();
		this.items.add(items);
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
	
	public void addItems(CartItemBean items) {
		this.items.add(items);
	}
	
	public void deleteItemByIndex(int index) {
		if(index < items.size() && index >= 0){
			this.items.remove(index);
		}
	}
	
	public void deleteItem(CartItemBean item) {
		this.items.remove(item);
	}
}
