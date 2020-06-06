package com.fis.humans.controller;

import com.fis.humans.dao.CustomerPendingDAO;
import com.fis.humans.model.CustomerPending;
import com.fis.humans.model.User;
import com.fis.humans.tools.ErrorHandler;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "InboxServlet", urlPatterns = {"/customer"})
public class InboxServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        ArrayList<CustomerPending> customerPendingList;

        if (user.getRole().equals("Manager")) {
            try {
                customerPendingList = CustomerPendingDAO.getInstance().getAllPendingCustomers(0);
            } catch (SQLException e) {
                e.printStackTrace();
                ErrorHandler.handle(request, response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Something went wrong!");
                return;
            }
            request.setAttribute("customerPendingList", customerPendingList);
        } if (user.getRole().equals("Director")) {
            try {
                customerPendingList = CustomerPendingDAO.getInstance().getAllPendingCustomers(1);
            } catch (SQLException e) {
                e.printStackTrace();
                ErrorHandler.handle(request, response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Something went wrong!");
                return;
            }
            request.setAttribute("customerPendingList", customerPendingList);
        }
        Boolean success = (Boolean) session.getAttribute("success");
        if (success != null) {
            session.removeAttribute("success");
            request.setAttribute("success", success);
        }
        Boolean error = (Boolean) session.getAttribute("error");
        if (error != null) {
            session.removeAttribute("error");
            request.setAttribute("error", error);
        }
        String customerFileContent = (String) session.getAttribute("customerFile");
        if (customerFileContent != null) {
            session.removeAttribute("customerFile");
            request.setAttribute("customerFile", customerFileContent);
        }

        request.getRequestDispatcher("/manager/inbox.jsp").forward(request, response);
    }
}
