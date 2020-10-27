package Darcy.springframework.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Darcy Xian  21/8/20  4:39 pm      spring5-recipe-app
 */
@Getter
@Setter
@NoArgsConstructor
public class CategoryCommand {
    private String id;
    private String description;
}
