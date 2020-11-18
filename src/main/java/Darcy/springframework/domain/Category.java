package Darcy.springframework.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

/**
 * spring5-recipe-app
 * Author: Darcy Xian  2020/8/610:49
 */
//@Data
@Getter
@Setter
@Document
public class Category {
    @Id
    private String id;
    private String description;

    private Set<Recipe> recipe ;

}
