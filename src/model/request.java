/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Tesla Laptops
 */
public class request {
   
    private int reqId;
    private int userId;
    private String skillName;

    public request() {}

    public request(int userId, String skillName) {
        this.userId = userId;
        this.skillName = skillName;
    }

    public int getReqId() { return reqId; }
    public void setReqId(int reqId) { this.reqId = reqId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getskillName() { return skillName; }
    public void setskillName(String skillName) { this.skillName = skillName; }

}
