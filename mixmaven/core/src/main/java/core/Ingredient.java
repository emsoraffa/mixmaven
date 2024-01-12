package core;

import static core.Constants.VALID_TYPES;

/**
 * Class that represents an ingredient in a drink. Provides methods to retrive information about an
 * ingredient; name, alcohol percentage, amount, unit and type.
 */
public final class Ingredient {
    private String name;
    private int alcoholPercentage;
    private double amount;
    private String unit;
    private String type;

    /**
     * Constructs an ingredient of type "mixer" or "extras".
     * @param name
     * @param amount
     * @param unit
     * @param type
     */
    public Ingredient(String name, double amount, String unit, String type) {
        if (!VALID_TYPES.contains(type))
            throw new IllegalArgumentException("Invalid type");
        this.name = name;
        setAmount(amount);
        this.unit = unit;
        this.type = type;
    }

    /**
     * Constructs an ingredients of type "alcohol".
     * @param name
     * @param alcoholPercentage
     * @param amount
     * @param unit
     * @param type
     */
    public Ingredient(String name, int alcoholPercentage, double amount, String unit, String type) {
        this(name, amount, unit, type);
        if (alcoholPercentage > 100 || alcoholPercentage < 0)
            throw new IllegalArgumentException();
        this.alcoholPercentage = alcoholPercentage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be a negative number");
        }
        this.amount = amount;
    }

    public int getAlcoholPercentage() {
        return alcoholPercentage;
    }

    public void setAlcoholPercentage(int alcoholPercentage) {
        this.alcoholPercentage = alcoholPercentage;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getType() {
        return type;
    }

    /**
     * Sets the type of an ingredient and checks that the type is valid.
     * @param type
     */
    public void setType(String type) {
        if (!VALID_TYPES.contains(type))
            throw new IllegalArgumentException("Not valid type");
        this.type = type;
    }

    @Override
    public String toString() {
        return amount + " " + unit + " " + name + " " + alcoholPercentage + "%";
    }
}
