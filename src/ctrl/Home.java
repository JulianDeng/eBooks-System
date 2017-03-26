package ctrl;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.CartModel;

/**
 * Servlet implementation class Start
 */
@WebServlet({"/Home", "/Home/*"})
public class Home extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Home() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		try{
			CartModel cm = new CartModel();
			getServletContext().setAttribute("cartModel", cm);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		CartModel cm = (CartModel) getServletContext().getAttribute("cartModel");
		if (request.getQueryString() == null && request.getMethod().equals("GET")) {
			String target = "/Home.jspx";
			request.getRequestDispatcher(target).forward(request, response);
		}else if(request.getParameter("back") != null){
			response.sendRedirect("Home");	    // Reserved for "back" button. Will implment if time permits.
		}else if(request.getPathInfo() != null && request.getPathInfo().equals("/catSelect/")){
			response.setContentType("text/html");
			PrintWriter pw = response.getWriter();
			//Ajax goes here; Show book list write html here
			// ajax button should be included for each book. hard code it.
			// e.g. <button type="button" name="show" value="true" onclick="doBookViewAjax('/eBooks/Start/book/', bid);return false;">Show</button>	
			// also not to forget to implement the review(pinglun) function here.
		}else if(request.getPathInfo() != null && request.getPathInfo().equals("/book/")){
			response.setContentType("text/html");
			PrintWriter pw = response.getWriter();
			//Ajax goes here; Show book view write html here
		}else if(request.getParameter("search") != null || request.getParameter("search").equals("")){
			//Search button is hit. Search works
		}else if(request.getParameter("Cart") != null){
			//Cart button is hit.
			String target = "Cart";
			this.getServletContext().getNamedDispatcher(target).forward(request, response);
		}else{
			//add a book; new information saved in CartModel in session scope.
			
			String target = "/Home.jspx";
			request.getRequestDispatcher(target).forward(request, response);
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
