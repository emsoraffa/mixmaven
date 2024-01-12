package core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Unit tests for the MixMavenModel class.
 */
public class MixMavenModelTest {

    private MixMavenModel mixMavenModel;

    /**
     * Initiation that occurs before all tests.
     */
    @BeforeEach
    public void setUp() {
        List<Drink> drinks = new ArrayList<>();
        drinks.add(new Drink("Mojito"));
        drinks.add(new Drink("Pina Colada"));
        mixMavenModel = new MixMavenModel(drinks);
        mixMavenModel.addDrink(new Drink("Gin Tonic"));
    }

    /**
     * Test for adding a new drink to the model.
     */
    @Test
    public void testAddDrink() {
        Drink newDrink = new Drink("Cosmopolitan");
        mixMavenModel.addDrink(newDrink);
        List<Drink> updatedDrinks = mixMavenModel.getDrinks();
        assertTrue(updatedDrinks.contains(newDrink));
    }

    /**
     * Test for retrieving a drink from the model by its ID.
     */
    @Test
    public void testGetDrink() {
        String drinkId = mixMavenModel.getDrinks().get(0).getId();
        Drink retrievedDrink = mixMavenModel.getDrink(drinkId);
        assertNotNull(retrievedDrink);
        assertEquals(drinkId, retrievedDrink.getId());
    }

    /**
     * Test for removing a drink from the model.
     */
    @Test
    public void testRemoveDrink() {
        Drink drink = mixMavenModel.getDrinks().get(0);
        mixMavenModel.removeDrink(drink.getId());
        List<Drink> updatedDrinks = mixMavenModel.getDrinks();
        assertFalse(updatedDrinks.contains(drink));
    }

    /**
     * Test for replacing an existing drink with a new one in the model.
     */
    @Test
    public void testReplaceDrink() {
        Drink oldDrink = mixMavenModel.getDrinks().get(0);
        Drink newDrink = new Drink("Negroni");
        mixMavenModel.replaceDrink(oldDrink.getId(), newDrink);
        List<Drink> updatedDrinks = mixMavenModel.getDrinks();
        assertTrue(updatedDrinks.contains(newDrink));
        assertFalse(updatedDrinks.contains(oldDrink));
    }

    /**
     * Test for setting the list of drinks in the model.
     */
    @Test
    public void testSetDrinks() {
        List<Drink> newDrinks = new ArrayList<>();
        newDrinks.add(new Drink("Martini"));
        mixMavenModel.setDrinks(newDrinks);
        List<Drink> updatedDrinks = mixMavenModel.getDrinks();
        assertEquals(1, updatedDrinks.size());
        assertEquals("Martini", updatedDrinks.get(0).getName());
    }
}
