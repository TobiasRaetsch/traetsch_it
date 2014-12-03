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
public class VideoGameOverviewItemDao {
    @Autowired
    private SessionFactory _sessionFactory;

    private Session getSession() {
        return _sessionFactory.getCurrentSession();
    }

    public void delete(VideoGameOverviewItem pVideoGameOverviewItem) {
        getSession().delete(pVideoGameOverviewItem);
    }

    public Developer getDeveloperByVideoGame(VideoGame pVideoGame) {
        Query query = getSession().createQuery("from VideoGameProperty vg where vg.videoGame = :videoGame");
        query.setParameter("videoGame", pVideoGame);
        VideoGameOverviewItem videoGameProperty = (VideoGameOverviewItem) query.uniqueResult();
        return (Developer) videoGameProperty.getDeveloper();
    }

    public Genre getGenreByVideoGame(VideoGame pVideoGame) {
        Query query = getSession().createQuery("from VideoGameProperty vg where vg.videoGame = :videoGame");
        query.setParameter("videoGame", pVideoGame);
        VideoGameOverviewItem videoGameProperty = (VideoGameOverviewItem) query.uniqueResult();
        return (Genre) videoGameProperty.getGenre();
    }

    public List<VideoGame> getVideoGamesByDeveloper(Developer pDeveloper, int pOffset, int pCount){
        Query query = getSession().createQuery("from VideoGameProperty vg where vg.developer = :developer");
        query.setParameter("developer", pDeveloper);
        query.setMaxResults(pCount);
        query.setFirstResult(pOffset);
        return query.list();
    }

    public VideoGameOverviewItem getVideoGameOverviewItemByVideoGame(VideoGame pVideoGame){
        Query query = getSession().createQuery("from VideoGameOverviewItem vg where vg.videoGame = :videoGame");
        query.setParameter("videoGame", pVideoGame);
        return (VideoGameOverviewItem) query.uniqueResult();
    }

    public List getAll() {
        return getSession().createQuery("from VideoGameOverviewItem").list();
    }

    public void save(VideoGameOverviewItem pVideoGameOverviewItem) {
        getSession().save(pVideoGameOverviewItem);
    }
}
