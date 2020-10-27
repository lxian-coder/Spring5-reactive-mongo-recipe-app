package Darcy.springframework.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Darcy Xian  21/8/20  4:53 pm      spring5-recipe-app
 */
@Setter
@Getter
@ToString
@NoArgsConstructor
public class NotesCommand {
    private String id;
    private String recipeNotes;

}
