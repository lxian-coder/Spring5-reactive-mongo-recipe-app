package Darcy.springframework.repositories.reactive;

import Darcy.springframework.domain.Category;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

/**
 * Darcy Xian  10/11/20  11:09 am      spring5-recipe-app
 */
@Repository
public interface CategoryReactiveRepository extends ReactiveMongoRepository<Category,String> {
    Mono<Category> findByDescription (String description);
}
