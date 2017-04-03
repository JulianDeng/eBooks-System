package analytics;

/**
 * When user view, put into cart, purchase, the quantity will increase
 * @author Administrator
 *
 */
public class BookVisit {
	private String bid;
	private int quantity;
	
	public BookVisit(String bid, int quantity){
		this.bid = bid;
		this.quantity = quantity;
	}

	public String getBid() {
		return bid;
	}

	public int getQuantity() {
		return quantity;
	}
	
	public void addOne() {
		this.quantity += 1;
	}
	
	public void addQuantity(int quantity) {
		if(quantity<0) return;
		this.quantity += quantity;
	}
}
