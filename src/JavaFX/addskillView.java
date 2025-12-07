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
 * AddSkillView is a GridPane that can be put inside a Tab.
 */
public class addskillView extends GridPane {

    private skillDAO skillDAO = new skillDAO();
    private User user;

    public addskillView(User user) {
        this.user = user;
        setHgap(10); setVgap(10); setPadding(new Insets(15));

        TextField tfSkill = new TextField();
        ComboBox<String> cbLevel = new ComboBox<>();
        cbLevel.getItems().addAll("Beginner", "Intermediate", "Expert");

        Button btnAdd = new Button("Add Skill");

        add(new Label("Skill Name:"), 0, 0);
        add(tfSkill, 1, 0);
        add(new Label("Level:"), 0, 1);
        add(cbLevel, 1, 1);
        add(btnAdd, 1, 2);

        btnAdd.setOnAction(e -> {
            String skillName = tfSkill.getText().trim();
            String level = cbLevel.getValue();
            if (skillName.isEmpty() || level == null) {
                showAlert("Enter skill and select level");
                return;
            }
            Skill s = new Skill(user.getUserId(), skillName, level);
            boolean ok = skillDAO.addSkill(s);
            showAlert(ok ? "Skill added" : "Error adding skill");
            if (ok) tfSkill.clear();
        });
    }

    private void showAlert(String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION, msg);
        a.setHeaderText(null);
        a.showAndWait();
    }
}
