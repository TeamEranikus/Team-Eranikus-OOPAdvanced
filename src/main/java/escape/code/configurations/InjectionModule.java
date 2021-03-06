package escape.code.configurations;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import escape.code.controllers.HowToPlayController;
import escape.code.controllers.LoginController;
import escape.code.controllers.MenuController;
import escape.code.core.engine.EngineImpl;
import escape.code.core.Game;
import escape.code.core.StageManager;
import escape.code.daos.puzzleDAO.PuzzleDao;
import escape.code.daos.puzzleDAO.PuzzleDaoImpl;
import escape.code.daos.puzzleRectangleDAO.PuzzleRectangleDao;
import escape.code.daos.puzzleRectangleDAO.PuzzleRectangleDaoImpl;
import escape.code.daos.userDAO.UserDao;
import escape.code.daos.userDAO.UserDaoImpl;
import escape.code.services.puzzleRectangleService.PuzzleRectangleService;
import escape.code.services.puzzleRectangleService.PuzzleRectangleServiceImpl;
import escape.code.services.puzzleService.PuzzleService;
import escape.code.services.puzzleService.PuzzleServiceImpl;
import escape.code.services.userService.UserService;
import escape.code.services.userService.UserServiceImpl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Injects game dependencies by binding interfaces to corresponding implementations
 */
public class InjectionModule extends AbstractModule {

    private static final ThreadLocal<EntityManager> ENTITY_MANAGER_CACHE = new ThreadLocal<>();
    private static final ThreadLocal<StageManager> STAGE_MANAGER_CACHE = new ThreadLocal<>();

    /**
     * Binds interfaces tho corresponding implementations
     */
    public void configure() {
        bind(UserDao.class).to(UserDaoImpl.class);
        bind(PuzzleDao.class).to(PuzzleDaoImpl.class);
        bind(PuzzleRectangleDao.class).to(PuzzleRectangleDaoImpl.class);
        bind(UserService.class).to(UserServiceImpl.class);
        bind(PuzzleService.class).to(PuzzleServiceImpl.class);
        bind(PuzzleRectangleService.class).to(PuzzleRectangleServiceImpl.class);

        requestStaticInjection(LoginController.class);
        requestStaticInjection(EngineImpl.class);
        requestStaticInjection(Game.class);
        requestStaticInjection(HowToPlayController.class);
        requestStaticInjection(MenuController.class);
    }

    /**
     * Provides entity manager by given entity manager factory, singleton implementation
     * @param entityManagerFactory - creates entity manager
     * @return created entity manager
     */

    @Provides
    @Singleton
    public EntityManager provideEntityManager(EntityManagerFactory entityManagerFactory) {
        EntityManager entityManager = ENTITY_MANAGER_CACHE.get();
        if (entityManager == null) {
            ENTITY_MANAGER_CACHE.set(entityManager = entityManagerFactory.createEntityManager());
        }
        return entityManager;
    }

    /**
     * Provides stage manager, singleton implementation
     * @return created stage manager
     */
    @Provides
    @Singleton
    public StageManager provideStageManager() {
        StageManager stageManager = STAGE_MANAGER_CACHE.get();
        if (stageManager == null) {
            STAGE_MANAGER_CACHE.set(stageManager = new StageManager());
        }
        return stageManager;
    }

    /**
     * Provides entity manager factory, used to create the entity manager
     * @return created entity manager factory
     */
    @Provides
    @Singleton
    private EntityManagerFactory provideEntityManagerFactory() {
        return Persistence.createEntityManagerFactory("escape_code");
    }
}
