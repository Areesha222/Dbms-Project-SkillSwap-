/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Entry point for the modernized JavaFX UI.
 * Loads CSS and shows Login screen.
 */
public class mainFX extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Show Login Stage (LoginView is a Stage)
        loginView login = new loginView();
        // Load CSS into the login scene inside LoginView (LoginView loads it itself).
        login.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

