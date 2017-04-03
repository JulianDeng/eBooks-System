package analytics;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.annotation.WebListener;

/**
 * Application Lifecycle Listener implementation class MostPopular
 *
 */
@WebListener
public class MostPopular implements ServletContextAttributeListener {

    /**
     * Default constructor. 
     */
    public MostPopular() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextAttributeListener#attributeAdded(ServletContextAttributeEvent)
     */
    public void attributeAdded(ServletContextAttributeEvent arg0) { 
         // TODO Auto-generated method stub
    }

	/**
     * @see ServletContextAttributeListener#attributeRemoved(ServletContextAttributeEvent)
     */
    public void attributeRemoved(ServletContextAttributeEvent arg0) { 
         // TODO Auto-generated method stub
    }

	/**
     * @see ServletContextAttributeListener#attributeReplaced(ServletContextAttributeEvent)
     */
    public void attributeReplaced(ServletContextAttributeEvent arg0) {
    	if(arg0.getName().equals("popularity")){
	    	ServletContext context = arg0.getServletContext();
	    	Popularity popularity = (Popularity) context.getAttribute("popularity");
	    	context.setAttribute("mostPopularViewed", popularity.mostPopularViewedBook());
	    	context.setAttribute("mostPopularCarted", popularity.mostPopularCartedBook());
	    	context.setAttribute("mostPopularPurchased", popularity.mostPopularPurchasedBook());
    	}
    }
	
}
