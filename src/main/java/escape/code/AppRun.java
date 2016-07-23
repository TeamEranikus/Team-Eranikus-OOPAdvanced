package escape.code;

import com.google.inject.Guice;
import com.google.inject.Injector;
import escape.code.configurations.InjectionModule;
import escape.code.core.Game;
import escape.code.services.puzzleRectangleService.PuzzleRectangleService;
import escape.code.services.puzzleService.PuzzleService;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.Arrays;


public class AppRun extends Application {

    @Override
    public void start(Stage primaryStage) {

//        //Todo: when you first build the program change hibernateUnils line 55 form update to create, then comment from here
        String[][] taskParams = {
                {"-What the following code will print?", "piano", "Do you really need the numbers?", "What could it be??", "/pictures/ComputerTaskWhite.png", "0", "NONE"},
                {"-One piano button is stuck!", "13", "How will you EXit?", "You are one step away from the exit.", "/pictures/PianoTask.jpg", "0", "BOOK"},
                {"-The way to unlock the door is to know the secret number.", "111", "Do you see something unusual?", "The door is open now.", "/pictures/LibraryWithJoker.jpg", "0", "KEY"},
                {"Success pass the level", "", "", "", "/pictures/LibraryWithJoker.jpg", "0", "NONE"}, //// TODO: 7/17/2016
                {"-Does the chessboard reminds you of something?", "matrix", "[][]", "Oh, look it's currently playing!", "/pictures/chessboard.jpg", "1", "NONE"},
                {"", "coffee", "It's hot", "What could it be??", "/pictures/TvPuzzle.png", "1", "BOOK"},
                {"Who collects the garbage?", "garbage collector", "Be like Java!! Collect your own garbage.", "The second door is open now.", "/pictures/Bin.png", "1", "KEY"},
                {"Success pass the level", "", "", "", "/pictures/LibraryWithJoker.jpg", "0", "NONE"} //// TODO: 7/17/2016
        };
        Injector injector = Guice.createInjector(new InjectionModule());
        PuzzleService puzzleService = injector.getInstance(PuzzleService.class);
        Arrays.stream(taskParams).forEach(puzzleService::createPuzzle);
//        // Todo: to here, and change to update again
        String[][] taskParamsRect = {
                {"computer", "0", "1"},
                {"piano", "0", "2"},
                {"library", "0", "3"},
                {"door","0","4"},
                {"chess", "1", "5"},
                {"tv", "1", "6"},
                {"kitchen", "1", "7"},
                {"door", "1", "8"},

        };
        PuzzleRectangleService puzzleRectangleService = injector.getInstance(PuzzleRectangleService.class);
        Arrays.stream(taskParamsRect).forEach(puzzleRectangleService::createPuzzleRectangle);

        Game.initialize(primaryStage);

    }

    public static void main(String[] args) {
        launch(args);
    }
}