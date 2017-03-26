package ctrl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.CartModel;

/**
 * Servlet implementation class Cart
 */
@WebServlet({"/Cart", "/Cart/*"})
public class Cart extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Cart() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		CartModel cm = (CartModel) getServletContext().getAttribute("cartModel");
		if (request.getQueryString() == null && request.getMethod().equals("GET")) {
			String target = "/Cart.jspx";
			request.getRequestDispatcher(target).forward(request, response);
		}else if(request.getParameter("back") != null){
			response.sendRedirect("Cart");	    // Reserved for "back" button. Will implment if time permits.
		}else if(request.getPathInfo() != null && request.getPathInfo().equals("/book/")){
			response.setContentType("text/html");
			PrintWriter pw = response.getWriter();
			//Ajax goes here; Show book detail; write html here
		}else if(request.getParameter("plus_one") != null){
			String bookID = request.getParameter("plus_one").substring(4);   //cannot hard code numbers here.
			//do something here to modify the model.
			
			
			String target = "/Cart.jspx";
			request.getRequestDispatcher(target).forward(request, response);
		}else if(request.getParameter("minus_one") != null){
			String bookID = request.getParameter("plus_one").substring(5);	 //cannot hard code numbers here.
			//do something here to modify the model.
			
			
			String target = "/Cart.jspx";
			request.getRequestDispatcher(target).forward(request, response);
		}else if(request.getParameter("update") != null){
			Enumeration<String> books = request.getParameterNames();
			while(books.hasMoreElements()){
				// use books.nextElement() to modify the model for each book.
			}
			
			String target = "/Cart.jspx";
			request.getRequestDispatcher(target).forward(request, response);
		}else if(request.getParameter("remove") != null){
			String bookID = request.getParameter("plus_one").substring(5);	 //cannot hard code numbers here.
			//do something here to modify the model.
			
			String target = "/Cart.jspx";
			request.getRequestDispatcher(target).forward(request, response);
		}else if(request.getParameter("payment") != null){
			// get the model from session scope and update it to database.
			String target = "OrderProcess";
			this.getServletContext().getNamedDispatcher(target).forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
