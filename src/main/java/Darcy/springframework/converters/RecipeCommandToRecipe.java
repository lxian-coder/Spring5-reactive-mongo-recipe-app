
package Darcy.springframework.converters;

import Darcy.springframework.commands.RecipeCommand;
import Darcy.springframework.domain.Notes;
import Darcy.springframework.domain.Recipe;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * Darcy Xian  23/8/20  11:00 pm      spring5-recipe-app
 */
@NoArgsConstructor
@AllArgsConstructor
@Component
public class RecipeCommandToRecipe implements Converter<RecipeCommand,Recipe> {
    @Autowired
    NotesCommandToNotes NOTES_converter;
    @Autowired
    IngredientCommandToIngredient IG_converter;
    @Autowired
    CategoryCommandToCategory CT_converter;

    @Synchronized
    @Nullable
    @Override
    public Recipe convert(RecipeCommand source) {
        if(source != null){
           Recipe recipe = new Recipe();
           // fix bug     因为在reicpe 里面setNotes method 比较独特，必须保持传入的NOTES  ！= null
           Notes notes = NOTES_converter.convert(source.getNotes());
           if (notes != null ) {
               recipe.setNotes(notes);
           }

           recipe.setDescription(source.getDescription());
           recipe.setDifficulty(source.getDifficulty());
           recipe.setCookTime(source.getCookTime());
           recipe.setDirections(source.getDirections());
           recipe.setPrepTime(source.getPrepTime());
           recipe.setId(source.getId());
           recipe.setServings(source.getServings());
           recipe.setUrl(source.getUrl());
           recipe.setSource(source.getSource());
           recipe.setImage(source.getImage());

           if(source.getIngredients() != null && source.getIngredients().size() > 0){
               source.getIngredients().forEach(
                       ingredientCommand -> recipe.getIngredients().add(IG_converter.convert(ingredientCommand)));
           }

           if(source.getCategories() != null && source.getCategories().size() > 0){
               source.getCategories().forEach(
                       categoryCommand -> recipe.getCategories().add(CT_converter.convert(categoryCommand))
               );
           }
         return recipe;
        }
       return null;

    }
}
