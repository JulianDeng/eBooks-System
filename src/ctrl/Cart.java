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
import javax.servlet.http.HttpSession;

import bean.CartBean;
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
		System.out.println("getting into cart");
		CartModel cm = (CartModel) getServletContext().getAttribute("cartModel");
		
		//if user pay bills
		if(request.getAttribute("goingtoshopcart") != null){
			String targetjspx = "/Cart.jspx";
			request.getRequestDispatcher(targetjspx).forward(request, response);
			
		}
		else if(request.getParameter("payment") != null){
			CartBean cart = (CartBean) request.getSession().getAttribute("cartlist");
			System.out.println(cart);
			String target = "OrderProcess";
			this.getServletContext().getNamedDispatcher(target).forward(request, response);
		}
		
		//if user remove books
		else if(request.getParameter("remove") != null){
			String bookID = request.getParameter("plus_one").substring(5);	 //cannot hard code numbers here.
			//do something here to modify the model.
			
			String target = "/Cart.jspx";
			request.getRequestDispatcher(target).forward(request, response);
		}
		
		//if user plus one book
		else if(checkName(request, "^plus_.*")){
			System.out.println("user add books");
			String bookID = (String) request.getAttribute("bookid");
			HttpSession session = request.getSession();
			CartBean cart = (CartBean) session.getAttribute("cartlist");
			cart.addOneExistItemByBid("bookid");
			
			request.removeAttribute("bookid");
			String target = "/Cart.jspx";
			request.getRequestDispatcher(target).forward(request, response);
		}
		
		//if user minus one book
		else if(checkName(request, "^minus_.*")){
			String bookID = (String) request.getAttribute("bookid");
			HttpSession session = request.getSession();
			CartBean cart = (CartBean) session.getAttribute("cartlist");
			cart.removeOneExistItemByBid("bookid");
			
			request.removeAttribute("bookid");
			String target = "/Cart.jspx";
			request.getRequestDispatcher(target).forward(request, response);
		}
		
		//Reserved for "back" button. Will implment if time permits.
		else if(request.getParameter("back") != null){
			response.sendRedirect("Cart");	   
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	/**
	 * Check whether there is an attribute name matches the specific pattern
	 * if matches then set attribute into request with attribute name bookid
	 * @param request
	 * @param pattern
	 * @return
	 */
	private boolean checkName(HttpServletRequest request, String pattern){
		Enumeration<String> name = request.getParameterNames();
		while(name.hasMoreElements()){
			String buttonname = name.nextElement();
			if(buttonname.matches(pattern)){
				request.setAttribute("bookid", buttonname.split("-")[1]);
				return true;
			}
		}
		return false;
	}
	
	
}
