package com.fis.humans.controller;

import com.fis.humans.dao.CustomerPendingDAO;
import com.fis.humans.dao.LocationDAO;
import com.fis.humans.model.City;
import com.fis.humans.model.CustomerPending;
import com.fis.humans.model.District;
import com.fis.humans.tools.ErrorHandler;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "AddCustomerServlet", urlPatterns = {"/customer/add"})
public class AddCustomerServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String city = request.getParameter("city");
        String district = request.getParameter("district");

        if (name.isEmpty() || city.isEmpty() || district.isEmpty()) {
            ErrorHandler.handle(request, response,
                    HttpServletResponse.SC_BAD_REQUEST, "Bad request!");
            return;
        }

        int birthYear;
        int income;
        try {
            birthYear = Integer.parseInt(request.getParameter("birthYear"));
            income = Integer.parseInt(request.getParameter("income"));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            ErrorHandler.handle(request, response,
                    HttpServletResponse.SC_BAD_REQUEST, "Bad request!");
            return;
        }

        CustomerPending newCustomer = new CustomerPending(
                0, name, birthYear, income, city, district, 0);
        int result;
        try {
            result = CustomerPendingDAO.getInstance().createPendingCustomer(newCustomer);
        } catch (SQLException e) {
            e.printStackTrace();
            ErrorHandler.handle(request, response,
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Something went wrong!");
            return;
        }

        if (result == 0) {
            request.setAttribute("error", true);
            request.getRequestDispatcher("/officer/addCustomer.jsp").forward(request, response);
            return;
        }
        request.setAttribute("success", true);
        request.getRequestDispatcher("/officer/addCustomer.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

        request.getRequestDispatcher("/officer/addCustomer.jsp").forward(request, response);
    }
}
