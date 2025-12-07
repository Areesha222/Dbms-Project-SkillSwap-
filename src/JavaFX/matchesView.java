/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafx;

import dao.matchDAO;
import model.User;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.sql.ResultSet;

/**
 * MatchesView - displays matches from Matches table
 */
public class matchesView extends VBox {

    private matchDAO matchDAO = new matchDAO();
    private User user;

    public matchesView(User user) {
        this.user = user;
        setPadding(new Insets(10));
        setSpacing(10);

        Button btnLoad = new Button("Load Matches");
        ListView<String> list = new ListView<>();

        btnLoad.setOnAction(e -> {
            list.getItems().clear();
            try {
                ResultSet rs = matchDAO.getAllMatches();
                while (rs != null && rs.next()) {
                    String row = "Match#" + rs.getInt("match_id")
                            + " | skill_id:" + rs.getInt("skill_id")
                            + " | req_id:" + rs.getInt("req_id")
                            + " | status:" + rs.getString("status");
                    list.getItems().add(row);
                }
            } catch (Exception ex) { ex.printStackTrace(); }
        });

        getChildren().addAll(btnLoad, list);
    }
}
