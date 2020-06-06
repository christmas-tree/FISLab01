package com.fis.humans.controller;

import com.fis.humans.dao.CustomerDAO;
import com.fis.humans.dao.CustomerPendingDAO;
import com.fis.humans.dao.LocationDAO;
import com.fis.humans.model.*;
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

@WebServlet(name = "VerifyCustomerServlet", urlPatterns = {"/customer/verify"})
public class VerifyCustomerServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String name = request.getParameter("name");
        String city = request.getParameter("city");
        String district = request.getParameter("district");

        if (action.isEmpty() || name.isEmpty() || city.isEmpty() || district.isEmpty()) {
            ErrorHandler.handle(request, response,
                    HttpServletResponse.SC_BAD_REQUEST, "Bad request!");
            return;
        }

        int birthYear;
        int income;
        int customerId;
        try {
            birthYear = Integer.parseInt(request.getParameter("birthYear"));
            income = Integer.parseInt(request.getParameter("income"));
            customerId = Integer.parseInt(request.getParameter("customerId"));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            ErrorHandler.handle(request, response,
                    HttpServletResponse.SC_BAD_REQUEST, "Bad request!");
            return;
        }

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Customer customer;
        int result = 0;

        try {
            if (user.getRole().equals("Manager")) {
                if (action.equals("Reject")) {
                    customer = new CustomerPending(
                            customerId, name, birthYear, income, city, district, -1);
                    result = CustomerPendingDAO.getInstance().updatePendingCustomer((CustomerPending) customer);
                } else if (action.equals("Approve")) {
                    if (income < 1000) {
                        customer = new Customer(
                                customerId, name, birthYear, income, city, district);
                        result = CustomerPendingDAO.getInstance().deletePendingCustomer(customerId);
                        if (result > 0)
                            result = CustomerDAO.getInstance().createCustomer(customer);
                    } else {
                        customer = new CustomerPending(
                                customerId, name, birthYear, income, city, district, 1);
                        result = CustomerPendingDAO.getInstance().updatePendingCustomer((CustomerPending) customer);
                    }
                } else {
                    ErrorHandler.handle(request, response,
                            HttpServletResponse.SC_BAD_REQUEST, "Bad request!");
                    return;
                }
            } else if (user.getRole().equals("Director")) {
                if (action.equals("Reject")) {
                    customer = new CustomerPending(
                            customerId, name, birthYear, income, city, district, -2);
                    result = CustomerPendingDAO.getInstance().updatePendingCustomer((CustomerPending) customer);
                } else if (action.equals("Approve")) {
                    customer = new CustomerPending(
                            customerId, name, birthYear, income, city, district, 2);
                    result = CustomerPendingDAO.getInstance().updatePendingCustomer((CustomerPending) customer);
                    if (result > 0)
                        result = CustomerDAO.getInstance().createCustomer(customer);
                    if (result > 0) {
                        session.setAttribute("customerFile", stringify(customer));
                    }

                } else {
                    ErrorHandler.handle(request, response,
                            HttpServletResponse.SC_BAD_REQUEST, "Bad request!");
                    return;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            ErrorHandler.handle(request, response,
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Something went wrong!");
            return;
        }

        if (result > 0) {
            session.setAttribute("success", true);
        } else {
            session.setAttribute("error", true);
        }
        response.sendRedirect(request.getContextPath() + "/customer");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int customerId;
        CustomerPending customerPending = null;

        try {
            customerId = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            ErrorHandler.handle(request, response, HttpServletResponse.SC_BAD_REQUEST, "Bad request!");
            return;
        }

        try {
            customerPending = CustomerPendingDAO.getInstance().getPendingCustomer(customerId);
        } catch (SQLException e) {
            e.printStackTrace();
            ErrorHandler.handle(request, response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Something went wrong!");
            return;
        }

        if (customerPending == null) {
            ErrorHandler.handle(request, response, HttpServletResponse.SC_NOT_FOUND, "Customer not found!");
            return;
        }
        request.setAttribute("customerPending", customerPending);

        ArrayList<City> cityList;
        ArrayList<District> districtList;
        try {
            cityList = LocationDAO.getInstance().getCities();
            districtList = LocationDAO.getInstance().getDistricts();
        } catch (SQLException e) {
            e.printStackTrace();
            ErrorHandler.handle(request, response,
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Something went wrong!");
            return;
        }

        request.setAttribute("cityList", cityList);
        request.setAttribute("districtList", districtList);

        User user = (User) request.getSession().getAttribute("user");
        if (user.getRole().equals("Director")) {
            request.setAttribute("disabled", "readonly");
        }
        request.getRequestDispatcher("/manager/customerEdit.jsp").forward(request, response);
    }

    private String stringify(Customer customer) {
        return "customerId, name, birthYear, income, city, district\n"+
                customer.getCustomerId() + "," +
                customer.getName() + "," +
                customer.getBirthYear() + "," +
                customer.getIncome() + "," +
                customer.getCity() + "," +
                customer.getDistrict();
    }
}
