package web_service;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import bean.OrderBean;
import model.OrderProcessModel;
import model.OrderWrapper;

@Path("/OrderProcessService")
public class OrderProcessService {
	
	OrderProcessModel orderDao;
	
	@Context
	private ServletContext context;
	
	@Context
	private HttpServletRequest request; 
	
	@Context
	private HttpServletResponse response;
	
	
	public OrderProcessService() throws ClassNotFoundException{
		orderDao = new OrderProcessModel();
		
	}
	
	@GET //get method
	@Path("/orders/{partNumber}")
	@Produces("text/html")
	public String getOrdersByPartNumber(@PathParam("partNumber") String partNumber) throws Exception{
		String result="Nothing for sale, please try again later";
		OrderWrapper wrapper = orderDao.getOrder(partNumber);
		result = orderDao.exportOrder(wrapper, context);
		return result;
	}
	
	@GET
	@Path("/ordersXML/{partNumber}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public OrderWrapper getOrderByPartNumberXML(@PathParam("partNumber") String partNumber) throws Exception{
		OrderWrapper wrapper = orderDao.getOrder(partNumber);
		return wrapper;
	}
	
	@GET
	@Path("/ordersHTML/{partNumber}")
	@Produces("text/html")
	public void getOrderByPartNumberHTML(@PathParam("partNumber") String partNumber) throws Exception{
		OrderWrapper wrapper = orderDao.getOrder(partNumber);
		OrderBean order = wrapper.getItems().get(0);
		request.getSession().setAttribute("order", order);
		
		String target = "/RESTHTML.jspx";
		request.getRequestDispatcher(target).forward(request, response);
		return;
	}
}
