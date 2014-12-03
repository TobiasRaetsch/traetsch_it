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
public class VideoGameDao {
    @Autowired
    private SessionFactory _sessionFactory;

    private Session getSession() {
        return _sessionFactory.getCurrentSession();
    }

    public void save(VideoGame videoGame) {
        getSession().save(videoGame);
    }

    public void delete(VideoGame videoGame) {
        getSession().delete(videoGame);
    }

    public List getAll() {
        return getSession().createQuery("from VideoGame").list();
    }

    public VideoGame getById(long id) {
        return (VideoGame) getSession().createQuery(
                "from VideoGame where id = :id")
                .setParameter("id", id)
                .uniqueResult();
    }

    public List<VideoGame> getVideoGamesByPlatform(Platform platform) {
        Query query = getSession().createQuery("select r from Platform r where r.platform = :platform ");
        query.setParameter("platform", platform);
        return query.list();
    }

    public List<VideoGame> getVideoGamesByGenre(Genre genre) {
        Query query = getSession().createQuery("select r from Genre r where r.genre = :genre ");
        query.setParameter("genre", genre);
        return query.list();
    }

    public VideoGame getByName(String pName) {
        return (VideoGame) getSession().createQuery(
                "from VideoGame where name = :name")
                .setParameter("name", pName)
                .uniqueResult();
    }

    public void update(VideoGame videoGame) {
        getSession().update(videoGame);
    }
}
