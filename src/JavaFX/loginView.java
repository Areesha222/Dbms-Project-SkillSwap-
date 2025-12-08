/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafx;

import dao.authDAO;
import model.User;
import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Modern LoginView for SkillSwap.
 * Uses styles.css for light blue theme.
 */
public class loginView extends Stage {

    private authDAO authDAO = new authDAO();

    public loginView() {
        setTitle("SkillSwap - Login");

        BorderPane root = new BorderPane();
        root.getStyleClass().add("root");

        // Header
        HBox header = new HBox();
        header.getStyleClass().add("header");
        header.setPadding(new Insets(16, 20, 16, 20));

        Label title = new Label("SkillSwap");
        title.getStyleClass().add("login-header-label"); // light blue title
        header.getChildren().add(title);
        root.setTop(header);

        // Center card
        VBox card = new VBox(12);
        card.getStyleClass().add("card");
        card.setMaxWidth(420);
        card.setPadding(new Insets(20));
        card.setAlignment(Pos.CENTER);

        Label lbl = new Label("Welcome!");
        lbl.getStyleClass().add("login-welcome-label"); // medium blue

        TextField tfName = new TextField();
        tfName.setPromptText("Username (unique)");
        tfName.getStyleClass().add("text-field");

        PasswordField pfPass = new PasswordField();
        pfPass.setPromptText("Password");
        pfPass.getStyleClass().add("text-field");

        HBox actions = new HBox(10);
        actions.setAlignment(Pos.CENTER_RIGHT);
        Button btnLogin = new Button("Login");
        btnLogin.getStyleClass().add("button-primary");
        Button btnRegister = new Button("Register");
        btnRegister.getStyleClass().add("button-primary");
        actions.getChildren().addAll(btnRegister, btnLogin);

        Label msg = new Label();
        msg.setWrapText(true);

        card.getChildren().addAll(lbl, tfName, pfPass, actions, msg);

        StackPane centerPane = new StackPane(card);
        centerPane.setPadding(new Insets(40));
        root.setCenter(centerPane);

        // Footer
        Label footer = new Label("Built with JavaFX • SkillSwap");
        footer.getStyleClass().add("footer");
        root.setBottom(footer);

        Scene scene = new Scene(root, 900, 560);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        setScene(scene);

        // Fade-in
        FadeTransition ft = new FadeTransition(Duration.millis(450), root);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();

        // Button actions
        btnRegister.setOnAction(e -> {
            String name = tfName.getText().trim();
            String pass = pfPass.getText().trim();
            if (name.isEmpty() || pass.isEmpty()) {
                showMsg(msg, "Enter name and password");
                return;
            }
            boolean ok = authDAO.register(name, "CS", pass, "student");
            showMsg(msg, ok ? "Registered successfully. Now login." : "Register failed (maybe user exists).");
        });

        btnLogin.setOnAction(e -> {
            String name = tfName.getText().trim();
            String pass = pfPass.getText().trim();
            if (name.isEmpty() || pass.isEmpty()) {
                showMsg(msg, "Enter name and password");
                return;
            }
            User u = authDAO.login(name, pass);
            if (u != null) {
                // success -> open dashboard
                dashboardView dash = new dashboardView(u);
                dash.show();
                this.close();
            } else {
                showMsg(msg, "Invalid credentials");
            }
        });
    }

    private void showMsg(Label lbl, String text) {
        lbl.setText(text);
    }
}
