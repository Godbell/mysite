package mysite.security;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import jakarta.servlet.http.HttpServletRequest;
import mysite.vo.UserVo;
public class AuthUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        AuthUser authUser = parameter.getParameterAnnotation(AuthUser.class);

        if (authUser == null || !parameter.getParameterType().equals(UserVo.class)) {
            return false;
        }

        return true;
    }

    @Override
    public Object resolveArgument(
        MethodParameter parameter,
        ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest,
        WebDataBinderFactory binderFactory
    ) throws Exception {
        if (!supportsParameter(parameter)) {
            return WebArgumentResolver.UNRESOLVED;
        }

        HttpServletRequest req = (HttpServletRequest)webRequest.getNativeRequest();
        return req.getSession().getAttribute("authUser");
    }
}
