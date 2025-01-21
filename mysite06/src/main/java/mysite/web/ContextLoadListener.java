package mysite.web;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
public class ContextLoadListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent e) {
        System.out.println("Application[MySite05] starts...");
    }

    @Override
    public void contextDestroyed(ServletContextEvent e) {
        /* DO NOTHING */
    }
}
