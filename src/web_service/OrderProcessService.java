package web_service;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

import model.OrderProcessModel;
import model.OrderWrapper;

@Path("OrderProcessService")
public class OrderProcessService {
	
	OrderProcessModel orderDao;
	
	public OrderProcessService() throws ClassNotFoundException{
		orderDao = new OrderProcessModel();
		
	}
	
	@GET //get method
	@Path("/orders/{partNumber}")
	@Produces("text/html")
	public String getOrdersByPartNumber(@PathParam("partNumber") String partNumber) throws Exception{
		String result="Nothing for sale, please try again later";
		OrderWrapper wrapper = orderDao.getAllOrder();
		String xml = orderDao.exportOrder(wrapper);
		return xml;
	}
}
