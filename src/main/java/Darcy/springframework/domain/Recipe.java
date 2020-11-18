package Darcy.springframework.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

/**
 * spring5-recipe-app
 * Author: Darcy Xian  2020/8/512:56
 */
@Getter
@Setter
@Document
public class Recipe {
    @Id
    private String id;

    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;
    private Difficulty difficulty;
    private String directions;
    // private Difficulty difficulty;

    private Byte[] image;

    private Notes notes;
    private Set<Ingredient> ingredients = new HashSet<>() ;

    private Set<Category> categories = new HashSet<>() ;

    public Recipe() {
    }

    public Notes setNotes(Notes notes) {
        this.notes = notes;
       // notes.setRecipe(this);
        return notes;
    }
    public Recipe addIngredient(Ingredient ingredient){
        this.getIngredients().add(ingredient);
        return this;
    }

}
