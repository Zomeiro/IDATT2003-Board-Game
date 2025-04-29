package edu.ntnu.idatt2003.idatt2003boardgame.repository;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import javafx.scene.image.Image;

public class PlayerDataAccess extends PlayerCSV {
    private final String ICON_RELATIVE_PATH = "/PlayerIcons/";
    public PlayerDataAccess() {
        super();
    }

    public ArrayList<String> getIconNames() {
        ArrayList<String> iconFileNames = new ArrayList<>();
        String directoryPath = "src/main/resources/PlayerIcons/";
        File dir = new File(directoryPath);

        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            if (files != null) {
                for (File file : files) {
                    iconFileNames.add(file.getName());
                }
            }
            return iconFileNames;
        } else {
            System.out.println("Not a directory: " + directoryPath);
            return null;
        }
    }
    public Image getImageFromFileName(String fileName) {
        String resourcePath = ICON_RELATIVE_PATH + fileName;
        return new Image(getClass().getResourceAsStream(resourcePath));
    }
}