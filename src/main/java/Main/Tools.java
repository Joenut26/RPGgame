package Main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

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

    public static ArrayList<Image> addImageToList(String pathName){
        File directory = new File(pathName);
        ArrayList<Image> imageArrayList = new ArrayList<>();
        for(String element: Objects.requireNonNull(directory.list())){
            String imagePath = pathName.concat("/" + element);
            Image image = requestImage(imagePath);
            imageArrayList.add(image);
        }
        return imageArrayList;
    }
}

