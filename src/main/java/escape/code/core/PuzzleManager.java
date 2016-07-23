package escape.code.core;

import escape.code.controllers.PuzzlesController;
import escape.code.models.Puzzle;
import escape.code.services.puzzleService.PuzzleService;

import java.util.LinkedList;
import java.util.stream.Collectors;

public class PuzzleManager {

    private LinkedList<Puzzle> puzzles;
    private PuzzleService puzzleService;


    public PuzzleManager() {
        this.puzzles = new LinkedList<>();
        //this.puzzleService = new PuzzleServiceImpl();
    }

    public void load(int level){
        this.puzzles = this.puzzleService.getAllByLevel(level).stream().
                collect(Collectors.toCollection(LinkedList<Puzzle>::new));
    }

    public void setPuzzle(){

        if (puzzles.size() > 0){
            PuzzlesController.setPuzzle(puzzles.pop());
        }
    }
}
