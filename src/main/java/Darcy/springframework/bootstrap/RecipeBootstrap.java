package Darcy.springframework.bootstrap;


import Darcy.springframework.domain.*;
import Darcy.springframework.repositories.CategoryRepository;
import Darcy.springframework.repositories.RecipeRepository;
import Darcy.springframework.repositories.UnitOfMeasureRepository;
import Darcy.springframework.repositories.reactive.CategoryReactiveRepository;
import Darcy.springframework.repositories.reactive.RecipeReactiveRepository;
import Darcy.springframework.repositories.reactive.UnitOfMeasureReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * spring5-recipe-app
 * Author: Darcy Xian  2020/8/9  9:09
 */
@Slf4j
@Component

public class RecipeBootstrap  implements ApplicationListener<ContextRefreshedEvent>{
    private final CategoryRepository categoryRepository;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    @Autowired
    UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;
    @Autowired
    CategoryReactiveRepository categoryReactiveRepository;
    @Autowired
    RecipeReactiveRepository recipeReactiveRepository;


    public RecipeBootstrap(UnitOfMeasureRepository unitOfMeasureRepository, CategoryRepository categoryRepository, RecipeRepository recipeRepository) {
        this.categoryRepository = categoryRepository;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {


        loadCategories();
        loadUom();
        recipeRepository.saveAll(getRecipes());
        log.debug("Loading Bootstrap Data");

        // just for fun
        log.error("#####");
        log.error("UOM Count:"+ unitOfMeasureReactiveRepository.count().block().toString());
        log.error("Category Count:" + categoryReactiveRepository.count().block().toString());
        log.error("Recipe Count:"+ recipeReactiveRepository.count().block().toString());
    }

    private void loadCategories(){
        Category cat1 = new Category();
        cat1.setDescription("American");
        categoryRepository.save(cat1);

        Category cat2 = new Category();
        cat2.setDescription("Italian");
        categoryRepository.save(cat2);

        Category cat3 = new Category();
        cat3.setDescription("Mexican");
        categoryRepository.save(cat3);

        Category cat4 = new Category();
        cat4.setDescription("Fast Food");
        categoryRepository.save(cat4);
    }

    private void loadUom(){
        UnitOfMeasure uom1 = new UnitOfMeasure();
        uom1.setDescription("Teaspoon");
        unitOfMeasureRepository.save(uom1);


        UnitOfMeasure uom2 = new UnitOfMeasure();
        uom2.setDescription("Tablespoon");
        unitOfMeasureRepository.save(uom2);

        UnitOfMeasure uom3 = new UnitOfMeasure();
        uom3.setDescription("Cup");
        unitOfMeasureRepository.save(uom3);

        UnitOfMeasure uom4 = new UnitOfMeasure();
        uom4.setDescription("Pinch");
        unitOfMeasureRepository.save(uom4);

        UnitOfMeasure uom5 = new UnitOfMeasure();
        uom5.setDescription("Ounce");
        unitOfMeasureRepository.save(uom5);

        UnitOfMeasure uom6 = new UnitOfMeasure();
        uom6.setDescription("Each");
        unitOfMeasureRepository.save(uom6);

        UnitOfMeasure uom7 = new UnitOfMeasure();
        uom7.setDescription("Pint");
        unitOfMeasureRepository.save(uom7);

        UnitOfMeasure uom8 = new UnitOfMeasure();
        uom8.setDescription("Dash");
        unitOfMeasureRepository.save(uom8);

    }

    private List<Recipe> getRecipes() {
        List<Recipe> recipes = new ArrayList<>();
        log.debug("I am in RecipeBootstrap!");

        Recipe guacRecipe = new Recipe();
        // UnitOfMeasure
        // Optional<UnitOfMeasure> checkDash = unitOfMeasureRepository.findByDescription("Dash");
        // if(!checkDash.isPresent()){
        //     throw new RuntimeException("Expected UOM Dash not found");
        // }
        guacRecipe.setSource("Dr.Berg");
        guacRecipe.setUrl("http://www.simplyrecipes.com/recipes/perfect_guacamole/");

        Optional<UnitOfMeasure> checkEach = unitOfMeasureRepository.findByDescription("Each");
        if(!checkEach.isPresent()){
            throw new RuntimeException("Expected UOM Each not found");
        }

        Optional<UnitOfMeasure> checkTeaspoon = unitOfMeasureRepository.findByDescription("Teaspoon");
        if(!checkTeaspoon.isPresent()){
            throw new RuntimeException("Expected UOM Teaspoon not found");
        }

        Optional<UnitOfMeasure> checkTablespoon = unitOfMeasureRepository.findByDescription("Tablespoon");
        if(!checkTablespoon.isPresent()){
            throw new RuntimeException("Expected UOM Tablespoon not found.");
        }

        Optional<UnitOfMeasure> checkCub = unitOfMeasureRepository.findByDescription("Cup");
        if(!checkCub.isPresent()){
            throw new RuntimeException("Expected UOM Cub not found.");
        }

        Optional<UnitOfMeasure> checkPinch = unitOfMeasureRepository.findByDescription("Pinch");
        if(!checkPinch.isPresent()){
            throw new RuntimeException("Expected UOM Pinch not found");
        }

        Optional<UnitOfMeasure> checkOunce = unitOfMeasureRepository.findByDescription("Ounce");
        if(!checkOunce.isPresent()){
            throw new RuntimeException("Expected UOM Ounce not found.");
        }

        //UnitOfMeasure savedDash = checkDash.get();
        UnitOfMeasure savedEach = checkEach.get();
        UnitOfMeasure savedTeaspoon = checkTeaspoon.get();
        UnitOfMeasure savedTable = checkTablespoon.get();
        UnitOfMeasure savedCub = checkCub.get();
        UnitOfMeasure savedPinch = checkPinch.get();
        UnitOfMeasure savedOunce = checkOunce.get();
        // ingredient
        guacRecipe.addIngredient(new Ingredient("ripe avocados",new BigDecimal(2),savedEach));
        guacRecipe.addIngredient(new Ingredient("salt",new BigDecimal(".25"),savedTeaspoon));
        guacRecipe.addIngredient(new Ingredient("fresh lime juice or lemon juice",new BigDecimal(1),savedTable));
        guacRecipe.addIngredient(new Ingredient("minced red onion or thinly sliced green onion",new BigDecimal(2), savedTable));
        guacRecipe.addIngredient(new Ingredient("serrano chiles.stems and seeds removed, minced", new BigDecimal(2),savedEach));
        guacRecipe.addIngredient(new Ingredient("cliantro (leaves and tender stems),finely chopped",new BigDecimal(2),savedTable));
        guacRecipe.addIngredient(new Ingredient("freshly grated black pepper",new BigDecimal(1), savedCub));

        // category
        Optional<Category> checkAmerican = categoryRepository.findByDescription("American");
        if(!checkAmerican.isPresent()){
            throw new RuntimeException("Expected American category not found");
        }

        Optional<Category> checkMexican = categoryRepository.findByDescription("Mexican");
        if(!checkMexican.isPresent()){
            throw new RuntimeException("Expected Mexican category not found");
        }

        Category savedAmerican = checkAmerican.get();
        Category saveMexican = checkMexican.get();

        guacRecipe.getCategories().add(savedAmerican);
        guacRecipe.getCategories().add(saveMexican);



        // difficulty
        guacRecipe.setDifficulty(Difficulty.EASY);

        //notes
        Notes recipeNotes = new Notes();
        //recipeNotes.setRecipe(guacRecipe);
        recipeNotes.setRecipeNotes("Once you have basic guacamole down, feel free to experiment with variations including " +
                "strawberries, peaches, pineapple, mangoes, even watermelon. One classic Mexican guacamole has pomegranate" +
                " seeds and chunks of peaches in it (a Diana Kennedy favorite). You can get creative with homemade " +
                "guacamole!" +
                "\n" +
                "Simple Guacamole: The simplest version of guacamole is just mashed avocados with salt. Don’t let the " +
                "lack of availability of other ingredients stop you from making guacamole." +
                "\n" +
                "Quick guacamole: For a very quick guacamole just take a 1/4 cup of salsa and mix it in with your mashed" +
                " avocados."+
                "\n"+
                "\n"+
                "Read More: https://www.simplyrecipes.com/recipes/perfect_guacamole/" );

        guacRecipe.setNotes(recipeNotes);




        String directions = "1 Cut the avocado, remove flesh: Cut the avocados in half. Remove the pit. Score the " +
                "inside of the avocado with a blunt knife and scoop out the flesh with a spoon. (See How to Cut and" +
                " Peel an Avocado.) Place in a bowl."+
                "\n"+
                "2 Mash with a fork: Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should be" +
                " a little chunky.)"+
                "\n"+
                "3 Add salt, lime juice, and the rest: Sprinkle with salt and lime (or lemon) juice. The acid in the lime juice will provide some balance to the richness of the avocado and will help delay the avocados from turning brown.\n" +
                "\n" +
                "Add the chopped onion, cilantro, black pepper, and chiles. Chili peppers vary individually in their hotness. So, start with a half of one chili pepper and add to the guacamole to your desired degree of hotness.\n" +
                "\n" +
                "Remember that much of this is done to taste because of the variability in the fresh ingredients. Start with this recipe and adjust to your taste.\n" +
                "\n" +
                "Chilling tomatoes hurts their flavor, so if you want to add chopped tomato to your guacamole, add it just before serving.";
        //  complete guacRecipe
        guacRecipe.setCookTime(10);
        guacRecipe.setDescription("Guacamole Recipe");
        guacRecipe.setDirections(directions);
        guacRecipe.setPrepTime(2);
        guacRecipe.setServings(3);

        System.out.println("guacRecipe completed!!!!!!!!!!!!!!");
        recipes.add(guacRecipe);

        Recipe tacosRecipe = new Recipe();

        // tacos description
        tacosRecipe.setDescription("Spicy Grilled Chicken Tacos Recipe");

        // ingredent
        tacosRecipe.addIngredient(new Ingredient("ancho chili", new BigDecimal("2"),savedTable));
        tacosRecipe.addIngredient(new Ingredient("dried oregano", new BigDecimal("1"), savedTeaspoon));
        tacosRecipe.addIngredient(new Ingredient("dried cumin", new BigDecimal("1"),savedTeaspoon));
        tacosRecipe.addIngredient(new Ingredient("suger",new BigDecimal("1"),savedTeaspoon));
        tacosRecipe.addIngredient(new Ingredient("salt", new BigDecimal("0.5"),savedTable));
        tacosRecipe.addIngredient(new Ingredient("clove garlic,finely chopped", new BigDecimal("1"), savedEach));
        tacosRecipe.addIngredient(new Ingredient("finely grated orange zest", new BigDecimal("1"),savedTable));
        tacosRecipe.addIngredient(new Ingredient("fresh-squeezed orange juice",new BigDecimal("3"),savedTable));
        tacosRecipe.addIngredient(new Ingredient("olive oil",new BigDecimal("2"),savedTable));




        // category
        tacosRecipe.getCategories().add(savedAmerican);

        // recipe notes
        Notes tacosNotes = new Notes();
        tacosNotes.setRecipeNotes("Look for ancho chile powder with the Mexican ingredients at your grocery store, on buy it online. (If you can't find ancho chili powder, you replace the ancho chili, the oregano, and the cumin with 2 1/2 tablespoons regular chili powder, though the flavor won't be quite the same.)");

        tacosRecipe.setNotes(tacosNotes);

        // descriptions
        String tacosDescriptions = "1 Prepare a gas or charcoal grill for medium-high, direct heat.\n" +
                "\n" +
                "2 Make the marinade and coat the chicken: In a large bowl, stir together the chili powder, oregano, cumin, sugar, salt, garlic and orange zest. Stir in the orange juice and olive oil to make a loose paste. Add the chicken to the bowl and toss to coat all over.\n" +
                "\n" +
                "Set aside to marinate while the grill heats and you prepare the rest of the toppings"+
                "3 Grill the chicken: Grill the chicken for 3 to 4 minutes per side, or until a thermometer inserted into the thickest part of the meat registers 165F. Transfer to a plate and rest for 5 minutes.\n" +
                "\n" +
                "4 Warm the tortillas: Place each tortilla on the grill or on a hot, dry skillet over medium-high heat. As soon as you see pockets of the air start to puff up in the tortilla, turn it with tongs and heat for a few seconds on the other side.\n" +
                "\n" +
                "Wrap warmed tortillas in a tea towel to keep them warm until serving.\n" +
                "\n" +
                "5 Assemble the tacos: Slice the chicken into strips. On each tortilla, place a small handful of arugula. Top with chicken slices, sliced avocado, radishes, tomatoes, and onion slices. Drizzle with the thinned sour cream. Serve with lime wedges";


        //complete tacosRecipe
        tacosRecipe.setCookTime(12);
        tacosRecipe.setDifficulty(Difficulty.KIND_OF_HARD);
        tacosRecipe.setPrepTime(20);
        tacosRecipe.setServings(22);
        tacosRecipe.setDirections(tacosDescriptions);


        System.out.println("TacosRecipe completed !!!!!!!");
        recipes.add(tacosRecipe);


        return recipes;

    }


}
