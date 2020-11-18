package Darcy.springframework.commands;

import Darcy.springframework.domain.Difficulty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Darcy Xian  21/8/20  4:46 pm      spring5-recipe-app
 */
@Setter
@Getter
@NoArgsConstructor
public class RecipeCommand {

    private String id;


    private String description;


    private Integer prepTime;


    private Integer cookTime;


    private Integer servings;

    private String source;



    private String url;


    private String directions;

    private NotesCommand notes;
    private Byte[] image;
    private List<IngredientCommand> ingredients = new ArrayList<>();
    private Difficulty difficulty;
    private List<CategoryCommand> categories = new ArrayList<>();

}
