package Darcy.springframework.repositories.reactive;

import Darcy.springframework.domain.Recipe;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Darcy Xian  10/11/20  11:16 am      spring5-recipe-app
 */
@Repository
public interface RecipeReactiveRepository extends ReactiveMongoRepository<Recipe,String> {

}
