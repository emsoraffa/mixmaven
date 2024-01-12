package ui;

import core.Drink;
import core.MixMavenModel;
import json.DataHandler;
import java.util.List;

/**
 * Provides direct data access to the application's data model through the MixMavenModel.
 * This class implements the DataAccess interface and acts as a bridge between the user interface
 * and the core data model.
 */
public class DirectDataAccess implements DataAccess {
    private MixMavenModel mixMavenModel;
    private DataHandler dataHandler;

    /**
     * Constructs a new DirectDataAccess object. Initializes the dataHandler with a default file path
     * and sets up the MixMavenModel.
     */
    public DirectDataAccess() {
        this.dataHandler = DataHandler.getInstance();
        setFilePath("Data.json");
        mixMavenModel = dataHandler.loadModel();
    }

    /**
     * Retrieves a list of all drinks from the data model.
     * @return A list of Drink objects representing all the drinks in the data model.
     */
    public List<Drink> getDrinks() {
        refreshModel();
        return mixMavenModel.getDrinks();
    }

    /**
     * Deletes a drink from the data model based on its unique identifier and saves the changes to the data file.
     * @param drinkId The unique identifier of the drink to be deleted.
     */
    public void deleteDrink(String drinkId) {
        refreshModel();
        mixMavenModel.removeDrink(drinkId);
        dataHandler.saveModel(mixMavenModel);
    }

    /**
     * Adds a new drink to the data model and saves the changes to the data file.
     * @param drink The Drink object to be added to the data model.
     */
    public void addDrink(Drink drink) {
        refreshModel();
        mixMavenModel.addDrink(drink);
        dataHandler.saveModel(mixMavenModel);
    }

    /**
     * Edits an existing drink in the data model with updated information and saves the changes to the data file.
     * @param oldDrinkId The unique identifier of the drink to be edited.
     * @param newDrink The updated Drink object to replace the old drink.
     */
    public void editDrink(String oldDrinkId, Drink newDrink) {
        refreshModel();
        mixMavenModel.replaceDrink(oldDrinkId, newDrink);
        dataHandler.saveModel(mixMavenModel);
    }

    /**
     * Sets the file path for data handling.
     * @param path path for persistence.
     */
    public void setFilePath(String path) {
        dataHandler.setFilePath(path);
    }

    /**
     * Refreshes the model, to ensure latest changes are loaded.
     */
    public void refreshModel() {
        this.mixMavenModel = dataHandler.loadModel();
    }

    /**
     * @return model.
     */
    public MixMavenModel getModel() {
        return mixMavenModel;
    }
}
