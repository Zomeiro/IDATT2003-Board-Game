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

public class PlayerCSV {

    // Defines player CSV file
    private static final File FILE = new File("src/main/resources/edu/ntnu/idatt2003/idatt2003boardgame/playerProfiles.csv");
    //note to self: working in playerdataaccess, not working here. Just move that part over here and we're good.
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

    public String[] getPlayerNames() {
        ArrayList<String[]> content = getCSVContent();
        String[] playerNames = new String[content.size()];
        for (int i = 0; i < playerNames.length; i++) {
            playerNames[i] = content.get(i)[0];
        }
        return playerNames;
    }

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
