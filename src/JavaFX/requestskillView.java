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
 * Request Skill pane for dashboard. Adds a request via RequestDAO.
 */
public class requestskillView extends GridPane {

    private final requestDAO requestDAO = new requestDAO();
    private final User user;

    public requestskillView(User user) {
        this.user = user;
        setHgap(10);
        setVgap(10);
        setPadding(new Insets(18));
        getStyleClass().add("card");

        Label title = new Label("Request a Skill");
        title.getStyleClass().add("section-title");

        TextField tfReq = new TextField();
        tfReq.setPromptText("e.g., Java, Calculus");
        tfReq.getStyleClass().add("text-field");

        Button btnReq = new Button("Add Request");
        btnReq.getStyleClass().add("button-primary");

        add(title, 0, 0, 2, 1);
        add(new Label("Skill you want:"), 0, 1);
        add(tfReq, 1, 1);
        add(btnReq, 1, 2);

        btnReq.setOnAction(e -> {
            String skill = tfReq.getText().trim();
            if (skill.isEmpty()) { showAlert("Enter a skill"); return; }
            request r = new request(user.getUserId(), skill);
            boolean ok = requestDAO.addRequest(r);
            showAlert(ok ? "Request added" : "Error adding request");
            if (ok) tfReq.clear();
        });
    }

    private void showAlert(String text) {
        Alert a = new Alert(Alert.AlertType.INFORMATION, text);
        a.setHeaderText(null);
        a.showAndWait();
    }
}
