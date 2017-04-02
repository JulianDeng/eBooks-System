package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import bean.BookBean;
import bean.CartItemBean;
import bean.DateBean;
import model.MonthlyReportWrapper;
import model.OrderItemWrapper;
import model.ReportWrapper;

public class VisitEventDAO {
	private DataSource ds;
	private int timeZone = -4;
	public VisitEventDAO() throws ClassNotFoundException{
		try {
			ds = (DataSource) (new InitialContext()).lookup("java:/comp/env/jdbc/EECS");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	public void addToVisitEvent(DateBean day, String bookid, String eventtype) throws SQLException{
		Connection con = this.ds.getConnection();
		String query = "INSERT INTO VisitEvent (day, bid, eventtype) VALUES (?, ?, ?)";
		PreparedStatement execute = con.prepareStatement(query);
		execute.setString(1, day.formatDateToYYYYMMDD());
		execute.setString(2, bookid);
		execute.setString(3, eventtype);
		execute.executeUpdate();
		execute.close();
		con.close();
	}
	
	public void addShoppingCartToVisitEvent(DateBean day, ArrayList<CartItemBean> book, String eventtype) throws SQLException{
		Connection con = this.ds.getConnection();
		String query = "INSERT INTO VisitEvent (day, bid, eventtype) VALUES (?, ?, ?)";
		PreparedStatement execute = con.prepareStatement(query);
		for(int i=0; i<book.size(); i++){
			execute.setString(1, day.formatDateToYYYYMMDD());
			execute.setString(2, book.get(i).getItem().getBid());
			execute.setString(3, eventtype);
			execute.executeUpdate();
		}
		execute.close();
		con.close();
	}
	
	/**
	 * Get all report from start month to end month
	 * @param visitType One of the VIEW, CART, PURCHASE
	 * @param start The start month, Which have following format: YYYYMMDD
	 * @param end The end month, report will include for this month, Which have following format: YYYYMMDD
	 * @throws SQLException
	 */
	public ReportWrapper getReport(String visitType, String start, String end) throws SQLException{
		DateBean date = new DateBean(timeZone); //make a date object for the report generating time
		Connection con = this.ds.getConnection();
		String query = "select book.bid, title, price, category, day from visitevent, book "
				+ "where book.bid=visitevent.bid and eventtype=? and day>? and day<?";
		PreparedStatement execute = con.prepareStatement(query);
		int startInt = Integer.parseInt(start);
		startInt /= 100;
		int endInt = Integer.parseInt(end);
		endInt /= 100;
		ArrayList<String> monthList = new ArrayList<String>(); //list item in YYYYMMDD format
		int tempMonth=startInt;
		//fill all month start date value, which is format YYYYMM00
		while(tempMonth<=endInt){
			monthList.add(""+(tempMonth*100));
			tempMonth++;
			if((tempMonth%100)>12){ //if month bigger than 12 then increase year
				tempMonth = tempMonth+88;
			}
		}
		monthList.add(""+(tempMonth*100)); //include one more month, which srand for the end date for the report
		
		//make an array for all monthly report
		ArrayList<MonthlyReportWrapper> allReportItems = new ArrayList<MonthlyReportWrapper>();
		//do all the data adding for every month
		for(int i=0; i<monthList.size()-1; i++){
			execute.setString(1, visitType);
			execute.setString(2, monthList.get(i)); //the start date for this month
			execute.setString(3, monthList.get(i+1)); //the start date for next month
			//get all visit report for this month
			ResultSet result = execute.executeQuery();
			//make an array for this month report
			ArrayList<OrderItemWrapper> itemList = new ArrayList<OrderItemWrapper>();
			while(result.next()){
				String day = result.getString(5); //get the item order date
				String bid = result.getString(1);
				String title = result.getString(2);
				int price = result.getInt(3);
				String category = result.getString(4);
				BookBean book = new BookBean(bid, title, price, category);
				itemList.add(new OrderItemWrapper(book, day)); //add item order into itemList
			}
			MonthlyReportWrapper monthlyItem = new MonthlyReportWrapper(monthList.get(i), itemList);
			allReportItems.add(monthlyItem);
			result.close();
		}
		ReportWrapper allReport = new ReportWrapper(date.formatDateToYYYYMMDD(), visitType, start, end, allReportItems);
		execute.close();
		con.close();
		return allReport;
	}
	
	
	/**
	 * Get all report within the specific month
	 * @param visitType One of the VIEW, CART, PURCHASE
	 * @param dateStr Which have following format: YYYYMMDD
	 * @throws SQLException
	 */
	public ReportWrapper getReport(String visitType, String dateStr) throws SQLException{
		Connection con = this.ds.getConnection();
		String query = "select book.bid, title, price, category, day from visitevent, book "
				+ "where book.bid=visitevent.bid and eventtype=? and day>? and day<?";
		PreparedStatement execute = con.prepareStatement(query);
		int dateInt = Integer.parseInt(dateStr);
		//change the start date just before first day in that month
		int startdate = dateInt / 100;
		startdate *= 100;
		String start = ""+startdate;
		//change the end date just before first day in next month
		String end;
		int enddate = dateInt;
		enddate /= 100; //change to YYYYMM format
		int month = dateInt%100; //get month by take reminder
		//if this month is December then next month is next year January
		if(month>=12){
			enddate += 89;
			end = enddate+"00";
		}else{
			enddate += 1;
			end = enddate+"00";
		}
		execute.setString(1, visitType);
		execute.setString(2, start);
		execute.setString(3, end);
		ResultSet result = execute.executeQuery();
		ArrayList<OrderItemWrapper> itemList = new ArrayList<OrderItemWrapper>();
		while(result.next()){
			String day = result.getString(5); //get the item order date
			String bid = result.getString(1);
			String title = result.getString(2);
			int price = result.getInt(3);
			String category = result.getString(4);
			BookBean book = new BookBean(bid, title, price, category);
			itemList.add(new OrderItemWrapper(book, day)); //add item order into itemList
		}
		//make object for this month report
		MonthlyReportWrapper monthlyReportItem = new MonthlyReportWrapper(start, itemList);
		DateBean date = new DateBean(timeZone);
		ArrayList<MonthlyReportWrapper> monthlyReport = new ArrayList<MonthlyReportWrapper>();
		monthlyReport.add(monthlyReportItem);
		ReportWrapper allReport = new ReportWrapper(date.formatDateToYYYYMMDD(), visitType, start, end, monthlyReport);
		result.close();
		execute.close();
		con.close();
		return allReport;
	}
	
}
