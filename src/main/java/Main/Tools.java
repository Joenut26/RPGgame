package Main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Tools {

    //returns the image from the specified path
    public static Image requestImage(String pathname) {
        Image image = null;
        try {
            image = ImageIO.read(new File(pathname));
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
            exception.printStackTrace();
        }
        return image;
    }
}

