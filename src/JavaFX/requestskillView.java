/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package javafx;

import dao.requestDAO;
import model.request;
import model.User;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

/**
 * RequestSkillView - UI to add a skill request
 */
public class requestskillView extends GridPane {

    private requestDAO requestDAO = new requestDAO();
    private User user;

    public requestskillView(User user) {
        this.user = user;
        setHgap(10); setVgap(10); setPadding(new Insets(15));

        TextField tfReq = new TextField();
        Button btnAdd = new Button("Add Request");

        add(new Label("Skill you want:"), 0, 0);
        add(tfReq, 1, 0);
        add(btnAdd, 1, 1);

        btnAdd.setOnAction(e -> {
            String skill = tfReq.getText().trim();
            if (skill.isEmpty()) {
                showAlert("Enter a skill to request");
                return;
            }
            request r = new request(user.getUserId(), skill);
            boolean ok = requestDAO.addRequest(r);
            showAlert(ok ? "Request added" : "Error adding request");
            if (ok) tfReq.clear();
        });
    }

    private void showAlert(String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION, msg);
        a.setHeaderText(null);
        a.showAndWait();
    }
}
