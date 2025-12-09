/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.MatchDTO;

public class matchDAO {

    public void generateMatches() {
        String q = """
            INSERT INTO Matches(skill_id, req_id, status)
            SELECT s.skill_id, r.req_id, 'Pending'
            FROM Skills s
            JOIN Requests r 
              ON s.skill_name = r.skill_name
            WHERE s.user_id <> r.user_id
              AND NOT EXISTS (
                  SELECT 1 
                  FROM Matches m
                  WHERE m.skill_id = s.skill_id 
                    AND m.req_id = r.req_id
              )
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(q)) {

            int rowsInserted = ps.executeUpdate();
            System.out.println("Matches generated: " + rowsInserted);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns matches relevant to a specific user
     * showing skill name, requester username, and status
     */
    public List<MatchDTO> getMatchesForUser(int userId) {
    List<MatchDTO> matches = new ArrayList<>();

    String q = """
        SELECT m.match_id, s.skill_name, u.name AS requester_name, m.status
        FROM Matches m
        JOIN Skills s ON m.skill_id = s.skill_id
        JOIN Requests r ON m.req_id = r.req_id
        JOIN Users u ON r.user_id = u.user_id
        WHERE s.user_id = ? OR r.user_id = ?
        ORDER BY m.match_id
    """;

    System.out.println("Fetching matches for userId = " + userId);

    try (Connection con = DBConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(q)) {

        ps.setInt(1, userId);
        ps.setInt(2, userId);

        ResultSet rs = ps.executeQuery();

        int count = 0;
        while (rs.next()) {
            MatchDTO match = new MatchDTO();
            match.setMatchId(rs.getInt("match_id"));
            match.setSkillName(rs.getString("skill_name"));
            match.setRequesterName(rs.getString("requester_name"));
            match.setStatus(rs.getString("status"));
            matches.add(match);
            count++;
            System.out.println("Match found: ID=" + match.getMatchId() +
                               ", Skill=" + match.getSkillName() +
                               ", Requester=" + match.getRequesterName() +
                               ", Status=" + match.getStatus());
        }

        if (count == 0) {
            System.out.println("No matches found for userId = " + userId);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return matches;
}


    public boolean updateMatchStatus(int matchId, String status) {
        String q = "UPDATE Matches SET status = ? WHERE match_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(q)) {

            ps.setString(1, status);
            ps.setInt(2, matchId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
