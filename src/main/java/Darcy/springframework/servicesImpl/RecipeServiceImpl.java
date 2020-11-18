package Darcy.springframework.servicesImpl;

import Darcy.springframework.commands.RecipeCommand;
import Darcy.springframework.converters.RecipeCommandToRecipe;
import Darcy.springframework.converters.RecipeToRecipeCommand;
import Darcy.springframework.domain.Recipe;
import Darcy.springframework.repositories.reactive.RecipeReactiveRepository;
import Darcy.springframework.services.RecipesService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Slf4j
@AllArgsConstructor
@Service
public class RecipeServiceImpl implements RecipesService {

private final RecipeReactiveRepository recipeReactiveRepository;
private final RecipeToRecipeCommand recipeToCommand;
private final RecipeCommandToRecipe commandToRecipe;



    @Override
    public Flux<Recipe> getRecipes() {
        log.debug("I am in recipeserviceImpl");
       return recipeReactiveRepository.findAll();

    }

    @Override
    public Mono<Recipe> getRecipeById(String id) {

           return recipeReactiveRepository.findById(id);
    }

    @Override
    public Mono<RecipeCommand> saveRecipeCommand(RecipeCommand recipeCommand) {

        return recipeReactiveRepository.save(commandToRecipe.convert(recipeCommand))
                .map(recipeToCommand::convert);
    }

    @Override
    public Mono<RecipeCommand> findCommandById(String l) {
      // 新版本
        return recipeReactiveRepository.findById(l)
                .map(recipe -> {
                    RecipeCommand recipeCommand = recipeToCommand.convert(recipe);

                    recipeCommand.getIngredients().forEach(rc -> {
                        rc.setRecipeId(recipeCommand.getId());
                    });

                    return recipeCommand;
                });

        // 老版本
        //  RecipeCommand recipeCommand = recipeToCommand.convert(getRecipeById(l).block());
//        // enhance command object with id value
//        if (recipeCommand.getIngredients() != null && recipeCommand.getIngredients().size()>0){
//            recipeCommand.getIngredients().forEach(rc -> {
//                rc.setRecipeId(recipeCommand.getId());
//            });

    }

    @Override
    public void deleteById(String id) {
        recipeReactiveRepository.deleteById(id).subscribe();

    }
}
