package ctrl;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sun.research.ws.wadl.Request;

import DAO.*;
import analytics.BookVisit;
import analytics.Popularity;
import bean.*;
import model.*;

/**
 * Servlet implementation class Start
 */
@WebServlet({"/Home", "/Home/*"})
public class Home extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private int timeZone = -4;
	private ServletContext context;
	
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
			Popularity popularity = new Popularity();
			context = this.getServletContext();
			context.setAttribute("cartModel", cm);
			context.setAttribute("popularity", popularity);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		CartModel cm = (CartModel) context.getAttribute("cartModel");
		System.out.println(request.getQueryString());


		//*********************************user select a category******************************************
		if(request.getParameter("select") != null){
		 	System.out.println("user select a category");		 	
		 	getBooksFromCategory(request, response, session, cm);
		}			
		
		
		//***********************user search keyword from category (if applicable)**************************
		else if(request.getParameter("search") != null){
			System.out.println("user press search button");
			getSearchResult(request, response, session, cm);	
		}


		//*********************************user press review book**************************************
		else if(checkName(request, "^preview_.*")){
			//user can only review one book at each time
			System.out.println("user press review book");
			previewBookDetails(request, response, session, cm);
		}
	
		
		//********************************user rate this book*********************************************
		else if(checkName(request, "^rate_.*")){
			System.out.println("user rate this book");
			rateBook(request, response, session, cm);
		}
		
		
		//********************************user comment this book******************************************
		else if(checkName(request, "^commentbtn_.*")){
			System.out.println("user rate this book");
			commentToBook(request, response, session, cm);
		}
		
		
		//********************************user press add book to cart button**************************************
		else if(checkName(request, "^add_.*")){
			System.out.println("press add a book");
			addBookToCart(request, response, session, cm);
		}


		//******************************if user press shoppingCart button*************************************
		//Forward to shopping cart page
		else if(request.getParameter("shoppingCart") != null){
			System.out.println("user press shoppingCart button");
			moveToCartPage(request, response, session, cm);
		}

		
		//*****************************user change quantity of books via Ajax***********************
		else if(request.getPathInfo() != null && request.getPathInfo().equals("/Ajax/")){
			Enumeration <String> params = request.getParameterNames();
			CartBean cart = (CartBean) session.getAttribute("cartlist");
			while(params.hasMoreElements()){
				String name = params.nextElement();
				String value = request.getParameter(name);
				cart.updateItemByid(name, Integer.parseInt(value));
			}
			cart.computeTotal();
			session.setAttribute("cartlist", cart);
			
			response.setContentType("text/html");
			PrintWriter pw = response.getWriter();
			pw.println("<label>Total amount: $" + cart.getTotal());	
		}
		
		
		//*****************************if user plus one book****************************************
		else if(checkName(request, "^plus_.*")){
			System.out.println("user add books");
			plusOneBookInCart(request, response, session, cm);
		}
		
		
		//*****************************if user minus one book****************************************
		else if(checkName(request, "^minus_.*")){
			minusOneBookInCart(request, response, session, cm);
		}
		
		
		//*****************************if user delete book from cart****************************************
		else if(checkName(request, "^delete_.*")){
			removeBookInCart(request, response, session, cm);
		}
		
		
		//*****************************if user press payment button*********************************
		else if(request.getParameter("payment") != null){
			System.out.println("user goes to payment");
			makePayment(request, response, session, cm);
		}
		
		//***************************user go back to search page****************************
		else if(request.getParameter("searchAgain") != null){
			searchAgain(request, response, session, cm);
		}
		
				
		//*****************************user login********************************************
		else if(request.getParameter("doLogin") != null){
			userLogin(request, response, session, cm);
		}
		
		
		//*****************************user register*****************************************
		//forward to register page and let user fill account information
		else if(request.getParameter("doCreate") != null){
			doRegister(request, response, session, cm);
		}
		
		
		//*****************************user register done*************************************
		else if(request.getParameter("doneCreate") != null){
			createUser(request, response, session, cm);
		}
		
		
		//***********************************user logout**************************************
		else if(request.getParameter("logout") != null){
			logoutUser(request, response, session, cm);

		}
		

		//*****************************if user press submit payment button*********************
		else if(request.getParameter("paySubmit") != null){
			System.out.println("user goes to submit payment");
			submitPayment(request, response, session, cm);
		}
		
		
		//****************************in failure page, go back to payment page******************
		else if(request.getParameter("back") != null){
			System.out.println("go back");
			goBack(request, response, session, cm);
		}
		
		//***************************in order success page, restart browsing books**************
		else if(request.getParameter("restart") != null){
			System.out.println("restart");
			reStartBrowsing(request, response, session, cm);
		}
		
		//***************************if submit report for range of month************************
		else if(request.getParameter("submitReport") != null){
			
		}
		
		//default; 
		else{
			session.removeAttribute("books");
			session.removeAttribute("bookPreviewing");
			
			setupAjaxAddress(request, response, session, cm);
			
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
	
	
	//user get books from category
	private void getBooksFromCategory(HttpServletRequest request, HttpServletResponse response, HttpSession session, CartModel cm) throws ServletException, IOException{
		String category = request.getParameter("cat");
		request.setAttribute("cat", category);
		
		Collection<BookBean> listOfBooks = cm.getBooksFromCategory(category);	 	
	 	session.setAttribute("books", listOfBooks);           

		String target = "/Home.jspx";
		request.getRequestDispatcher(target).forward(request, response);
	}
	
	
	//user search keyword from category
	private void getSearchResult(HttpServletRequest request, HttpServletResponse response, HttpSession session, CartModel cm) throws ServletException, IOException{
		String category = request.getParameter("cat");
		String searchText = request.getParameter("searchText");
		request.setAttribute("cat", category);
		request.setAttribute("searchText", searchText);	
		
		if(category == null) category = "";
		Collection<BookBean> listOfBooks = cm.getSearchResult(searchText, category);
		session.setAttribute("books", listOfBooks);           

		String target = "/Home.jspx";
		request.getRequestDispatcher(target).forward(request, response);	
	}

	
	//If user want to view a specific book detail
	//Need add book into VisitEvent
	private void previewBookDetails(HttpServletRequest request, HttpServletResponse response, HttpSession session, CartModel cm) throws ServletException, IOException{
		
		String bookid = (String) request.getAttribute("bookid");		
								
		//retrieve a book to be previewed.
		Collection<BookBean> bookInSearchList = (Collection<BookBean>) session.getAttribute("books");				
		BookInfoBean bookPreviewing = cm.getBookPreview(bookid, bookInSearchList);
		session.setAttribute("bookPreviewing", bookPreviewing);		
		
		//add visit records for viewing	
		cm.addViewVisit(timeZone, bookid);
		
		//handle popularity matters.
		Popularity popularity = (Popularity) context.getAttribute("popularity");		
		popularity.updateView(bookid);		
		context.setAttribute("popularity", popularity); //set this attribute and inform listener

		String target = "/Home.jspx";
		request.getRequestDispatcher(target).forward(request, response);
	}

	
	//if user want to rate a specific book
	private void rateBook(HttpServletRequest request, HttpServletResponse response, HttpSession session, CartModel cm) throws ServletException, IOException{
		String bookid = (String) request.getAttribute("bookid");
		byte score = Byte.parseByte(request.getParameter("rating_"+bookid));
		
		cm.addRating(bookid, score);
		Collection<BookBean> bookInSearchList = (Collection<BookBean>) session.getAttribute("books");	
		BookInfoBean bookPreviewing = cm.getBookPreview(bookid, bookInSearchList);
		session.setAttribute("bookPreviewing", bookPreviewing);
		
		String target = "/Home.jspx";
		request.getRequestDispatcher(target).forward(request, response);
	}
	
	
	//if user want to write comment to a specific book
	private void commentToBook(HttpServletRequest request, HttpServletResponse response, HttpSession session, CartModel cm) throws ServletException, IOException{
		String bookid = (String) request.getAttribute("bookid");
		String comment = request.getParameter("comment_"+bookid);
		
		cm.commentBook(bookid, comment);
		Collection<BookBean> bookInSearchList = (Collection<BookBean>) session.getAttribute("books");	
		BookInfoBean bookPreviewing = cm.getBookPreview(bookid, bookInSearchList);
		session.setAttribute("bookPreviewing", bookPreviewing);
		
		String target = "/Home.jspx";
		request.getRequestDispatcher(target).forward(request, response);
	}

	
	//If user add book into shopping cart
	//Need add book into VisitEvent
	private void addBookToCart(HttpServletRequest request, HttpServletResponse response, HttpSession session, CartModel cm) throws ServletException, IOException{
		String bookid = (String) request.getAttribute("bookid");
		
		Collection<BookBean> bookInSearchList = (Collection<BookBean>) session.getAttribute("books");
		CartBean cart = (CartBean) session.getAttribute("cartlist");		
		cart = cm.getCartWithOneBookAdded(cart, bookInSearchList, bookid);
		session.setAttribute("cartlist", cart);
		
		//add visit records for adding items to shopping cart
		cm.addCartVisit(timeZone, bookid);
		
		//handle popularity matters
		Popularity popularity = (Popularity) context.getAttribute("popularity");		
		popularity.updateCart(bookid);		
		context.setAttribute("popularity", popularity); //set this attribute and inform listener		

		session.removeAttribute("bookPreviewing");
		String target = "/Home.jspx";
		request.getRequestDispatcher(target).forward(request, response);
	}
	
	
	private void moveToCartPage(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			CartModel cm) throws ServletException, IOException {
		CartBean cart = (CartBean) session.getAttribute("cartlist");
		cart.computeTotal();
		

		session.removeAttribute("bookPreviewing");
		session.setAttribute("lastTarget", "/Home.jspx");			// record last visit page
		String target = "/Cart.jspx";
		request.getRequestDispatcher(target).forward(request, response);
	}
	
	
	//if user plus one book in his shopping cart
	private void plusOneBookInCart(HttpServletRequest request, HttpServletResponse response, HttpSession session, CartModel cm) throws ServletException, IOException{
		String bookID = (String) request.getAttribute("bookid");
		CartBean cart = (CartBean) session.getAttribute("cartlist");
		
		cart.addOneExistItemByBid(bookID);
		cart.computeTotal();
		request.removeAttribute("bookid");
		
		String target = "/Cart.jspx";
		request.getRequestDispatcher(target).forward(request, response);
	}
	
	
	//if user minus one book in his shopping cart
	private void minusOneBookInCart(HttpServletRequest request, HttpServletResponse response, HttpSession session, CartModel cm) throws ServletException, IOException{
		String bookID = (String) request.getAttribute("bookid");
		CartBean cart = (CartBean) session.getAttribute("cartlist");
		
		cart.removeOneExistItemByBid(bookID);
		cart.computeTotal();

		request.removeAttribute("bookid");
		
		String target = "/Cart.jspx";
		request.getRequestDispatcher(target).forward(request, response);
	}
	
	
	//if user remove a specific book from his shopping cart
	private void removeBookInCart(HttpServletRequest request, HttpServletResponse response, HttpSession session, CartModel cm) throws ServletException, IOException{
		String bookID = (String) request.getAttribute("bookid");
		CartBean cart = (CartBean) session.getAttribute("cartlist");
		
		cart.deleteItemByBid(bookID);	
		cart.computeTotal();

		request.removeAttribute("bookid");
		
		String target = "/Cart.jspx";
		request.getRequestDispatcher(target).forward(request, response);
	}
	
	
	//if user goes to payment page and start enter his payment information
	private void makePayment(HttpServletRequest request, HttpServletResponse response, HttpSession session, CartModel cm) throws ServletException, IOException{
		session.setAttribute("lastTarget", "/Cart.jspx");

		CartBean cart = (CartBean) session.getAttribute("cartlist");
		cart.computeTotal();
		if(session.getAttribute("someuserLogin") != null){
			cart = (CartBean) session.getAttribute("cartlist");

			System.out.println(cart);
			String target = "/Payment.jspx";
			request.getRequestDispatcher(target).forward(request, response);
		} 
		else{
			String target = "/Login.jspx";
			request.getRequestDispatcher(target).forward(request, response);
		}
	}	
	
	//user go back to search page.
	private void searchAgain(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			CartModel cm) throws ServletException, IOException {
		String target = "/Home.jspx";
		request.getRequestDispatcher(target).forward(request, response);
	}

	
	
	//If user submit login request to server
	private void userLogin(HttpServletRequest request, HttpServletResponse response, HttpSession session, CartModel cm) throws ServletException, IOException{
		String username = request.getParameter("user");
		String password = request.getParameter("pswd");

		int uid = cm.getLoginUid(username, password);	
		String target = "";
		if(uid==0){
			target = "/Login.jspx";
		}
		else{
			session.setAttribute("someuserLogin", uid+":"+username+":"+password);
			target = "/Payment.jspx";
		}
		request.getRequestDispatcher(target).forward(request, response);		
		
	}
	
	private void doRegister(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			CartModel cm) throws ServletException, IOException {
		request.setAttribute("userwantregister", "true");
		String target = "/Login.jspx";
		request.getRequestDispatcher(target).forward(request, response);	
	}
		
	//If user register a new account
	private void createUser(HttpServletRequest request, HttpServletResponse response, HttpSession session, CartModel cm) throws ServletException, IOException{
		String fname = request.getParameter("fname");
		String lname = request.getParameter("lname");
		String username = request.getParameter("user");
		String password = request.getParameter("pswd1");

		boolean success = cm.register(lname, fname, username, password);
		if(success){
			System.out.println("user account is successfully created");
			request.setAttribute("msg", "user account is successfully created");
		} else {
			System.out.println("user account is not created");
			request.setAttribute("msg", "user account is not created");
		}
	
		String target = "/Login.jspx";
		request.getRequestDispatcher(target).forward(request, response);
	}
	
	
	private void logoutUser(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			CartModel cm) throws ServletException, IOException {
		session.removeAttribute("someuserLogin");
		String target = "/Login.jspx";
		request.getRequestDispatcher(target).forward(request, response);	
	}
	
	
	//if user submit payment (after enter his payment information)
	//Need add book into VisitEvent
	private void submitPayment(HttpServletRequest request, HttpServletResponse response, HttpSession session, CartModel cm) throws ServletException, IOException{
		String target = "/Payment.jspx";
		
		if(session.getAttribute("someuserLogin") != null){
			
			boolean paySuccess = false;
			
			String userInfo = (String) session.getAttribute("someuserLogin");			
			String userid = userInfo.split(":")[0];
			int uid = Integer.parseInt(userid);
			
			String street = request.getParameter("streetNum") + " " + request.getParameter("streetName");
			String province = request.getParameter("province");
			String country = request.getParameter("country");
			String zip = request.getParameter("zip");
			String phone = request.getParameter("phone");
			AddressBean address = cm.getAddress(street, province, country, zip, phone);
			
			CartBean cart = (CartBean) session.getAttribute("cartlist");
			ArrayList<CartItemBean> items = cart.getItems();
			ArrayList<CartItemBean> realItem = new ArrayList<CartItemBean>();
			realItem = cm.getRealCartItem(items);              // get item list with all 0 quantity items removed						
			
			// hard code every 3rd order request failed.
			try{
				if(context.getAttribute("orderRequestCount") == null){
					int count = 1;
					context.setAttribute("orderRequestCount", count);
					cm.clearingPayment(uid, address, realItem);        //return book item without 0 quantity.	
					paySuccess = true;					
				}else{
					int count = (int) context.getAttribute("orderRequestCount");
					count++;					
					if(count % 3 == 0){
						paySuccess = false;
					}else{
						cm.clearingPayment(uid, address, realItem);        //return book item without 0 quantity.	
						paySuccess = true;
					}					
					context.setAttribute("orderRequestCount", count);
				}
			}catch(Exception e){
				paySuccess = false;
			}
			
			// if success, set popularity and visit event.
			if(paySuccess == true){
				Popularity popularity = (Popularity) context.getAttribute("popularity");
				popularity.updatePurchase(realItem);
				context.setAttribute("popularity", popularity); //set this attribute and inform listener
						
				cm.addPurchaseVisit(timeZone, realItem);
				session.removeAttribute("cartlist");            //when successfully finish purchase then remove shopping cart
			}
			
			session.setAttribute("lastTarget", "/Payment.jspx");								
			request.setAttribute("paySuccess", paySuccess);
			target = "/Result.jspx";		
		}
		else{
			target = "/Login.jspx";
		}
		request.getRequestDispatcher(target).forward(request, response);

	}

	

	private void goBack(HttpServletRequest request, HttpServletResponse response, HttpSession session, CartModel cm) throws ServletException, IOException {
		String target = (String) session.getAttribute("lastTarget");
		request.getRequestDispatcher(target).forward(request, response);
	}

	
	private void reStartBrowsing(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			CartModel cm) throws ServletException, IOException {
		session.removeAttribute("lastTarget");
		String target = "/Home.jspx";
		request.getRequestDispatcher(target).forward(request, response);		
	}

	
	private void setupAjaxAddress(HttpServletRequest request, HttpServletResponse response, HttpSession session, CartModel cm) {
		String ajaxAddress = request.getRequestURI() + "/Ajax/";
		session.setAttribute("ajaxAddress", ajaxAddress);
	}
	
	/**
	 * Check whether there is an attribute name matches the specific pattern
	 * if matches then set attribute into request with attribute name bookid
	 * @param request
	 * @param pattern
	 * @return
	 */
	private boolean checkName(HttpServletRequest request, String pattern){
		Enumeration<String> names = request.getParameterNames();
		boolean hasPattern = false;
		while(names.hasMoreElements()){
			String buttonname = names.nextElement();
			if(buttonname.matches(pattern)){
				request.setAttribute("bookid", buttonname.split("_")[1]);
				hasPattern = true;
				break;
			}
		}
		return hasPattern;
	}
		
}
