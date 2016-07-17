package escape.code.controllers;

import escape.code.core.ResizableCanvas;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class DemoController implements Initializable {

    @FXML
    private ImageView imagePlayer;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private ResizableCanvas mainCanvas;

    public ResizableCanvas getMainCanvas() {
        return mainCanvas;
    }

    public ImageView getImagePlayer() {
        return imagePlayer;
    }

    public void onMouseClickKeyItem(Event event) {

    }

    public void onMouseClickPapierItem(Event event) {

    }

    public void onMouseClickBookItem(Event event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

}
