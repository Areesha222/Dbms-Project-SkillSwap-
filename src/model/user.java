/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package model;
 

/**
 *
 * @author Tesla Laptops
 */
public class user {
   
/**
 * Simple POJO for User table
 */
    private int userId;
    private String name;
    private String dept;
    private String role; // 'student' or 'admin'

    public User() {}

    public User(int userId, String name, String dept) {
        this.userId = userId;
        this.name = name;
        this.dept = dept;
        this.role = "student";
    }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDept() { return dept; }
    public void setDept(String dept) { this.dept = dept; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}
