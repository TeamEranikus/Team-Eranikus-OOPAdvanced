package escape.code.services.puzzleRectangleService;

import escape.code.models.PuzzleRectangle;

public interface PuzzleRectangleService {
    void createPuzzleRectangle(String... params);
    PuzzleRectangle getFirst();
   //List<PuzzleRectangle> getAllPuzzleRectangleByLevel(int level);
   PuzzleRectangle getOneById(long id);
}
