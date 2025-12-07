/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafx;

import dao.authDAO;
import model.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 * Simple login/register stage. On successful login opens DashboardView.
 */
public class loginView extends Stage {

    private authDAO authDAO = new authDAO();

    public loginView() {
        setTitle("SkillSwap - Login");
        VBox root = new VBox(10);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);

        TextField tfName = new TextField();
        tfName.setPromptText("Enter name (unique)");

        PasswordField pfPass = new PasswordField();
        pfPass.setPromptText("Password");

        Button btnLogin = new Button("Login");
        Button btnRegister = new Button("Register Demo User");

        HBox row = new HBox(10, btnLogin, btnRegister);
        row.setAlignment(Pos.CENTER);

        root.getChildren().addAll(new Label("Name:"), tfName, new Label("Password:"), pfPass, row);

        btnLogin.setOnAction(e -> {
            String name = tfName.getText().trim();
            String pass = pfPass.getText().trim();
            if (name.isEmpty() || pass.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Enter both name and password");
                return;
            }
            User u = authDAO.login(name, pass);
            if (u != null) {
                showAlert(Alert.AlertType.INFORMATION, "Welcome, " + u.getName());
                dashboardView dash = new dashboardView(u);
                dash.show();
                this.close();
            } else {
                showAlert(Alert.AlertType.ERROR, "Invalid credentials");
            }
        });

        btnRegister.setOnAction(e -> {
            String name = tfName.getText().trim();
            String pass = pfPass.getText().trim();
            if (name.isEmpty() || pass.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Enter both name and password");
                return;
            }
            boolean ok = authDAO.register(name, "CS", pass, "student");
            showAlert(ok ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR, ok ? "Registered!" : "Register failed");
        });

        setScene(new Scene(root, 360, 260));
    }

    private void showAlert(Alert.AlertType type, String msg) {
        Alert a = new Alert(type, msg);
        a.setHeaderText(null);
        a.showAndWait();
    }
}
