package escape.code.daos.puzzledaos;

import escape.code.configurations.HibernateUtils;
import escape.code.models.Puzzle;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

public class PuzzleDaoImpl implements PuzzleDao {
    private static Session session;
    static {
        session = HibernateUtils.openSession();
    }

    @Override
    public List<Puzzle> getAllByLevel(int level) {
        session.beginTransaction();
        Query query = session
                .createQuery("SELECT pu FROM Puzzle AS pu where pu.level like :level");
        query.setParameter("level",level);

        List<Puzzle> allPuzzle = query.list();
        session.getTransaction().commit();
        return allPuzzle;
    }

    @Override
    public void createPuzzle(Puzzle puzzle) {
        session.beginTransaction();
        session.persist(puzzle);
        session.getTransaction().commit();
    }
}
