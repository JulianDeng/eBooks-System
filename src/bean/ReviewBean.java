package bean;

import java.util.*;

public class ReviewBean {
	private String bid;
	private String review;
	private String date;
	
	public ReviewBean(){
		
	}
	
	public ReviewBean(String bid, String review, String date) {
		super();
		this.bid = bid;
		this.review = review;
		this.date = date;
	}

	public String getBid() {
		return bid;
	}

	public String getReview() {
		return review;
	}

	public String getDate() {
		return date;
	}
}
