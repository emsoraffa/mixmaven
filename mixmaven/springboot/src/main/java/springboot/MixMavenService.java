package springboot;

import core.Drink;
import core.MixMavenModel;
import json.DataHandler;
import org.springframework.stereotype.Service;

/**
 * Service class for managing dataoperations in MixMavenController.
 */
@Service
public class MixMavenService {
    private MixMavenModel mixMavenModel;
    private DataHandler dataHandler;

    /**
     * Initializes the MixMavenService with a default data file path and loads the MixMavenModel
     * from the specified file.
     */
    public MixMavenService() {
        this.dataHandler = DataHandler.getInstance();
        dataHandler.setFilePath("springbootserver-data.json");
        this.mixMavenModel = dataHandler.loadModel();
    }

    /**
     * Gets the current MixMavenModel.
     * @return The MixMavenModel instance.
     */
    public MixMavenModel getMixMavenModel() {
        return mixMavenModel;
    }

    /**
     * Auto-saves the current MixMavenModel using the associated DataHandler.
     */
    public void autoSaveMixMaven() {
        dataHandler.saveModel(mixMavenModel);
    }

    /**
     * Deserializes a drink from its string representation.
     * @param drink The string representation of the drink.
     * @return The Drink object deserialized from the input string.
     */
    public Drink deserializeDrink(String drink) {
        return dataHandler.deserializeDrink(drink);
    }

    /**
     * Sets the file path for data handling.
     * @param filename The file path to set.
     */
    public void setFilePath(String filename) {
        dataHandler.setFilePath(filename);
    }

}
