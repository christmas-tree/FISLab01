package com.fis.humans.dao;

import com.fis.humans.connection.DbConnection;
import com.fis.humans.model.City;
import com.fis.humans.model.District;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LocationDAO {
    private static LocationDAO instance = new LocationDAO();

    private LocationDAO() {}

    public static LocationDAO getInstance() {
        return instance;
    }

    public ArrayList<City> getCities() throws SQLException {
        ArrayList<City> cityList = new ArrayList<>();
        City city = null;
        Connection con = DbConnection.getConnection();

        String sql = "SELECT CityID, CityName FROM City";
        PreparedStatement stmt = con.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            city = new City();
            city.setCityId(rs.getInt("CityID"));
            city.setCityName(rs.getNString("CityName"));
            cityList.add(city);
        }
        rs.close();
        stmt.close();
        con.close();

        return cityList;
    }

    public ArrayList<District> getDistricts() throws SQLException {
        ArrayList<District> districtList = new ArrayList<>();
        District district = null;
        Connection con = DbConnection.getConnection();

        String sql = "SELECT DistrictID, DistrictName, CityID FROM District";
        PreparedStatement stmt = con.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            district = new District();
            district.setDistrictId(rs.getInt("DistrictID"));
            district.setDistrictName(rs.getNString("DistrictName"));
            district.setCityId(rs.getInt("CityID"));
            districtList.add(district);
        }
        rs.close();
        stmt.close();
        con.close();

        return districtList;
    }
}
