package escape.code.services.puzzleService;

import escape.code.daos.puzzledaos.PuzzleDao;
import escape.code.daos.puzzledaos.PuzzleDaoImpl;
import escape.code.enums.Item;
import escape.code.models.Puzzle;

import java.util.List;

public class PuzzleServiceImpl implements PuzzleService {

    private static PuzzleDao puzzleDao;

    static {
        puzzleDao = new PuzzleDaoImpl();
    }


    @Override
    public void createPuzzle(String... params) {
        Puzzle puzzle = new Puzzle();
        puzzle.setQuestion(params[0]);
        puzzle.setCorrectAnswer(params[1]);
        puzzle.setHint(params[2]);
        puzzle.setNextClue(params[3]);
        puzzle.setImagePath(params[4]);
        puzzle.setLevel(Integer.parseInt(params[5]));
        puzzle.setItem(Item.valueOf(params[6].toUpperCase()));
        puzzleDao.createPuzzle(puzzle);
    }

    @Override
    public List<Puzzle> getAllByLevel(int level) {
        return puzzleDao.getAllByLevel(level);
    }
}
