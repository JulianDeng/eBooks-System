package web_service;

import javax.servlet.ServletContext;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import model.OrderProcessModel;
import model.OrderWrapper;

@Path("OrderProcessService")
public class OrderProcessService {
	
	OrderProcessModel orderDao;
	
	@Context
	private ServletContext context;
	
	
	public OrderProcessService() throws ClassNotFoundException{
		orderDao = new OrderProcessModel();
		
	}
	
	@GET //get method
	@Path("/orders/{partNumber}")
	@Produces("text/html")
	public String getOrdersByPartNumber(@PathParam("partNumber") String partNumber) throws Exception{
		String result="Nothing for sale, please try again later";
		OrderWrapper wrapper = orderDao.getOrder(partNumber);
		String xml = orderDao.exportOrder(wrapper, context);
		return xml;
	}
}
