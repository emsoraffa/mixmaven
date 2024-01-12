package core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the Drink class.
 */
@SuppressWarnings("magicnumber")
public class DrinkTest {

    /**
     * Tests adding a new empty drink, without ingredients.
     */
    @Test
    public void testConstructorWithoutIngredients() {
        Drink drink = new Drink("Cosmopolitan");
        assertTrue(drink.getName().equals("Cosmopolitan"));
        assertEquals(0, drink.getIngredients().size());
        assertEquals(0, drink.getAlcoholContent(), 0.01);
    }

    /**
     * Tests adding a new drink, with ingredients.
     */
    @Test
    public void testConstructorWithIngredients() {
        List<Ingredient> ingredients = new ArrayList<>();

        ingredients.add(new Ingredient("Vodka", 40, 5, "ml", "alcohol"));
        ingredients.add(new Ingredient("Limejuice", 0.5, "ml", "extras"));
        ingredients.add(new Ingredient("Ginger beer", 1.5, "dl", "mixer"));

        Drink drink = new Drink("Moscow mule", ingredients);

        assertTrue(drink.getName().equals("Moscow mule"));
        assertEquals(3, drink.getIngredients().size());
        assertEquals(0.012903225, drink.getAlcoholContent(), 0.01);
        // Might expect 1.
    }

    /**
     * Tests using the method addIngredient to add a new ingredient to the drink.
     */
    @Test
    public void testAddIngredient() {
        Drink drink = new Drink("Vodka redbull");
        Ingredient redBull = new Ingredient("redBull", 0, 2, "ml", "mixer");

        drink.addIngredient(redBull);

        assertEquals(1, drink.getIngredients().size());
        assertEquals(redBull, drink.getIngredients().get(0));
        assertEquals(0, drink.getAlcoholContent(), 0.01);
    }

    /**
     * Tests using the method removeIngredient to remove an ingredient from the drink.
     */
    @Test
    public void testRemoveDrink() {
        List<Ingredient> ingredients = new ArrayList<>();

        ingredients.add(new Ingredient("Gin", 40, 4, "ml", "alcohol"));
        ingredients.add(new Ingredient("Tonic", 8, "ml", "mixer"));

        Drink drink = new Drink("Gin Tonic", ingredients);

        assertEquals(2, drink.getIngredients().size());

        drink.removeIngredient(0);

        assertEquals(1, drink.getIngredients().size());
    }

    /**
     * Tests the method that calculates alcohol content in a drink.
     */
    @Test
    public void testCalculateAlcoholContent() {
        Ingredient vodka = new Ingredient("Vodka", 40, 1, "ml", "alcohol");
        Ingredient juice = new Ingredient("Pinapple juice", 0, 1, "ml", "mixer");

        Drink pinappleHell = new Drink("pinappleHell");

        pinappleHell.addIngredient(vodka);
        pinappleHell.addIngredient(juice);

        assertEquals(0.2, pinappleHell.getAlcoholContent(), 0.01);

    }
}
