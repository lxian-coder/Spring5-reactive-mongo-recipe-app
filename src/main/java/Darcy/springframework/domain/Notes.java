package Darcy.springframework.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

/**
 * spring5-recipe-app
 * Author: Darcy Xian  2020/8/512:55
 */
//@Data
//@EqualsAndHashCode(exclude = "recipe")
@Getter
@Setter

public class Notes {
    @Id
    private String id;
    private String recipeNotes;



}
