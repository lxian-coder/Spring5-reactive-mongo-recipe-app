package Darcy.springframework.config;

import Darcy.springframework.domain.Recipe;
import Darcy.springframework.services.RecipesService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

/**
 * Darcy Xian  18/11/20  3:27 pm      spring5-recipe-app
 */
@Configuration
public class WebConfig {
    @Bean
    RouterFunction<?> routes(RecipesService recipesService){
        return RouterFunctions.route(GET("/api/recipes"),
                serverRequest -> ServerResponse
                                 .ok()
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .body(recipesService.getRecipes(), Recipe.class));
       }
    }



