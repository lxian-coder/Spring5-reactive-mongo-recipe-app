package Darcy.springframework.servicesImpl;

import Darcy.springframework.commands.RecipeCommand;
import Darcy.springframework.converters.RecipeCommandToRecipe;
import Darcy.springframework.converters.RecipeToRecipeCommand;
import Darcy.springframework.domain.Recipe;
import Darcy.springframework.exceptions.NotFoundException;
import Darcy.springframework.repositories.RecipeRepository;
import Darcy.springframework.services.RecipesService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Slf4j
@AllArgsConstructor
@Service
public class RecipeServiceImpl implements RecipesService {

private final RecipeRepository recipeRepository;
private final RecipeToRecipeCommand recipeToCommand;
private final RecipeCommandToRecipe commandToRecipe;



    @Override
    public Iterable<Recipe> getRecipe() {
        log.debug("I am in recipeserviceImpl");
       return recipeRepository.findAll();

    }

    @Override
    public Recipe getRecipeById(String id) {

        Optional<Recipe> recipeOptional = recipeRepository.findById(id);

        if(!recipeOptional.isPresent()){
            throw new NotFoundException("Recipe Not Found, For ID value: " + id.toString());
        }
        return recipeOptional.get();
    }

    @Override
    public RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand) {
        Recipe detacheRecipe = commandToRecipe.convert(recipeCommand);
        Recipe savedRecipe = recipeRepository.save(detacheRecipe);
        log.debug("Saved RecipeId:" + savedRecipe.getId());

        return recipeToCommand.convert(savedRecipe);
    }

    @Override
    public RecipeCommand findCommandById(String l) {

        return recipeToCommand.convert(getRecipeById(l));
    }

    @Override
    public void deleteById(String id) {
        recipeRepository.deleteById(id);
    }
}
