package app;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.UUID;

@Repository
@Transactional
public class ImageDao {
    @Autowired
    private SessionFactory _sessionFactory;

    @Autowired
    private VideoGameDao _videoGameDao;

    @Value("${imagesPath}")
    String imagesPath;

    private Session getSession() {
        return _sessionFactory.getCurrentSession();
    }

    public void delete(Image image) {
        getSession().delete(image);
    }

    public Image downloadAndCreateImage(String imageUrl) throws IOException {
        UUID uuid = UUID.randomUUID();
        String s = uuid.toString();
        URL url = new URL(imageUrl);
        int startImageType = imageUrl.lastIndexOf(".");
        String imageType = "";
        if(startImageType > -1) {
            imageType = imageUrl.substring(startImageType);
        }

        String imageName = s + imageType;
        String destinationFile = getFullImagePath(imageName, imagesPath);
        FileOutputStream output = new FileOutputStream(destinationFile);
        try {
            IOUtils.copy(url.openStream(), output);
        } finally {
            output.close();
        }
        Image image = new Image();
        image.setPath(imageName);


        save(image);

        return image;
    }

    public static String getFullImagePath(String pImageName, String pImageBasePath) {
        return pImageBasePath + (StringUtils.isNotBlank(pImageBasePath) && !StringUtils.endsWith(pImageBasePath, "/") ? "/" : "") + pImageName;
    }

    public List<Image> getAll() {
        Query query = getSession().createQuery("from Image");
        return query.list();
    }

    public Image getById(long pId) {
        return (Image) getSession().createQuery(
                "from Image where id = :id")
                .setParameter("id", pId)
                .uniqueResult();
}

    public Image getByVideoGameId(long pVideoGameId) {
        return (Image) getSession().createQuery(
                "from Image where videoGame_id = :videoGame_id")
                .setParameter("videoGame_id", pVideoGameId)
                .uniqueResult();
    }

    public void update(Image image) {
        getSession().update(image);
    }

    private void save(Image pImage) {
        getSession().save(pImage);
    }
}
