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
public class VideoGameReleaseDao {
    @Autowired
    private SessionFactory _sessionFactory;

    @Autowired
    private VideoGameDao _videoGameDao;

    private Session getSession() {
        return _sessionFactory.getCurrentSession();
    }

    public void delete(VideoGameRelease pVideoGameRelease) {
        getSession().delete(pVideoGameRelease);
    }

    public List getAll() {
        return getSession().createQuery("from VideoGameRelease").list();
    }

    public Genre getById(long pId) {
        return (Genre) getSession().createQuery(
                "from VideoGameRelease where id = :id")
                .setParameter("id", pId)
                .uniqueResult();
    }


    public Genre getByVideoGame(VideoGame pVideoGame) {
        return (Genre) getSession().createQuery(
                "from VideoGameRelease vgr where vgr.videoGame = :videoGame")
                .setParameter("videoGame", pVideoGame)
                .uniqueResult();
    }

    public void update(VideoGameRelease pVideoGameRelease) {
        getSession().update(pVideoGameRelease);
    }

    public void save(VideoGameRelease pVideoGameRelease) {
        getSession().save(pVideoGameRelease);
    }
}
