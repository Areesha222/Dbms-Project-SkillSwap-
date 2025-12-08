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

/**
 * RatingsView - UI to submit ratings for a match
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

        TextField tfMatch = new TextField();
        tfMatch.setPromptText("Match ID");
        tfMatch.getStyleClass().add("text-field");

        ComboBox<Integer> cbStars = new ComboBox<>();
        cbStars.getItems().addAll(1, 2, 3, 4, 5);

        TextArea taReview = new TextArea();
        taReview.setPromptText("Write a short review...");
        taReview.setPrefRowCount(4);
        taReview.getStyleClass().add("text-area");

        Button btnSubmit = new Button("Submit Rating");
        btnSubmit.getStyleClass().add("button-primary");

        add(title, 0, 0, 2, 1);
        add(new Label("Match ID:"), 0, 1);
        add(tfMatch, 1, 1);
        add(new Label("Stars (1-5):"), 0, 2);
        add(cbStars, 1, 2);
        add(new Label("Review:"), 0, 3);
        add(taReview, 1, 3);
        add(btnSubmit, 1, 4);

        btnSubmit.setOnAction(e -> {
            try {
                int matchId = Integer.parseInt(tfMatch.getText().trim());
                Integer stars = cbStars.getValue();
                String review = taReview.getText().trim();
                if (stars == null) { showAlert("Choose stars"); return; }
                boolean ok = ratingDAO.addRating(matchId, user.getUserId(), stars, review);
                showAlert(ok ? "Rating submitted" : "Error submitting rating");
                if (ok) {
                    tfMatch.clear();
                    taReview.clear();
                    cbStars.getSelectionModel().clearSelection();
                }
            } catch (NumberFormatException ex) {
                showAlert("Match ID must be a number");
            }
        });
    }

    private void showAlert(String text) {
        Alert a = new Alert(Alert.AlertType.INFORMATION, text);
        a.setHeaderText(null);
        a.showAndWait();
    }
}
