package model;

import java.sql.SQLException;
import java.util.HashMap;

import DAO.BookDAO;
import bean.BookBean;

public class BookAccessModel {
	private BookDAO bookDao;

	
	public BookAccessModel() throws ClassNotFoundException {
		super();
		bookDao = new BookDAO();
	}
	
	public BookBean getBookById(String bid) throws SQLException{
		if(bid==null || bid.equals("")){
			return null;
		}
		else return bookDao.getBookById(bid);
	}
	
	public HashMap<String, BookBean> getBooks(String titlePrefix, int maxPrice, int minPrice, String category) throws SQLException{
		if(titlePrefix==null) titlePrefix="";
		if(category==null) category="";
		return bookDao.getBooks(titlePrefix, maxPrice, minPrice, category);
	}
	
	public HashMap<String, BookBean> getAllBook() throws SQLException{
		return bookDao.getAllBook();
	}
	
}
