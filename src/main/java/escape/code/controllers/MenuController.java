package escape.code.controllers;

import com.google.inject.Inject;
import escape.code.core.Game;
import escape.code.core.StageManager;
import escape.code.utils.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController {

    // Fields must be private with FXML annotations
    @FXML
    private Button newGameButton;

    @FXML
    private Button howToPlayButton;

    @FXML
    private Button quitButton;

    private Stage currentStage;

    @Inject
    private StageManager stageManager;

    public void onNewGameClicked(ActionEvent event) throws IOException {
        Game.run();
    }

    public void onHowToPlayClicked(ActionEvent event) throws IOException {
        currentStage = (Stage)howToPlayButton.getScene().getWindow();
        stageManager.loadSceneToPrimaryStage(currentStage,Constants.HOW_TO_PLAY_FXML_PATH);
    }

    public void onQuitClicked(ActionEvent event) {
        Stage stage = (Stage) quitButton.getScene().getWindow();
        stage.close();
        // should call confirm box? "Are you sure?"
    }
}
