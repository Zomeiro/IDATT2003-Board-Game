package edu.ntnu.idatt2003.idatt2003boardgame.repository;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

import javafx.scene.image.Image;


/**
 * Extends {@link CsvPlayerRepository} to provide additional functionalities
 * related to player icons, such as retrieving icon filenames and loading icon images.
 * It assumes player icons are stored in a specific resource path.
 * 
 * @author Bjørn Adam Vangen
 */
public class PlayerRepository extends CsvPlayerRepository {
    // This path must match the resource location inside src/main/resources
    private final String relativeIconPath = "/edu/ntnu/idatt2003/idatt2003boardgame/PlayerIcons/";

    /**
     * Constructs a new PlayerRepository.
     * Calls the superclass constructor {@link CsvPlayerRepository#CsvPlayerRepository()}.
     */
    public PlayerRepository() {
        super();
    }

    /**
     * Retrieves a list of filenames for all icons found in the predefined player icon resource directory.
     * It scans the directory specified by {@code relativeIconPath}.
     * If the directory is not found, is invalid, or is not a directory, an error message
     * is printed to standard error and an empty list is returned.
     *
     * @return An {@link ArrayList} of strings, where each string is the filename of an icon.
     * Returns an empty list if no icons are found or if an error occurs.
     */
    public ArrayList<String> getIconNames() {
        ArrayList<String> iconFileNames = new ArrayList<>();
        URL dirURL = getClass().getResource(relativeIconPath);

        if (dirURL == null) {
            System.err.println("Icon resource folder not found: " + relativeIconPath);
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

    /**
     * Loads and returns a JavaFX {@link Image} object for the specified icon filename.
     * The image is loaded from the predefined player icon resource directory.
     *
     * @param fileName The filename of the icon (e.g., "bingus.png") located in the icon resource directory.
     * @return An {@code Image} object representing the loaded icon.
     * @throws IllegalArgumentException if the icon file specified by {@code fileName} is not found
     * within the icon resource directory.
     */
    public Image getImageFromFileName(String fileName) {
        String resourcePath = relativeIconPath + fileName;
        if (getClass().getResourceAsStream(resourcePath) == null) {
            throw new IllegalArgumentException("Icon not found: " + resourcePath);
        }
        return new Image(getClass().getResourceAsStream(resourcePath));
    }
}