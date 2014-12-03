package app;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Totz on 28.11.2014.
 */
public class URLReaderTmp {

    public static void main(String[] args) {

        Document doc;
        try {

            // need http protocol
            doc = Jsoup.connect("http://www.vgchartz.com/game/51022/assassins-creed-revelations/").get();

            for (Element gameBodyElement : doc.select("#game_body table tbody tr td img")) {
                //System.out.println("Image scr: " + gameBodyElement.attr("src"));
            }

            Elements description = doc.select("#game_table_box div p");

            for (Element gameInfoElement : description) {
                //System.out.print(description.text());
            }


            Elements gameInfoBox = doc.select("#game_infobox tbody tr td");

            for (Element gameInfoElement : gameInfoBox) {

                if (gameInfoElement.text().contains("Platform:")) {
                    for (Element itemLink : gameInfoElement.select("a")) {
                        //System.out.println("Platform: " + itemLink.text());
                    }
                }

                if (gameInfoElement.text().contains("Also on:")) {
                    for (Element itemLink : gameInfoElement.select("a")) {
                        //System.out.println("More Platform: " + itemLink.text());
                    }
                }

                if (gameInfoElement.text().contains("Developer:")) {
                    for (Element itemLink : gameInfoElement.select("a")) {
                        //System.out.println("Developer: " + itemLink.text());
                    }
                }

                if (gameInfoElement.text().contains("Genre:")) {
                    for (Element itemLink : gameInfoElement.select("a")) {
                        //System.out.println("Genre: " + itemLink.text());
                    }
                }
            }

            Elements gameTableBox = doc.select("#game_table_box table tbody tr");

            for (int i = 0; i < gameTableBox.size(); i++) {
                Element gameTableBoxElement = gameTableBox.get(i);
                Elements gameTableBoxTds = gameTableBoxElement.children();
                if(i>0){
                    for (int i1 = 0; i1 < gameTableBoxTds.size(); i1++) {
                        Element gameTableBoxTd = gameTableBoxTds.get(i1);
                        if(i1==0) System.out.println("TITLE: " + gameTableBoxTd.text());
                        if(i1==1) System.out.println("Publisher: " + gameTableBoxTd.text());
                        if(i1==2) System.out.println("Country: " + gameTableBoxTd.text());
                        if(i1==3) {
                            System.out.println("ORIG Date: " + gameTableBoxTd.text());
                            String day = gameTableBoxTd.text().substring(0,2).trim();
                            String rest = gameTableBoxTd.text().substring(4).trim();
                            //System.out.println("Day: " + day);
                            //System.out.println("Rest: " + rest);
                            System.out.println("NEW: " + day + rest);
                            SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy", Locale.US);
                            try {

                                Date date = formatter.parse(day + " " + rest);
                                System.out.println("SQL: " + date);
                                //System.out.println("THIRD" + formatter.format(date));

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                        if(i1==4) System.out.println("Distribution: " + gameTableBoxTd.text());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
