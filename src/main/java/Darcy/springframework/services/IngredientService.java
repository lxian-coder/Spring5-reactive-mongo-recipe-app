package Darcy.springframework.services;

import Darcy.springframework.commands.IngredientCommand;
import reactor.core.publisher.Mono;

/**
 * Darcy Xian  1/9/20  7:09 pm      spring5-recipe-app
 */
public interface IngredientService {

    Mono<IngredientCommand> findByRecipeIdAndIngredientId(String recipeId, String ingredientId);


    void saveIngredientCommand(IngredientCommand command);

    Mono<Void> deleteIngredientById (String recipeId, String InId);



}

