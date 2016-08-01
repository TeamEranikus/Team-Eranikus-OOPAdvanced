package escape.code.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * Controls fxml file for the finish game scene
 */
public class GameFinishedController {

    @FXML
    private Button exitButton;

    /**
     * Exits executing game
     * @param event
     */
    public void exitGame(ActionEvent event){
        System.exit(0);
    }
}
