package escape.code;

import escape.code.core.Game;
import javafx.application.Application;
import javafx.stage.Stage;


public class AppRun extends Application {

    @Override
    public void start(Stage primaryStage) {


//        //Todo: when you first build the program change hibernateUnils line 55 form update to create, then comment from here
//        String[][] taskParams = {
//                {"-What the following code will print?", "piano", "Do you really need the numbers?", "What could it be??", "/pictures/ComputerTaskWhite.png", "0", "NONE"},
//                {"-One piano button is stuck!", "13", "How will you EXit?", "You are one step away from the exit.", "/pictures/PianoTask.jpg", "0", "BOOK"},
//                {"-The way to unlock the door is to know the secret number.", "111", "Do you see something unusual?", "The door is open now.", "/pictures/LibraryWithJoker.jpg", "0", "KEY"},
//                {"-Does the chessboard reminds you of something?", "matrix", "[][]", "Oh, look it's currently playing!", "/pictures/chessboard.jpg", "1", "NONE"},
//                {"", "coffee", "It's hot", "What could it be??", "/pictures/TvPuzzle.png", "1", "BOOK"},
//                {"Who collects the garbage?", "garbage collector", "Be like Java!! Collect your own garbage.", "The second door is open now.", "/pictures/Bin.png", "1", "KEY"}
//
//        };
//        PuzzleService puzzleService = new PuzzleServiceImpl();
//        Arrays.stream(taskParams).forEach(puzzleService::createPuzzle);
//        // Todo: to here, and change to update again

        Game.initialize(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}