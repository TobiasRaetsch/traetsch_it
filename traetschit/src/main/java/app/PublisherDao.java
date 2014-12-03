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
public class PublisherDao {
    @Autowired
    private SessionFactory _sessionFactory;

    @Autowired
    private VideoGameDao _videoGameDao;

    private Session getSession() {
        return _sessionFactory.getCurrentSession();
    }

    public void delete(Publisher pPublisher) {
        getSession().delete(pPublisher);
    }

    public List getAll() {
        return getSession().createQuery("from Publisher").list();
    }

    public Publisher getById(long pId) {
        return (Publisher) getSession().createQuery(
                "from Publisher where id = :id")
                .setParameter("id", pId)
                .uniqueResult();
    }

    public Publisher getOrCreateByName(String pName) {
        Publisher result = getByName(pName);
        if(result == null) {
            result = new Publisher();
            result.setName(pName);
            save(result);
        }
        return result;
    }

    public Publisher getByName(String pName) {
        return (Publisher) getSession().createQuery(
                "from Publisher where name = :name")
                .setParameter("name", pName)
                .uniqueResult();
    }

    public void update(Publisher pPublisher) {
        getSession().update(pPublisher);
    }

    public void save(Publisher pPublisher) {
        getSession().save(pPublisher);
    }
}
