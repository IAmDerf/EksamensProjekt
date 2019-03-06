import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataGetter {


        public static String readFromUrl(String URL){
            String output = new String();
            try {
                URL url = new URL(URL);
                URLConnection con = url.openConnection();
                BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String currLine;
                while ((currLine = reader.readLine())!=null){
                    output+=currLine;
                }
                reader.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return output;
        }

        public static void main(String[] args){
            opretVideo("https://www.themoviedb.org/movie/490132-green-book");


        }
        public static String[] find(String finder,String html){
            String[] findersplit = html.split(finder);
            String[] finders = new String[findersplit.length-1];
            for(int i=1; i<findersplit.length;i++){
                finders[i-1] = findersplit[i].split(">")[1].split("<")[0];
            }
            return finders;
        }
        public static Video opretVideo(String URL){
            String html = readFromUrl(URL);

            BufferedImage cover=null;
            try {
                cover = ImageIO.read(new URL(html.split("og:image\"")[1].split("\"")[1]));
            } catch (Exception e) {
                try {
                   cover=ImageIO.read(new File("ohFuck.png"));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

            String titleAndYear = (html.split("<title>")[1]);
            String title;
            try {
                    title = titleAndYear.split("&#8212")[0].substring(0, titleAndYear.split("&#8212")[0].length() - 8);


            } catch (Exception e){
                title = "n/A";
            }
            title = title.replace(":","");
            title = title.replace("?", "");
            title = title.replace("/","");
            title = title.replace("\\","");
            title = title.replace("*","");
            title = title.replace("\"","");
            title = title.replace("<","");
            title = title.replace(">","");
            title = title.replace("|","");
            title = title.replace("&#x27;","'");
            title = title.replace("&#x2F;"," ");
            title = title.replace("&amp;","&");

            int releaseYear;
            try {
                releaseYear = Integer.parseInt(titleAndYear.split("&#8212")[0].substring(titleAndYear.split("&#8212")[0].length()-6,titleAndYear.split("&#8212")[0].length()-2));
            } catch (Exception e){
                releaseYear=0;
            }
            String director;
            try {
                director = (html.split("href=\"/person")[3]).split(">")[1].split("<")[0];
            } catch(Exception ee){
                director = "n/A";
                }
            String [] genres = find("href=\\\"/genre",html);
            String [] keywords = find("href=\\\"/keyword",html);
            String [] actorsplit = html.split("2x\" alt=\"");
            String [] actors = new String[3];
            for(int i=2;i<5;i++){
                try {
                    actors[i-2]=actorsplit[i].split("\"")[0];
                } catch (ArrayIndexOutOfBoundsException e){

                }

            }
            int runtime;
            try {
                String runtimeRaw = html.split("Runtime")[1].split(">")[2].split("<")[0];
                runtime = Integer.parseInt(runtimeRaw.split("\\D")[1]) * 60 + Integer.parseInt(runtimeRaw.split("\\D")[3]);
            } catch (ArrayIndexOutOfBoundsException e){
                runtime=0;
            }

            try {
                File coverSave = new File("covers/" + title +".png");
                ImageIO.write(cover, "png", coverSave);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return new Video(title,director,actors,runtime,releaseYear,genres,keywords/*,cover*/);
        }
        public static ArrayList<Video> getAllVideosOnPage(int fra, int til){
            ArrayList<Video> output = new ArrayList<>();
            for(int i = fra; i<=til;i++) {
                System.out.println(i);
                String input = readFromUrl("https://www.themoviedb.org/movie?page=" + i);
                Pattern pattern = Pattern.compile("href=\"/movie/[0-9]+");
                Matcher matcher = pattern.matcher(input);
                ArrayList<Integer> movieNumbers = new ArrayList<>();
                while (matcher.find()) {
                    int movieNr = Integer.parseInt(matcher.group(0).split("/")[2]);
                    if (!movieNumbers.contains(movieNr)) {
                        movieNumbers.add(movieNr);
                    }
                }

            for (int movie: movieNumbers) {
                Video test = opretVideo("https://www.themoviedb.org/movie/" + movie);
                output.add(test);
            }
            }
            return output;
        }
    }


