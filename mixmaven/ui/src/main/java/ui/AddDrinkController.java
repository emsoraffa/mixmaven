package ui;

import core.Ingredient;
import core.Drink;
import static core.Constants.VALID_UNITS;
import static core.Constants.VALID_TYPES;

import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

/**
 * Controller class for the "Add Drink" UI view.
 */
public final class AddDrinkController {
	@FXML private Label errorLabel;
	@FXML private TextField drinkNameField;
	@FXML private TextField ingredientNameField;
	@FXML private TextField alcoholPercentField;
	@FXML private TextField amountField;
	@FXML private ListView<Ingredient> ingredientList;
	@FXML private ChoiceBox<String> typeChoiceBox;
	@FXML private ChoiceBox<String> unitChoiceBox;
	private List<Ingredient> selectedIngredients = new ArrayList<>();
	private MixMavenController mixMavenController;

	public AddDrinkController(MixMavenController mixMavenController) {
		this.mixMavenController = mixMavenController;
	}

	/**
	 * Initialized the choiceboxes with values.
	 */
	@FXML
	public void initialize() {
		unitChoiceBox.setValue("Unit of Measurement");
		unitChoiceBox.getItems().addAll(VALID_UNITS);
		typeChoiceBox.setValue("Ingredient Type");
		typeChoiceBox.getItems().addAll(VALID_TYPES);

		typeChoiceBox.setOnAction(event -> {
			if (!"alcohol".equalsIgnoreCase(typeChoiceBox.getValue())) {
				alcoholPercentField.setEditable(false);
				alcoholPercentField.clear();
			} else {
				alcoholPercentField.setEditable(true);
			}
		});
	}

	/**
	 * Deletes the ingredient selected in the listview.
	 */
	@FXML
	public void deleteIngredientBtn() {
		try {
			int selectedIndex = ingredientList.getSelectionModel().getSelectedIndex();
			selectedIngredients.remove(selectedIndex);
			ingredientList.getItems().remove(selectedIndex);
		} catch (IndexOutOfBoundsException e) {
			errorLabel.setText("Select something to delete");
		}
	}

	/**
	 * Adds a ingredient to the listview and to the list of ingredients to be added to the drink.
	 */
	@FXML
	public void addIngredientBtn() {
		String ingredientName = ingredientNameField.getText();
		int alcoholPercent;
		double amount;
		String unit = unitChoiceBox.getValue();
		String type = typeChoiceBox.getValue();

		// Verifies the ingredient name Parameter.
		if (ingredientName.length() == 0) {
			errorLabel.setText("Name the ingredient");
			return;
		}

		// Verifies the amount Parameter.
		try {
			amount = Double.parseDouble(amountField.getText());
		} catch (NumberFormatException e) {
			errorLabel.setText("Amount must be a number!");
			return;
		}

		// Verifies the choiceboxes, unit and type.
		if (unit.equals("Unit of measurement") || type.equals("Ingredient Type")) {
			errorLabel.setText("Choose options from both the choiceboxes!");
			return;
		}
		// Verifies the alchohol Percent parameter.
		try {
			if (alcoholPercentField.getText().equals("")) {
				alcoholPercent = 0;
			} else {
				alcoholPercent = Integer.parseInt(alcoholPercentField.getText());
			}
		} catch (NumberFormatException e) {
			errorLabel.setText("AlcoholPercentage must be a number!");
			return;
		}

		// Verifies that a liquid is measured in volume.
		if (!type.equals("extras") && unit.equals("gram")) {
			errorLabel.setText("Insert liquids as volume!");
			return;
		}

		// Creates a new ingredient and adds it to the view.
		Ingredient newIngredient = new Ingredient(ingredientName, alcoholPercent, amount, unit, type);
		selectedIngredients.add(newIngredient);
		ingredientList.getItems().add(newIngredient);
		ingredientList.refresh();
		clearFields();
	}

	/**
	 * Returns the user to the main screen.
	 */
	@FXML
	public void backBtn() {
		mixMavenController.showBrowseDrinks();
	}

	/**
	 * Creates a new drink object with name from the drinknameLabel and ingredients from the
	 * selectedIngredients List, then returns user to main screen.
	 */
	@FXML
	public void addDrinkBtn() {
        // Verify that name and ingredient is not empty.
		if (selectedIngredients.isEmpty()) {
			errorLabel.setText("Cannot make a Drink with no ingredients");
		} else if (drinkNameField.getText().trim().isEmpty()) {
			errorLabel.setText("Write a Drink Name");
		} else {
            // Add drink and redirect to browse view.
			mixMavenController.getDataAccess().
			addDrink(new Drink(drinkNameField.getText(), selectedIngredients));
			mixMavenController.showBrowseDrinks();
		}
	}

	/**
	 * Clears the parameter fields.
	 */
	private void clearFields() {
		ingredientNameField.clear();
		amountField.clear();
		alcoholPercentField.clear();
		unitChoiceBox.setValue(null);
		typeChoiceBox.setValue(null);
		alcoholPercentField.setEditable(true);
		errorLabel.setText("");
	}
}
