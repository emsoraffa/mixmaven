package core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the Ingredient class.
 */
@SuppressWarnings("magicnumber")
public class IngredientTest {

    /**
     * Tests creating a new drink without alcohol.
     */
    @Test
    public void testConstructorForMixerAndExtras() {
        Ingredient ingredient = new Ingredient("lime", 1, "ml", "extras");
        assertTrue(ingredient.getName().equals("lime"));
        assertEquals(1, ingredient.getAmount());
        assertTrue(ingredient.getUnit().equals("ml"));
        assertTrue(ingredient.getType().equals("extras"));
    }

    /**
     * Tests creating a new drink with alcohol.
     */
    @Test
    public void testConstructorForAlcohol() {
        Ingredient ingredient = new Ingredient("vodka", 40, 4, "ml", "alcohol");
        assertThrows(IllegalArgumentException.class,
                () -> new Ingredient("AlchoholOver100", 1, "ml", "alchohol"));
        assertTrue(ingredient.getName().equals("vodka"));
        assertEquals(40, ingredient.getAlcoholPercentage());
        assertEquals(4, ingredient.getAmount());
        assertTrue(ingredient.getUnit().equals("ml"));
        assertTrue(ingredient.getType().equals("alcohol"));
    }

    /**
     * Test creating new drink with invalid type.
     * Should throw illegalArgumentException.
     * @throws IllegalArgumentException
     */
    @Test
    public void testInvalidType() throws IllegalArgumentException {
        assertThrows(IllegalArgumentException.class,
                () -> new Ingredient("invalid", 1, "ml", "invalidType"));
    }

    /**
     * Test creating new drink with invalid alcohol percentage.
     * You should not be able to add an ingredient with alcohol over 100%.
     * Should throw illegalArgumentException.
     * @throws IllegalArgumentException
     */
    @Test
    public void testInvalidAlcoholPercentage() throws IllegalArgumentException {
        assertThrows(IllegalArgumentException.class,
                () -> new Ingredient("invalid", 120, 1, "ml", "invalidType"));
    }

    /**
     * Tests the all setters in the ingredient class.
     */
    @Test
    public void testSetters() {
        Ingredient ingredient = new Ingredient("vodka", 40, 4, "dl", "alcohol");

        // Test setType.
        assertThrows(IllegalArgumentException.class, () -> ingredient.setType("invalid"));
        ingredient.setType("mixer");
        assertEquals("mixer", ingredient.getType());

        // Test setUnit.
        ingredient.setUnit("ml");
        assertEquals("ml", ingredient.getUnit());

        // Test setName.
        ingredient.setName("Lemon Juice");
        assertEquals("Lemon Juice", ingredient.getName());

        // Test setAmount.
        ingredient.setAmount(15.0);
        assertEquals(15.0, ingredient.getAmount(), 0.001);

        // Test setAlchoholPercentage.
        ingredient.setAlcoholPercentage(50);
        assertEquals(50, ingredient.getAlcoholPercentage());

        // Test toString.
        String expected = "15.0 ml Lemon Juice 50%";
        assertEquals(expected, ingredient.toString());
    }
}
