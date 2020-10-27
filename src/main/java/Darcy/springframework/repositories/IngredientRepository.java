package Darcy.springframework.repositories;

import Darcy.springframework.domain.Ingredient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Darcy Xian  5/9/20  9:52 pm      spring5-recipe-app
 */
@Repository
public interface IngredientRepository extends CrudRepository<Ingredient,String> {

}
