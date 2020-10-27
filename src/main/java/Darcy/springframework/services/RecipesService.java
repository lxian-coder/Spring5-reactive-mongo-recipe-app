package Darcy.springframework.services;

import Darcy.springframework.commands.RecipeCommand;
import Darcy.springframework.domain.Recipe;



public interface RecipesService {
    Iterable<Recipe> getRecipe();
    Recipe getRecipeById(String id);

    RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand);
    RecipeCommand findCommandById(String l);

    void deleteById(String id);


}




