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
public class GenreDao {
    @Autowired
    private SessionFactory _sessionFactory;

    @Autowired
    private VideoGameDao _videoGameDao;

    private Session getSession() {
        return _sessionFactory.getCurrentSession();
    }

    public void delete(Genre pGenre) {
        getSession().delete(pGenre);
    }

    public List getAll() {
        return getSession().createQuery("from Genre").list();
    }

    public Genre getById(long pId) {
        return (Genre) getSession().createQuery(
                "from Genre where id = :id")
                .setParameter("id", pId)
                .uniqueResult();
    }

    public Genre getOrCreateByName(String pName) {
        Genre result = getByName(pName);
        if(result == null) {
            result = new Genre();
            result.setName(pName);
            save(result);
        }
        return result;
    }

    public Genre getByName(String pName) {
        return (Genre) getSession().createQuery(
                "from Genre where name = :name")
                .setParameter("name", pName)
                .uniqueResult();
    }

    public void update(Genre pGenre) {
        getSession().update(pGenre);
    }

    public void save(Genre pGenre) {
        getSession().save(pGenre);
    }
}
