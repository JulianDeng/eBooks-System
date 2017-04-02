package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import bean.ReviewBean;

public class BookReviewDAO {
	private DataSource ds;
	
	public BookReviewDAO() throws ClassNotFoundException{
		try {
			ds = (DataSource) (new InitialContext()).lookup("java:/comp/env/jdbc/EECS");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<ReviewBean> getReview(String bid) throws SQLException{
		Connection con = this.ds.getConnection();
		String query = "SELECT comment, date FROM Review where bid=?";
		PreparedStatement execute = con.prepareStatement(query);
		execute.setString(1, bid);
		ResultSet result = execute.executeQuery();
		ArrayList<ReviewBean> reviews = new ArrayList<ReviewBean>();
		while(result.next()){
			String comment = result.getString(1);
			String date = result.getString(2);
			reviews.add(new ReviewBean(bid,comment,date));
		}
		
		result.close();
		execute.close();
		con.close();
		return reviews;
	}
	
	public double getOverallRating(String bid) throws SQLException{
		Connection con = this.ds.getConnection();
		String query = "SELECT score FROM Rating where bid=?";
		PreparedStatement execute = con.prepareStatement(query);
		execute.setString(1, bid);
		ResultSet result = execute.executeQuery();
		int count=0, total=0;
		while(result.next()){
			total += result.getInt(1);
			count ++;
		}
		total *= 10;
		if(count>0) total /= count;
		double average = total;
		average = average / 10.0;
		result.close();
		execute.close();
		con.close();
		return average;
	}
	
	public void addRating(String bid, byte score) throws SQLException{
		Connection con = this.ds.getConnection();
		String update = "INSERT INTO Rating (bid, score) VALUES (?, ?)";
		PreparedStatement execute = con.prepareStatement(update);
		execute.setString(1, bid);
		execute.setByte(2, score);
		execute.executeUpdate();
		execute.close();
		con.close();
	}
	
	public void addReview(ReviewBean review) throws SQLException{
		Connection con = this.ds.getConnection();
		String update = "INSERT INTO Review (bid, comment, date) VALUES (?, ?, ?)";
		PreparedStatement execute = con.prepareStatement(update);
		execute.setString(1, review.getBid());
		execute.setString(2, review.getReview());
		execute.setString(3, review.getDate());
		execute.executeUpdate();
		execute.close();
		con.close();
	}

}
