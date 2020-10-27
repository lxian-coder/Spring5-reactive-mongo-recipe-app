package Darcy.springframework.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Darcy Xian  21/8/20  4:44 pm      spring5-recipe-app
 */
@Getter
@Setter
@NoArgsConstructor
public class IngredientCommand {

    private String id;
    private String description;
    private BigDecimal amount;
    private UnitOfMeasureCommand uomC;
    private String recipeId;

}
