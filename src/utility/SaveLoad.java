package utility;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;

import javax.imageio.ImageIO;


public class SaveLoad {

    public static final String PLAYER_ATLAS = "knight2.png";
    public static final String LEVEL_ATLAS = "outside_sprites.png";
    public static final String MENU_BUTTONS = "button_atlas.png";
    public static final String MENU_PANEL = "menu_background.png";
    public static final String PAUSE_PANEL = "pause_panel.png";
    public static final String SOUND_BUTTONS = "sound_button.png";
    public static final String CRE_BUTTONS = "cre_buttons.png";
    public static final String VOLUME_BUTTONS = "volume_buttons.png";
    public static final String ENEMY1 = "05knight.png";
    public static final String HUD_STATUS = "health_power_bar.png";
    public static final String COMPLETE_PANEL = "completed_panel.png";

    public static  BufferedImage getTextureAtlas(String fileName){
        BufferedImage img = null;
        InputStream is = SaveLoad.class.getResourceAsStream("/" + fileName);
        
        try {
            img = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try{
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return img;
    }

    public static BufferedImage[] GetAllLevels() {
        URL url  = SaveLoad.class.getResource("/lvls");
        File file = null;

        try {
            file = new File(url.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        File[] files = file.listFiles();
        File[] filesSorted = new File[files.length];

        //File Debugging Purposes
        // for (File f : files)
        //     System.out.println("File : " + f.getName());

        for (int i = 0; i < filesSorted.length; i++) 
            for (int j = 0; j < files.length; j++) {
                if (files[j].getName().equals((i) + ".png"))
                    filesSorted[i] = files[j];
            }

        //File Debugging Purposes
        // for (File f : files)
        //     System.out.println("File : " + f.getName());

        // for (File f : filesSorted)
        // System.out.println("File Sorted: " + f.getName());

        BufferedImage[] img = new BufferedImage[filesSorted.length];

        for (int i = 0; i < img.length; i++)
            try {
                img[i] = ImageIO.read(filesSorted[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        return img;
    }

}
