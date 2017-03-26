package model;

import java.util.Map;

public class OrderProcessModel {
	
	public OrderProcessModel(){
		
	}
	
	public boolean login(String user, String pswd){
		return false;
	}
	
	public boolean create(Map<String, String> data){
		return false;
		
	}
	
	public boolean veryfy(String CCN){
		//3rd time submit will fail
		return false;
	}
	
	
}
