/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafx;

import dao.matchDAO;
import model.MatchDTO;
import model.User;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.List;

public class matchesView extends VBox {

    private final matchDAO matchDAO = new matchDAO();
    private final User user;
    private final ListView<MatchDTO> list = new ListView<>();

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

        // Set custom cell factory for buttons
        list.setCellFactory(param -> new MatchCell());
    }

    public void reload() {
        list.getItems().clear();
        try {
            List<MatchDTO> matches = matchDAO.getMatchesForUser(user.getUserId());
            if (matches.isEmpty()) {
                list.getItems().add(null); // will show "No matches"
            } else {
                list.getItems().addAll(matches);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Custom cell for each match with Accept/Reject buttons
    private class MatchCell extends ListCell<MatchDTO> {
    HBox hbox = new HBox(10);
    Label label = new Label();
    Button btnAccept = new Button("Accept");
    Button btnReject = new Button("Reject");

    public MatchCell() {
        super();
        hbox.setPadding(new Insets(5));
        HBox spacer = new HBox();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        hbox.getChildren().addAll(label, spacer, btnAccept, btnReject);

        btnAccept.setOnAction(e -> {
            MatchDTO match = getItem();
            if (match != null) {
                boolean success = matchDAO.updateMatchStatus(match.getMatchId(), "Accepted");
                if (success) {
                    match.setStatus("Accepted");
                    updateItem(match, false);
                }
            }
        });

        btnReject.setOnAction(e -> {
            MatchDTO match = getItem();
            if (match != null) {
                boolean success = matchDAO.updateMatchStatus(match.getMatchId(), "Rejected");
                if (success) {
                    match.setStatus("Rejected");
                    updateItem(match, false);
                }
            }
        });
    }

    @Override
    protected void updateItem(MatchDTO match, boolean empty) {
        super.updateItem(match, empty);
        if (empty || match == null) {
            setText(null);
            setGraphic(new Label("No matches found"));
            setStyle(""); // reset style
        } else {
            label.setText(match.toString());
            setGraphic(hbox);

            // Color coding based on status
            switch (match.getStatus()) {
                case "Accepted" -> setStyle("-fx-background-color: #d4edda;"); // light green
                case "Rejected" -> setStyle("-fx-background-color: #f8d7da;"); // light red
                case "Pending" -> setStyle("-fx-background-color: #fff3cd;");  // light yellow
                default -> setStyle("");
            }
        }
    }
    }
}
