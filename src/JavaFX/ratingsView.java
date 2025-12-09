/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafx;

import dao.ratingDAO;
import model.User;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.List;

/**
 * RatingsView - UI to submit ratings for a match
 * Shows only matches the user hasn't rated yet.
 */
public class ratingsView extends GridPane {

    private final ratingDAO ratingDAO = new ratingDAO();
    private final User user;

    public ratingsView(User user) {
        this.user = user;
        setHgap(10);
        setVgap(10);
        setPadding(new Insets(18));
        getStyleClass().add("card");

        Label title = new Label("Submit Rating");
        title.getStyleClass().add("section-title");

        // ComboBox for Match IDs
        ComboBox<Integer> cbMatch = new ComboBox<>();
        cbMatch.setPromptText("Select Match ID");
        cbMatch.getStyleClass().add("combo-box");

        // Load only unrated match IDs
        List<Integer> matchIds = ratingDAO.getUnratedMatchIdsForUser(user.getUserId());
        cbMatch.getItems().addAll(matchIds);

        // Stars ComboBox
        ComboBox<Integer> cbStars = new ComboBox<>();
        cbStars.getItems().addAll(1, 2, 3, 4, 5);

        TextArea taReview = new TextArea();
        taReview.setPromptText("Write a short review...");
        taReview.setPrefRowCount(4);
        taReview.getStyleClass().add("text-area");

        Button btnSubmit = new Button("Submit Rating");
        btnSubmit.getStyleClass().add("button-primary");

        // Layout
        add(title, 0, 0, 2, 1);
        add(new Label("Match ID:"), 0, 1);
        add(cbMatch, 1, 1);
        add(new Label("Stars (1-5):"), 0, 2);
        add(cbStars, 1, 2);
        add(new Label("Review:"), 0, 3);
        add(taReview, 1, 3);
        add(btnSubmit, 1, 4);

        // Submit button action
        btnSubmit.setOnAction(e -> {
            Integer matchId = cbMatch.getValue();
            Integer stars = cbStars.getValue();
            String review = taReview.getText().trim();

            if (matchId == null) { showAlert("Select a match"); return; }
            if (stars == null) { showAlert("Choose stars"); return; }

            boolean ok = ratingDAO.addRating(matchId, user.getUserId(), stars, review);
            showAlert(ok ? "Rating submitted" : "Error submitting rating");

            if (ok) {
                // Remove rated match from ComboBox
                cbMatch.getItems().remove(matchId);
                cbMatch.getSelectionModel().clearSelection();
                cbStars.getSelectionModel().clearSelection();
                taReview.clear();
            }
        });
    }

    private void showAlert(String text) {
        Alert a = new Alert(Alert.AlertType.INFORMATION, text);
        a.setHeaderText(null);
        a.showAndWait();
    }
}
