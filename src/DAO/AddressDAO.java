package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import bean.AddressBean;

public class AddressDAO {
	private DataSource ds;
	
	public AddressDAO() throws ClassNotFoundException{
		try {
			ds = (DataSource) (new InitialContext()).lookup("java:/comp/env/jdbc/EECS");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Get address by specific ID
	 * @param ID
	 * @return
	 * @throws SQLException
	 */
	public AddressBean getAddressById(int ID) throws SQLException{
		AddressBean address=null;
		if(ID<0) return null;
		Connection con = this.ds.getConnection();
		String query = "select * from ADDRESS where id=" + ID;
		Statement execute = con.createStatement();
		ResultSet result = execute.executeQuery(query);
		if(result.next()){
			int id = result.getInt("id");
			String street = result.getString("street");
			String province = result.getString("province");
			String country = result.getString("country");
			String zip = result.getString("zip");
			String phone = result.getString("phone");
			address = new AddressBean(id, street, province, country, zip, phone);
		}
		result.close();
		execute.close();
		con.close();
		return address;
	}
	/**
	 * Get all addresses by specific province
	 * @param province
	 * @return
	 * @throws SQLException
	 */
	public HashMap<Integer, AddressBean> getAddressByProvince(String province) throws SQLException{
		HashMap<Integer, AddressBean> address = new HashMap<Integer, AddressBean>();
		if(province==null || province.equals("")) return null;
		Connection con = this.ds.getConnection();
		String query = "select * from ADDRESS where province='" + province +"'";
		Statement execute = con.createStatement();
		ResultSet result = execute.executeQuery(query);
		while(result.next()){
			int id = result.getInt("id");
			String street = result.getString("street");
			String country = result.getString("country");
			String zip = result.getString("zip");
			String phone = result.getString("phone");
			AddressBean addr = new AddressBean(id, street, province, country, zip, phone);
			address.put(id, addr);
		}
		result.close();
		execute.close();
		con.close();
		return address;
	}
	/**
	 * Get all address by specific country
	 * @param country
	 * @return
	 * @throws SQLException
	 */
	public HashMap<Integer, AddressBean> getAddressByCountry(String country) throws SQLException{
		HashMap<Integer, AddressBean> address = new HashMap<Integer, AddressBean>();
		if(country==null || country.equals("")) return null;
		Connection con = this.ds.getConnection();
		String query = "select * from ADDRESS where country='" + country +"'";
		Statement execute = con.createStatement();
		ResultSet result = execute.executeQuery(query);
		while(result.next()){
			int id = result.getInt("id");
			String street = result.getString("street");
			String province = result.getString("province");
			String zip = result.getString("zip");
			String phone = result.getString("phone");
			AddressBean addr = new AddressBean(id, street, province, country, zip, phone);
			address.put(id, addr);
		}
		result.close();
		execute.close();
		con.close();
		return address;
	}
	/**
	 * Get Address by specific information
	 * @param streetPrefix if you want search any street just leave as blank
	 * @param province if you want search any province just leave as blank
	 * @param country if you want search for any country just leave as blank
	 * @param zip if you don't want to care zip just leave as blank
	 * @param phonePrefix the phone number that start with phonePrefix. if you want search for any phone just leave as blank
	 * @return
	 * @throws SQLException
	 */
	public HashMap<Integer, AddressBean> getAddress(String streetPrefix, String province, String country, String zip, String phonePrefix) throws SQLException{
		HashMap<Integer, AddressBean> address = new HashMap<Integer, AddressBean>();
		Connection con = this.ds.getConnection();
		if(streetPrefix==null) streetPrefix="";
		if(province==null) province="";
		if(country==null) country="";
		if(zip==null) zip="";
		if(phonePrefix==null) phonePrefix="";
		
		StringBuilder queryBuffer = new StringBuilder("select * from ADDRESS where street like '%" + streetPrefix + "%'");
		if(!province.equals("")){
			queryBuffer.append(" and province='"+province+"'");
		}
		if(!country.equals("")){
			queryBuffer.append(" and country='"+country+"'");
		}
		if(!zip.equals("")){
			queryBuffer.append(" and zip='"+zip+"'");
		}
		queryBuffer.append(" and phone like '"+phonePrefix+"%'");
		
		String query = queryBuffer.toString();
		Statement execute = con.createStatement();
		ResultSet result = execute.executeQuery(query);
		while(result.next()){
			int id = result.getInt("id");
			String street = result.getString("street");
			String pvce = result.getString("province");
			String ctry = result.getString("country");
			String ZIP = result.getString("zip");
			String phone = result.getString("phone");
			AddressBean addr = new AddressBean(id, street, pvce, ctry, ZIP, phone);
			address.put(id, addr);
		}
		result.close();
		execute.close();
		con.close();
		return address;
	}
	
}
