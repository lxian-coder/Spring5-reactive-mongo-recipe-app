package Darcy.springframework.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * spring5-recipe-app
 * Author: Darcy Xian  2020/8/512:55
 */
//@Data
//@EqualsAndHashCode(exclude = "recipe")
@Getter
@Setter

public class Notes {

    private String id;


    private Recipe recipe;


    private String recipeNotes;



}
