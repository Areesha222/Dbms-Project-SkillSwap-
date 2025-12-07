/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.User;
import java.sql.*;

/**
 * Basic CRUD operations for Users
 */
public class userDAO {

    public boolean addUser(User user) {
        String sql = "INSERT INTO Users(name, dept, password, role) VALUES(?,?,?,?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getDept());
            ps.setString(3, null); // password handled by AuthDAO
            ps.setString(4, user.getRole() == null ? "student" : user.getRole());
            int n = ps.executeUpdate();
            if (n > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) user.setUserId(rs.getInt(1));
                return true;
            }
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    public User getUserById(int id) {
        String q = "SELECT user_id, name, dept, role FROM Users WHERE user_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(q)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User u = new User(rs.getInt("user_id"), rs.getString("name"), rs.getString("dept"));
                u.setRole(rs.getString("role"));
                return u;
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }
}
