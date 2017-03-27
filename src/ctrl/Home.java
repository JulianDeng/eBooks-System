package ctrl;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.BookDAO;
import bean.BookBean;
import model.CartModel;

/**
 * Servlet implementation class Start
 */
@WebServlet({"/Home", "/Home/*"})
public class Home extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ServletContext context;
	private BookDAO bookDao;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Home() {
        super();
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init() throws ServletException {
		super.init();
		try{
			CartModel cm = new CartModel();
			context = this.getServletContext();
			context.setAttribute("cartModel", cm);
			bookDao = new BookDAO();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		CartModel cm = (CartModel) context.getAttribute("cartModel");
		
		//If user press back button (Will implement if time permits)
		if(request.getParameter("back") != null){ 
			response.sendRedirect("Home");
		}
		//if user want to view books
		else if(request.getPathInfo() != null && request.getPathInfo().equals("/book/")){
			response.setContentType("text/html");
			System.out.println("user select view books");
			PrintWriter pw = response.getWriter();
			//Ajax goes here; Show book view write html here
		}
		//if user press search button, search books with specific title
		else if(request.getParameter("search") != null){
			System.out.println("user press search button");
			String category = request.getParameter("cat");
			String searchText = request.getParameter("searchText");
			HashMap<String, BookBean> result;
			try {
				result = bookDao.getBooks(searchText, Integer.MAX_VALUE, 0, category);
				Collection<BookBean> list = result.values();
				request.setAttribute("books", list);
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			String target = "/Home.jspx";
			request.getRequestDispatcher(target).forward(request, response);
		}
		//if user press shoppingCart button (forward to shopping cart page)
		else if(request.getParameter("shoppingCart") != null){
			String target = "Cart";
			System.out.println("user press shoppingCart button");
			
			Enumeration<String> e = request.getParameterNames();
			while(e.hasMoreElements()){
				String element = e.nextElement();
				System.out.println(element+": "+request.getParameter(element));
			}
			RequestDispatcher nd = context.getNamedDispatcher(target);
			nd.forward(request, response);
		}

		//add a book, new information saved in CartModel in session scope.
		else{
			
			
			String target = "/Home.jspx";
			request.getRequestDispatcher(target).forward(request, response);
		}		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
