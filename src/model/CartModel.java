package model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import DAO.BookDAO;
import DAO.BookReviewDAO;
import DAO.LoginDAO;
import DAO.PurchaseOrderDAO;
import DAO.VisitEventDAO;
import analytics.BookVisit;
import bean.AddressBean;
import bean.BookBean;
import bean.BookInfoBean;
import bean.CartBean;
import bean.CartItemBean;
import bean.DateBean;
import bean.OrderBean;
import bean.ReviewBean;

public class CartModel {

	private BookDAO bookDao;
	private BookReviewDAO reviewDao;
	private VisitEventDAO visitDao;
	private LoginDAO loginDao;
	private PurchaseOrderDAO purchaseDao;
	
	public CartModel() throws ClassNotFoundException {
		bookDao = new BookDAO();
		reviewDao = new BookReviewDAO();
		visitDao = new VisitEventDAO();
		loginDao = new LoginDAO();
		purchaseDao = new PurchaseOrderDAO();
	}
	
	
	public Collection<BookBean> getBooksFromCategory(String category){		
		Collection<BookBean> listOfBooks = null;	
		try {
			listOfBooks = bookDao.getBooks("", Integer.MAX_VALUE, 0, category).values();   //search text is empty, price range(0,MAX)
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listOfBooks;
	}
	
	
	public Collection<BookBean> getSearchResult(String searchText, String category){
		Collection<BookBean> listOfBooks = null;	
		try {
			listOfBooks = bookDao.getBooks(searchText, Integer.MAX_VALUE, 0, category).values();   //price range(0,MAX), search keyword from category
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listOfBooks;
	}

	
	public BookBean getBookFromList(Collection<BookBean> bookInSearchList, String bookid){
		BookBean bookFound = null;
		//Check whether list contains string bookid. If contains, return the book.
		for(BookBean book : bookInSearchList){
			if(book.getBid().equals(bookid)){
				bookFound = book;
				break;
			}
		}
		
		//if the book you want to review is not inside search result list, then get book information from database
		if(bookFound == null){
			try {
				bookFound = bookDao.getBookById(bookid);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return bookFound;
	}

	
	public ArrayList<ReviewBean> getReviewFromBook(String bookid) {
		ArrayList<ReviewBean> reviews = null;
		try {
			reviews = reviewDao.getReview(bookid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return reviews;
	}

	
	public void addViewVisit(int timeZone, String bookid){
		try {
			visitDao.addToVisitEvent(new DateBean(timeZone), bookid, "VIEW");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
	public BookInfoBean getBookPreview(String bookid, Collection<BookBean> bookInSearchList) {
		BookBean book = getBookFromList(bookInSearchList, bookid);
		ArrayList<ReviewBean> reviews = getReviewFromBook(bookid);
		double overallRating = -1;                 // if no rating records, return -1
		try {
			overallRating = reviewDao.getOverallRating(bookid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		BookInfoBean bookPreviewing = new BookInfoBean(book, reviews, overallRating);
		return bookPreviewing;
	}

	
	public void addRating(String bookid, byte score) {
		try {
			reviewDao.addRating(bookid, score);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
	public void commentBook(String bookid, String comment) {
		DateBean date = new DateBean(-4);              //UTC
		String dateString = date.formatDateToYYYYMMDD();
		ReviewBean review = new ReviewBean(bookid, comment, dateString);
		try {
			reviewDao.addReview(review);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
	public CartBean getCartWithOneBookAdded(CartBean cart, Collection<BookBean> bookInSearchList, String bookid) {
		//if there are no cart, create new one
		if(cart == null){ 
			cart = new CartBean(); 
		}
		
		BookBean book = getBookFromList(bookInSearchList, bookid);
		CartItemBean item = new CartItemBean(book,1); 
		cart.addItems(item);
				
		return cart;
	}

	
	public void addCartVisit(int timeZone, String bookid) {
		try{
			visitDao.addToVisitEvent(new DateBean(timeZone), bookid, "CART");
		} catch(SQLException e){
			e.printStackTrace();
		}
	}


	public int getLoginUid(String username, String password) {
		int loginUid = 0;
		try{
			loginUid = loginDao.checkLogin(username, password);
		}catch(SQLException e){
			e.printStackTrace();
		}
		return loginUid;
	}


	public boolean register(String lname, String fname, String username, String password) {
		boolean success = false; 
		try {
			success = loginDao.register(lname, fname, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return success;
	}


	public AddressBean getAddress(String street, String province, String country, String zip, String phone) {
		AddressBean address = new AddressBean();
		address.setStreet(street);
		address.setProvince(province);
		address.setCountry(country);
		address.setZip(zip);
		address.setPhone(phone);
		return address;
	}


	public ArrayList<CartItemBean> getRealCartItem(ArrayList<CartItemBean> items) {
		
		ArrayList<CartItemBean> realItem = new ArrayList<CartItemBean>();		
		for(int i=0; i<items.size(); i++){
			CartItemBean item = items.get(i);
			if(item.getQuantity()>0){			
				realItem.add(item);
			}
		}
		return realItem;
	}
	

	public void clearingPayment(int uid, AddressBean address, ArrayList<CartItemBean> realItem) throws SQLException {
		//Purchase order ID will change after accessing database
			OrderBean order = new OrderBean(realItem, 0, "ORDERED", address, uid);
			purchaseDao.doPurchase(order);
	}


	public void addPurchaseVisit(int timeZone, ArrayList<CartItemBean> realItem) {
		try {
			visitDao.addShoppingCartToVisitEvent(new DateBean(timeZone), realItem, "PURCHASE");
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}


	
}
