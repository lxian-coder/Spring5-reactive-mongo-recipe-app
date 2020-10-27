package Darcy.springframework.handlers;

import Darcy.springframework.domain.Recipe;
import Darcy.springframework.services.RecipesService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Darcy Xian  3/9/20  8:16 pm      spring5-recipe-app
 */
@Slf4j
@Component
@AllArgsConstructor
public class findRecipeHandler {
    private final RecipesService recipesService;

    public Recipe findRecipeById(String id) {
        if (id != null) {
            Recipe recipe = recipesService.getRecipeById(id);
            return recipe;
        } else {
            log.error("The Recipe ID is null :" + id);
        }
        return null;
    }

}
