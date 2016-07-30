package escape.code.daos.puzzleDAO;

import com.google.inject.Inject;
import escape.code.models.Puzzle;
import javax.persistence.EntityManager;
import java.util.List;

public class PuzzleDaoImpl implements PuzzleDao {

    @Inject
    private EntityManager entityManager;

    @Override
    @SuppressWarnings("unchecked")
    public List<Puzzle> getAllByLevel(int level) {
      return this.entityManager
                .createQuery("SELECT puzzle FROM Puzzle AS puzzle where puzzle.level like :level")
                .setParameter("level",level)
                .getResultList();
    }

    @Override
    public void createPuzzle(Puzzle puzzle) {
        this.entityManager.getTransaction().begin();
        this.entityManager.persist(puzzle);
        this.entityManager.getTransaction().commit();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Puzzle getOneById(long id) {
        List<Puzzle> allPuzzle = this.entityManager
                .createQuery("SELECT puzzle FROM Puzzle AS puzzle where puzzle.id like :id")
                .setParameter("id",id)
                .getResultList();
        return allPuzzle.get(0);
    }
}
