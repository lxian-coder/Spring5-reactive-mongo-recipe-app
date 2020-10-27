package Darcy.springframework.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * spring5-recipe-app
 * Author: Darcy Xian  2020/8/515:15
 */

@Getter
@Setter
//@Table(name = "Ingerdients")
public class Ingredient {

    private String id;
    //@Column(name = "descriptionsss")
    private String description;
    private BigDecimal amount;

    private Recipe recipe;
    private UnitOfMeasure  uom;

    public Ingredient() {
    }

    public Ingredient(String description, BigDecimal amount, UnitOfMeasure uom) {
        this.description = description;
        this.amount = amount;
        this.uom = uom;
    }

    public Ingredient(String description, BigDecimal amount, UnitOfMeasure uom, Recipe recipe) {
        this.description = description;
        this.amount = amount;
        this.uom = uom;
        this.recipe = recipe;
    }

}
