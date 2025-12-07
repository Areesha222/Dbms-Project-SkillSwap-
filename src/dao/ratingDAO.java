/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.*;

/**
 * Add/fetch ratings
 */
public class ratingDAO {

    public boolean addRating(int matchId, int raterUserId, int stars, String review) {
        String sql = "INSERT INTO Ratings(match_id, rater_user_id, stars, review) VALUES(?,?,?,?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, matchId);
            ps.setInt(2, raterUserId);
            ps.setInt(3, stars);
            ps.setString(4, review);
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    public ResultSet getRatingsForUser(int userId) {
        String q = "SELECT r.* FROM Ratings r JOIN Matches m ON r.match_id = m.match_id JOIN Skills s ON m.skill_id = s.skill_id WHERE s.user_id = ?";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(q);
            ps.setInt(1, userId);
            return ps.executeQuery();
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }
}
