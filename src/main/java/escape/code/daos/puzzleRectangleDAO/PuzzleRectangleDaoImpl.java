package escape.code.daos.puzzleRectangleDAO;

import com.google.inject.Inject;
import escape.code.models.PuzzleRectangle;

import javax.persistence.EntityManager;
import java.util.List;

public class PuzzleRectangleDaoImpl implements PuzzleRectangleDao {

    @Inject
    private EntityManager entityManager;

    @Override
    public void createPuzzleRectangle(PuzzleRectangle currentRectangle) {
        this.entityManager.getTransaction().begin();
        this.entityManager.persist(currentRectangle);
        this.entityManager.getTransaction().commit();
    }

    @Override
    @SuppressWarnings("unchecked")
    public PuzzleRectangle getFirst() {
        List<PuzzleRectangle> rectangles = this.entityManager
                .createQuery("SELECT rect FROM PuzzleRectangle AS rect where rect.id=:id")
                .setParameter("id",1L).getResultList();
        return rectangles.get(0);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<PuzzleRectangle> getAllPuzzleRectangleByLevel(int level) {
        List<PuzzleRectangle> rectangles = this.entityManager
                .createQuery("SELECT rect FROM PuzzleRectangle AS rect where rect.level like :level")
                .setParameter("level", level).getResultList();
        return rectangles;
    }

    @Override
    @SuppressWarnings("unchecked")
    public PuzzleRectangle getOneById(long id) {
        List<PuzzleRectangle> rectangles = this.entityManager
                .createQuery("SELECT rect FROM PuzzleRectangle AS rect where rect.id=:id")
                .setParameter("id", id).getResultList();
        return rectangles.get(0);
    }
}
