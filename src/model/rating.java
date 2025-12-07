/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Tesla Laptops
 */
public class rating {
   
    private int ratingId;
    private int matchId;
    private int raterUserId;
    private int stars;
    private String review;

    public rating() {}

    public int getratingId() { return ratingId; }
    public void setratingId(int ratingId) { this.ratingId = ratingId; }

    public int getMatchId() { return matchId; }
    public void setMatchId(int matchId) { this.matchId = matchId; }

    public int getRaterUserId() { return raterUserId; }
    public void setRaterUserId(int raterUserId) { this.raterUserId = raterUserId; }

    public int getStars() { return stars; }
    public void setStars(int stars) { this.stars = stars; }

    public String getReview() { return review; }
    public void setReview(String review) { this.review = review; }

}
