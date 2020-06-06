package com.fis.humans.filter;

import com.fis.humans.model.User;
import com.fis.humans.tools.ErrorHandler;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

@WebFilter(filterName = "PermissionFilter")
public class PermissionFilter implements Filter {
    static HashMap<String, String[]> secureUrls = new HashMap<>();

    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String uri = req.getServletPath();
        System.out.println("Servlet path is " +  req.getServletPath());
        String[] authorizedRoles = secureUrls.get(uri);
        if (authorizedRoles != null) {
            HttpSession session = req.getSession();
            User user = (User) session.getAttribute("user");

            for (String role: authorizedRoles) {
                if (user.getRole().equals(role)) {
                    chain.doFilter(request, response);
                    return;
                }
            }
            ErrorHandler.handle(req, res, HttpServletResponse.SC_FORBIDDEN, "You don't have access to this page!");
            return;
        }
        chain.doFilter(request, response);
    }

    public void init(FilterConfig config) throws ServletException {
        secureUrls.put("/customer/verify",      new String[]{"Director", "Manager"});
        secureUrls.put("/customer",            new String[]{"Director", "Manager"});
    }
}