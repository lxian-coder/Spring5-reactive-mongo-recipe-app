package Darcy.springframework.converters;

import Darcy.springframework.commands.IngredientCommand;
import Darcy.springframework.domain.Ingredient;
import Darcy.springframework.domain.Recipe;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;



/**
 * Darcy Xian  22/8/20  10:05 pm      spring5-recipe-app
 */
@NoArgsConstructor
@AllArgsConstructor
@Component
public class IngredientCommandToIngredient implements Converter<IngredientCommand, Ingredient> {
     @Autowired
    private  UnitOfMeasureCommandToUnitOfMeasure converter;

    @Synchronized
    @Nullable
    @Override
    public Ingredient convert(IngredientCommand source) {
        if (source != null) {
            final Ingredient ingredient = new Ingredient();

            ingredient.setUom(converter.convert(source.getUomC()));
            ingredient.setDescription(source.getDescription());
            ingredient.setAmount(source.getAmount());
            ingredient.setId(source.getId());
            // 如果ingredient 有 recipe, 那么就建立以个新的recipe，赋予ID 和 ingredient.
            // 这个地方有点疑惑？  如果recipeID 是数据库已经存在的ID，那么这是个什么逻辑？
            if(source.getRecipeId() != null){
                Recipe recipe = new Recipe();
                recipe.setId(source.getRecipeId());

                recipe.addIngredient(ingredient);
            }
           return ingredient;
        }
        return null;
    }
}
