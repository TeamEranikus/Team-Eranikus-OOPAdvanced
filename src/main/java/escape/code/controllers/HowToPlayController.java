package escape.code.controllers;

import com.google.inject.Inject;
import escape.code.core.StageManager;
import escape.code.utils.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class HowToPlayController {

    @FXML
    private Button backToGame;
    private Stage currentStage;

    @Inject
    private static StageManager stageManager;

    public void backToMenu(ActionEvent event) throws IOException {
        this.currentStage = (Stage) this.backToGame.getScene().getWindow();
        stageManager.loadSceneToPrimaryStage(this.currentStage, Constants.MENU_FXML_PATH);
    }
}
