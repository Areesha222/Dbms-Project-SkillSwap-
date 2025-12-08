/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafx;

import dao.skillDAO;
import model.Skill;
import model.User;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

/**
 * Add Skill pane (GridPane) used inside Dashboard center.
 * Uses DAO to insert and shows a brief alert message on success/failure.
 */
public class addskillView extends GridPane {

    private final skillDAO skillDAO = new skillDAO();
    private final User user;

    public addskillView(User user) {
        this.user = user;
        setHgap(10);
        setVgap(10);
        setPadding(new Insets(18));
        getStyleClass().add("card");

        Label title = new Label("Add a Skill");
        title.getStyleClass().add("section-title");

        TextField tfSkill = new TextField();
        tfSkill.setPromptText("e.g., Python, Guitar, Photoshop");
        tfSkill.getStyleClass().add("text-field");

        ComboBox<String> cbLevel = new ComboBox<>();
        cbLevel.getItems().addAll("Beginner", "Intermediate", "Expert");
        cbLevel.getStyleClass().add("combo-box");

        Button btnAdd = new Button("Add Skill");
        btnAdd.getStyleClass().add("button-primary");

        add(title, 0, 0, 2, 1);
        add(new Label("Skill Name:"), 0, 1);
        add(tfSkill, 1, 1);
        add(new Label("Level:"), 0, 2);
        add(cbLevel, 1, 2);
        add(btnAdd, 1, 3);

        btnAdd.setOnAction(e -> {
            String name = tfSkill.getText().trim();
            String level = cbLevel.getValue();
            if (name.isEmpty() || level == null) {
                showAlert("Enter skill name and choose level");
                return;
            }
            Skill s = new Skill(user.getUserId(), name, level);
            boolean ok = skillDAO.addSkill(s);
            showAlert(ok ? "Skill added" : "Error adding skill");
            if (ok) {
                tfSkill.clear();
                cbLevel.getSelectionModel().clearSelection();
            }
        });
    }

    private void showAlert(String text) {
        Alert a = new Alert(Alert.AlertType.INFORMATION, text);
        a.setHeaderText(null);
        a.showAndWait();
    }
}
