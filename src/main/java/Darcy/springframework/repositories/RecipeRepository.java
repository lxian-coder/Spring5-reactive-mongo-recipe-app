package Darcy.springframework.repositories;

import Darcy.springframework.domain.Recipe;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * spring5-recipe-app
 * Author: Darcy Xian  2020/8/711:42
 */
@Repository
public interface RecipeRepository extends CrudRepository<Recipe,String> {
}
