/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.Skill;
import java.sql.*;

/**
 * DAO for Skills table
 */
public class skillDAO {

    public boolean addSkill(Skill skill) {
        String sql = "INSERT INTO Skills(user_id, skill_name, level) VALUES(?,?,?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, skill.getUserId());
            ps.setString(2, skill.getSkillName());
            ps.setString(3, skill.getLevel());
            int n = ps.executeUpdate();
            if (n > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) skill.setSkillId(rs.getInt(1));
                return true;
            }
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    public ResultSet getAllSkills() {
        try {
            Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();
            return st.executeQuery("SELECT * FROM Skills");
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }
}
