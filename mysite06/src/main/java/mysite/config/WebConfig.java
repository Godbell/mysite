package mysite.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

import mysite.web.FileUploadConfig;
import mysite.web.LocaleConfig;
import mysite.web.MvcConfig;

@Configuration
@EnableAspectJAutoProxy
@Import({
    LocaleConfig.class,
    MvcConfig.class,
    FileUploadConfig.class
})
@ComponentScan({"mysite.controller", "mysite.exception"})
public class WebConfig {
}
