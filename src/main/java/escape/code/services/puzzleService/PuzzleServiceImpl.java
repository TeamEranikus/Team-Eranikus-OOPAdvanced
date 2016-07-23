package escape.code.services.puzzleService;

import com.google.inject.Inject;
import escape.code.daos.puzzleDAO.PuzzleDao;
import escape.code.enums.Item;
import escape.code.models.Puzzle;

import java.util.List;

public class PuzzleServiceImpl implements PuzzleService {

    private PuzzleDao puzzleDao;

    @Inject
    public PuzzleServiceImpl(PuzzleDao puzzleDao){
        this.puzzleDao = puzzleDao;
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
        this.puzzleDao.createPuzzle(puzzle);
    }

    @Override
    public List<Puzzle> getAllByLevel(int level) {
        return this.puzzleDao.getAllByLevel(level);
    }

    @Override
    public Puzzle getOneById(long id) {
        return puzzleDao.getOneById(id);
    }
}
