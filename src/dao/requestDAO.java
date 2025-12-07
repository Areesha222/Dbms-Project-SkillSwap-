/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.request;
import java.sql.*;

/**
 * DAO for Requests table
 */
public class requestDAO {

    public boolean addRequest(request r) {
        String sql = "INSERT INTO Requests(user_id, skill_name) VALUES(?,?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, r.getUserId());
            ps.setString(2, r.getskillName());
            int n = ps.executeUpdate();
            if (n > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) r.setReqId(rs.getInt(1));
                return true;
            }
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    public ResultSet getAllRequests() {
        try {
            Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();
            return st.executeQuery("SELECT * FROM Requests");
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }
}
