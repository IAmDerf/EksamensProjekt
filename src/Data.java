import java.io.File;
import java.util.ArrayList;

public class Data {

    private static ArrayList<Video> videos;
    private static final File VIDEOINFO = new File("videoinfo.ser");

    public static void setUpVideos(){
        if(true) {
            videos = DataGetter.getAllVideosOnPage(1, 1);
            Serializer.serialize(videos, "videoinfo.ser");
        } else{
            try {
                videos = (ArrayList<Video>) Serializer.deserialize("videoinfo.ser");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public static ArrayList<Video> getVideos() {
        return videos;
    }
}
