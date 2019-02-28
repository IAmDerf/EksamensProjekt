import java.awt.*;

public class Video implements java.io.Serializable {
    private String title;
    private String director;
    private String[] hovedSkuespiller;
    private int varighed;
    private int release;
    private String[] genre;
    private String[] keywords;
    private Image cover;



    private int rating;

    public Video(String title,String director, String [] hovedSkuespiller, int varighed, int release, String [] genre, String[] keywords, Image cover){
        this.title=title;
        this.director=director;
        this.hovedSkuespiller=hovedSkuespiller;
        this.varighed=varighed;
        this.release=release;
        this.genre=genre;
        this.keywords=keywords;
        this.cover=cover;

    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getRating() {
        return rating;
    }

    public int getRelease() {
        return release;
    }

    public int getVarighed() {
        return varighed;
    }

    public String getDirector() {
        return director;
    }

    public String getTitle() {
        return title;
    }

    public String[] getGenre() {
        return genre;
    }

    public String[] getHovedSkuespiller() {
        return hovedSkuespiller;
    }

    public String[] getKeywords() {
        return keywords;
    }

    public Image getCover() {
        return cover;
    }
}
