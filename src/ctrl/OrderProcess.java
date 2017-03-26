package ctrl;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.OrderProcessModel;

/**
 * Servlet implementation class OrderProcess
 */
@WebServlet("/OrderProcess")
public class OrderProcess extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrderProcess() {
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
			OrderProcessModel op = new OrderProcessModel();
			getServletContext().setAttribute("orderProcess", op);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		OrderProcessModel op = (OrderProcessModel) getServletContext().getAttribute("orderProcess");
		if (request.getQueryString() == null && request.getMethod().equals("GET")) {
			if(request.getSession().getAttribute("login") == null){
				String jspTarget = "/Login.jspx";
				request.setAttribute("jspTarget", jspTarget);
			}else{
				String jspTarget = "/Payment.jspx";
				request.setAttribute("jspTarget", jspTarget);
			}			
			String target = "/OrderProcess.jspx";
			request.getRequestDispatcher(target).forward(request, response);
		}else if(request.getParameter("back") != null){
			response.sendRedirect("OrderProcess");	    // Reserved for "back" button. Will implment if time permits.
		}else{
			if(request.getParameter("doLogin") != null){
				try{
					String userName = request.getParameter("user");
					String passWord = request.getParameter("pswd");
					
					// do code here.
					
					String jspTarget = "/Payment.jspx";
					request.setAttribute("jspTarget", jspTarget);
				}catch(Exception e){
					String jspTarget = "/Login.jspx";
					request.setAttribute("jspTarget", jspTarget);
					request.setAttribute("errorMsg", e.getMessage());
				}
				String target = "/OrderProcess.jspx";
				request.getRequestDispatcher(target).forward(request, response);
				
			}else if(request.getParameter("doCreate") != null){
				String jspTarget = "/Create.jspx";
				request.setAttribute("jspTarget", jspTarget);
				String target = "/OrderProcess.jspx";
				request.getRequestDispatcher(target).forward(request, response);
			}else if(request.getParameter("newCreate") != null){
				try{
					String newUserName = request.getParameter("newUser");
					// do code here....
					
					String jspTarget = "/Login.jspx";
					request.setAttribute("jspTarget", jspTarget);
				}catch(Exception e){
					String jspTarget = "/Create.jspx";
					request.setAttribute("jspTarget", jspTarget);
					request.setAttribute("errorMsg", e.getMessage());
				}
				String target = "/OrderProcess.jspx";
				request.getRequestDispatcher(target).forward(request, response);
			}else if(request.getParameter("logout") != null){
				request.getSession().removeAttribute("login");
				response.sendRedirect("OrderProcess");
			}else if(request.getParameter("verify") != null){
				String CCN = request.getParameter("CCN");
				// do coding here...
				
				String jspTarget = "/Result.jspx";
				request.setAttribute("jspTarget", jspTarget);
				String target = "/OrderProcess.jspx";
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
