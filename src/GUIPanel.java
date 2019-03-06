import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GUIPanel extends JPanel {
    JSlider slider;
    JButton anmeld;
    JButton skift;
    JButton skip;
    JCheckBox enable;
    BufferedImage image;
    int currVid = 2;
    boolean drawBackground = false;

    public GUIPanel(){
        File mappe = new File("covers");
        if(!drawBackground){
            System.out.println(Data.getVideos().get(currVid).getTitle());
            image = new BufferedImage(500,750,BufferedImage.TYPE_INT_ARGB);
            try {
                image =  ImageIO.read(mappe.listFiles((dir1, name) -> name.startsWith(Data.getVideos().get(currVid).getTitle()))[0]) ;
            } catch (IOException e) {
                e.printStackTrace();
            }

            slider=new JSlider(-10,10);
            add(slider);

            anmeld = new JButton("Anmeld");
            add(anmeld);
            anmeld.addActionListener(e -> {
                Data.getVideos().get(currVid).setRating(slider.getValue());
                Data.getVideos().get(currVid).setRated(true);
                System.out.println(slider.getValue());
                currVid = (int) (Math.random()*Data.getVideos().size());
                try{
                image=ImageIO.read(mappe.listFiles((dir1, name) -> name.startsWith(Data.getVideos().get(currVid).getTitle()))[0]) ;
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                repaint();
                revalidate();
            });
            skip = new JButton("Har ikke set");
            add(skip);
            skip.addActionListener(e -> {
                currVid = (int) (Math.random()*Data.getVideos().size());
                try{
                    image=ImageIO.read(mappe.listFiles((dir1, name) -> name.startsWith(Data.getVideos().get(currVid).getTitle()))[0]) ;
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                repaint();
                revalidate();
            });
            skift = new JButton("Skift perspektiv");
            skift.addActionListener(e -> {
                if(drawBackground){
                    anmeld.setVisible(true);
                    slider.setVisible(true);
                    enable.setVisible(true);
                    drawBackground = false;
                    revalidate();
                }else {
                    drawBackground = true;
                    anmeld.setVisible(false);
                    slider.setVisible(false);
                    enable.setVisible(false);
                    repaint();
                    revalidate();
                }
                repaint();
            });
            add(skift);
            enable = new JCheckBox("Få forbedrede forslag");
            add(enable);
        }


    }

    public void paintComponent(Graphics g){
        g.setColor(getBackground());
        g.fillRect(0,0,getWidth(),getHeight());

        if(drawBackground){
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            int w = getWidth();
            int h = getHeight();
            Color color1 = Color.RED;
            Color color2 = Color.GREEN;
            GradientPaint gp = new GradientPaint(0, 0, color1, w, 0, color2);
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, w, h);
            for(Video video: Data.getVideos()){
                if(video.isRated()){
                    //g.drawImage(video.getCover().getImage(),getWidth()/2+(video.getRating()*(getWidth()/20))-35,getHeight()/2,70,120,null);
                }
            }
        } else{
            g.drawImage(image,500,50,null);
            g.setFont(new Font("Droid Sans", Font.BOLD,18));
            g.setColor(Color.BLACK);
            g.drawString(Data.getVideos().get(currVid).getTitle()+" (" + Data.getVideos().get(currVid).getRelease()+")",750-(Data.getVideos().get(currVid).getTitle().length()*5),820);
        }
    }
    public static BufferedImage toBuffered (Image img){
        BufferedImage output = new BufferedImage(img.getWidth(null),img.getHeight(null),BufferedImage.TYPE_INT_ARGB);
        Graphics2D bGr = output.createGraphics();
        bGr.drawImage(img,0,0,null);
        bGr.dispose();
        return output;
    }
}
