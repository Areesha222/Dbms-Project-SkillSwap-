/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Tesla Laptops
 */
public class skill {
   
    private int skillId;
    private int userId;
    private String skillName;
    private String level;

    public skill() {}

    public skill(int userId, String skillName, String level) {
        this.userId = userId;
        this.skillName = skillName;
        this.level = level;
    }

    public int getskillId() { return skillId; }
    public void setskillId(int skillId) { this.skillId = skillId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getskillName() { return skillName; }
    public void setskillName(String skillName) { this.skillName = skillName; }

    public String getLevel() { return level; }
    public void setLevel(String level) { this.level = level; }

}
