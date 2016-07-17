package escape.code.core;

import escape.code.utils.Constants;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;


public class StageManager {
    private AnchorPane root;
    public FXMLLoader loadSceneToPrimaryStage(Stage currentStage, String fxmlPath) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPath));
        try {
            Region region = (Region) fxmlLoader.load();
            double origW = Constants.FULL_HD_WIDTH;
            double origH = Constants.FULL_HD_HIGH;

            if (region.getPrefWidth() == Region.USE_COMPUTED_SIZE) {
                region.setPrefWidth(origW);
            } else {
                origW = region.getPrefWidth();
            }

            if (region.getPrefHeight() == Region.USE_COMPUTED_SIZE) {
                region.setPrefHeight(origH);
            } else {
                origH = region.getPrefHeight();
            }
            Group group = new Group(region);
            StackPane rootPane = new StackPane();
            rootPane.getChildren().add(group);
            Scene scene = new Scene(rootPane, origW, origH);
            group.scaleXProperty().bind(scene.widthProperty().divide(origW));
            group.scaleYProperty().bind(scene.heightProperty().divide(origH));
            currentStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
        currentStage.show();
        currentStage.centerOnScreen();
        return fxmlLoader;
    }

    public Stage loadNewStage(String fxmlPath) throws IOException {
        Stage newStage = new Stage();
        newStage.setTitle(Constants.TITLE);
        root = FXMLLoader.load(getClass().getResource(fxmlPath));
        Region contentRootRegion = (Region) FXMLLoader.load(getClass().getResource(fxmlPath));
        double origW = Constants.FULL_HD_WIDTH;
        double origH = Constants.FULL_HD_HIGH;

        if (contentRootRegion.getPrefWidth() == Region.USE_COMPUTED_SIZE) {
            contentRootRegion.setPrefWidth(origW);
        } else {
            origW = contentRootRegion.getPrefWidth();
        }

        if (contentRootRegion.getPrefHeight() == Region.USE_COMPUTED_SIZE) {
            contentRootRegion.setPrefHeight(origH);
        } else {
            origH = contentRootRegion.getPrefHeight();
        }
        Group group = new Group(contentRootRegion);
        StackPane rootPane = new StackPane();
        rootPane.getChildren().add(group);
        Scene scene = new Scene(rootPane, origW, origH);
        group.scaleXProperty().bind(scene.widthProperty().divide(origW));
        group.scaleYProperty().bind(scene.heightProperty().divide(origH));
        // primaryStage.setResizable(false);
        newStage.setScene(scene);
        newStage.show();
        newStage.centerOnScreen();
        return newStage;
    }
}

