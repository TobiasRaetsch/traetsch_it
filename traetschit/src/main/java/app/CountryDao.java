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
public class CountryDao {
    @Autowired
    private SessionFactory _sessionFactory;

    @Autowired
    private VideoGameDao _videoGameDao;

    private Session getSession() {
        return _sessionFactory.getCurrentSession();
    }

    public void delete(Country pCountry) {
        getSession().delete(pCountry);
    }

    public List getAll() {
        return getSession().createQuery("from Country").list();
    }

    public Genre getById(long pId) {
        return (Genre) getSession().createQuery(
                "from Country where id = :id")
                .setParameter("id", pId)
                .uniqueResult();
    }

    public Country getOrCreateByName(String pName) {
        Country result = getByName(pName);
        if(result == null) {
            result = new Country();
            result.setName(pName);
            save(result);
        }
        return result;
    }

    public Country getByName(String pName) {
        return (Country) getSession().createQuery(
                "from Country where name = :name")
                .setParameter("name", pName)
                .uniqueResult();
    }

    public void update(Country pCountry) {
        getSession().update(pCountry);
    }

    public void save(Country pCountry) {
        getSession().save(pCountry);
    }
}
