package org.scy.priv.interceptor;

import org.apache.commons.lang3.StringUtils;
import org.scy.common.utils.HttpUtilsEx;
import org.scy.common.web.controller.HttpResult;
import org.scy.priv.annotation.AccessToken;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * AccessToken 拦截器
 * Created by shicy on 2017/9/26.
 */
@Component
public class AccessTokenInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean validate = super.preHandle(request, response, handler);

        if (validate) {
            if (!handler.getClass().isAssignableFrom(HandlerMethod.class)) {
                return false;
            }

            String token = request.getHeader("access_token");
            request.setAttribute("access_token", token);

            AccessToken accessToken = null;

            Method method = ((HandlerMethod)handler).getMethod();
            if (method.isAnnotationPresent(AccessToken.class)) {
                accessToken = method.getAnnotation(AccessToken.class);
            }
            else {
                Class cls = method.getDeclaringClass();
                if (cls.isAnnotationPresent(AccessToken.class))
                    accessToken = (AccessToken) cls.getAnnotation(AccessToken.class);
            }

            if (accessToken != null && accessToken.required()) {
                if (StringUtils.isBlank(token)) {
                    writeWithNoToken(response);
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * 获取 AccessToken 失败，返回相应信息
     */
    private void writeWithNoToken(HttpServletResponse response) throws Exception {
        HttpResult result = new HttpResult(HttpResult.FORBID, "缺少 AccessToken 信息！");
        HttpUtilsEx.writeJsonToResponse(response, result.toJSON());
    }

}
