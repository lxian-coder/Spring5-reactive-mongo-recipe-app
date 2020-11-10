package Darcy.springframework.converters;

import Darcy.springframework.commands.IngredientCommand;
import Darcy.springframework.domain.Ingredient;
import lombok.NoArgsConstructor;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * Darcy Xian  21/8/20  10:33 pm      spring5-recipe-app
 */
@NoArgsConstructor
@Component
public class IngredientToIngredientCommand implements Converter<Ingredient,IngredientCommand> {
    @Autowired
    UnitOfMeasureToUnitOfMeasureCommand converter;

    public IngredientToIngredientCommand(UnitOfMeasureToUnitOfMeasureCommand converter) {
        this.converter = converter;
    }

    @Synchronized
    @Nullable
    @Override
    public IngredientCommand convert(Ingredient ingredient) {
        if(ingredient != null){
            IngredientCommand ingredientCom = new IngredientCommand();
            ingredientCom.setAmount(ingredient.getAmount());
            ingredientCom.setDescription(ingredient.getDescription());
            ingredientCom.setId(ingredient.getId());
            ingredientCom.setUomC(converter.convert(ingredient.getUom()));

            return ingredientCom;
        }
        return null;
    }
}
