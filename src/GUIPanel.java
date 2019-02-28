import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GUIPanel extends JPanel {
    JSlider slider;
    JButton anmeld;
    JButton skift;
    JCheckBox enable;
    BufferedImage image;
    int currVid = 0;
    boolean drawBackground = false;

    public GUIPanel(){
        if(!drawBackground){
            image =(BufferedImage) Data.getVideos().get(currVid).getCover();
            slider=new JSlider(-10,10);
            add(slider);

            anmeld = new JButton("Anmeld");
            add(anmeld);
            anmeld.addActionListener(e -> {
                Data.getVideos().get(currVid).setRating(slider.getValue());
                System.out.println(slider.getValue());
                image=(BufferedImage) Data.getVideos().get(++currVid).getCover();
                repaint();
                revalidate();
            });
            skift = new JButton("Skift perspektiv");
            skift.addActionListener(e -> {
                if(drawBackground){
                    drawBackground = false;
                    revalidate();
                }else {
                    drawBackground = true;
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
        } else{
            g.drawImage(image,50,50,null);
        }
    }
}
