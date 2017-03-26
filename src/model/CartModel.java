package model;

import java.util.List;

import DAO.BookDAO;
import DAO.BookReviewDAO;
import DAO.VisitEventDAO;
import bean.BookBean;
import bean.CartBean;

public class CartModel {

	private BookDAO bDAO; 
	private BookReviewDAO brDAO;
	private VisitEventDAO veDAO;
	private CartBean cart;

	
	public CartModel() {
		bDAO = new BookDAO();
		brDAO = new BookReviewDAO();
		veDAO = new VisitEventDAO();
		cart = new CartBean();
	}
	
	public void addVisitEvent(String bid, String event){
		// Do not forget to mark down the timestamp in the format mmddyyyy.
		// insert into database.
	}
	
	public List<BookBean> getBookList(String category){
		return null;
	}
	
	public List<BookBean> getSearchResult(String keyword){
		return null;
	}
		
	public BookBean getBook(String bid){
		return null;
	}
	
	public void addBook(String bid){
	
	}
	
	public void addBookReview(String bid, String review){
		
	}
	
	public void removeBook(String bid){
		
	}
	
	public void plusOneBook(String bid){
		
	}
	
	public void minusOneBook(String bid){
		
	}
	
	
	
}
