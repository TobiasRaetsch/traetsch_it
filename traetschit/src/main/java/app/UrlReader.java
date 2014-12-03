package app;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class UrlReader {
    String content;

    public UrlReader() {

    }

    public List<GameScan> readUrl(String pRequestUrl){
        Document doc;
        List<GameScan> gameScans = new ArrayList<GameScan>();
        String name = "";
        String href = "";
        String platform = "";

        try {

            // need http protocol
            System.out.print("REQUEST URL: " + pRequestUrl);
            doc = Jsoup.connect("http://www.vgchartz.com/gamedb/?name=" + pRequestUrl).get();

            // get all links
            Elements links = doc.select("a[href]");
            int id = 0;
            for (Element link : links) {
                // get the value from href attribute
                if(link.attr("href").indexOf("/game/") > -1 && link.text().indexOf(pRequestUrl) > -1){
                    name = link.text();
                    href = link.attr("href");

                }

                if(link.attr("href").indexOf("/platform/") > -1){
                    platform = link.text();
                }

                if(name != "" && platform != "" && href != ""){
                    GameScan gameScan = new GameScan(name, platform, href, pRequestUrl, id);
                    gameScans.add(gameScan);
                    name = "";
                    href = "";
                    platform = "";
                    id++;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return gameScans;
    }

    public static class VideoGameParams {
        private List<String> platformNames = new ArrayList<String>();
        private String developerName;
        private String genreName;
        private String description;
        private String image;
        private String Publisher;
        private String Country;
        private Date date;
        private String distribution;

        public List<String> getPlatformNames() {
            return platformNames;
        }

        public void addPlatformName(String pName){
            this.platformNames.add(pName);
        }

        public void setPlatformNames(List<String> platformNames) {
            this.platformNames = platformNames;
        }

        public String getDeveloperName() {
            return developerName;
        }

        public void setDeveloperName(String developerName) {
            this.developerName = developerName;
        }

        public String getGenreName() {
            return genreName;
        }

        public void setGenreName(String genreName) {
            this.genreName = genreName;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String pDescription) {
            description = pDescription;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String pImage) {
            image = pImage;
        }

        public String getPublisher() {
            return Publisher;
        }

        public void setPublisher(String pPublisher) {
            Publisher = pPublisher;
        }

        public String getCountry() {
            return Country;
        }

        public void setCountry(String pCountry) {
            Country = pCountry;
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date pDate) {
            date = pDate;
        }

        public String getDistribution() {
            return distribution;
        }

        public void setDistribution(String pDistribution) {
            distribution = pDistribution;
        }
    }

    public VideoGameParams scanVideoGame(GameScan pGameScan){
        Document doc;
        VideoGameParams videoGameParams = new VideoGameParams();

        try {

            // need http protocol
            doc = Jsoup.connect(pGameScan.getHref()).get();

            for (Element gameBodyElement : doc.select("#game_body table tbody tr td img")) {
                videoGameParams.setImage(gameBodyElement.attr("src"));
            }

            Elements gameInfoBox = doc.select("#game_infobox tbody tr td");

            for (Element gameInfoElement : gameInfoBox) {

                if (gameInfoElement.text().contains("Platform:")) {
                    for (Element itemLink : gameInfoElement.select("a")) {
                        System.out.println("Platform: " + itemLink.text());
                        videoGameParams.addPlatformName(itemLink.text());
                    }
                }

                if (gameInfoElement.text().contains("Also on:")) {
                    for (Element itemLink : gameInfoElement.select("a")) {
                        System.out.println("More Platform: " + itemLink.text());
                        videoGameParams.addPlatformName(itemLink.text());
                    }
                }

                if (gameInfoElement.text().contains("Developer:")) {
                    for (Element itemLink : gameInfoElement.select("a")) {
                        System.out.println("Developer: " + itemLink.text());
                        videoGameParams.setDeveloperName(itemLink.text());
                    }
                }

                if (gameInfoElement.text().contains("Genre:")) {
                    for (Element itemLink : gameInfoElement.select("a")) {
                        System.out.println("Genre: " + itemLink.text());
                        videoGameParams.setGenreName(itemLink.text());
                    }
                }
            }

            Elements description = doc.select("#game_table_box div p");

            for (Element gameInfoElement : description) {
                videoGameParams.setDescription(description.text());
            }

            Elements gameTableBox = doc.select("#game_table_box table tbody tr");

            for (int i = 0; i < gameTableBox.size(); i++) {
                Element gameTableBoxElement = gameTableBox.get(i);
                Elements gameTableBoxTds = gameTableBoxElement.children();
                if(i>0){
                    for (int i1 = 0; i1 < gameTableBoxTds.size(); i1++) {
                        Element gameTableBoxTd = gameTableBoxTds.get(i1);
                        if(i1==1) videoGameParams.setPublisher(gameTableBoxTd.text());
                        if(i1==2) videoGameParams.setCountry(gameTableBoxTd.text());
                        if(i1==3) {
                            String day = gameTableBoxTd.text().substring(0,2).trim();
                            String rest = gameTableBoxTd.text().substring(4).trim();
                            SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy", Locale.US);
                            try {
                                Date date = formatter.parse(day + " " + rest);
                                videoGameParams.setDate(date);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                        if(i1==4) {
                            System.out.print("DISTRIBUTION: " + videoGameParams.getDistribution());
                            videoGameParams.setDistribution(gameTableBoxTd.text());
                        }
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return videoGameParams;
    }
}