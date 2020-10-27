package Darcy.springframework.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * spring5-recipe-app
 * Author: Darcy Xian  2020/8/512:56
 */
@Getter
@Setter
public class Recipe {

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

    private Set<Ingredient> ingredients = new HashSet<>();

    private Set<Category> categories = new HashSet<>();

    public Recipe() {
    }

    public void setNotes(Notes notes) {
        this.notes = notes;
        notes.setRecipe(this);
    }
    public void addIngredient(Ingredient ingredient){
        this.getIngredients().add(ingredient);
        ingredient.setRecipe(this);
    }

}
