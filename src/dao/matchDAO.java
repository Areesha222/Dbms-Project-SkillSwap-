/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.*;

/**
 * Match generation and retrieval.
 * generateMatches: simple join on skill_name = skill_name
 * getAllMatches: fetches all rows from Matches
 */
public class matchDAO {

    // creates matches for any skill/request pair where names match
    public void generateMatches() {
        String q = "SELECT s.skill_id, r.req_id FROM Skills s JOIN Requests r ON s.skill_name = r.skill_name";
        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(q)) {

            String insert = "INSERT INTO Matches(skill_id, req_id, status) VALUES(?,?,?)";
            PreparedStatement ps = con.prepareStatement(insert);
            while (rs.next()) {
                int skillId = rs.getInt(1);
                int reqId = rs.getInt(2);

                // To avoid duplicates, you might check first. For simplicity we insert.
                ps.setInt(1, skillId);
                ps.setInt(2, reqId);
                ps.setString(3, "pending");
                try {
                    ps.executeUpdate();
                } catch (SQLException ignored) {
                    // ignore duplicate or FK errors for demo
                }
            }
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ResultSet getAllMatches() {
        try {
            Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();
            return st.executeQuery("SELECT * FROM Matches");
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    // example helper to update match status
    public boolean updateMatchStatus(int matchId, String status) {
        String q = "UPDATE Matches SET status = ? WHERE match_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(q)) {
            ps.setString(1, status);
            ps.setInt(2, matchId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }
}
