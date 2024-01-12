package core;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents a model for managing a collection of drinks in MixMaven. Provides methods
 * to add, retrive, remove and replace drinks in the collection.
 */
public class MixMavenModel {
    private List<Drink> drinks;

    /**
     * Constructs a model with an initial list of drinks.
     * @param drinks
     */
    public MixMavenModel(List<Drink> drinks) {
        this.drinks = drinks;
    }

    public final void setDrinks(List<Drink> drinks) {
        this.drinks = drinks;
    }

    /**
     * Adds a drink to the model.
     * @param drink
     */
    public void addDrink(Drink drink) {
        if (drink != null) {
            drinks.add(drink);
        }
    }

    /**
     * Retrives a new list containing all drinks in the model.
     * @return list of drinks.
     */
    public final List<Drink> getDrinks() {
        return new ArrayList<>(drinks);
    }

    /**
     * Retrives a spesific drink from the model based on the id.
     * @param drinkId
     * @return drink with given id.
     */
    public final Drink getDrink(String drinkId) {
        return drinks.stream().filter(e -> e.getId().equals(drinkId)).findFirst().orElse(null);
    }

    /**
     * Removes a drink from the model based on its id.
     * @param drinkId
     */
    public final void removeDrink(String drinkId) {
        drinks.remove(getDrink(drinkId));
    }

    /**
     * Replaces an existing drink in the model with a new drink based on the id.
     * @param oldDrinkId
     * @param newDrink
     */
    public final void replaceDrink(String oldDrinkId, Drink newDrink) {
        drinks.set(drinks.indexOf(getDrink(oldDrinkId)), newDrink);
    }
}
