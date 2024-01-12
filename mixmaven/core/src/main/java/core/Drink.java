package core;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Class that represents a drink in the MixMaven application. Provides methods to calculate
 * alcoholpercentage and add/remove ingredients in the drink.
 */
public final class Drink {
	private String id;
	private String name;
	private List<Ingredient> ingredients = new ArrayList<Ingredient>();
	private double alcoholContent = 0;

	/**
	 * Constructs a drink without ingredients.
	 * @param name
	 */
	public Drink(String name) {
		this.name = name;
		this.id = UUID.randomUUID().toString();
	}

	/**
	 * Constructs drink with ingredients.
	 * @param name
	 * @param ingredients
	 */
	public Drink(String name, List<Ingredient> ingredients) {
		this(name);
		this.ingredients = new ArrayList<>(ingredients);
		this.alcoholContent = calculateAlcoholContent();
		this.id = UUID.randomUUID().toString();
	}

	/**
	 * Constructs drink with ingredients and given id.
	 * @param name
	 * @param ingredients
	 * @param id
	 */
	public Drink(String name, List<Ingredient> ingredients, String id) {
		this(name);
		this.ingredients = new ArrayList<>(ingredients);
		this.alcoholContent = calculateAlcoholContent();
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public List<Ingredient> getIngredients() {
		return new ArrayList<>(ingredients);
	}

	public double getAlcoholContent() {
		return alcoholContent;
	}

	/**
	 * Calculates the alcoholvolume (ABV*volume) divided by the total volume of all ingredients in
	 * the drink. When calculating the alcoholcontent it does not consider ingredients of unit =
	 * gram because it will not affect the alcoholcontent in the drink.
	 * @return alcoholcontent of the drink.
	 */
	private double calculateAlcoholContent() {
		double volume = 0;
		double alcoholVolume = 0;
		for (Ingredient ingredient : ingredients) {
			if (ingredient.getUnit().equals("gram"))
				continue;

			if (ingredient.getUnit().equals("cl")) {
				volume += ingredient.getAmount() * 10;
				alcoholVolume += ingredient.getAlcoholPercentage() * ingredient.getAmount() / 10;
			} else if (ingredient.getUnit().equals("dl")) {
				volume += ingredient.getAmount() * 100;
				alcoholVolume += ingredient.getAlcoholPercentage() * ingredient.getAmount();
			} else {
				volume += ingredient.getAmount();
				alcoholVolume += ingredient.getAlcoholPercentage() * ingredient.getAmount() / 100;
			}
		}
		return alcoholVolume / volume;
	}

	/**
	 * Adds a new ingredient to the drink.
	 * @param ingredient
	 */
	public void addIngredient(Ingredient ingredient) {
		ingredients.add(ingredient);
		this.alcoholContent = calculateAlcoholContent();
	}

	/**
	 * Removes the ingredient, on the given index, from the drink.
	 * @param index of the ingredient to remove.
	 */
	public void removeIngredient(int index) {
		ingredients.remove(index);
		this.alcoholContent = calculateAlcoholContent();
	}

	public String getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Drink [name=" + name + ", alcoholContent=" + alcoholContent + "]";
	}

}
