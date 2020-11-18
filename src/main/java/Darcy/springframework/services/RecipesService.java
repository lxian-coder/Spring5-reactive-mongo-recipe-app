package Darcy.springframework.services;

import Darcy.springframework.commands.RecipeCommand;
import Darcy.springframework.domain.Recipe;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface RecipesService {
    Flux<Recipe> getRecipes();
    Mono<Recipe> getRecipeById(String id);

    Mono<RecipeCommand> saveRecipeCommand(RecipeCommand recipeCommand);
    Mono<RecipeCommand> findCommandById(String l);

    void deleteById(String id);


}




