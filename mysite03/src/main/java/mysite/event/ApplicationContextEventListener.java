package mysite.event;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

import jakarta.servlet.ServletContext;
import mysite.service.SiteService;
import mysite.web.SiteMetadata;
public class ApplicationContextEventListener {
    private final String metadataBeanName = "metadata";

    @EventListener({ContextRefreshedEvent.class})
    public void handlerContextRefreshedEvent(ContextRefreshedEvent event) {
        SiteService siteService = event.getApplicationContext().getBean(SiteService.class);

        SiteMetadata metadata = siteService.getSiteMetadata();

        ConfigurableListableBeanFactory beanFactory =
            ((ConfigurableApplicationContext)event.getApplicationContext())
                .getBeanFactory();

        if (beanFactory.containsBean(metadataBeanName)) {
            beanFactory.destroySingletons();
        }

        beanFactory.registerSingleton(metadataBeanName, metadata);

        ServletContext servletContext =
            event.getApplicationContext().getBean(ServletContext.class);
        servletContext.setAttribute(metadataBeanName, metadata);

        System.out.println("-- Context Refreshed Event Received --" + siteService);
    }
}
