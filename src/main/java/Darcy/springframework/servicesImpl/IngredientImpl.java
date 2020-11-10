package Darcy.springframework.servicesImpl;

import Darcy.springframework.commands.IngredientCommand;
import Darcy.springframework.converters.IngredientCommandToIngredient;
import Darcy.springframework.converters.IngredientToIngredientCommand;
import Darcy.springframework.domain.Ingredient;
import Darcy.springframework.domain.Recipe;
import Darcy.springframework.repositories.RecipeRepository;
import Darcy.springframework.repositories.UnitOfMeasureRepository;
import Darcy.springframework.services.IngredientService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Darcy Xian  1/9/20  7:13 pm      spring5-recipe-app
 */
@Slf4j
@Service
@AllArgsConstructor
public class IngredientImpl implements IngredientService {

    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;


    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(String recipeId, String ingredientId) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if(!recipeOptional.isPresent()){
            //todo impl error handling
            log.error("recipe id not found. Id: " + recipeId);
        }

        Recipe recipe = recipeOptional.get();

        Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .map( ingredient -> ingredientToIngredientCommand.convert(ingredient)).findFirst();

        if(!ingredientCommandOptional.isPresent()){
            //todo impl error handling
            log.error("Ingredient id not found: "+ingredientId);
        }
        return ingredientCommandOptional.get();
    }

    @Override
    public IngredientCommand saveIngredientCommand(IngredientCommand command) {

        // 当ingredient 传进来后，先查找数据库里的recipe
        Optional<Recipe> recipeOptional = recipeRepository.findById(command.getRecipeId());

        if(!recipeOptional.isPresent()){
            log.error("Recipe not found for id :"+command.getId());
            return new IngredientCommand();
        }else {
            Recipe recipe = recipeOptional.get();
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
                // uomC 从form传过来只有ID， description是null； 所以查找这uom的id 给它赋值；
                if(command.getUomC()!=null) {
                    ingredientFound.setUom(unitOfMeasureRepository.findById(command.getUomC().getId())
                            .orElseThrow(() -> new RuntimeException("UOM not found")));
                }else {
                    ingredientFound.setUom(null);
                }
            }else{
                // 如果没有储存这个ingredient, 就在这个recipe里加上这个ingredent
                Ingredient ingredient = ingredientCommandToIngredient.convert(command);
               // ingredient.setRecipe(recipe);
                recipe.addIngredient(ingredient);
            }
            // 储存修改过的recipe
            Recipe savedRecipe = recipeRepository.save(recipe);

            //回到新储存的 savedRecipe 里面寻找和 command 相同ID的ingredient；
            Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients().stream()
                    .filter(recipeIngredients -> recipeIngredients.getId().equals(command.getId()))
                    .findFirst();

            //check by description
            if(!savedIngredientOptional.isPresent()){
                // not totally safe.. But best guess
                savedIngredientOptional = savedRecipe.getIngredients().stream()
                        .filter(reipeIngredients -> reipeIngredients.getDescription().equals(command.getDescription()))
                        .filter(ingredient -> ingredient.getAmount().equals(command.getAmount()))
                        .filter(ingredient -> ingredient.getUom().getId().equals(command.getUomC().getId()))
                        .findFirst();
            }

            //to do check for fail
            // enhance with id value
            IngredientCommand ingredientCommandSaved = ingredientToIngredientCommand.convert(savedIngredientOptional.get());
            ingredientCommandSaved.setRecipeId(recipe.getId());

            return ingredientCommandSaved;
        }
    }


    @Override
    public void deleteIngredientById( String recipeId, String InId) {
        // 在数据库查找到recipe
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if(recipeOptional.isPresent()){
           log.debug("recipe find ID :"+recipeId);
           //确认有recipe,然后查找ingredient
            Recipe recipe = recipeOptional.get();

            Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream()
                                .filter(ingredient -> ingredient.getId().equals(InId))
                                .findFirst();
            if(ingredientOptional.isPresent()){
                //确认有ingredient
                Ingredient ingredient = ingredientOptional.get();
                //取消双向关系
                recipe.getIngredients().remove(ingredient);
                //ingredient.setRecipe(null);
                //重新储存recipe
                Recipe savedRecipe = recipeRepository.save(recipe);
            }else {
                log.error("not found ingredient ID:" + InId );
                return;
            }
        }else {
            log.error("not found Recipe ID:" + recipeId);
            return;
        }
    }
}








































