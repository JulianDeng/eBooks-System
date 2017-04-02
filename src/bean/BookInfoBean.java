package bean;

import java.util.ArrayList;

public class BookInfoBean {
	private BookBean item;
	private ArrayList<ReviewBean> review;
	private double rating;
	
	public BookInfoBean(){
		
	}
	
	public BookInfoBean(BookBean item, ArrayList<ReviewBean> review, double rating) {
		super();
		this.item = item;
		this.review = review;
		this.rating = rating;
	}
	
	public BookBean getItem() {
		return item;
	}
	public ArrayList<ReviewBean> getReview() {
		return review;
	}
	public double getRating() {
		return rating;
	}
	
	
	
}
