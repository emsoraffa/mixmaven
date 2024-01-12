package ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.service.query.EmptyNodeQueryException;
import core.Ingredient;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SuppressWarnings("magicnumber")
public class MixMavenAppTest extends ApplicationTest {

    // Fields.
    private static HashMap<String, String> testIngredients;
    private Parent root;

    /**
     * Initiates the JavaFX application for testing by starting the provided Stage with the
     * specified FXML and controllers. Handles the initialization of the Scene and styling
     * configurations.
     *
     * @param stage The primary stage used for displaying the JavaFX application.
     * @throws IOException If an error occurs during the FXML file loading.
     */
    @Override
    public final void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("MixMaven.fxml"));
        root = fxmlLoader.load();
        MixMavenController controller = fxmlLoader.<MixMavenController>getController();
        controller.getDataAccess().setFilePath("uiTestData.json");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("styles/MixMaven.css").toExternalForm());
        stage.show();
    }

    /**
     * Initiazes the test class by preparing ingredients to be used in the tests.
     */
    @BeforeAll
    static void setup() {
        testIngredients = new HashMap<>();
        testIngredients.put("Vodka", "20 dl Vodka 40 alcohol ");
        testIngredients.put("Fireball", "20 dl Fireball 20 alcohol ");
        testIngredients.put("Tonic", "20 dl Tonic 0 mixer ");
        testIngredients.put("Gin", "20 dl Gin 40 alcohol ");
        testIngredients.put("Lime", "30 gram Lime 0 extras ");
    }

    /**
     * Performs a test for navigating within the UI.
     */
    @Test
    public void testNavigate() {
        navToAddDrink();
        Assertions.assertTrue(getRootNode().lookup("#addDrinkPane") != null);

        clickOn("Back");
        Assertions.assertTrue(getRootNode().lookup("#browseDrinksPane") != null);
    }

    /**
     * Performs a test for creating a new unnamed drink in the UI.
     */
    @Test
    public void testCreateUnnamedDrink() {
        navToAddDrink();
        clickOn("Add New Drink");

        Assertions.assertTrue(getRootNode().lookup("#addDrinkPane") != null);
    }

    /**
     * Performs a test for editing a drink by adding an ingredient in the UI.
     */
    @Test
    public void testEditDrinkAddIngredient() {
        createTmpDrink("Vodka Redbull", "20 dl Vodka 40 alcohol ");
        List<String> ingredient = Arrays.asList(testIngredients.get("Lime").split(" "));
        navToEditDrink();

        write("#ingredientNameField", ingredient.get(2));
        write("#amountField", ingredient.get(0));

        clickOn("#unitChoiceBox");
        clickOn(ingredient.get(1));

        clickOn("#typeChoiceBox");
        clickOn(ingredient.get(4));

        clickOn("Add New Ingredient");

        clickOn("Update Drink");

        String[] createdDrink =
                {"Moscow Mule", testIngredients.get("Vodka") + testIngredients.get("Lime")};

        Assertions.assertTrue(searchDrinks(createdDrink));
        deleteTmpDrink();
    }

    /**
     * Performs a test for editing a drink by editing an ingredient in the UI.
     */
    @Test
    public void testEditDrinkEditIngredient() {
        createTmpDrink("Vodka Redbull", "20 dl Vodka 40 alcohol ");
        List<String> ingredient = Arrays.asList(testIngredients.get("Lime").split(" "));
        navToEditDrink();

        clickOn("#ingredientList .list-cell");

        clear("#ingredientNameField");
        clear("#amountField");

        write("#ingredientNameField", ingredient.get(2));
        write("#amountField", ingredient.get(0));

        clickOn("Update Ingredient");
        clickOn("Update Drink");

        String[] createdDrink =
                {"Moscow Mule", testIngredients.get("Vodka") + testIngredients.get("Lime")};

        Assertions.assertTrue(searchDrinks(createdDrink));
        deleteTmpDrink();
    }

    /**
     * Performs a test for editing a drink by deleting an ingredient in the UI.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testEditDrinkDeleteIngredient() {
        createTmpDrink("Vodka Redbull", "20 dl Vodka 40 alcohol ");
        List<String> ingredient = Arrays.asList(testIngredients.get("Lime").split(" "));
        navToEditDrink();

        write("#ingredientNameField", ingredient.get(2));
        write("#amountField", ingredient.get(0));
        clickOn("#unitChoiceBox");
        clickOn(ingredient.get(1));
        clickOn("#typeChoiceBox");
        clickOn(ingredient.get(4));
        clickOn("Add New Ingredient");

        Node listView = lookup("#ingredientList").query();
        ObservableList<Ingredient> items = ((ListView<Ingredient>) listView).getItems();
        int lastIndex = items.size() - 1;
        String lastItem = items.get(lastIndex).toString();

        // clickOn on the last item in the ListView.
        clickOn(listView);
        moveTo(lastItem);
        clickOn(lastItem);
        clickOn("Delete ingredient");
        clickOn("Update Drink");

        String[] createdDrink =
                {"Moscow Mule", testIngredients.get("Vodka") + testIngredients.get("Lime")};
        Assertions.assertTrue(searchDrinks(createdDrink));
        deleteTmpDrink();
    }

    /**
     * Performs a test for editing a drink with wrong parameters in the UI.
     */
    @Test
    public void testEditDrinkAddIngredientWrongParameters() {
        List<String> ingredient = Arrays.asList(testIngredients.get("Lime").split(" "));
        createTmpDrink("Vodka Redbull", "20 dl Vodka 40 alcohol ");
        navToEditDrink();

        clear("#drinkNameField");

        // Try to delete ingredient with no ingredient selected.
        clickOn("Delete ingredient");
        checkErrorLabel("Select something to delete");

        // Write no ingredient name.
        clickOn("Add New Ingredient");
        checkErrorLabel("Name the ingredient");
        write("#ingredientNameField", ingredient.get(2));

        // Write no amount.
        clickOn("Add New Ingredient");
        checkErrorLabel("Amount must be a number!");
        write("#amountField", ingredient.get(0));

        // Dont choose unit.
        clickOn("Add New Ingredient");
        checkErrorLabel("Choose options from both the choiceboxes!");
        clickOn("#unitChoiceBox");
        clickOn(ingredient.get(1));

        // Dont choose type.
        clickOn("Add New Ingredient");
        checkErrorLabel("Choose options from both the choiceboxes!");
        clickOn("#typeChoiceBox");
        clickOn(ingredient.get(4));

        // Write Alcohol as non-number.
        clickOn("#typeChoiceBox");
        clickOn("alcohol");
        write("#alcoholPercentField", "a");
        clickOn("Add New Ingredient");
        checkErrorLabel("AlcoholPercentage must be a number!");

        // Test if liquid is measured in gram.
        clickOn("#unitChoiceBox");
        clickOn("gram");
        clickOn("#typeChoiceBox");
        clickOn("mixer");
        clickOn("Add New Ingredient");
        checkErrorLabel("Insert liquids as volume!");
        clickOn("Back");
        deleteTmpDrink();
    }

    /**
     * Performs a test for editing a drink with wrong parameters in the UI.
     */
    @Test
    public void testEditDrinkEditIngredientWrongParameters() {
        List<String> ingredient = Arrays.asList(testIngredients.get("Lime").split(" "));
        createTmpDrink("Vodka Redbull", "20 dl Vodka 40 alcohol ");
        navToEditDrink();

        // Try to edit ingredient while no ingredient selected.
        clickOn("Update Ingredient");
        checkErrorLabel("Select an ingredient to edit!");

        // Try to delete ingredient while no ingredient selected.
        clickOn("Delete ingredient");
        checkErrorLabel("Select something to delete");

        // Clear fields to use wrong param on.
        clickOn("#ingredientList .list-cell");
        clear("#drinkNameField");
        clear("#ingredientNameField");
        clear("#amountField");

        // Missing ingredient name.
        clickOn("Update Ingredient");
        checkErrorLabel("Name the ingredient");
        write("#ingredientNameField", ingredient.get(2));

        // Missing amount.
        clickOn("Update Ingredient");
        checkErrorLabel("Amount must be a number!");
        write("#amountField", ingredient.get(0));

        // Test if liquid is measured in gram.
        clickOn("#unitChoiceBox");
        clickOn("gram");
        clickOn("#typeChoiceBox");
        clickOn("mixer");
        clickOn("Update Ingredient");
        checkErrorLabel("Insert liquids as volume!");
        clickOn("#unitChoiceBox");
        clickOn("dl");

        // Missing drink name.
        clickOn("Update Drink");
        checkErrorLabel("Write a Drink Name");
        clickOn("Back");
        deleteTmpDrink();
    }

    /**
     * Performs a test for adding a drink with wrong parameters in the UI.
     */
    @Test
    public void testAddDrinkWrongParameters() {
        List<String> ingredient = Arrays.asList(testIngredients.get("Lime").split(" "));
        navToAddDrink();

        // Try to delete ingredient with no ingredient selected.
        clickOn("Delete ingredient");
        checkErrorLabel("Select something to delete");

        // Write no ingredient name.
        clickOn("Add Ingredient");
        checkErrorLabel("Name the ingredient");
        write("#ingredientNameField", ingredient.get(2));

        // Write no amount.
        clickOn("Add Ingredient");
        checkErrorLabel("Amount must be a number!");
        write("#amountField", ingredient.get(0));

        // Dont choose unit.
        clickOn("Add Ingredient");
        checkErrorLabel("Choose options from both the choiceboxes!");
        clickOn("#unitChoiceBox");
        clickOn(ingredient.get(1));

        // Dont choose type.
        clickOn("Add Ingredient");
        checkErrorLabel("Choose options from both the choiceboxes!");
    }

    /**
     * Testing creating different drinks.
     * @param name
     * @param ingredientString string in the format "Amount Unit Name % Type".
     */
    @ParameterizedTest
    @MethodSource
    public final void testCreateDrink(String name, String ingredientString) {
        navToAddDrink();

        List<String> ingredients =
                new ArrayList<String>(Arrays.asList(ingredientString.split(" ")));
        for (int i = 0; i < ingredients.size(); i += 5) {
            // Ingredient name.
            write("#ingredientNameField", ingredients.get(i + 2));

            // Ingredient amount.
            write("#amountField", ingredients.get(i));

            // Select unit type.
            clickOn("#unitChoiceBox");
            clickOn(ingredients.get(i + 1));

            // Select ingredient type.
            clickOn("#typeChoiceBox");
            clickOn(ingredients.get(i + 4));

            // Alcohol content.
            write("#alcoholPercentField", ingredients.get(i + 3));

            clickOn("Add Ingredient");
        }

        write("#drinkNameField", name);
        clickOn("Add New Drink");

        // Assert that a drink with the corresponding ingredients was added.
        String[] createdDrink = {name, ingredientString};
        Assertions.assertTrue(searchDrinks(createdDrink));
    }

    /**
     * Generates arguments for creating different drinks.
     * @return a stream of arguments for creating different drinks.
     */
    private static Stream<Arguments> testCreateDrink() {
        return Stream.of(Arguments.of("Moscow Mule", testIngredients.get("Vodka")),
                Arguments.of("GT", testIngredients.get("Gin") + testIngredients.get("Tonic")
                        + testIngredients.get("Lime")));
    }

    @Test
    public void testDeleteDrink() {
        // Create drink to delete.
        createTmpDrink("Just Vodka", "20 dl Vodka 40 alcohol ");

        // Delete the created drink.
        clickOn("Delete Drink");
        String[] drinkToDelete = {"Just Vodka", ""};
        Assertions.assertFalse(searchDrinks(drinkToDelete));
    }

    // Below are internal methods to streamline the tests.

    /**
     * Test that checks if the errorLabel has the expected text.
     * @param expectedText
     */
    private void checkErrorLabel(String expectedText) {
        Label label = lookup("#errorLabel").query();
        String errorlabel = ((Label) label).getText();
        assertEquals(expectedText, errorlabel);
    }

    /**
     * Gets the root node of the UI.
     * @return the root node of the UI.
     */
    private Parent getRootNode() {
        return root;
    }

    /**
     * Simulates writing text into a specified label.
     * @param label the label to interact with.
     * @param text the text to write into the label.
     */
    private void write(String label, String text) {
        clickOn(label).write(text);
    }

    /**
     * Clears the text from a specified label.
     * @param label The label to clear.
     */
    private void clear(String label) {
        doubleClickOn(label).eraseText(8);
    }

    /**
     * Navigates to the edit drink page in the UI.
     */
    private void navToEditDrink() {
        clickOn("Your Drinks");
        clickOn("Edit Drink");
    }

    /**
     * Navigates to the add drink page in the UI.
     */
    private void navToAddDrink() {
        clickOn("Your Drinks");
        clickOn("Add Drink");
    }

    /**
     * Deletes the latest drink, is only used in tandem with createTmpDrink().
     */
    private void deleteTmpDrink() {
        clickOn("Delete Drink");
    }

    /**
     * Creates a drink to be used in testing where we do not want to keep the drink.
     * @param drinkName
     * @param ingredientString
     */
    private void createTmpDrink(String drinkName, String ingredientString) {
        navToAddDrink();
        List<String> ingredient = new
        ArrayList<String>(Arrays.asList(ingredientString.split(" ")));

        write("#ingredientNameField", ingredient.get(2));

        write("#amountField", ingredient.get(0));

        write("#alcoholPercentField", ingredient.get(3));

        clickOn("#unitChoiceBox");
        clickOn(ingredient.get(1));

        clickOn("#typeChoiceBox");
        clickOn(ingredient.get(4));

        clickOn("Add Ingredient");
        write("#drinkNameField", drinkName);
        clickOn("Add New Drink");
    }

    /**
     * @param compareString to check if it is a substring.
     * @param targetString to check if compareString is included in.
     * @return true if comparestring is a subbstring of target.
     */
    private boolean isSubstring(String compareString, String targetString) {
        return targetString.startsWith(compareString);
    }

    /**
     * Searches for drinks based on provided arguments in the UI.
     * @param args the arguments for searching drinks (name and ingredient).
     * @return true if the searched drink is found, false otherwise.
     */
    private boolean searchDrinks(String[] args) {
        // Use FxRobot to interact with the JavaFX application.
        FxRobot robot = new FxRobot();

        try {
            // Locate the VBox.
            VBox drinkContainer = robot.lookup("#drinkContainer").query();

            for (Node drinkBox : drinkContainer.getChildren()) {
                if (drinkBox instanceof VBox) {
                    String[] results = formatDrinkBox(drinkBox);
                    String searchIngredients = formatSearchIngredients(args);

                    if (results[0].equals(args[0]) && isSubstring(results[1], searchIngredients)) {
                        return true;
                    }
                }
            }
        } catch (EmptyNodeQueryException e) {
            return false;
        }
        return false;
    }

    /**
     * Extracts and formats information from a drink box.
     * @param drinkBox the VBox representing a drink in the UI.
     * @return an array containing the formatted name and ingredients of the drink.
     */
    private String[] formatDrinkBox(Node drinkBox) {
        Text nameLabel = (Text) ((VBox) drinkBox).getChildren().get(0);

        String name = nameLabel.getText().replace("Name: ", "");
        // String formatting.
        String ingredient = ((Text) ((VBox) drinkBox).getChildren().get(1)).getText()
                .replace("Type: ", "").replace("• ", "").replace(".0", "")
                .replace("%", "").replaceAll("\\s+", " ");

        ingredient = ingredient.trim();
        ingredient += " ";
        return new String[]{name, ingredient};

    }

    /**
     * Formats search ingredients by removing specific keywords and extra spaces.
     * @param args the arguments for searching drinks (name and ingredient).
     * @return the formatted search ingredients.
     */
    private String formatSearchIngredients(String[] args) {
        return args[1]
        .replace("alcohol", "").replace("mixer", "")
        .replace("extras", "").replaceAll("  ", " ");
    }

}
