package Darcy.springframework.services;

import Darcy.springframework.commands.IngredientCommand;

/**
 * Darcy Xian  1/9/20  7:09 pm      spring5-recipe-app
 */
public interface IngredientService {

    IngredientCommand findByRecipeIdAndIngredientId(String recipeId, String ingredientId);
    IngredientCommand saveIngredientCommand(IngredientCommand command);
    void deleteIngredientById (String recipeId, String InId);



}

