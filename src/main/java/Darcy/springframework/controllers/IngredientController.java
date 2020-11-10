package Darcy.springframework.controllers;


import Darcy.springframework.commands.IngredientCommand;
import Darcy.springframework.commands.RecipeCommand;
import Darcy.springframework.commands.UnitOfMeasureCommand;
import Darcy.springframework.services.IngredientService;
import Darcy.springframework.services.RecipesService;
import Darcy.springframework.services.UnitOfMeassureService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Darcy Xian  1/9/20  12:08 pm      spring5-recipe-app
 */
@Slf4j
@Controller
@AllArgsConstructor
public class IngredientController {

    private final RecipesService recipesService;
    private final IngredientService ingredientService;
    private final UnitOfMeassureService unitOfMeassureService;

    @GetMapping("/recipe/{id}/ingredients")
    public String listIngredients(@PathVariable String id, Model model) throws Exception {
        log.debug("Getting ingredient list for recipe id: " + id);

        model.addAttribute("recipe", recipesService.findCommandById(id));

        return "recipe/ingredient/list";
    }

    @GetMapping("/recipe/{idR}/ingredient/{idI}/show")
    public String showIngredient(@PathVariable String idR,
                                 @PathVariable String idI, Model model) {
        model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(idR, idI).block());
        model.addAttribute("recipeId",idR);
        return "recipe/ingredient/show";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/update")
    public String updateRecipeIngredient(@PathVariable String recipeId,
                                         @PathVariable String ingredientId, Model model) {
        model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(recipeId, ingredientId).block());
        model.addAttribute("uomList", unitOfMeassureService.listAllUoms().collectList().block());

        model.addAttribute("recipeId",recipeId);

        return "/recipe/ingredient/ingredientform";
    }

    @PostMapping("recipe/{recipeId}/ingredient")
    public String saveOrUpdate(@ModelAttribute IngredientCommand ingredientCommand) {
        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(ingredientCommand).block();
        log.debug("saved receipe id:" + savedCommand.getRecipeId());
        log.debug("saved ingredient id:" + savedCommand.getId());

        return "redirect:/recipe/" + savedCommand.getRecipeId() + "/ingredient/" + savedCommand.getId() + "/show";
    }
    @GetMapping("/recipe/{recipeId}/ingredient/new")
    public String addNewIngredient(@PathVariable String recipeId,Model model){
        // make sure we have a good id value
        RecipeCommand recipeCommand = recipesService.findCommandById(recipeId);
        // todo raise exception if null

        // need to return back parent id for hidden form property
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setRecipeId(recipeId);
        // init uom
        ingredientCommand.setUomC(new UnitOfMeasureCommand());

        model.addAttribute("ingredient",ingredientCommand);
        model.addAttribute("uomList",unitOfMeassureService.listAllUoms().collectList().block());
        return "/recipe/ingredient/ingredientform";
    }
    @GetMapping("/recipe/{recipeId}/ingredient/{inId}/delete")
    public String deleteIngredient(@PathVariable String recipeId,
                                   @PathVariable String inId){

        ingredientService.deleteIngredientById(recipeId,inId).block();

        return "redirect:/recipe/"+recipeId+"/ingredients";
    }
}















