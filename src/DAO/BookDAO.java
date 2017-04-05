package DAO;

import java.sql.*;
import java.util.*;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import bean.BookBean;

public class BookDAO {
	private DataSource ds;
	
	public BookDAO() throws ClassNotFoundException{
		try {
			ds = (DataSource) (new InitialContext()).lookup("java:/comp/env/jdbc/EECS");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Get book by specified ID
	 * @param bid 
	 * @return
	 * @throws SQLException
	 */
	public BookBean getBookById(String bid) throws SQLException{
		BookBean book=null;
		Connection con = this.ds.getConnection();
		String query = "select * from BOOK where bid=?";
		PreparedStatement execute = con.prepareStatement(query);
		execute.setString(1, bid);
		ResultSet result = execute.executeQuery();
		if(result.next()){
			String title = result.getString("title");
			int price = result.getInt("price");
			String category = result.getString("category");
			book = new BookBean(bid, title, price, category);
		}
		result.close();
		execute.close();
		con.close();
		return book;
	}
	
	/**
	 * Get books with the specified titlePrefix, price within minPrice to maxPrice, and with specified category
	 * @param titlePrefix Which is the book title you want to search
	 * @param maxPrice Which is the maximum price you want to include on you result
	 * @param minPrice Which is the minimum price you want to include on you result
	 * @param category Which is the category you want to search. Empty string means all category
	 * @return
	 * @throws SQLException
	 */
	public HashMap<String, BookBean> getBooks(String titlePrefix, int maxPrice, int minPrice, String category) throws SQLException{
		HashMap<String, BookBean> books = new HashMap<String, BookBean>();
		
		Connection con = this.ds.getConnection();
		
		StringBuilder queryBuffer = new StringBuilder("select * from BOOK ");
		queryBuffer.append("where title like ? and price>=? and price<=?");
		
		if(!category.equals("")){
			queryBuffer.append(" and category=?");
		}
		
		String query = queryBuffer.toString();
		PreparedStatement execute = con.prepareStatement(query);
		execute.setString(1, "%"+titlePrefix+"%");
		execute.setInt(2, minPrice);
		execute.setInt(3, maxPrice);
		if(!category.equals("")){
			execute.setString(4, category);
		}
		ResultSet result = execute.executeQuery();
		
		while(result.next()){
			String bid = result.getString("bid");
			String title = result.getString("title");
			int price = result.getInt("price");
			String cat = result.getString("category");
			BookBean book = new BookBean(bid, title, price, cat);
			books.put(bid, book);
		}
		result.close();
		execute.close();
		con.close();
		return books;
	}
	/**
	 * Get all books from database
	 * @return
	 * @throws SQLException
	 */
	public HashMap<String, BookBean> getAllBook() throws SQLException{
		HashMap<String, BookBean> books = new HashMap<String, BookBean>();
		Connection con = this.ds.getConnection();
		String query = "select * from BOOK";
		Statement execute = con.createStatement();
		ResultSet result = execute.executeQuery(query);
		
		while(result.next()){
			String bid = result.getString("bid");
			String title = result.getString("title");
			int price = result.getInt("price");
			String cat = result.getString("category");
			BookBean book = new BookBean(bid, title, price, cat);
			books.put(bid, book);
		}
		result.close();
		execute.close();
		con.close();
		return books;
	}
	
	
}
