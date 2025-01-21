package mysite.web;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import mysite.event.ApplicationContextEventListener;

@Configuration
@EnableWebMvc
@PropertySource("classpath:/mysite/config/web/fileupload.properties")
public class MvcConfig implements WebMvcConfigurer {
    private final Environment env;

    public MvcConfig(Environment env) {
        this.env = env;
    }

    // View Resolver
    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");
        viewResolver.setExposeContextBeansAsAttributes(true);
        viewResolver.setExposedContextBeanNames("site");

        return viewResolver;
    }

    // Message Conveter
    @Bean
    public StringHttpMessageConverter stringHttpMessageConverter() {
        StringHttpMessageConverter messageConverter = new StringHttpMessageConverter();
        messageConverter.setSupportedMediaTypes(
            Arrays.asList(
                new MediaType("text", "html", Charset.forName("utf-8"))
            )
        );

        return messageConverter;
    }

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder()
            .indentOutput(true)
            .dateFormat(new SimpleDateFormat("yyyy-mm-dd hh:MM:ss"));

        MappingJackson2HttpMessageConverter messageConveter = new MappingJackson2HttpMessageConverter(builder.build());
        messageConveter.setSupportedMediaTypes(
            Arrays.asList(
                new MediaType("application", "json", Charset.forName("utf-8"))
            )
        );

        return messageConveter;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(stringHttpMessageConverter());
        converters.add(mappingJackson2HttpMessageConverter());
    }

    // ApplicationContextEventListener
    @Bean
    public ApplicationContextEventListener applicationContextEventListener() {
        return new ApplicationContextEventListener();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(env.getProperty("fileupload.resourceUrl") + "**")
            .addResourceLocations(env.getProperty("fileupload.location"));
        registry.addResourceHandler("assets/**")
            .addResourceLocations("classpath:/assets/");
    }
}