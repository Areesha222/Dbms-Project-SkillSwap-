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
 * Matches view. Loads matches using MatchDAO and displays in a ListView.
 */
public class matchesView extends VBox {

    private final matchDAO matchDAO = new matchDAO();
    private final User user;
    private final ListView<String> list = new ListView<>();

    public matchesView(User user) {
        this.user = user;
        setSpacing(10);
        setPadding(new Insets(18));
        getStyleClass().add("card");

        Label title = new Label("Matches");
        title.getStyleClass().add("section-title");

        Button btnLoad = new Button("Load Matches");
        btnLoad.getStyleClass().add("button-primary");

        btnLoad.setOnAction(e -> reload());

        getChildren().addAll(title, btnLoad, list);
    }

    public void reload() {
        list.getItems().clear();
        try {
            ResultSet rs = matchDAO.getAllMatches();
            while (rs != null && rs.next()) {
                // You can customize display to show skill names/owner by joining tables in DAO
                String row = "Match#" + rs.getInt("match_id")
                        + " | skill_id:" + rs.getInt("skill_id")
                        + " | req_id:" + rs.getInt("req_id")
                        + " | status:" + rs.getString("status");
                list.getItems().add(row);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            list.getItems().add("Error loading matches");
        }
    }
}
