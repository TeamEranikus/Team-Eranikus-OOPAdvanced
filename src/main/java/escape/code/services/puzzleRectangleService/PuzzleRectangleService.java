package escape.code.services.puzzleRectangleService;

import escape.code.models.PuzzleRectangle;

public interface PuzzleRectangleService {

    void createPuzzleRectangle(String... params);

    PuzzleRectangle getFirst();

    PuzzleRectangle getOneById(long id);
}
