package json;

import core.Drink;
import core.MixMavenModel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import com.google.gson.Gson;

/**
 * Manages data handling, including file I/O, serialization, and deserialization.
 */
public final class DataHandler {
    private File dataFile;
    private static DataHandler singleInstance;

    private DataHandler() {
        this.setFilePath("Data.json");
    }

    /**
     * Returns the singleton instance of the DataHandler.
     * @return The DataHandler instance.
     */
    public static synchronized DataHandler getInstance() {
        if (singleInstance == null)
            singleInstance = new DataHandler();
        return singleInstance;
    }

    /**
     * Gets the name of the current data file.
     * @return The name of the data file.
     */
    public String getDataFile() {
        return dataFile.getName();
    }

    /**
     * Sets the file path for data handling.
     * @param fileName The name of the data file to use.
     */
    public void setFilePath(String fileName) {
        String userHome = System.getProperty("user.home");
        File folder = new File(userHome, "MixMaven");

        setupDataFolder(folder);

        setupDataFolder(folder);

        dataFile = new File(folder, fileName);

        try {
            if (dataFile.createNewFile()) {
                UtilityJson.saveObjectToJsonFile(new MixMavenModel(new ArrayList<>()), dataFile);
                System.out.println("dataFile created.");
            }

        } catch (IOException e) {
            System.err.println("An error occurred while creating the file: " + e.getMessage());
        }
    }

    /**
     * @param folder The folder to set up.
     * @throws RuntimeException If the folder doesn't exist and cannot be created.
     */
    private void setupDataFolder(File folder) {
        // Check if directory exists.
        if (folder.exists()) return;
        // Attempt to create new directory.
        if (folder.mkdir()) {
            System.out.println("Folder created successfully.");
        } else {
            System.err.println("Failed to create the folder.");
            throw new RuntimeException("Failed to create the folder.");
        }
    }

    /**
     * Saves the provided MixMavenModel to the data file.
     * @param mixMavenModel The model to be saved.
     */
    public void saveModel(MixMavenModel mixMavenModel) {
        UtilityJson.saveObjectToJsonFile(mixMavenModel, dataFile);
    }

    /**
     * Loads the MixMavenModel from the data file.
     * @return The loaded MixMavenModel.
     */
    public MixMavenModel loadModel() {
        MixMavenModel loadedModel = UtilityJson.loadObjectFromJson(dataFile);
        if (loadedModel == null) {
            System.err.println("Failed to load model.");
            return new MixMavenModel(new ArrayList<>());
        }
        return loadedModel;
    }

    /**
     * Deserializes a JSON string into a Drink object.
     * @param drink The JSON string representing a Drink.
     * @return A Drink object deserialized from the JSON string.
     */
    public Drink deserializeDrink(String drink) {
        Gson gson = new Gson();
        return gson.fromJson(drink, Drink.class);
    }
}
