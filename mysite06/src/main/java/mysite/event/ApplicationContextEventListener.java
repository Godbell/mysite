package mysite.event;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

import jakarta.servlet.ServletContext;
import mysite.service.SiteService;
import mysite.vo.SiteMetadata;

public class ApplicationContextEventListener {
    private final SiteService siteService;

    public ApplicationContextEventListener(SiteService siteService) {
        this.siteService = siteService;
    }

    @EventListener(ContextRefreshedEvent.class)
    public void handleContextRefreshedEvent(ContextRefreshedEvent event) {
        final String METADATA_BEAN_NAME = "siteMetadata";
        // Retrieve the application context
        BeanDefinitionRegistry registry = (BeanDefinitionRegistry)event.getApplicationContext()
            .getAutowireCapableBeanFactory();

        // Check if the bean is already registered
        if (registry.containsBeanDefinition(METADATA_BEAN_NAME)) {
            registry.removeBeanDefinition(METADATA_BEAN_NAME);
        }

        // Define properties for the new bean
        MutablePropertyValues propertyValues = new MutablePropertyValues();
        propertyValues.add("title", siteService.getSiteMetadata().getTitle());

        // Create a GenericBeanDefinition for the bean
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(SiteMetadata.class); // Set the class for the bean
        beanDefinition.setPropertyValues(propertyValues); // Add properties to the bean
        beanDefinition.setScope("singleton"); // Set the bean scope

        // Register the bean definition
        registry.registerBeanDefinition(METADATA_BEAN_NAME, beanDefinition);

        // Optionally, update ServletContext with the new bean instance
        ServletContext servletContext = event.getApplicationContext().getBean(ServletContext.class);
        SiteMetadata metadata = (SiteMetadata)event.getApplicationContext().getBean(METADATA_BEAN_NAME);
        servletContext.setAttribute(METADATA_BEAN_NAME, metadata);

        System.out.println("-- Dynamic Bean Registered -- " + metadata);
    }
}
