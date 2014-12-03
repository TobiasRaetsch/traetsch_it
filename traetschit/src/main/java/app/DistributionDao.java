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
public class DistributionDao {
    @Autowired
    private SessionFactory _sessionFactory;

    @Autowired
    private VideoGameDao _videoGameDao;

    private Session getSession() {
        return _sessionFactory.getCurrentSession();
    }

    public void delete(Distribution pDistribution) {
        getSession().delete(pDistribution);
    }

    public List getAll() {
        return getSession().createQuery("from Distribution").list();
    }

    public Genre getById(long pId) {
        return (Genre) getSession().createQuery(
                "from Distribution where id = :id")
                .setParameter("id", pId)
                .uniqueResult();
    }

    public Distribution getOrCreateByName(String pName) {
        Distribution result = getByName(pName);
        if(result == null) {
            result = new Distribution();
            result.setName(pName);
            save(result);
        }
        return result;
    }

    public Distribution getByName(String pName) {
        return (Distribution) getSession().createQuery(
                "from Distribution where name = :name")
                .setParameter("name", pName)
                .uniqueResult();
    }

    public void update(Distribution pDistribution) {
        getSession().update(pDistribution);
    }

    public void save(Distribution pDistribution) {
        getSession().save(pDistribution);
    }
}
