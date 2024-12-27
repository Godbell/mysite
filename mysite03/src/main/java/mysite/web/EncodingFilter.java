package mysite.web;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpFilter;

public class EncodingFilter extends HttpFilter implements Filter {
    private String encoding;

    @Override
    public void init(FilterConfig config) throws ServletException {
        encoding = config.getInitParameter("encoding");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws
        ServletException,
        IOException {
        /*
         * proeceed req
         */
        System.out.println("EncodingFilter.doFilter() called: request processing");

        req.setCharacterEncoding(encoding);
        System.out.println("Set encoding to " + encoding);

        chain.doFilter(req, res);

        /*
         * proceed res
         */
        System.out.println("EncodingFilter.doFilter() called: response processing");
    }

    @Override
    public void destroy() {}

}
