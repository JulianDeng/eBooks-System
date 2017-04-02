package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import bean.BookBean;

public class LoginDAO {
	private DataSource ds;
	
	public LoginDAO() throws ClassNotFoundException{
		try {
			ds = (DataSource) (new InitialContext()).lookup("java:/comp/env/jdbc/EECS");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	public boolean adminLogin(String username, String password) throws SQLException{
		if(username==null || username.equals("")){
			return false;
		}
		if(password==null || password.equals("")){
			return false;
		}
		Connection con = this.ds.getConnection();
		String query = "select password from Administrator where username=?";
		PreparedStatement execute = con.prepareStatement(query);
		execute.setString(1, username);
		ResultSet result = execute.executeQuery();
		String pwd;
		boolean returnValue;
		if(result.next()){ //if username match admin username
			pwd=result.getString("password");
			if(pwd.equals(password)){ //if password match admin password
				returnValue = true;
			}
			else returnValue = false;
		}
		else returnValue = false;
		result.close();
		execute.close();
		con.close();
		return returnValue;
	}
	
	
	public int checkLogin(String username, String password) throws SQLException{
		if(username==null || username.equals("")){
			return 0;
		}
		if(password==null || password.equals("")){
			return 0;
		}
		int userid = 0;
		Connection con = this.ds.getConnection();
		String query = "select uid from CUSTOMER where username=? and password=?";
		PreparedStatement execute = con.prepareStatement(query);
		execute.setString(1, username);
		execute.setString(2, password);
		ResultSet result = execute.executeQuery();
		
		if(result.next()){
			System.out.println("login success");
			userid=result.getInt("uid");
		}else{
			System.out.println("login failed");
		}
		result.close();
		execute.close();
		con.close();
		return userid;
	}

	public boolean register(String lname, String fname, String username, String password) throws SQLException{
		//if user didn't provide any of those then just do not put it into database
		if(lname==null || lname.equals("")) return false;
		if(fname==null || fname.equals("")) return false;
		if(username==null || username.equals("")) return false;
		if(password==null) return false;
		else if(password.length()>20 || password.length()<10){
			return false;
		}
		Connection con = this.ds.getConnection();
		String query = "SELECT username FROM Customer where username=?";
		PreparedStatement check = con.prepareStatement(query);
		check.setString(1, username);
		ResultSet result = check.executeQuery();
		boolean success=false;
		if(result.next()){
			System.out.println("There are someone already using you username, please choose another username");
			success=false;
		}
		else {
			String update = "INSERT INTO Customer (username, password, lname, fname) VALUES (?, ?, ?, ?)";
			PreparedStatement insert = con.prepareStatement(update);
			insert.setString(1, username);
			insert.setString(2, password);
			insert.setString(3, lname);
			insert.setString(4, fname);
			insert.executeUpdate();
			insert.close();
			success=true;
		}
		result.close();
		check.close();
		con.close();
		return success;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
