package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import java.sql.Statement;

import bean.CartItemBean;
import bean.OrderBean;

public class PurchaseOrderDAO {
	private DataSource ds;
	
	public PurchaseOrderDAO() throws ClassNotFoundException{
		try {
			ds = (DataSource) (new InitialContext()).lookup("java:/comp/env/jdbc/EECS");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	public void doPurchase(OrderBean order) throws SQLException{
		Connection con = this.ds.getConnection();
		//check whether order address is already inside address table
		String addressQuery = "select id from ADDRESS where street=? and province=? "+
				"and country=? and zip=? and phone=?";
		PreparedStatement execute = con.prepareStatement(addressQuery);
		execute.setString(1, order.getAddress().getStreet());
		execute.setString(2, order.getAddress().getProvince());
		execute.setString(3, order.getAddress().getCountry());
		execute.setString(4, order.getAddress().getZip());
		execute.setString(5, order.getAddress().getPhone());
		ResultSet result = execute.executeQuery();
		PreparedStatement update;
		String addressUpdate;
		int addressID;
		//if order address is not inside address table then add this address into address table
		if(result.next()){
			addressID = result.getInt(1);
		}else{
			addressUpdate = "INSERT INTO Address (street, province, country, zip, phone) VALUES (?, ?, ?, ?, ?)";
			update = con.prepareStatement(addressUpdate);
			update.setString(1, order.getAddress().getStreet());
			update.setString(2, order.getAddress().getProvince());
			update.setString(3, order.getAddress().getCountry());
			update.setString(4, order.getAddress().getZip());
			update.setString(5, order.getAddress().getPhone());
			update.executeUpdate();
			update.close();
			//get the address ID from address table
			result = execute.executeQuery();
			result.next();
			addressID = result.getInt(1);
			execute.close();
		}
		//add this order into purchase order table
		addressUpdate = "INSERT INTO PO (status, address, uid) VALUES (?, ?, ?)";
		update = con.prepareStatement(addressUpdate);
		update.setString(1, order.getStatus());
		update.setInt(2, addressID);
		update.setInt(3, order.getUid());
		update.executeUpdate();
		update.close();
		//get order id in descending order and get the latest order id (which is the order just added)
		addressQuery = "SELECT id FROM PO order by id desc";
		Statement getId = con.createStatement();
		result = getId.executeQuery(addressQuery);
		result.next(); //move the cursor into first result
		int orderID = result.getInt(1);
		//add shopping cart items into POItem table
		addressUpdate = "INSERT INTO POItem (id, bid, price, quantity) VALUES (?, ?, ?, ?)";
		update = con.prepareStatement(addressUpdate);
		ArrayList<CartItemBean> items = order.getItems();
		for(int i=0; i<items.size(); i++){
			update.setInt(1, orderID);
			update.setString(2, items.get(i).getItem().getBid());
			update.setInt(3, items.get(i).getPrice());
			update.setInt(4, items.get(i).getQuantity());
			update.executeUpdate();
		}
		update.close();
		result.close();
		execute.close();
		con.close();
	}
	
}
