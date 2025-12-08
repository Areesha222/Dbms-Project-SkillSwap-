/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafx;

import model.User;
import dao.matchDAO;
import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.control.Label;


/**
 * Modern dashboard with sidebar navigation.
 * Center area swaps content panes for each section.
 */
public class dashboardView extends Stage {

    private final User user;
    private final matchDAO matchDAO = new matchDAO();

    // center panes (views)
    private addskillView addSkillPane;
    private requestskillView requestPane;
    private matchesView matchesPane;
    private ratingsView ratingsPane;

    private BorderPane root;
    private StackPane centerHolder;

    public dashboardView(User user) {
        this.user = user;
        setTitle("SkillSwap - Dashboard (" + user.getName() + ")");

        root = new BorderPane();
        root.getStyleClass().add("root");

        // Header
        HBox header = new HBox();
        header.getStyleClass().add("header");
        header.setPadding(new Insets(12));

        // Create Label with light blue font
        Label headerLabel = new Label("SkillSwap — Welcome, " + user.getName());
        headerLabel.getStyleClass().add("header-label-lightblue"); // CSS class

header.getChildren().add(headerLabel);
root.setTop(header);


        // sidebar
        VBox sidebar = new VBox(6);
        sidebar.getStyleClass().add("sidebar");
        sidebar.setPadding(new Insets(18));
        sidebar.setSpacing(6);

        Button btnDashboard = createSidebarButton("Dashboard");
        Button btnAddSkill = createSidebarButton("Add Skill");
        Button btnRequest = createSidebarButton("Request Skill");
        Button btnMatches = createSidebarButton("Matches");
        Button btnRatings = createSidebarButton("Ratings");
        Button btnGenerate = createSidebarButton("Generate Matches");
        Button btnLogout = createSidebarButton("Logout");

        sidebar.getChildren().addAll(
                btnDashboard, new Separator(), btnAddSkill, btnRequest,
                btnMatches, btnRatings, new Separator(), btnGenerate, btnLogout
        );

        root.setLeft(sidebar);

        // center holder
        centerHolder = new StackPane();
        centerHolder.setPadding(new Insets(18));
        root.setCenter(centerHolder);

        // initialize views
        addSkillPane = new addskillView(user);
        requestPane = new requestskillView(user);
        matchesPane = new matchesView(user);
        ratingsPane = new ratingsView(user);

        // default view
        setCenterContent(buildWelcomePane());

        // sidebar actions
        btnDashboard.setOnAction(e -> setCenterContent(buildWelcomePane()));
        btnAddSkill.setOnAction(e -> setCenterContent(addSkillPane));
        btnRequest.setOnAction(e -> setCenterContent(requestPane));
        btnMatches.setOnAction(e -> setCenterContent(matchesPane));
        btnRatings.setOnAction(e -> setCenterContent(ratingsPane));

        btnGenerate.setOnAction(e -> {
            matchDAO.generateMatches();
            // refresh matches list if open
            matchesPane.reload();
            javafx.scene.control.Alert a = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION, "Matches generated");
            a.setHeaderText(null);
            a.showAndWait();
        });

        btnLogout.setOnAction(e -> {
            this.close();
            new loginView().show();
        });

        Scene scene = new Scene(root, 1000, 650);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        setScene(scene);
    }

    private Button createSidebarButton(String text) {
        Button b = new Button(text);
        b.getStyleClass().add("sidebar-button");
        b.setMaxWidth(Double.MAX_VALUE);
        return b;
    }

    private void setCenterContent(javafx.scene.Node node) {
        centerHolder.getChildren().clear();
        centerHolder.getChildren().add(node);
        // fade transition for smoothness
        FadeTransition ft = new FadeTransition(Duration.millis(300), node);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();
    }

    private javafx.scene.Node buildWelcomePane() {
        VBox box = new VBox(12);
        box.getStyleClass().add("card");
        box.setPadding(new Insets(20));
        javafx.scene.control.Label t = new javafx.scene.control.Label("Dashboard");
        t.getStyleClass().add("section-title");
        javafx.scene.control.Label info = new javafx.scene.control.Label("Use the sidebar to navigate the app. Add skills, request skills, view matches and leave ratings.");
        info.setWrapText(true);
        box.getChildren().addAll(t, info);
        return box;
    }
}
