package com.kaishengit.crm.controller.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by zjs on 2017/7/19.
 */
public class Logininterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String url = request.getRequestURI();
        if(url.startsWith("/static/")){
            return true;
        }

        if("/".equals(url)) {
            return true;
        } else {
            HttpSession session = request.getSession();
            if(session.getAttribute("curr_user") == null) {
                    response.sendRedirect("/?callback=" + url);
                return false;
            }
        }
        return true;
    }
}
