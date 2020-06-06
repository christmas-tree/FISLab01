package com.fis.humans.dao;

import com.fis.humans.connection.DbConnection;
import com.fis.humans.model.CustomerPending;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerPendingDAO {

    private static CustomerPendingDAO instance = new CustomerPendingDAO();

    private CustomerPendingDAO() {}

    public static CustomerPendingDAO getInstance() {
        return instance;
    }

    public ArrayList<CustomerPending> getAllPendingCustomers(int statusCode) throws SQLException {
        ArrayList<CustomerPending> customerPendingList = new ArrayList<>();
        CustomerPending customerPending = null;
        Connection con = DbConnection.getConnection();

        String sql = "SELECT CustomerID, Name, Birth_Year, Income, City, District, Status FROM Customer_Pending WHERE Status=?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, statusCode);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            customerPending = new CustomerPending(
                    rs.getInt("CustomerID"),
                    rs.getString("Name"),
                    rs.getInt("Birth_Year"),
                    rs.getInt("Income"),
                    rs.getNString("City"),
                    rs.getNString("District"),
                    rs.getInt("Status")
            );
            customerPendingList.add(customerPending);
        }
        rs.close();
        stmt.close();
        con.close();

        return customerPendingList;
    }

    public CustomerPending getPendingCustomer(int customerId) throws SQLException {
        CustomerPending customerPending = null;
        Connection con = DbConnection.getConnection();

        String sql = "SELECT CustomerID, Name, Birth_Year, Income, City, District, Status FROM Customer_Pending WHERE CustomerID=?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, customerId);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            customerPending = new CustomerPending(
                    rs.getInt("CustomerID"),
                    rs.getString("Name"),
                    rs.getInt("Birth_Year"),
                    rs.getInt("Income"),
                    rs.getNString("City"),
                    rs.getNString("District"),
                    rs.getInt("Status")
            );
        }
        rs.close();
        stmt.close();
        con.close();

        return customerPending;
    }

    public int updatePendingCustomer(CustomerPending customerPending) throws SQLException {
        Connection con = DbConnection.getConnection();

        String sql = "UPDATE Customer_Pending SET Name=?, Birth_Year=?, Income=?, City=?, District=?, Status=? WHERE CustomerID=?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setNString(1, customerPending.getName());
        stmt.setInt(2, customerPending.getBirthYear());
        stmt.setInt(3, customerPending.getIncome());
        stmt.setNString(4, customerPending.getCity());
        stmt.setNString(5, customerPending.getDistrict());
        stmt.setInt(6, customerPending.getStatus());
        stmt.setInt(7, customerPending.getCustomerId());
        int result = stmt.executeUpdate();

        stmt.close();
        con.close();

        return result;
    }

    public int createPendingCustomer(CustomerPending customerPending) throws SQLException {
        Connection con = DbConnection.getConnection();

        String sql = "INSERT INTO Customer_Pending(Name, Birth_Year, Income, City, District, Status) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setNString(1, customerPending.getName());
        stmt.setInt(2, customerPending.getBirthYear());
        stmt.setInt(3, customerPending.getIncome());
        stmt.setNString(4, customerPending.getCity());
        stmt.setNString(5, customerPending.getDistrict());
        stmt.setInt(6, customerPending.getStatus());
        int result = stmt.executeUpdate();

        stmt.close();
        con.close();

        return result;
    }

    public int deletePendingCustomer(int customerId) throws SQLException {
        Connection con = DbConnection.getConnection();

        String sql = "DELETE FROM Customer_Pending WHERE CustomerID=?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, customerId);
        int result = stmt.executeUpdate();

        stmt.close();
        con.close();

        return result;
    }
}
