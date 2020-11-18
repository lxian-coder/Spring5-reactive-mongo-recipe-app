package Darcy.springframework.controllers;


import Darcy.springframework.commands.IngredientCommand;
import Darcy.springframework.commands.UnitOfMeasureCommand;
import Darcy.springframework.converters.IngredientToIngredientCommand;
import Darcy.springframework.domain.Ingredient;
import Darcy.springframework.domain.UnitOfMeasure;
import Darcy.springframework.services.IngredientService;
import Darcy.springframework.services.RecipesService;
import Darcy.springframework.services.UnitOfMeassureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import reactor.core.publisher.Flux;

/**
 * Darcy Xian  1/9/20  12:08 pm      spring5-recipe-app
 */
@Slf4j
@Controller

public class IngredientController {

    private final RecipesService recipesService;
    private final IngredientService ingredientService;
    private final UnitOfMeassureService unitOfMeassureService;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;



    public IngredientController(RecipesService recipesService, IngredientService ingredientService, UnitOfMeassureService unitOfMeassureService,  IngredientToIngredientCommand ingredientToIngredientCommand) {
        this.recipesService = recipesService;
        this.ingredientService = ingredientService;
        this.unitOfMeassureService = unitOfMeassureService;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;

    }



    @GetMapping("/recipe/{id}/ingredients")
    public String listIngredients(@PathVariable String id, Model model) throws Exception {
        log.debug("Getting ingredient list for recipe id: " + id);

        model.addAttribute("recipe", recipesService.findCommandById(id));

        return "recipe/ingredient/list";
    }

    @GetMapping("/recipe/{idR}/ingredient/{idI}/show")
    public String showIngredient(@PathVariable String idR,
                                 @PathVariable String idI, Model model) {
        model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(idR, idI));
        model.addAttribute("recipeId",idR);
        return "recipe/ingredient/show";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/update")
    public String updateRecipeIngredient(@PathVariable String recipeId,
                                         @PathVariable String ingredientId, Model model) {
        model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(recipeId, ingredientId));
      //  model.addAttribute("uomList", unitOfMeassureService.listAllUoms());

        model.addAttribute("recipeId",recipeId);

        return "/recipe/ingredient/ingredientform";
    }

    @PostMapping("recipe/{recipeId}/ingredient")
    public String saveOrUpdate(@ModelAttribute IngredientCommand ingredientCommand,
                               @PathVariable String recipeId,Model model) {



        ingredientCommand.setRecipeId(recipeId);
        ingredientService.saveIngredientCommand(ingredientCommand);


        return "redirect:/recipe/" + recipeId + "/ingredient/" + ingredientCommand.getId() + "/show";
    }
    @GetMapping("/recipe/{recipeId}/ingredient/new")
    public String addNewIngredient(@PathVariable String recipeId,Model model){
        // make sure we have a good id value
        //recipesService.findCommandById(recipeId).subscribe();
        // todo raise exception if null

        // need to return back parent id for hidden form property

        Ingredient ingredient = new Ingredient();
        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        ingredient.setUom(unitOfMeasure);
        IngredientCommand ingredientCommand = ingredientToIngredientCommand.convert(ingredient );
        ingredientCommand.setRecipeId(recipeId);


        model.addAttribute("ingredient", ingredientCommand);
    //    model.addAttribute("uomList",unitOfMeassureService.listAllUoms());
        return "/recipe/ingredient/ingredientform";
    }
    @GetMapping("/recipe/{recipeId}/ingredient/{inId}/delete")
    public String deleteIngredient(@PathVariable String recipeId,
                                   @PathVariable String inId){

        ingredientService.deleteIngredientById(recipeId,inId);

        return "redirect:/recipe/"+recipeId+"/ingredients";
    }

    // 很多地方都用到 "uomlist" 所以单独写出一个方法
    @ModelAttribute("uomList")
    public Flux<UnitOfMeasureCommand> populateUomList(){
        return unitOfMeassureService.listAllUoms();
    }
}















