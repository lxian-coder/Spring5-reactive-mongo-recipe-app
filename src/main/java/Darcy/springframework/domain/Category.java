package Darcy.springframework.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * spring5-recipe-app
 * Author: Darcy Xian  2020/8/610:49
 */
//@Data
@Getter
@Setter

public class Category {

    private String id;
    private String description;

   private Set<Recipe> recipe = new HashSet<>();

}
