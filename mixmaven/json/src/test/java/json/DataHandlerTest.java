package json;

import core.Drink;
import core.MixMavenModel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Test class for DataHandler, which tests methods related to data handling.
 */
public class DataHandlerTest {

    private static MixMavenModel mixMavenTestModel;
    private static DataHandler dataHandler;

    @BeforeAll
    public static void setUpClass() {
        dataHandler = DataHandler.getInstance();

        List<Drink> testDrinks = new ArrayList<>();
        testDrinks.add(new Drink("testDrink1"));
        testDrinks.add(new Drink("testDrink2"));

        mixMavenTestModel = new MixMavenModel(testDrinks);
    }

    /**
     * Test the setFilePath method to ensure the data file path is correctly set.
     */
    @Test
    public void testSetFilePath() {
        dataHandler.setFilePath("dataHandlerTestData1.json");
        assertEquals("dataHandlerTestData1.json", dataHandler.getDataFile());
    }

    /**
     * Test the saveModel and loadModel methods to ensure data can be saved and loaded correctly.
     */
    @Test
    public void testSaveAndLoadModel() {
        dataHandler.setFilePath("dataHandlerTestData1.json");
        dataHandler.saveModel(mixMavenTestModel);
        MixMavenModel loadedModel = dataHandler.loadModel();

        assertEquals(mixMavenTestModel.getDrinks().size(), loadedModel.getDrinks().size());
        for (int i = 0; i < mixMavenTestModel.getDrinks().size(); i++) {
            Drink loadedDrink = loadedModel.getDrinks().get(i);
            Drink testDrink = mixMavenTestModel.getDrinks().get(i);

            assertEquals(testDrink.getName(), loadedDrink.getName());
            assertTrue(testDrink.getIngredients().toString().equals(loadedDrink.getIngredients().toString()));
            assertEquals(testDrink.getAlcoholContent(), loadedDrink.getAlcoholContent());
        }
    }

    /**
     * Test the deserializeDrink method to ensure JSON strings can be correctly deserialized into Drink objects.
     */
    @Test
    public void testDeserializeDrink() {
        String drinkJson = "{\"name\":\"TestDrink\",\"ingredients\":[]}";
        Drink drink = dataHandler.deserializeDrink(drinkJson);

        assertEquals("TestDrink", drink.getName());
    }
}
