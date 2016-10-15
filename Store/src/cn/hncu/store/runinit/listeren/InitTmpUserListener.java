package cn.hncu.store.runinit.listeren;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import cn.hncu.store.domain.User;

/**
 * Application Lifecycle Listener implementation class InitTmpUserListener
 *
 */
@WebListener
public class InitTmpUserListener implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public InitTmpUserListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent sce) {
        Map<String, User> map=new HashMap<String, User>(0);
        sce.getServletContext().setAttribute("tmpUsers", map);
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent sce) {
        // TODO Auto-generated method stub
    }
	
}
