package escape.code.controllers;

import escape.code.models.Puzzle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class PuzzleController implements Initializable {
    @FXML
    Button hintButton;
    @FXML
    Label hintText;
    @FXML
    Label description;
    @FXML
    Label nextClue;
    @FXML
    ImageView puzzleImage;
    @FXML
    TextField userAnswer;

    @FXML
    Button answerButton;

    private static Puzzle puzzle;

    public static void setPuzzle(Puzzle puzzleToSet){
        puzzle = puzzleToSet;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.puzzleImage.setImage(new Image(puzzle.getImagePath()));
        centerImage();
        this.hintText.setText(puzzle.getHint());
        this.description.setText(puzzle.getQuestion());
        this.nextClue.setText("Incorrect answer!");


    }

    public void giveHint(ActionEvent actionEvent) {
        if(actionEvent.getSource()==hintButton) {
            this.hintText.setVisible(true);
        }
    }


    public void checkAnswer(ActionEvent actionEvent) {
        String userAnswerString = this.userAnswer.getText().toLowerCase().trim();
        if (puzzle.checkCorrectAnswer(userAnswerString)){
            this.nextClue.setText(puzzle.getNextClue());
            this.nextClue.setVisible(true);
            puzzle.setAnswerGiven(true);
            this.answerButton.setDisable(true);
        }
        else{
            this.nextClue.setVisible(true);
        }
    }

    private void centerImage() {
        Image image = puzzleImage.getImage();

        if (image != null) {
            double width = 0;
            double height = 0;

            double ratioX = puzzleImage.getFitWidth() / image.getWidth();
            double ratioY = puzzleImage.getFitHeight() / image.getHeight();

            double reduceCoeff = 0;
            if (ratioX >= ratioY) {
                reduceCoeff = ratioY;
            } else {
                reduceCoeff = ratioX;
            }

            width = image.getWidth() * reduceCoeff;
            height = image.getHeight() * reduceCoeff;

            puzzleImage.setX((puzzleImage.getFitWidth() - width) / 2);
            puzzleImage.setY((puzzleImage.getFitHeight() - height) / 2);

        }
    }
}
