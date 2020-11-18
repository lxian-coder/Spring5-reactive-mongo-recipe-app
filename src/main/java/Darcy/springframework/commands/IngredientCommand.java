package Darcy.springframework.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Darcy Xian  21/8/20  4:44 pm      spring5-recipe-app
 */
@Getter
@Setter
@NoArgsConstructor
public class IngredientCommand {

    private String id;

    @NotBlank
    private String description;

    @NotNull
    @Min(1)
    private BigDecimal amount;

    @NotNull
    private UnitOfMeasureCommand uomC;
    private String recipeId;

}
