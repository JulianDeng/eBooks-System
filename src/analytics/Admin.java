package analytics;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBException;

import org.xml.sax.SAXException;

import DAO.BookDAO;
import DAO.BookReviewDAO;
import DAO.LoginDAO;
import DAO.PurchaseOrderDAO;
import DAO.VisitEventDAO;
import bean.BookBean;
import model.AnalyticsModel;
import model.AnnomizedReportWrapper;
import model.CartModel;
import model.OrderProcessModel;
import model.OrderWrapper;
import model.BookVisitReportWrapper;

/**
 * Servlet implementation class Admin
 */
@WebServlet({"/Admin", "/Admin/*"})
public class Admin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private ServletContext context;
	private AnalyticsModel analyticsmodel;
	private VisitEventDAO visitDao;
	private LoginDAO loginDao;
	private BookDAO bookDao;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Admin() {
        super();
        // TODO Auto-generated constructor stub
    }

	public void init() throws ServletException {
		super.init();
		try{
			CartModel cm = new CartModel();
			Popularity popularity = new Popularity();
			context = this.getServletContext();
			context.setAttribute("cartModel", cm);
			context.setAttribute("popularity", popularity);
			loginDao = new LoginDAO();
			visitDao = new VisitEventDAO();
			analyticsmodel = new AnalyticsModel();
			bookDao = new BookDAO();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		
		//************************For Administrator login*********************************

		//if admin want to visit website (check whether he is logging in, if not then let admin to login)
		if(session.getAttribute("AdminLoggingIn")!=null && session.getAttribute("AdminLoggingIn").equals("true")){
			System.out.println("AdminLoggingIn");
			//if admin submit 
			if(request.getParameter("submitMonthVisitReport") != null){
				try {
					String eventtype = request.getParameter("eventtype");
					String year = request.getParameter("year");
					String month = request.getParameter("month");
					if(month.length()==1) month = "0"+month;
					BookVisitReportWrapper report = visitDao.getReport(eventtype, year+month+"01");
					
					String f = "analytics"+ File.separator + "monthVisitReport.xml";
					String filename = this.getServletContext().getRealPath(File.separator + f);
					System.out.println(filename);
					analyticsmodel.exportVisitReport(report, filename);
					
					request.setAttribute("monthReport", f);
					request.getRequestDispatcher("AnalysisResult.jspx").forward(request, response);
				} catch (SQLException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			else if(request.getParameter("submitVisitReport") != null){
				try {
					String eventtype = request.getParameter("eventtype");
					String yearbegin = request.getParameter("yearbegin");
					String monthbegin = request.getParameter("monthbegin");
					String yearend = request.getParameter("yearend");
					String monthend = request.getParameter("monthend");
					if(monthbegin.length()==1) monthbegin = "0"+monthbegin;
					if(monthend.length()==1) monthend = "0"+monthend;
					String dateBegin = yearbegin+monthbegin+"00";
					String dateEnd = yearend+monthend+"00";
					BookVisitReportWrapper report = visitDao.getReport(eventtype, dateBegin, dateEnd);
					
					String f = "analytics"+ File.separator + "monthVisitReport.xml";
					String filename = this.getServletContext().getRealPath(File.separator + f);
					analyticsmodel.exportVisitReport(report, filename);
					
					request.setAttribute("branchOfMonthReport", f);
					request.getRequestDispatcher("AnalysisResult.jspx").forward(request, response);
				} catch (SQLException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			else if(request.getParameter("submitPopular") != null){
				if(request.getParameter("eventtype").equals("view")){
					request.setAttribute("mostpopular", context.getAttribute("mostPopularViewed"));
				}
				else if(request.getParameter("eventtype").equals("cart")){
					request.setAttribute("mostpopular", context.getAttribute("mostPopularCarted"));
				}
				else if(request.getParameter("eventtype").equals("purchase")){
					request.setAttribute("mostpopular", context.getAttribute("mostPopularPurchased"));
				}
				String target = "/Analytics.jspx";
				request.getRequestDispatcher(target).forward(request, response);
			}
			else if(request.getParameter("submitAnnomizedReport") != null){
				try {
					AnnomizedReportWrapper report = (AnnomizedReportWrapper) request.getAttribute("annomizedReport");
					String f = "analytics"+ File.separator + "annomizedReport.xml";
					String filename = this.getServletContext().getRealPath(File.separator + f);
					System.out.println(filename);
					analyticsmodel.exportAnnomizedReport(report, filename);
					request.setAttribute("annomizedReport", f);
					request.getRequestDispatcher("AnalysisResult.jspx").forward(request, response);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			else{
				String target = "/Analytics.jspx";
				request.getRequestDispatcher(target).forward(request, response);
			}

		}
		//if admin is not logging in
		else{
			System.out.println("Admin not login");
			try {
				if(request.getParameter("adminLogin") != null){
					System.out.println("adminLogin is not null");
					String adminUserName = request.getParameter("adminUserName");
					String adminPassword = request.getParameter("adminPassword");
					boolean success = loginDao.adminLogin(adminUserName, adminPassword);
					if(success){
						session.setAttribute("AdminLoggingIn", "true");
						String target = "/Analytics.jspx";
						request.getRequestDispatcher(target).forward(request, response);
					} 
					else {
						String target = "/AdminLogin.jspx";
						request.getRequestDispatcher(target).forward(request, response);
					}
				}
				else{
					String target = "/AdminLogin.jspx";
					request.getRequestDispatcher(target).forward(request, response);
				}
			} catch (SQLException e) {
				e.printStackTrace();
				String target = "/AdminLogin.jspx";
				request.getRequestDispatcher(target).forward(request, response);
			}
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
