package Darcy.springframework.config;

import Darcy.springframework.domain.Recipe;
import Darcy.springframework.services.RecipesService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import reactor.core.publisher.Flux;

import static org.mockito.Mockito.when;

/**
 * Darcy Xian  18/11/20  5:54 pm      spring5-recipe-app
 */
public class WebConfigTest {

    WebTestClient webTestClient;

    @Mock
    RecipesService recipesService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        WebConfig webConfig = new WebConfig();

        RouterFunction<?> routerFunction = webConfig.routes(recipesService);

        webTestClient = WebTestClient.bindToRouterFunction(routerFunction).build();

    }


    @Test
    public void testGetRecipes()  throws  Exception{
        when(recipesService.getRecipes()).thenReturn(Flux.just());

        webTestClient.get().uri("/api/recipes")
                .accept(MediaType.APPLICATION_JSON)
                .exchange() // invoke serverice
                .expectStatus().isOk();
    }

    @Test
    public void testGetRecipesWithDatas()  throws  Exception{
        when(recipesService.getRecipes()).thenReturn(Flux.just(new Recipe(),new Recipe()));

        webTestClient.get().uri("/api/recipes")
                .accept(MediaType.APPLICATION_JSON)
                .exchange() // invoke serverice
                .expectStatus().isOk()
                .expectBodyList(Recipe.class);
    }
}