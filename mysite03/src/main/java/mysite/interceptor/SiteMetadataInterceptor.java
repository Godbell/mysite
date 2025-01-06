package mysite.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mysite.service.SiteService;
public class SiteMetadataInterceptor implements HandlerInterceptor {
    private final SiteService siteService;

    public SiteMetadataInterceptor(SiteService siteService) {
        this.siteService = siteService;
    }

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) {
        req.setAttribute("metadata", siteService.getSiteMetadata());
        return true;
    }
}
