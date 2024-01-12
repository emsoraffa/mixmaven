package springboot;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import core.Drink;
import core.MixMavenModel;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Controller for handling RESTful API requests related to the MixMaven application.
 */
@CrossOrigin
@RestController
@RequestMapping()
public class MixMavenController {

    @Autowired
    private MixMavenService mixMavenService;

    /**
     * Retrieve the MixMaven model.
     * @return The MixMavenModel instance.
     */
    private MixMavenModel getMixMavenModel() {
        return mixMavenService.getMixMavenModel();
    }

    /**
     * Automatically save the MixMavenModel.
     */
    private void autoSaveMixMaven() {
        mixMavenService.autoSaveMixMaven();
    }

    /**
     * Retrieves a list of drinks from the MixMavenModel.
     * @return A list of Drink objects.
     */
    @GetMapping("/drinks")
    public List<Drink> drinks() {
        return getMixMavenModel().getDrinks();
    }

    /**
     * Update a drink by its ID.
     * @param id The ID of the drink to replace.
     * @param drinkString The JSON representation of the new drink.
     */
    @PutMapping(path = "/drinks/{id}")
    public void replaceDrink(@PathVariable("id") String id, @RequestBody String drinkString) {
        Drink drink = mixMavenService.deserializeDrink(drinkString);
        getMixMavenModel().replaceDrink(id, drink);
        autoSaveMixMaven();
    }

    /**
     * Delete a drink by its ID.
     * @param id The ID of the drink to delete.
     */
    @DeleteMapping(path = "/drinks/{id}")
    public void deleteDrink(@PathVariable("id") String id) {
        getMixMavenModel().removeDrink(id);
        autoSaveMixMaven();
    }

    /**
     * Add a new drink to the mixMavenModel.
     * @param drinkString The JSON representation of the new drink.
     */
    @PostMapping(path = "/drinks")
    public void addDrink(@RequestBody String drinkString) {
        Drink drink = mixMavenService.deserializeDrink(drinkString);
        getMixMavenModel().addDrink(drink);
        autoSaveMixMaven();
    }

    /**
     * Set the file path for the MixMavenModel.
     * @param fileName The file path to set.
     */
    @PostMapping(path = "/drinks/setfile")
    public void setFilePath(@RequestBody String fileName) {
        mixMavenService.setFilePath(fileName);
        autoSaveMixMaven();
    }
}
