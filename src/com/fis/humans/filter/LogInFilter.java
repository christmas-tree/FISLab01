package com.fis.humans.filter;

import com.fis.humans.model.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "LogInFilter")
public class LogInFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String uri = req.getServletPath();
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null && !(uri.equals("/login"))) {
            System.out.println("Filter check: User is not logged in.");
            res.sendRedirect(req.getContextPath() + "/login");
        } else {
            System.out.println("Filter check: User is logged in.");
            chain.doFilter(request, response);
        }
    }

    public void init(FilterConfig config) throws ServletException {
    }

}
