package escape.code.services.puzzleRectangleService;

import escape.code.models.PuzzleRectangle;

import java.util.List;

public interface PuzzleRectangleService {
    void createPuzzleRectangle(String... params);

    PuzzleRectangle getFirst();

    List<PuzzleRectangle> getAllPuzzleRectangleByLevel(int level);

    PuzzleRectangle getOneById(long id);
}
