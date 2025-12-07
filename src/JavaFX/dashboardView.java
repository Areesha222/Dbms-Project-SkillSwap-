/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafx;

import dao.matchDAO;
import model.User;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

/**
 * Dashboard stage uses tabs and contains the main functional views.
 */
public class dashboardView extends Stage {

    private User user;
    private matchDAO matchDAO = new matchDAO();

    public dashboardView(User user) {
        this.user = user;
        setTitle("SkillSwap Dashboard - " + user.getName());
        TabPane tabs = new TabPane();

        // Each custom view is a Node (Pane/Control)
        Tab t1 = new Tab("Add Skill", new AddSkillView(user));
        t1.setClosable(false);

        Tab t2 = new Tab("Request Skill", new RequestSkillView(user));
        t2.setClosable(false);

        Tab t3 = new Tab("Matches", new MatchesView(user));
        t3.setClosable(false);

        Tab t4 = new Tab("Ratings", new RatingsView(user));
        t4.setClosable(false);

        tabs.getTabs().addAll(t1, t2, t3, t4);

        // admin-only tab example
        if ("admin".equalsIgnoreCase(user.getRole())) {
            Tab admin = new Tab("Admin");
            AdminPane ap = new AdminPane(); // small helper below
            admin.setContent(ap);
            admin.setClosable(false);
            tabs.getTabs().add(admin);
        }

        setScene(new Scene(tabs, 800, 500));
    }

    // small inner admin pane
    private class AdminPane extends javafx.scene.layout.VBox {
        public AdminPane() {
            javafx.scene.control.Button btnGen = new javafx.scene.control.Button("Generate Matches");
            btnGen.setOnAction(e -> {
                matchDAO.generateMatches();
                javafx.scene.control.Alert a = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION, "Matches generated");
                a.setHeaderText(null);
                a.showAndWait();
            });
            setSpacing(10);
            setPadding(new javafx.geometry.Insets(10));
            getChildren().add(btnGen);
        }
    }
}
