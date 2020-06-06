package com.fis.humans.dao;

import com.fis.humans.connection.DbConnection;
import com.fis.humans.model.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CustomerDAO {
    private static CustomerDAO instance = new CustomerDAO();

    private CustomerDAO() {}

    public static CustomerDAO getInstance() {
        return instance;
    }

    public int createCustomer(Customer customer) throws SQLException {
        Connection con = DbConnection.getConnection();

        String sql = "INSERT INTO Customer(Name, Birth_Year, Income, City, District) " +
                "VALUES (?, ?, ?, ?, ?)";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setNString(1, customer.getName());
        stmt.setInt(2, customer.getBirthYear());
        stmt.setInt(3, customer.getIncome());
        stmt.setNString(4, customer.getCity());
        stmt.setNString(5, customer.getDistrict());
        int result = stmt.executeUpdate();

        stmt.close();
        con.close();

        return result;
    }
}