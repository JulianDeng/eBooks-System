package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import bean.AnnomizedReportBean;
import model.AnnomizedReportWrapper;

public class CustomerDAO {
	private DataSource ds;
	
	public CustomerDAO() throws ClassNotFoundException{
		try {
			ds = (DataSource) (new InitialContext()).lookup("java:/comp/env/jdbc/EECS");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	public AnnomizedReportWrapper getSpendReport(String generateDate, String orderBy, String order) throws SQLException{
		if(order==null) order="";
		if(!orderBy.equals("uid") && !orderBy.equals("fname") && !orderBy.equals("lname") && !orderBy.equals("total_spend")){
			return null;
		}
		ArrayList<AnnomizedReportBean> returnList = new ArrayList<AnnomizedReportBean>();
		Connection con = this.ds.getConnection();
		StringBuilder queryBuffer = new StringBuilder("select customer.uid, fname, lname, poitem.quantity*book.price as total_spend ");
		queryBuffer.append("from customer, po, poitem, book ");
		queryBuffer.append("where customer.uid=po.uid and po.id=poitem.id and poitem.bid=book.bid ");
		queryBuffer.append("group by customer.uid order by ?");
		if(order.equals("descending")){
			queryBuffer.append(" desc");
		}
		PreparedStatement execute = con.prepareStatement(queryBuffer.toString());
		execute.setString(1, orderBy);
		ResultSet result = execute.executeQuery();
		//get all user spend report
		while(result.next()){
			int uid = result.getInt(1);
			String fname = result.getString(2);
			String lname = result.getString(3);
			int total = result.getInt(4);
			returnList.add(new AnnomizedReportBean(uid, fname, lname, total));
		}
		AnnomizedReportWrapper report = new AnnomizedReportWrapper(generateDate, returnList, orderBy, order);
		result.close();
		execute.close();
		con.close();
		return report;
	}
	
}
