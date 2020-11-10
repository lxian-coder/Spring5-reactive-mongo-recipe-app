package Darcy.springframework.repositories.reactive;

import Darcy.springframework.domain.Recipe;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

/**
 * Darcy Xian  10/11/20  12:25 pm      spring5-recipe-app
 */
@RunWith(SpringRunner.class)
@DataMongoTest
public class RecipeReactiveRepositoryTest {

    @Autowired
    RecipeReactiveRepository recipeReactiveRepository;
    @Before
    public void setUp() throws Exception {
        recipeReactiveRepository.deleteAll().block();
    }
    @Test
    public void testRecipeSave() throws Exception{
        Recipe recipe = new Recipe();
        recipe.setDescription("Yummy");

        // this is gonna act reactively
        recipeReactiveRepository.save(recipe).block();

        Long count = recipeReactiveRepository.count().block();
        assertEquals(Long.valueOf(1L),count);

    }
}