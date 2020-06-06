package com.fis.humans.controller;

import com.fis.humans.dao.UserDAO;
import com.fis.humans.model.User;
import com.fis.humans.tools.ErrorHandler;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "AuthenticateServlet", urlPatterns = {"/login", "/logout"})
public class AuthenticateServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String userPath = request.getServletPath();
        User user = (User) session.getAttribute("user");

        if (userPath.equals("/login")) {
            if (user != null) {
                System.out.println("User not null so cannot log in!");
                response.sendRedirect(request.getContextPath() + "/");
                return;
            }
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            try {
                user = UserDAO.getInstance().authenticate(username, password);
            } catch (SQLException e) {
                e.printStackTrace();
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
                ErrorHandler.handle(request, response,
                        HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Something went wrong!");
                return;
            }

            if (user != null) {
                System.out.println("Authenticated");
                session.setAttribute("user", user);
                if (user.getRole().equals("Officer"))
                    response.sendRedirect(request.getContextPath() + "/customer/add");
                else
                    response.sendRedirect(request.getContextPath() + "/customer");
                return;
            }
            response.sendRedirect(request.getContextPath() + "/");

        } else if (userPath.equals("/logout")) {
            session.removeAttribute("user");
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String userPath = request.getServletPath();
        User user = (User) session.getAttribute("user");

        if (userPath.equals("/login")) {
            if (user != null) {
                response.sendRedirect(request.getContextPath() + "/");
                return;
            }
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        } else if (userPath.equals("/logout")) {
            response.sendRedirect(request.getContextPath() + "/");
        }
    }
}
