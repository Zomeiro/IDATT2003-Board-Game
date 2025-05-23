package edu.ntnu.idatt2003.idatt2003boardgame.repository;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;

/**
 * Manages player profiles stored in a CSV file.
 * This repository provides functionalities to register new players,
 * update player information such as wins and icons, and retrieve player data.
 * Player data is assumed to be stored with fields for name, icon path, and win count.
 * 
 * @author Hector Mendana Morales
 * @author Bjørn Adam Vangen
 */
public class CsvPlayerRepository {

    // Defines player CSV file
    private static final File FILE = new File("src/main/resources/edu/ntnu/idatt2003/idatt2003boardgame/playerProfiles.csv");
    private static ArrayList<String[]> getCSVContent() {
        ArrayList<String[]> allPlayers = new ArrayList<>();

        try ( // create FileWriter object with file as parameter
                CSVReader reader = new CSVReader(new FileReader(FILE))) {
            String[] row;
            while ((row = reader.readNext()) != null) {
                //Saves the CSV content locally
                allPlayers.add(row);
            }
        } catch (IOException | CsvValidationException e) {
            throw new IllegalArgumentException("Failed to read CSV player file.");
        }

        return allPlayers;

    }

    private static void rewriteFile(ArrayList<String[]> allPlayers) {

        try (CSVWriter writer = new CSVWriter(new FileWriter(FILE))) {
            writer.writeAll(allPlayers);
        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to read CSV player file.");
        }

    }

    //Taken and adapted from GeeksForGeeks
    /**
     * Registers a new player profile with the given name and icon path.
     * A new entry is added to the CSV file with the player's name, icon, and an initial win count of "0".
     *
     * @param name The name of the player to register.
     * @param icon The file path or identifier for the player's icon.
     * @throws IllegalArgumentException if a player profile with the given name already exists,
     * or if there's an issue reading/writing the CSV file.
     */
    public void registerNewPlayer(String name, String icon) {

        ArrayList<String[]> allPlayers = getCSVContent();

        Iterator<String[]> iterator = allPlayers.iterator();

        while (iterator.hasNext()) {
            if (iterator.next()[0].equals(name)) {
                throw new IllegalArgumentException("A player profile with the given name already exists.");
            }
        }

        allPlayers.add(new String[]{name, icon, "0"});

        rewriteFile(allPlayers);

    }

    /**
     * Attempts to increment the win count for a player with the given name.
     * It reads the current win count, parses it as an integer, increments it by one,
     * converts the new count to a string, and then *appends* this new string to the
     * existing win count string in the CSV file.
     * For example, if the win count was "0", it becomes "01". If it was "5", it becomes "56".
     *
     * @param name The name of the player whose win count is to be updated.
     * @throws IllegalArgumentException if there's an issue reading/writing the CSV file.
     */
    public static void incrementPlayerWins(String name) {

        ArrayList<String[]> allPlayers = getCSVContent();

        Iterator<String[]> iterator = allPlayers.iterator();

        String[] row;

        while (iterator.hasNext()) {
            row = iterator.next();

            if (row[0].equals(name)) {
                row[2] += Integer.toString(Integer.parseInt(row[2]) + 1);
                break;
            }
        }

        rewriteFile(allPlayers);

    }

    /**
     * Changes the icon path for a player with the given name.
     * The player's profile in the CSV file is updated with the new icon path.
     *
     * @param name The name of the player whose icon is to be changed.
     * @param icon The new file path or identifier for the player's icon.
     * @throws IllegalArgumentException if there's an issue reading/writing the CSV file.
     */
    public static void changeIcon(String name, String icon) {
        ArrayList<String[]> allPlayers = getCSVContent();

        Iterator<String[]> iterator = allPlayers.iterator();

        String[] row;

        while (iterator.hasNext()) {
            row = iterator.next();

            if (row[0].equals(name)) {
                row[1] = icon;
                break;
            }
        }

        rewriteFile(allPlayers);

    }

    /**
     * Retrieves an array of all player names currently stored in the CSV file.
     *
     * @return A string array containing the names of all registered players.
     * The order corresponds to their order in the CSV file.
     * @throws IllegalArgumentException if there's an issue reading the CSV file.
     */
    public String[] getPlayerNames() {
        ArrayList<String[]> content = getCSVContent();
        String[] playerNames = new String[content.size()];
        for (int i = 0; i < playerNames.length; i++) {
            playerNames[i] = content.get(i)[0];
        }
        return playerNames;
    }

    /**
     * Retrieves the icon path for a player with the given name.
     *
     * @param playerName The name of the player whose icon path is to be retrieved.
     * @return The string representing the icon path for the specified player.
     * @throws IllegalArgumentException if the player is not found, or if there's an issue reading the CSV file.
     */
    public String getPlayerIconByPlayerName(String playerName) {
        ArrayList<String[]> content = getCSVContent();

        for (String[] row : content) {
            if (row[0].equals(playerName)) {
                return row[1];
            }
        }

        throw new IllegalArgumentException("Player not found.");
    }
}