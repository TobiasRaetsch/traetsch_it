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
public class PlatformDao {
    @Autowired
    private SessionFactory _sessionFactory;

    @Autowired
    private VideoGameDao _videoGameDao;

    private Session getSession() {
        return _sessionFactory.getCurrentSession();
    }

    public void delete(Platform platform) {
        getSession().delete(platform);
    }

    public List getAll() {
        return getSession().createQuery("from Platform").list();
    }

    public Platform getById(long pId) {
        return (Platform) getSession().createQuery(
                "from Platform where id = :id")
                .setParameter("id", pId)
                .uniqueResult();
    }

    public Platform getOrCreateByName(String pName) {
        Platform result = getByName(pName);
        if(result == null) {
            result = new Platform();
            result.setName(pName);
            save(result);
        }
        return result;
    }

    public Platform getByName(String pName) {
        return (Platform) getSession().createQuery(
                "from Platform where name = :name")
                .setParameter("name", pName)
                .uniqueResult();
    }

    public void update(Platform platform) {
        getSession().update(platform);
    }

    public void save(Platform platform) {
        getSession().save(platform);
    }
}
