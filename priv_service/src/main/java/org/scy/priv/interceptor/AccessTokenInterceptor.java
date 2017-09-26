package org.scy.priv.interceptor;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * AccessToken 拦截器
 * Created by shicy on 2017/9/26.
 */
public class AccessTokenInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean validate = super.preHandle(request, response, handler);

        if (validate) {
            if (!handler.getClass().isAssignableFrom(HandlerMethod.class)) {
                return false;
            }

            String accessToken = request.getHeader("access_token");
            Method method = ((HandlerMethod)handler).getMethod();
            Class cls = method.getDeclaringClass();
        }

        return validate;
    }

}
