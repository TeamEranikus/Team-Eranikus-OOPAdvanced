package escape.code.services.puzzleRectangleService;

import com.google.inject.Inject;
import escape.code.daos.puzzleRectangleDAO.PuzzleRectangleDao;
import escape.code.models.Puzzle;
import escape.code.models.PuzzleRectangle;
import escape.code.services.puzzleService.PuzzleService;

public class PuzzleRectangleServiceImpl implements PuzzleRectangleService {

    private PuzzleRectangleDao puzzleRectangleDao;
    private PuzzleService puzzleService;

    @Inject
    public PuzzleRectangleServiceImpl(PuzzleRectangleDao puzzleRectangleDao, PuzzleService puzzleService) {
        this.puzzleRectangleDao = puzzleRectangleDao;
        this.puzzleService = puzzleService;
    }

    @Override
    public void createPuzzleRectangle(String... params) {
        PuzzleRectangle puzzleRectangle = new PuzzleRectangle();
        puzzleRectangle.setName(params[0]);
        puzzleRectangle.setLevel(Integer.parseInt(params[1]));
        Puzzle puzzle = this.puzzleService.getOneById(Long.parseLong(params[2]));
        puzzleRectangle.setPuzzle(puzzle);
        this.puzzleRectangleDao.createPuzzleRectangle(puzzleRectangle);
    }

    @Override
    public PuzzleRectangle getFirst() {
        return this.puzzleRectangleDao.getFirst();
    }

    @Override
    public PuzzleRectangle getOneById(long id) {
        return this.puzzleRectangleDao.getOneById(id);
    }
}
