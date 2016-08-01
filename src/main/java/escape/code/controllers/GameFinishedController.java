package escape.code.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class GameFinishedController {

    @FXML
    private Button exitButton;

    public void exitGame(ActionEvent event){
        System.exit(0);
    }
}
