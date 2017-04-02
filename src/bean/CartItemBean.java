package bean;

public class CartItemBean {
	private BookBean item;
	private int quantity;
	
	public CartItemBean() {
		super();
	}
	public CartItemBean(BookBean item, int quantity) {
		super();
		this.item = item;
		this.quantity = quantity;
	}
	
	public BookBean getItem() {
		return item;
	}
	public void setItem(BookBean item) {
		this.item = item;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public void addQuantity(int quantity) {
		this.quantity += quantity;
	}
}
