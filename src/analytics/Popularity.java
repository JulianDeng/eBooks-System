package analytics;

import java.util.ArrayList;

public class Popularity {
	private ArrayList<BookVisit> view;
	private ArrayList<BookVisit> cart;
	private ArrayList<BookVisit> purchase;
	
	public Popularity(){
		this.view = new ArrayList<BookVisit>();
		view.add(new BookVisit("b001",0));
		this.cart = new ArrayList<BookVisit>();
		cart.add(new BookVisit("b001",0));
		this.purchase = new ArrayList<BookVisit>();
		purchase.add(new BookVisit("b001",0));
	}

	public ArrayList<BookVisit> getView() {
		return view;
	}

	public ArrayList<BookVisit> getCart() {
		return cart;
	}

	public ArrayList<BookVisit> getPurchase() {
		return purchase;
	}
	
	public void addBookToView(BookVisit view) {
		this.view.add(view);
	}
	
	public void addBookToCart(BookVisit cart) {
		this.cart.add(cart);
	}
	
	public void addBookToPurchase(BookVisit purchase) {
		this.purchase.add(purchase);
	}
	
	public void updateBookToView(String bid, int amount) {
		for(int i=0; i<view.size(); i++){
			if(view.get(i).getBid().equals(bid)){
				view.get(i).addQuantity(amount);
			}
		}
	}
	
	public void updateBookToCart(String bid, int amount) {
		for(int i=0; i<cart.size(); i++){
			if(cart.get(i).getBid().equals(bid)){
				cart.get(i).addQuantity(amount);
			}
		}
	}
	
	public void updateBookToPurchase(String bid, int amount) {
		for(int i=0; i<purchase.size(); i++){
			if(purchase.get(i).getBid().equals(bid)){
				purchase.get(i).addQuantity(amount);
			}
		}
	}
	
	public void updateBookToViewByIndex(int index, int amount) {
		view.get(index).addQuantity(amount);
	}
	
	public void updateBookToCartByIndex(int index, int amount) {
		cart.get(index).addQuantity(amount);
	}
	
	public void updateBookToPurchaseByIndex(int index, int amount) {
		purchase.get(index).addQuantity(amount);
	}
	
	
	
	/**
	 * Check if the list contains the specific books
	 * @param bid
	 * @param type 1 for view, 2 for cart, 3 for purchase
	 * @return If have then return index of the book in the list, else return -1
	 */
	public int checkIfContains(String bid, int type){
		if(type==1){
			for(int i=0; i<view.size(); i++){
				if(view.get(i).getBid().equals(bid)){
					return i;
				}
			}
			return -1; //if not found then return a invalid index
		}
		else if(type==2){
			for(int i=0; i<cart.size(); i++){
				if(cart.get(i).getBid().equals(bid)){
					return i;
				}
			}
			return -1;
		}
		else if(type==3){
			for(int i=0; i<purchase.size(); i++){
				if(purchase.get(i).getBid().equals(bid)){
					return i;
				}
			}
			return -1;
		}
		return -1;
	}
	
	
	/**
	 * Return the most popular viewed book
	 * @return
	 */
	public BookVisit mostPopularViewedBook() {
		BookVisit bookvisit=view.get(0);
		BookVisit temp;
		for(int i=0; i<view.size(); i++){
			temp = view.get(i);
			if(temp.getQuantity()>bookvisit.getQuantity()){
				bookvisit = temp;
			}
		}
		return bookvisit;
	}
	
	/**
	 * Return the most popular carted book
	 * @return
	 */
	public BookVisit mostPopularCartedBook() {
		BookVisit bookvisit=cart.get(0);
		BookVisit temp;
		for(int i=0; i<cart.size(); i++){
			temp = cart.get(i);
			if(temp.getQuantity()>bookvisit.getQuantity()){
				bookvisit = temp;
			}
		}
		return bookvisit;
	}
	
	/**
	 * Return the most popular purchased book
	 * @return
	 */
	public BookVisit mostPopularPurchasedBook() {
		BookVisit bookvisit=purchase.get(0);
		BookVisit temp;
		for(int i=0; i<purchase.size(); i++){
			temp = purchase.get(i);
			if(temp.getQuantity()>bookvisit.getQuantity()){
				bookvisit = temp;
			}
		}
		return bookvisit;
	}
	
}
