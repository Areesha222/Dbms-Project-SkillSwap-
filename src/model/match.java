/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Tesla Laptops
 */
public class match {
    
    private int matchId;
    private int skillId;
    private int reqId;
    private String status;

    public match() {}

    public int getmatchId() { return matchId; }
    public void setmatchId(int matchId) { this.matchId = matchId; }

    public int getskillId() { return skillId; }
    public void setskillId(int skillId) { this.skillId = skillId; }

    public int getReqId() { return reqId; }
    public void setReqId(int reqId) { this.reqId = reqId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

}
