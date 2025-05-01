package edu.ntnu.idatt2003.idatt2003boardgame.repository;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

import javafx.scene.image.Image;


public class PlayerDataAccess extends PlayerCSV {
    // This path must match the resource location inside src/main/resources
    private final String ICON_RELATIVE_PATH = "/edu/ntnu/idatt2003/idatt2003boardgame/PlayerIcons/";

    public PlayerDataAccess() {
        super();
    }

    public ArrayList<String> getIconNames() {
        ArrayList<String> iconFileNames = new ArrayList<>();
        URL dirURL = getClass().getResource(ICON_RELATIVE_PATH);
        
        if (dirURL == null) {
            System.err.println("Icon resource folder not found: " + ICON_RELATIVE_PATH);
            return iconFileNames;
        }

        File dir;
        try {
            dir = new File(dirURL.toURI());
        } catch (URISyntaxException e) {
            System.err.println("Invalid URI for icon folder: " + e.getMessage());
            return iconFileNames;
        }

        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        iconFileNames.add(file.getName());
                    }
                }
            }
        } else {
            System.err.println("Not a directory: " + dir.getAbsolutePath());
        }
        return iconFileNames;
    }
    
    public Image getImageFromFileName(String fileName) {
        String resourcePath = ICON_RELATIVE_PATH + fileName;
        if (getClass().getResourceAsStream(resourcePath) == null) {
            throw new IllegalArgumentException("Icon not found: " + resourcePath);
        }
        return new Image(getClass().getResourceAsStream(resourcePath));
    }
}
