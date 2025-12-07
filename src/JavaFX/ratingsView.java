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
 * RatingsView - submit rating for a match
 */
public class ratingsView extends GridPane {

    private ratingDAO ratingDAO = new ratingDAO();
    private User user;

    public ratingsView(User user) {
        this.user = user;
        setHgap(10); setVgap(10); setPadding(new Insets(15));

        TextField tfMatchId = new TextField();
        ComboBox<Integer> cbStars = new ComboBox<>();
        cbStars.getItems().addAll(1,2,3,4,5);
        TextArea taReview = new TextArea();
        taReview.setPrefRowCount(4);
        Button btnSubmit = new Button("Submit Rating");

        add(new Label("Match ID:"), 0, 0);
        add(tfMatchId, 1, 0);
        add(new Label("Stars:"), 0, 1);
        add(cbStars, 1, 1);
        add(new Label("Review:"), 0, 2);
        add(taReview, 1, 2);
        add(btnSubmit, 1, 3);

        btnSubmit.setOnAction(e -> {
            try {
                int matchId = Integer.parseInt(tfMatchId.getText().trim());
                int stars = cbStars.getValue();
                String review = taReview.getText().trim();
                boolean ok = ratingDAO.addRating(matchId, user.getUserId(), stars, review);
                showAlert(ok ? "Rating submitted" : "Failed to submit rating");
                if (ok) {
                    tfMatchId.clear();
                    taReview.clear();
                    cbStars.getSelectionModel().clearSelection();
                }
            } catch (Exception ex) {
                showAlert("Invalid input for match id or stars");
            }
        });
    }

    private void showAlert(String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION, msg);
        a.setHeaderText(null);
        a.showAndWait();
    }
}
