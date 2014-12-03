package app;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class VideoGamePlatformDao {
    @Autowired
    private SessionFactory _sessionFactory;

    private Session getSession() {
        return _sessionFactory.getCurrentSession();
    }

    public void delete(VideoGamePlatform videoGamePlatform) {
        getSession().delete(videoGamePlatform);
    }

    public List<Platform> getPlatformByVideoGame(VideoGame pVideoGame) {
        Query query = getSession().createQuery("from VideoGamePlatform vg where vg.videoGame = :videoGame");
        query.setParameter("videoGame", pVideoGame);
        return query.list();
    }

    public List<VideoGame> getVideoGameByPlatform(Platform pPlatform, int pOffset, int pCount){
        Query query = getSession().createQuery("from VideoGamePlatform vg where vg.platform = :platform");
        query.setParameter("platform", pPlatform);
        query.setMaxResults(pCount);
        query.setFirstResult(pOffset);
        return query.list();
    }

    public void save(VideoGamePlatform pVideoGamePlatform) {
        getSession().save(pVideoGamePlatform);
    }
}
