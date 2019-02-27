import javax.swing.*;

public class Main {

    public static void main (String[] args){
        JFrame frame = new JFrame();
        GUIPanel panel = new GUIPanel();
        frame.add(panel);
        frame.setSize(500,500);
        frame.setVisible(true);
    }
}
