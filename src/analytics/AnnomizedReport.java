package analytics;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.Filter;
import javax.servlet.DispatcherType;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import DAO.CustomerDAO;
import bean.AnnomizedReportBean;
import bean.DateBean;
import model.AnalyticsModel;
import model.AnnomizedReportWrapper;

/**
 * Servlet Filter implementation class AnnomizedReport
 */
@WebFilter(dispatcherTypes = {DispatcherType.REQUEST}, urlPatterns = {"/Admin", "/Admin/*"}, servletNames = {"Admin"})
public class AnnomizedReport implements Filter {

	private AnalyticsModel analytics;
	private int timezone=-4;
    /**
     * Default constructor. 
     * @throws ClassNotFoundException 
     */
    public AnnomizedReport(){
    	
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		if(request.getParameter("submitAnnomizedReport") != null){
			try {
				String generateDate = (new DateBean(timezone)).formatDateToYYYYMMDD();
				String orderBy = request.getParameter("orderBy");
				String order = request.getParameter("order");
				AnnomizedReportWrapper report = analytics.getAnnomizedReport(generateDate, orderBy, order);
				//annomize user name information
				ArrayList<AnnomizedReportBean> users = report.getAnnomizedUser();
				for(int i=0; i<users.size(); i++){
					users.get(i).setFname("****");
					users.get(i).setLname("****");
				}
				request.setAttribute("annomizedReport", report);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		try {
			analytics = new AnalyticsModel();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
		
	}
	

}
