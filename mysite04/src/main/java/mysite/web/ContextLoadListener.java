package mysite.web;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import mysite.service.SiteService;
public class ContextLoadListener implements ServletContextListener {
    private final SiteService siteService;

    public ContextLoadListener(SiteService siteService) {
        this.siteService = siteService;
    }

    @Override
    public void contextInitialized(ServletContextEvent e) {
        System.out.println("Application[MySite03] starts...");
    }

    @Override
    public void contextDestroyed(ServletContextEvent e) {
        /* DO NOTHING */
    }
}
