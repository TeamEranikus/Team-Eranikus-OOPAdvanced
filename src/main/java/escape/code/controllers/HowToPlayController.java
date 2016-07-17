package escape.code.controllers;

import escape.code.core.StageManager;
import escape.code.utils.Constants;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class HowToPlayController {

    @FXML
    private Button backToGame;

    private Stage currentStage;
    private StageManager stageManager;


    public void  backToMenu(ActionEvent event) throws IOException {
        currentStage = (Stage) backToGame.getScene().getWindow();
        stageManager = new StageManager();
        stageManager.loadSceneToPrimaryStage(currentStage,Constants.MENU_FXML_PATH);
    }
}
