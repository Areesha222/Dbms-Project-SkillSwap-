/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Add/fetch ratings
 */
public class ratingDAO {

    // Add a new rating
    public boolean addRating(int matchId, int raterUserId, int stars, String review) {
        String sql = "INSERT INTO Ratings(match_id, rater_user_id, stars, review) VALUES(?,?,?,?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, matchId);
            ps.setInt(2, raterUserId);
            ps.setInt(3, stars);
            ps.setString(4, review);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Fetch ratings for a specific user
    public ResultSet getRatingsForUser(int userId) {
        String q = "SELECT r.* FROM Ratings r " +
                   "JOIN Matches m ON r.match_id = m.match_id " +
                   "JOIN Skills s ON m.skill_id = s.skill_id " +
                   "WHERE s.user_id = ?";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(q);
            ps.setInt(1, userId);
            return ps.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // NEW: Get all match IDs for the user that haven't been rated yet
    public List<Integer> getUnratedMatchIdsForUser(int userId) {
        List<Integer> ids = new ArrayList<>();
        String sql = "SELECT m.match_id " +
                     "FROM Matches m " +
                     "WHERE m.req_id = ? " +
                     "AND m.match_id NOT IN (" +
                     "    SELECT r.match_id FROM Ratings r WHERE r.rater_user_id = ?" +
                     ")";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ids.add(rs.getInt("match_id"));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ids;
    }
}
