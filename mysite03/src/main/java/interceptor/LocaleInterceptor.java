package interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.LocaleResolver;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
public class LocaleInterceptor implements HandlerInterceptor {
    private final LocaleResolver localeResolver;

    public LocaleInterceptor(LocaleResolver localeResolver) {
        this.localeResolver = localeResolver;
    }

    @Override
    public boolean preHandle(
        HttpServletRequest request,
        HttpServletResponse response,
        Object handler
    ) throws Exception {
        String lang = localeResolver.resolveLocale(request).getLanguage();
        request.setAttribute("lang", lang);

        return true;
    }
}
