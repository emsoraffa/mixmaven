package json;

import core.Drink;
import core.MixMavenModel;
import core.Ingredient;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Test class for UtilityJson, which tests JSON serialization and deserialization methods.
 */
@SuppressWarnings("magicnumber")
public class UtilityJsonTest {

    private MixMavenModel mixMavenTestModel;
    private File testFile;

    /**
     * Initiation that occurs before all tests.
     */
    @BeforeEach
    public void setUp() {
        DataHandler dataHandler = DataHandler.getInstance();
        dataHandler.setFilePath("utilityJsonTestData.json");
        String path = System.getProperty("user.home") + "/MixMaven/" + dataHandler.getDataFile();
        testFile = new File(path);

        mixMavenTestModel = new MixMavenModel(createTestDrinks());
    }

    /**
     * Helper method for creating a list of test drinks.
     * @return A list of test drinks.
     */
    private List<Drink> createTestDrinks() {
        List<Drink> drinks = new ArrayList<>();

        List<Ingredient> ingredients1 = new ArrayList<>();
        ingredients1.add(new Ingredient("Vodka", 40, 4, "ml", "alcohol"));
        ingredients1.add(new Ingredient("Tonic", 8, "ml", "mixer"));
        Drink drink1 = new Drink("Vodka tonic", ingredients1);

        List<Ingredient> ingredients2 = new ArrayList<>();
        ingredients2.add(new Ingredient("Vodka", 40, 4, "ml", "alcohol"));
        ingredients2.add(new Ingredient("Orange juice", 8, "ml", "mixer"));
        Drink drink2 = new Drink("Screwdriver", ingredients2);

        drinks.add(drink1);
        drinks.add(drink2);
        return drinks;
    }

    /**
     * Test the saveObjectToJsonFile method to ensure JSON objects can be saved and loaded correctly.
     */
    @Test
    public void saveObjectToJsonFileTest() {
        UtilityJson.saveObjectToJsonFile(mixMavenTestModel, testFile);
        MixMavenModel loadedModel = UtilityJson.loadObjectFromJson(testFile);
        assertNotNull(loadedModel);
        assertEquals(mixMavenTestModel.getDrinks().size(), loadedModel.getDrinks().size());

        for (int i = 0; i < mixMavenTestModel.getDrinks().size(); i++) {
            Drink originalDrink = mixMavenTestModel.getDrinks().get(i);
            Drink loadedDrink = loadedModel.getDrinks().get(i);

            assertEquals(originalDrink.getName(), loadedDrink.getName());
            assertTrue(originalDrink.getIngredients().toString().equals(loadedDrink.getIngredients().toString()));
            assertEquals(originalDrink.getAlcoholContent(), loadedDrink.getAlcoholContent());
        }
    }
}
