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
public class DeveloperDao {
    @Autowired
    private SessionFactory _sessionFactory;

    @Autowired
    private VideoGameDao _videoGameDao;

    private Session getSession() {
        return _sessionFactory.getCurrentSession();
    }

    public void delete(Developer developer) {
        getSession().delete(developer);
    }

    public List getAll() {
        return getSession().createQuery("from Developer").list();
    }

    public Developer getById(long id) {
        return (Developer) getSession().createQuery(
                "from Developer where id = :id")
                .setParameter("id", id)
                .uniqueResult();
    }

    public Developer getOrCreateByName(String pName) {
        Developer result = getByName(pName);
        if(result == null) {
            result = new Developer();
            result.setName(pName);
            save(result);
        }
        return result;
    }

    public Developer getByName(String pName) {
        return (Developer) getSession().createQuery(
                "from Developer where name = :name")
                .setParameter("name", pName)
                .uniqueResult();
    }

    public void update(Image image) {
        getSession().update(image);
    }

    public void save(Developer pDeveloper) {
        getSession().save(pDeveloper);
    }
}
