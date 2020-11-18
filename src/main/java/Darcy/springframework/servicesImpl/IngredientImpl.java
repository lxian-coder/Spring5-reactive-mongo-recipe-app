package Darcy.springframework.servicesImpl;

import Darcy.springframework.commands.IngredientCommand;
import Darcy.springframework.converters.IngredientCommandToIngredient;
import Darcy.springframework.converters.IngredientToIngredientCommand;
import Darcy.springframework.domain.Ingredient;
import Darcy.springframework.domain.Recipe;
import Darcy.springframework.repositories.UnitOfMeasureRepository;
import Darcy.springframework.repositories.reactive.RecipeReactiveRepository;
import Darcy.springframework.services.IngredientService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;

/**
 * Darcy Xian  1/9/20  7:13 pm      spring5-recipe-app
 */
@Slf4j
@Service
@AllArgsConstructor
public class IngredientImpl implements IngredientService {

    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final RecipeReactiveRepository recipeReactiveRepository;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    @Override
    public Mono<IngredientCommand> findByRecipeIdAndIngredientId(String recipeId, String ingredientId) {
                      // reactive 的解决方式
        return recipeReactiveRepository
                 // 找到 recipe
                .findById(recipeId)
                 // 抽取出 Ingredient iterabel
                .flatMapIterable(Recipe::getIngredients)
                  // 找到特定的ingredient
                .filter(ingredient -> ingredient.getId().equalsIgnoreCase(ingredientId))
                .single()

                .map(ingredient -> {
                    IngredientCommand command = ingredientToIngredientCommand.convert(ingredient);
                    command.setRecipeId(recipeId);
                    return command;
                });

    }

    @Override
    public void saveIngredientCommand(IngredientCommand command) {

        // 当ingredient 传进来后，先查找数据库里的recipe
       recipeReactiveRepository.findById(command.getRecipeId()).subscribe( recipe -> {

        if(recipe == null){
            log.error("Recipe not found for id :"+command.getId());
            log.error("ingredientCommand getRecipeId:"+command.getRecipeId());
        }else {
            // 查找数据库如果存在这个recipe, 那么查找这个recipe有没有这个要储存的ingredient；
            Optional<Ingredient> ingredientOptional = recipe
                    .getIngredients()
                    .stream()
                    .filter(ingredient -> ingredient.getId().equals(command.getId()))
                    .findFirst();
            // 如果已经储存了  那么就更新它的内容
            if(ingredientOptional.isPresent()){
                Ingredient ingredientFound = ingredientOptional.get();
                ingredientFound.setDescription(command.getDescription());
                ingredientFound.setAmount(command.getAmount());
                ingredientFound.setId(command.getId());
                // uomC 从form传过来只有ID， description是null； 所以查找这uom的id 给它赋值；
                if(command.getUomC()!=null) {
                    ingredientFound.setUom(unitOfMeasureRepository.findById(command.getUomC().getId())
                            .orElseThrow(() -> new RuntimeException("UOC can't find."))
                    );
                }else {
                    ingredientFound.setUom(null);
                }
            }else{
                // 如果没有储存这个ingredient, 就在这个recipe里加上这个ingredent

                Ingredient ingredient = ingredientCommandToIngredient.convert(command);
                // uomC 从form传过来只有ID， description是null； 所以查找这uom的id 给它赋值；
                ingredient.setUom(unitOfMeasureRepository.findById(command.getUomC().getId())
                        .orElseThrow(() -> new RuntimeException("Uoc can't find")));
               // ingredient.setRecipe(recipe);
                recipe.addIngredient(ingredient);
            }
            // 储存修改过的recipe
            recipeReactiveRepository.save(recipe).subscribe();


        }
    });
    }

    @Override
    public Mono<Void> deleteIngredientById(String recipeId, String InId) {
        log.debug("Deleting ingredient: " + recipeId+": "+InId);


        // 在数据库查找到recipe
         recipeReactiveRepository.findById(recipeId).subscribe(result ->{

        if(result != null){
           log.debug("recipe find ID :"+recipeId);
           //确认有recipe,然后查找ingredient

            Optional<Ingredient> ingredientOptional = result.getIngredients().stream()
                                .filter(ingredient -> ingredient.getId().equals(InId))
                                .findFirst();
            if(ingredientOptional.isPresent()){
                //确认有ingredient
                Ingredient ingredient = ingredientOptional.get();
                //取消双向关系
                result.getIngredients().remove(ingredient);
                //ingredient.setRecipe(null);
                //重新储存recipe
             recipeReactiveRepository.save(result).subscribe();
            }else {
                log.error("not found ingredient ID:" + InId );

            }
        }else {
            log.error("not found Recipe ID:" + recipeId);

        }});
        return Mono.empty();
    }
}








































