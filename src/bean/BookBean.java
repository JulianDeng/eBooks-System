package bean;

import javax.xml.bind.annotation.*;

@XmlType(propOrder={"bid", "title", "price", "category"})
public class BookBean {
	@XmlElement
	private String bid;
	@XmlElement
	private String title;
	@XmlElement
	private int price;
	@XmlElement
	private String category;
	
	
	public BookBean(){
		
	}

	public BookBean(String bid, String title, int price, String category) {
		this.bid = bid;
		this.title = title;
		this.price = price;
		this.category = category;
	}

	@XmlTransient
	public String getBid() {
		return bid;
	}

	public void setBid(String bid) {
		this.bid = bid;
	}

	@XmlTransient
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@XmlTransient
	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	@XmlTransient
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	public boolean isEqual(BookBean book){
		if(this.bid.equals(book.getBid()) &&
				this.title.equals(book.getTitle()) &&
				this.price==book.getPrice() &&
				this.category.equals(book.getCategory())
				){
			return true;	
		}
		else return false;
	}
}
