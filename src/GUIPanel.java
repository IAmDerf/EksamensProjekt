import javax.swing.*;

public class GUIPanel extends JPanel {
    JSlider slider;
    JButton anmeld;
    JButton skip;
    JCheckBox enable;

    public GUIPanel(){
        slider=new JSlider(-10,10);
        add(slider);
        anmeld = new JButton("Anmeld");
        add(anmeld);
        skip = new JButton("Har ikke set");
        add(skip);
        enable = new JCheckBox("Få forbedrede forslag");
        add(enable);
    }
}
