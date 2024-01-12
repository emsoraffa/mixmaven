package ui;

import core.Constants;
import core.Drink;

import static core.Constants.FONT_SIZE_40;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;

import java.util.List;

/**
 * Controller class for the "Browse Drinks" view in MixMaven.
 * This class is responsible for displaying all drinks.
 */
public final class BrowseDrinksController {
    @FXML private AnchorPane browseDrinksPane;
    @FXML private ScrollPane scrollPane;
    @FXML private VBox drinkContainer;
    @FXML private Button addDrinkBtn;
    private List<Drink> drinks;
    private MixMavenController mixMavenController;

    /**
     * Constructs a new BrowseDrinksController.
     * @param mixMavenController The main controller for MixMaven.
     */
    public BrowseDrinksController(MixMavenController mixMavenController) {
        this.mixMavenController = mixMavenController;

    }

    /**
     * Initializes the BrowseDrinksController.
     * Retrieves the list of drinks from the data access layer and populates
     * the UI with drink information.
     */
    public void initialize() {
        drinks = mixMavenController.getDataAccess().getDrinks();
        browseDrinksPane.setPrefSize(Constants.SCENE_WIDTH, Constants.CONTENT_HEIGHT);
        scrollPane.setLayoutX((Constants.SCENE_WIDTH - scrollPane.getPrefWidth()) / 2);

        // Generates a drinkbox for every drink in MixMaven.
        for (int i = drinks.size() - 1; i >= 0; i--) {
            VBox drinkBox = new VBox();
            drinkBox.getStyleClass().add("drinkBox");
            String drinkId = drinks.get(i).getId();

            Text drinkName = new Text(drinks.get(i).getName());
            drinkName.setFont(new Font(FONT_SIZE_40));

            StringBuilder str = new StringBuilder();
            drinks.get(i).getIngredients().stream()
                .forEach(ingredient -> str.append("     • " + ingredient.toString() + "\n"));

            Text ingredients = new Text(str.toString());

            Button deleteBtn = new Button("Delete Drink");
            deleteBtn.getStyleClass().add("drinkBtn");
            deleteBtn.setUserData(drinkId); // The drink to be deleted if the button is pressed.

            Button editButton = new Button("Edit Drink");
            editButton.getStyleClass().add("drinkBtn");
            editButton.setUserData(drinkId); // The drink to be edited when the button is pressed.

            deleteBtn.setOnAction(event -> {
                mixMavenController.getDataAccess().deleteDrink((String) deleteBtn.getUserData());
                mixMavenController.showBrowseDrinks();
            });

            editButton.setOnAction(event -> mixMavenController.showEditDrink((String) editButton.getUserData()));

            HBox buttonBox = new HBox();
            buttonBox.getStyleClass().add("buttonBox");

            buttonBox.getChildren().addAll(deleteBtn, editButton);
            drinkBox.getChildren().addAll(drinkName, ingredients, buttonBox);
            drinkContainer.getChildren().add(drinkBox);
        }
    }
}
