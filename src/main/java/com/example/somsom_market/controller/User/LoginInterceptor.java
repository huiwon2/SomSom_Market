package com.example.somsom_market.controller.User;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    private static final String LOGIN_FORM = "user/loginForm";

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, "userSession");
        if (userSession == null) {
            String url = request.getRequestURL().toString(); // 사용자의 현재 url을 저장 후 로그인 폼으로 이동
            String query = request.getQueryString();
            ModelAndView modelAndView = new ModelAndView(LOGIN_FORM);
            if (query != null) { // 만약 url에 query가 있으면
                modelAndView.addObject("forwardAction", url+"?"+query); // 따로 설정
            }
            else {
                modelAndView.addObject("forwardAction", url);
            }
            throw new ModelAndViewDefiningException(modelAndView);
        }
        else { // 로그인 되어 있을 경우, 계속 진행
            return true;
        }
    }
}
