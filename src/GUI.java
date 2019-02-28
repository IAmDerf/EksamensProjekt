import javax.swing.*;

public class GUI {

    public GUI(){
        JFrame frame = new JFrame();
        GUIPanel panel = new GUIPanel();
        frame.add(panel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(700,1000);
        frame.setVisible(true);
    }
}
