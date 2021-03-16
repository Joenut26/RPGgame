package Displays;

import javax.swing.*;
import java.awt.*;

public class BackgroundPanel extends JPanel {

    private Image backgroundImage;

    @Override
    public void paintComponents(Graphics g){
        super.paintComponents(g);
        g.drawImage(backgroundImage,0, 0, this.getWidth(), this.getHeight(), this);    }


    public Image getBackgroundImage() {
        return this.backgroundImage;
    }

    public void setBackgroundImage(final Image image){
        this.backgroundImage = image;
    }
}
