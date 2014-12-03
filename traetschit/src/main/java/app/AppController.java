package app;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

@Controller
public class AppController {
    private static final Logger logger = LoggerFactory.getLogger(AppController.class);
    private static final Collection<String> ANGULAR_PAGES = Collections.unmodifiableCollection(new HashSet<String>(Arrays.asList("angularapp")));

    @Autowired
    private ImageDao _imageDao;

    @Autowired
    private VideoGameDao _videoGameDao;

    @Autowired
    private VideoGamePlatformDao _videoGamePlatformDao;

    @Autowired
    private PlatformDao _platformDao;

    @Autowired
    private DeveloperDao _developerDao;

    @Autowired
    private GenreDao _genreDao;

    @Autowired
    private VideoGameOverviewItemDao _videoGameOverviewItemDao;

    @Autowired
    private VideoGameReleaseDao _videoGameReleaseDao;

    @Autowired
    private CountryDao _countryDao;

    @Autowired
    private PublisherDao _publisherDao;

    @Autowired
    private DistributionDao _distributionDao;

    @Value("${imagesPath}")
    String imagesPath;

    @RequestMapping("/loadoverview")
    @ResponseBody
    public List<VideoGame> videoGames() {
        List<VideoGame> videoGames = _videoGameDao.getAll();
        return videoGames;
    }

    @RequestMapping("/showitem")
    public @ResponseBody VideoGameOverviewItemPlatform videoGameOverviewItemPlatform(
            @RequestParam(value="itemid", required=false, defaultValue="0") int videoGameId) {
        logger.info("==== REQUEST-->vVdeo Game ID: {} ====", videoGameId);
        VideoGame videoGame = _videoGameDao.getById(videoGameId);
        List<Platform> platforms = _videoGamePlatformDao.getPlatformByVideoGame(videoGame);
        VideoGameOverviewItem videoGameOverviewItem = _videoGameOverviewItemDao.getVideoGameOverviewItemByVideoGame(videoGame);
        VideoGameOverviewItemPlatform videoGameOverviewItemPlatform = new VideoGameOverviewItemPlatform(videoGameOverviewItem, platforms);
        return videoGameOverviewItemPlatform;
    }
    /*
    @RequestMapping("/searchitem")
    public @ResponseBody GameScan gameScan(
            @RequestParam(value="request", required=false, defaultValue="0") String request) {
        //logger.info("==== REQUEST--> Searchstring ====", request);
        UrlReader urlReader = new UrlReader();
        String content = urlReader.readUrl();
        logger.info("==== REQUEST--> Searchstring ====", request);
        GameScan gameScan = new GameScan("Name", "PS3", "www.there.de", request);
        return gameScan;
    }
    */
    @RequestMapping(value = "/searchitem", method = RequestMethod.POST)
    public @ResponseBody List<GameScan> gameScan(
            @RequestBody @Valid final String request)
    {
        UrlReader urlReader = new UrlReader();
        List<GameScan> gameScans;
        gameScans = urlReader.readUrl(request);
        List<GameScan> scannedGames = new ArrayList<GameScan>();
        for(GameScan scan : gameScans)if(_videoGameDao.getByName(scan.getName()) != null)scannedGames.add(scan);
        for(GameScan scannedGame : scannedGames) gameScans.remove(scannedGame);

        return gameScans;
    }

    @RequestMapping(value = "/image/{imageId}", method = RequestMethod.GET)
    public void deliverImage(@PathVariable("imageId") final long pImageId, HttpServletResponse pResponse) throws IOException {
        Image image = _imageDao.getById(pImageId);
        if (image == null) {
            throw new RuntimeException("Not found!");
        }
        FileInputStream is = new FileInputStream(ImageDao.getFullImagePath(image.getPath(), imagesPath));
        try {
            IOUtils.copy(is, pResponse.getOutputStream());
        } finally {
            is.close();
        }
    }

    @RequestMapping(value = "/scanitem", method = RequestMethod.POST)
    public @ResponseBody VideoGame videoGame(
            @RequestBody @Valid final GameScan gameScan) throws IOException {
        UrlReader urlReader = new UrlReader();
        UrlReader.VideoGameParams videoGameParams;
        videoGameParams = urlReader.scanVideoGame(gameScan);

        VideoGame videoGame = new VideoGame();
        videoGame.setName(gameScan.getName());
        videoGame.setPrice(0.00);

        Image image = _imageDao.downloadAndCreateImage(videoGameParams.getImage());

        videoGame.setImage(image);
        _videoGameDao.save(videoGame);

        Genre genre = _genreDao.getOrCreateByName(videoGameParams.getGenreName());
        Developer developer = _developerDao.getOrCreateByName(videoGameParams.getDeveloperName());
        String description = videoGameParams.getDescription() != null ? videoGameParams.getDescription() : "No Description available!";
        VideoGameOverviewItem videoGameOverviewItem = new VideoGameOverviewItem(genre, developer, videoGame, description);
        _videoGameOverviewItemDao.save(videoGameOverviewItem);

        for(String platformName : videoGameParams.getPlatformNames()){
            Platform platform = _platformDao.getOrCreateByName(platformName);
            VideoGamePlatform videoGamePlatform = new VideoGamePlatform(videoGame, platform);
            _videoGamePlatformDao.save(videoGamePlatform);
        }

        Country country = _countryDao.getOrCreateByName(videoGameParams.getCountry());
        Publisher publisher = _publisherDao.getOrCreateByName(videoGameParams.getPublisher());
        Distribution distribution = _distributionDao.getOrCreateByName(videoGameParams.getDistribution());
        VideoGameRelease videoGameRelease = new VideoGameRelease(videoGame, videoGameParams.getDate(), country, publisher, distribution);
        _videoGameReleaseDao.save(videoGameRelease);

        return videoGame;
    }


    //scanitem

    @RequestMapping("/{pageName}")
    public String includeHtml(Model model, @PathVariable("pageName") String pageName) {
        fillModel(model, pageName);
        logger.info("Including index.jsp for page {}", pageName);
        return ANGULAR_PAGES.contains(pageName) ? "angular" : "index";
    }

    private void fillModel(Model model, String pageName) {
        model.addAttribute("pageName", pageName);
        List<NavigationEntry> navi = new ArrayList<NavigationEntry>();
        navi.add(new NavigationEntry("Home", "/home", "home", "home".equalsIgnoreCase(pageName) ? "selected" : ""));
        navi.add(new NavigationEntry("Impressum", "/impressum", "impressum", "impressum".equalsIgnoreCase(pageName) ? "selected" : ""));
        navi.add(new NavigationEntry("Angular App", "/angularapp", "angularapp", "angularapp".equalsIgnoreCase(pageName) ? "selected" : ""));
        model.addAttribute("navi", navi);
    }

    @RequestMapping("/")
     public String includeHtml(Model model) {
        fillModel(model, "home");
        return "index";
    }

    public static class NavigationEntry {
        private final String label;
        private final String url;
        private final String id;
        private final String cssClass;

        public NavigationEntry(String label, String url, String id, String cssClass) {
            this.label = label;
            this.url = url;
            this.cssClass = cssClass;
            this.id = id;
        }

        public String getId() {
            return id;
        }

        public String getLabel() {
            return label;
        }

        public String getUrl() {
            return url;
        }

        public String getCssClass() {
            return cssClass;
        }
    }
}
