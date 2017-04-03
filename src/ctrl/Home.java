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
	private BookDAO bookDao;
	private LoginDAO loginDao;
	private PurchaseOrderDAO purchaseDao;
	private BookReviewDAO reviewDao;
	private VisitEventDAO visitDao;
	private AnalyticsModel analyticsmodel;
	
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
			bookDao = new BookDAO();
			loginDao = new LoginDAO();
			purchaseDao = new PurchaseOrderDAO();
			reviewDao = new BookReviewDAO();
			visitDao = new VisitEventDAO();
			analyticsmodel = new AnalyticsModel();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	boolean debug=true;
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		System.out.println(request.getQueryString());
		
		if(debug){
		Enumeration enu = request.getParameterNames();
		while(enu.hasMoreElements()){
			String varname = (String) enu.nextElement();
			System.out.println(varname+": "+request.getParameter(varname));
		}
			
		}
		
		//*****************************user login*****************************
		if(request.getParameter("doLogin") != null){
			userLogin(request, response, session);
		}
		
		//*****************************user want register*****************************
		//forward to register page and let user fill account information
		else if(request.getParameter("doCreate") != null){
			request.setAttribute("userwantregister", "true");
			String target = "/Login.jspx";
			request.getRequestDispatcher(target).forward(request, response);
		}
		
		//*****************************user register*****************************
		else if(request.getParameter("doneCreate") != null){
			createUser(request, response, session);
		}
		
		//*****************************user logout*****************************
		else if(request.getParameter("logout") != null){
			session.removeAttribute("someuserLogin");
			String target = "/Home.jspx";
			request.getRequestDispatcher(target).forward(request, response);
		}
		//**********************Code related to home.jspx*******************************************
		//*********************************if user press search button**************************************
		//search books with specific title
		else if(request.getParameter("search") != null){
			System.out.println("user press search button");
			searchBook(request, response, session);
		}
		
		//user select a category
		 else if(request.getParameter("cat") != null){
		 	System.out.println("user select a category");
		 	searchBook(request, response, session);
		 }			
		
		//*********************************if user press add book to cart button**************************************
		else if(checkName(request, "^add_.*")){
			System.out.println("press add a book");
			addBookToCart(request, response, session);
		}
		
		
		//*********************************if user press review book**************************************
		else if(checkName(request, "^preview_.*")){
			//user can only review one book at each time
			System.out.println("user press review book");
			previewBookDetails(request, response, session);
		}
		
		
		//*********************************if user want rate this book**************************************
		else if(checkName(request, "^rate_.*")){
			System.out.println("user rate this book");
			rateBooks(request, response, session);
		}
		
		//*********************************if user want comment this book**************************************
		else if(checkName(request, "^commentbtn_.*")){
			System.out.println("user rate this book");
			commentToBooks(request, response, session);
		}
		
		
		
		//******************************if user press shoppingCart button*************************************
		//Forward to shopping cart page
		else if(request.getParameter("shoppingCart") != null){
			System.out.println("user press shoppingCart button");
			
			String target = "/Cart.jspx";
			session.setAttribute("lastTarget", target);
			request.getRequestDispatcher(target).forward(request, response);
		}
		
		
		//**********************Code related to cart.jspx****************************************
		//*****************************if user press payment button*********************************
		else if(request.getParameter("payment") != null){
			System.out.println("user goes to payment");
			userPayBooks(request, response, session);
		}
		
		//*****************************if user plus one book****************************************
		else if(checkName(request, "^plus_.*")){
			System.out.println("user add books");
			plusOneBookInCart(request, response, session);
		}
		
		//*****************************if user minus one book****************************************
		else if(checkName(request, "^minus_.*")){
			minusOneBookInCart(request, response, session);
		}
		
		//*****************************if user delete book from cart****************************************
		else if(checkName(request, "^delete_.*")){
			removeBookInCart(request, response, session);
		}
		
		//*****************************if user press back button to main page*******************************
		else if(request.getParameter("backFromCart") != null){
			response.sendRedirect("Cart");	   
		}
		
		//**********************Code related to payment.jspx****************************************
		//*****************************if user press submit payment button*********************************
		else if(request.getParameter("paySubmit") != null){
			System.out.println("user goes to submit payment");
			submitPayment(request, response, session);
		}
		

		
		//***************************if submit report for range of month************************
		else if(request.getParameter("submitReport") != null){
			
		}
		
		//default
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
				request.setAttribute("bookid", buttonname.split("_")[1]);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Check whether list contains string bid
	 * @param lists
	 * @param bid
	 * @return
	 */
	private BookBean listContains(Collection<BookBean> lists, String bid){
		for(BookBean book : lists){
			if(book.getBid().equals(bid)){
				return book;
			}
		}
		return null;
	}
	
	/**
	 * Test if any user is logging in
	 * @param session
	 * @return
	 */
	private boolean testUserLoggingIn(HttpSession session){
		if(session.getAttribute("someuserLogin") != null){
			return true;
		} else {
			return false;
		}
	}

	//If user submit login request to server
	private void userLogin(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException{
		String username = request.getParameter("user");
		String password = request.getParameter("pswd");

		try {
			int uid = loginDao.checkLogin(username, password);
			if(uid==0){
				String target = "/Login.jspx";
				request.getRequestDispatcher(target).forward(request, response);
			}
			else{
				session.setAttribute("someuserLogin", uid+":"+username+":"+password);
				String target = "/Home.jspx";
				if(session.getAttribute("lastTarget")!=null){
					target = (String) session.getAttribute("lastTarget");
				}
				request.getRequestDispatcher(target).forward(request, response);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//If user register a new account
	private void createUser(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException{
		String fname = request.getParameter("fname");
		String lname = request.getParameter("lname");
		String username = request.getParameter("user");
		String password = request.getParameter("pswd1");
		try {
			boolean success = loginDao.register(lname, fname, username, password);
			if(success){
				System.out.println("user account is successfully created");
				request.setAttribute("msg", "user account is successfully created");
			} else {
				System.out.println("user account is not created");
				request.setAttribute("msg", "user account is not created");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String target = "/Login.jspx";
		if(session.getAttribute("lastTarget")!=null){
			target = (String) session.getAttribute("lastTarget");
		}
		request.getRequestDispatcher(target).forward(request, response);
	}
	
	//If user search books
	private void searchBook(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException{
		String category = request.getParameter("cat");
		String searchText = request.getParameter("searchText");
		request.setAttribute("cat", category);
		request.setAttribute("searchText", searchText);
		
		HashMap<String, BookBean> result;
		try {
			result = bookDao.getBooks(searchText, Integer.MAX_VALUE, 0, category);
			Collection<BookBean> list = result.values();
			session.setAttribute("books", list); //set the search result book list into session
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		session.setAttribute("lastTarget", "/Home.jspx");
		String target = "/Home.jspx";
		request.getRequestDispatcher(target).forward(request, response);
	}
	
	//If user add book into shopping cart
	//Need add book into VisitEvent
	private void addBookToCart(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException{
		String bookid = (String) request.getAttribute("bookid");
		
		Popularity popularity = (Popularity) context.getAttribute("popularity");
		
		int containIndex = popularity.checkIfContains(bookid, 2);
		//if book not in popularity list (means no one put this book into shopping cart before)
		if(containIndex == -1){
			popularity.addBookToCart(new BookVisit(bookid, 1));
		}
		else{
			popularity.updateBookToCartByIndex(containIndex, 1);
		}
		context.setAttribute("popularity", popularity); //set this attribute and inform listener
		//if there are no cart (user not add anything to cart)
		if(session.getAttribute("cartlist")==null){ 
			CartBean cart = new CartBean(); //create a cart
			//check if the book want to add is inside the search result list
			@SuppressWarnings("unchecked")
			BookBean book = listContains((Collection<BookBean>) session.getAttribute("books"), bookid);
			//if the book you want to add is not inside search result list, then get book from database (not likely to happen)
			if(book==null){
				try {
					book = bookDao.getBookById(bookid); //get book information from database
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			CartItemBean item = new CartItemBean(book,1); //create a new shopping cart for this session
			cart.addItems(item);
			try{
				visitDao.addToVisitEvent(new DateBean(timeZone), bookid, "CART");
			} catch(SQLException e){
				e.printStackTrace();
			}
			session.setAttribute("cartlist", cart); //set shopping cart for this session
		}
		else{ //if there are something inside cart
			CartBean cart = (CartBean) session.getAttribute("cartlist"); //get shopping cart from session
			//check if the book want to add is inside the search result list
			@SuppressWarnings("unchecked")
			BookBean book = listContains((Collection<BookBean>) session.getAttribute("books"), bookid);
			//if the book you want to add is not inside search result list, then get book from database (not likely to happen)
			if(book==null){
				try {
					book = bookDao.getBookById(bookid);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(cart.addItems(new CartItemBean(book,1))){
				try{
					visitDao.addToVisitEvent(new DateBean(timeZone), bookid, "CART");
				} catch(SQLException e){
					e.printStackTrace();
				}
			}
		}
		request.removeAttribute("boodAddToCart");
		String target = "/Home.jspx";
		session.setAttribute("lastTarget", target);
		request.getRequestDispatcher(target).forward(request, response);
	}
	
	//If user want to view a specific book detail
	//Need add book into VisitEvent
	private void previewBookDetails(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException{
		String bookid = (String) request.getAttribute("bookid");
		
		Popularity popularity = (Popularity) context.getAttribute("popularity");
		int containIndex = popularity.checkIfContains(bookid, 1);
		//if book not in popularity list (means no one put this book into shopping cart before)
		if(containIndex == -1){
			popularity.addBookToView(new BookVisit(bookid, 1));
		}
		else{
			popularity.updateBookToViewByIndex(containIndex, 1);
		}
		context.setAttribute("popularity", popularity); //set this attribute and inform listener
		//check if the book want to review is inside the search result list
		@SuppressWarnings("unchecked")
		BookBean book = listContains((Collection<BookBean>) session.getAttribute("books"), bookid);
		//if the book you want to review is not inside search result list, then get book information from database
		if(book==null){
			try {
				book = bookDao.getBookById(bookid);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		//get all review information for this book and add to session
		//if there is SQL exception then book review will not add
		ArrayList<ReviewBean> reviews;
		try {
			reviews = reviewDao.getReview(bookid);
			double overallRating = reviewDao.getOverallRating(bookid);
			BookInfoBean bookReviewing = new BookInfoBean(book, reviews, overallRating);
			session.setAttribute("bookPreviewing", bookReviewing);
			//add this review to visit event
			visitDao.addToVisitEvent(new DateBean(timeZone), bookid, "VIEW");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String target = "/Home.jspx";
		session.setAttribute("lastTarget", target);
		request.getRequestDispatcher(target).forward(request, response);
	}
	
	//if user want to rate a specific books
	private void rateBooks(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException{
		String bookid = (String) request.getAttribute("bookid");
		byte score = Byte.parseByte(request.getParameter("rating_"+bookid));
		try {
			reviewDao.addRating(bookid, score);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String target = "/Home.jspx";
		session.setAttribute("lastTarget", target);
		request.getRequestDispatcher(target).forward(request, response);
	}
	
	//if user want to write comment to a specific books
	private void commentToBooks(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException{
		String bookid = (String) request.getAttribute("bookid");
		String comment = request.getParameter("comment_"+bookid);
		DateBean date = new DateBean(-4);
		String dateString = date.formatDateToYYYYMMDD();
		ReviewBean review = new ReviewBean(bookid, comment, dateString);
		try {
			reviewDao.addReview(review);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String target = "/Home.jspx";
		session.setAttribute("lastTarget", target);
		request.getRequestDispatcher(target).forward(request, response);
	}
	
	//if user goes to payment page and start enter his payment information
	private void userPayBooks(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException{
		session.setAttribute("lastTarget", "/Payment.jspx");
		if(testUserLoggingIn(session)){
			CartBean cart = (CartBean) request.getSession().getAttribute("cartlist");
			System.out.println(cart);
			String target = "/Payment.jspx";
			request.getRequestDispatcher(target).forward(request, response);
		} 
		else{
			String target = "/Login.jspx";
			request.getRequestDispatcher(target).forward(request, response);
		}
	}
	
	//if user plus one book in his shopping cart
	private void plusOneBookInCart(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException{
		String bookID = (String) request.getAttribute("bookid");
		CartBean cart = (CartBean) session.getAttribute("cartlist");
		cart.addOneExistItemByBid(bookID);

		request.removeAttribute("bookid");
		String target = "/Cart.jspx";
		request.getRequestDispatcher(target).forward(request, response);
	}
	
	//if user minus one book in his shopping cart
	private void minusOneBookInCart(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException{
		String bookID = (String) request.getAttribute("bookid");
		CartBean cart = (CartBean) session.getAttribute("cartlist");
		cart.removeOneExistItemByBid(bookID);
		
		request.removeAttribute("bookid");
		String target = "/Cart.jspx";
		request.getRequestDispatcher(target).forward(request, response);
	}
	
	//if user remove a specific book from his shopping cart
	private void removeBookInCart(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException{
		String bookID = (String) request.getAttribute("bookid");
		CartBean cart = (CartBean) session.getAttribute("cartlist");
		cart.deleteItemByBid(bookID);
		request.removeAttribute("bookid");
		String target = "/Cart.jspx";
		request.getRequestDispatcher(target).forward(request, response);
	}
	
	//if user submit payment (after enter his payment information)
	//Need add book into VisitEvent
	private void submitPayment(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException{
		if(testUserLoggingIn(session)){
			String userInfo = (String) session.getAttribute("someuserLogin");
			String userid = userInfo.split(":")[0];
			int uid = Integer.parseInt(userid);
			AddressBean address = new AddressBean();
			String street = request.getParameter("streetNum")+" "+request.getParameter("streetName");
			address.setStreet(street);
			address.setProvince(request.getParameter("province"));
			address.setCountry(request.getParameter("country"));
			address.setZip(request.getParameter("zip"));
			address.setPhone(request.getParameter("phone"));
			
			Popularity popularity = (Popularity) context.getAttribute("popularity");

			CartBean cart = (CartBean) session.getAttribute("cartlist");
			ArrayList<CartItemBean> items = cart.getItems();
			ArrayList<CartItemBean> realItem = new ArrayList<CartItemBean>();
			//remove all item with quantity of 0
			for(int i=0; i<items.size(); i++){
				CartItemBean item = items.get(i);
				if(item.getQuantity()>0){
					int containIndex = popularity.checkIfContains(item.getItem().getBid(), 3);
					//if book not in popularity list (means no one put this book into shopping cart before)
					if(containIndex == -1){
						popularity.addBookToView(new BookVisit(item.getItem().getBid(), item.getQuantity()));
					}
					else{
						popularity.updateBookToViewByIndex(containIndex, item.getQuantity());
					}
					context.setAttribute("popularity", popularity); //set this attribute and inform listener
					realItem.add(item);
				}
			}
			//Purchase order ID will change after accessing database
			OrderBean order = new OrderBean(realItem, 0, "ORDERED", address, uid);
			try {
				purchaseDao.doPurchase(order);
				visitDao.addShoppingCartToVisitEvent(new DateBean(timeZone), cart.getItems(), "PURCHASE");
				session.removeAttribute("cartlist"); //when successfully finish purchase then remove shopping cart
			} catch (SQLException e) {
				e.printStackTrace();
			}
			String target = "/Payment.jspx";
			request.getRequestDispatcher(target).forward(request, response);
		}
		else{
			String target = "/Login.jspx";
			request.getRequestDispatcher(target).forward(request, response);
		}
	}
	
}
