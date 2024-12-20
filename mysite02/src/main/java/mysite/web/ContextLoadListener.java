package mysite.web;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
public class ContextLoadListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent e) {
        System.out.println("Application[MySite02] starts...");
        ServletContext context = e.getServletContext();
        String contextConfigLocation = context.getInitParameter("contextConfigLocation");
        System.out.println(contextConfigLocation);
    }

    @Override
    public void contextDestroyed(ServletContextEvent e) {
        /* DO NOTHING */
    }
}
