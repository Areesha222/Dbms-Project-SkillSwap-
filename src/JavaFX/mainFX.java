/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafx;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main JavaFX launcher. Run this to start the app.
 */
public class mainFX extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Show login window
        loginView login = new loginView();
        login.show(); // login extends Stage (see LoginView)
    }

    public static void main(String[] args) {
        launch(args);
    }
}
