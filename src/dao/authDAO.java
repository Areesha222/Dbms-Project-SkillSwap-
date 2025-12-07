/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.User;
import java.sql.*;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Authentication helper - simple salted SHA-256 hashing for demo.
 * For production use bcrypt/argon2.
 */
public class authDAO {

    private static final SecureRandom RANDOM = new SecureRandom();

    // returns salt:hash
    private String hashPasswordWithSalt(String password) throws Exception {
        byte[] saltBytes = new byte[16];
        RANDOM.nextBytes(saltBytes);
        String salt = Base64.getEncoder().encodeToString(saltBytes);

        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(salt.getBytes("UTF-8"));
        byte[] hashed = md.digest(password.getBytes("UTF-8"));
        String hash = Base64.getEncoder().encodeToString(hashed);
        return salt + ":" + hash;
    }

    private String hashPasswordWithGivenSalt(String password, String salt) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(salt.getBytes("UTF-8"));
        byte[] hashed = md.digest(password.getBytes("UTF-8"));
        return Base64.getEncoder().encodeToString(hashed);
    }

    public boolean register(String name, String dept, String password, String role) {
        String store = null;
        try {
            store = hashPasswordWithSalt(password);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        String sql = "INSERT INTO Users(name, dept, password, role) VALUES(?,?,?,?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, name);
            ps.setString(2, dept);
            ps.setString(3, store);
            ps.setString(4, role == null ? "student" : role);
            int n = ps.executeUpdate();
            return n > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public User login(String name, String password) {
        String sql = "SELECT user_id, name, dept, password, role FROM Users WHERE name = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String stored = rs.getString("password");
                if (stored == null) return null;
                String[] parts = stored.split(":");
                if (parts.length != 2) return null;
                String salt = parts[0];
                String hash = parts[1];
                String check = hashPasswordWithGivenSalt(password, salt);
                if (check.equals(hash)) {
                    User u = new User(rs.getInt("user_id"), rs.getString("name"), rs.getString("dept"));
                    u.setRole(rs.getString("role"));
                    return u;
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }
}
