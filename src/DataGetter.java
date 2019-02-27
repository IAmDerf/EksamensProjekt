import java.io.BufferedReader;
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
            for (int i =1; i<5;i++){
                getAllVideosOnPage(i);
            }


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
            String titleAndYear = (html.split("<title>")[1]);
            String title = titleAndYear.split("\\(")[0].substring(0,titleAndYear.split("\\(")[0].length()-1);
            int releaseYear = Integer.parseInt(titleAndYear.split("\\(")[1].substring(0,4));
            String director = (html.split("href=\"/person")[3]).split(">")[1].split("<")[0];
            String [] genres = find("href=\\\"/genre",html);
            String [] keywords = find("href=\\\"/keyword",html);
            String [] actorsplit = html.split("2x\" alt=\"");
            String [] actors = new String[3];
            for(int i=2;i<5;i++){
                actors[i-2]=actorsplit[i].split("\"")[0];
            }
            int runtime;
            try {
                String runtimeRaw = html.split("Runtime")[1].split(">")[2].split("<")[0];
                runtime = Integer.parseInt(runtimeRaw.split("\\D")[1]) * 60 + Integer.parseInt(runtimeRaw.split("\\D")[3]);
            } catch (ArrayIndexOutOfBoundsException e){
                runtime=0;
            }
            return new Video(title,director,actors,runtime,releaseYear,genres,keywords);
        }
        public static ArrayList<Video> getAllVideosOnPage(int i){
            ArrayList<Video> output = new ArrayList<>();
            String input = readFromUrl("https://www.themoviedb.org/movie?page="+i);
            Pattern pattern = Pattern.compile("href=\"/movie/[0-9]{1,}");
            Matcher matcher = pattern.matcher(input);
            ArrayList<Integer> movieNumbers = new ArrayList<>();
            while(matcher.find()){
                int movieNr = Integer.parseInt(matcher.group(0).split("/")[2]);
                if(!movieNumbers.contains(movieNr)){
                    movieNumbers.add(movieNr);
                }
            }
            for (int movie: movieNumbers) {
                Video test = opretVideo("https://www.themoviedb.org/movie/"+movie);
                System.out.println(test.getTitle() + ", directed by " + test.getDirector() + ", released in " + test.getRelease() + ", lasting " + test.getVarighed()+ " minutes");
                System.out.print("Lead actors: ");
                for (String actor: test.getHovedSkuespiller()) {
                    System.out.print(actor +", ");
                }
                System.out.println();
                System.out.print("Genres: ");
                for (String genre: test.getGenre()) {
                    System.out.print(genre + ", ");
                }
                System.out.println();
                System.out.print("Keywords: ");
                for (String keyword: test.getKeywords()) {
                    System.out.print(keyword + ", ");
                }
                System.out.println();
                System.out.println();
                output.add(test);
            }
            return output;
        }
    }


