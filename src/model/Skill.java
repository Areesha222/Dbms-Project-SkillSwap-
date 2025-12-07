/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 * POJO representing a skill offering
 */
public class Skill {
    private int skillId;
    private int userId;
    private String skillName;
    private String level;

    public Skill() {}

    public Skill(int userId, String skillName, String level) {
        this.userId = userId;
        this.skillName = skillName;
        this.level = level;
    }

    public int getSkillId() { return skillId; }
    public void setSkillId(int skillId) { this.skillId = skillId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getSkillName() { return skillName; }
    public void setSkillName(String skillName) { this.skillName = skillName; }

    public String getLevel() { return level; }
    public void setLevel(String level) { this.level = level; }
}
