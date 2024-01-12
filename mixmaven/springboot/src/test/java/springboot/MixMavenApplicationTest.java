package springboot;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import static org.junit.jupiter.api.Assertions.fail;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import core.Drink;
import core.Ingredient;

@AutoConfigureMockMvc
@ContextConfiguration(
        classes = {MixMavenController.class, MixMavenService.class, MixMavenApplication.class})
@WebMvcTest
@SuppressWarnings("magicnumber")
public class MixMavenApplicationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    /**
     * Setup method to initialize Gson before each test.
     * @throws Exception if an error occurs during setup.
     */
    @BeforeEach
    public void setup() throws Exception {
        gson = new Gson();
    }

    /**
     * Test to verify the successful retrieval of drinks from the API.
     * @throws Exception if an error occurs during the test.
     */
    @Test
    public void testGetDrinks() throws Exception {
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.get("/drinks").accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        try {
            TypeToken<List<Drink>> drinkListType = new TypeToken<List<Drink>>() { };
            List<Drink> response = gson.fromJson(result.getResponse().getContentAsString(),
                    drinkListType.getType());

            assertNotEquals(null, response);
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test to verify the successful creation of a new drink through the API.
     * @throws Exception if an error occurs during the test.
     */
    @Test
    public void testCreateDrink() throws Exception {

        List<Ingredient> ingredients = new ArrayList<>();

        ingredients.add(new Ingredient("Vodka", 40, 5, "ml", "alcohol"));
        ingredients.add(new Ingredient("Limejuice", 0.5, "ml", "extras"));
        ingredients.add(new Ingredient("Ginger beer", 1.5, "dl", "mixer"));

        Drink testDrink = new Drink("Moscow mule", ingredients);

        mockMvc.perform(MockMvcRequestBuilders.post("/drinks")
                .contentType(MediaType.APPLICATION_JSON).content(gson.toJson(testDrink))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
    }

    /**
     * Test to verify the successful change of datafile through the API.
     * @throws Exception if an error occurs during the test.
     */
    @Test
    public void testSetFilePath() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/drinks/setfile")
                .contentType(MediaType.APPLICATION_JSON)
                .content("springbootserver-data.json").accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
    }

    /**
     * Test to verify the successful deletion of a drink through the API.
     * @throws Exception if an error occurs during the test.
     */
    @Test
    public void testDeleteDrink() throws Exception {
        List<Ingredient> ingredients = new ArrayList<>();

        ingredients.add(new Ingredient("Vodka", 40, 5, "ml", "alcohol"));
        ingredients.add(new Ingredient("Limejuice", 0.5, "ml", "extras"));
        ingredients.add(new Ingredient("Ginger beer", 1.5, "dl", "mixer"));

        Drink testDrink = new Drink("Moscow mule", ingredients);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/drinks").contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(testDrink)).accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        mockMvc.perform(MockMvcRequestBuilders.delete("/drinks/{id}", testDrink.getId())
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
    }
}
