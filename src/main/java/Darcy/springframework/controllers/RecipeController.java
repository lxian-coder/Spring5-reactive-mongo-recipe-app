package Darcy.springframework.controllers;

import Darcy.springframework.commands.RecipeCommand;
import Darcy.springframework.domain.Recipe;
import Darcy.springframework.exceptions.NotFoundException;
import Darcy.springframework.services.RecipesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;


/**
 * Darcy Xian  21/8/20  9:17 am      spring5-recipe-app
 */
@Slf4j
@Controller
public class RecipeController {

   private final RecipesService recipesService;
   private static final String RECIPE_RECIPEFORM_URL = "recipe/recipeform";

    public RecipeController(RecipesService recipesService) {
        this.recipesService = recipesService;
    }
    @GetMapping({"/recipe/{id}/show"})
    public String displayRecipeById (@PathVariable String id, Model model){

        Recipe recipe = recipesService.getRecipeById(id);
        model.addAttribute("recipeId",recipesService.getRecipeById(id));
        return "recipe/show";
    }
    @GetMapping("recipe/new")
    public String newRecipe(Model model){
        model.addAttribute("recipe", new RecipeCommand());

        return "recipe/recipeform";
    }
    @GetMapping("recipe/{id}/update")
    public String updateRecipe(@PathVariable String id, Model model){
        // 从数据库里取出 recipe   然后转化为 RecipeCommand
        model.addAttribute("recipe", recipesService.findCommandById(id));
      return "recipe/recipeform";
    }

   @PostMapping("recipeee")
    public String saveOrUpdate(@Valid  @ModelAttribute("recipe") RecipeCommand command, BindingResult bindingResult){
        if (bindingResult.hasErrors()){

            bindingResult.getAllErrors().forEach(objectError -> {
                log.debug(objectError.toString());
            });
            return RECIPE_RECIPEFORM_URL;
        }
        // 实质是把recipe 转化为 recipecommand  然后存储起来  然后返回recipecommand
        RecipeCommand savedCommand = recipesService.saveRecipeCommand(command);

        return "redirect:/recipe/" + savedCommand.getId() + "/show/";
    }
    @GetMapping("/recipe/{id}/delete")
    public String deleteById(@PathVariable String id){
        log.debug("Deleting id: " + id);

        recipesService.deleteById(id);
        return "redirect:/";
    }
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFound(Exception exception){
        log.error("Handling not found exception");
        log.error(exception.getMessage());
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("404error");
        modelAndView.addObject("exception", exception);

        return modelAndView;
    }

}
