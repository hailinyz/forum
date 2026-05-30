package com.bite.forum.interceptor;

import com.bite.forum.config.AppConfig;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @Author 梁元章
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {
    // 从配置⽂件中获取默认登录⻚的URL
    @Value("${lyz-forum.login.url}")
    private String defaultURL;
    /**
     * 预处理(请求的前置处理)回调⽅法<br/>
     * 返回值: <br/>true 流程继续;<br/>
     * false流程中断, 不会再调⽤其他的拦截器
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse
            response, Object handler) throws Exception {
        // 获取⽤⼾session, 并判断⽤⼾是否登录
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute(AppConfig.USER_SESSION) !=
                null) {
            // ⽤⼾已登录
            return true;
        }
        // 没有登录强制跳转到登录⻚⾯
        if (!defaultURL.startsWith("/")) {
            defaultURL = "/" + defaultURL;
        }
        response.sendRedirect(defaultURL);
        return false;
    }
}
