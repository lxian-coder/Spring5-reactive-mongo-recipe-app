package Darcy.springframework.controllers;

import Darcy.springframework.commands.RecipeCommand;
import Darcy.springframework.exceptions.NotFoundException;
import Darcy.springframework.services.RecipesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.exceptions.TemplateInputException;
import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.AtomicReference;


/**
 * Darcy Xian  21/8/20  9:17 am      spring5-recipe-app
 */
@Slf4j
@Controller
public class RecipeController {

   private final RecipesService recipesService;
   private static final String RECIPE_RECIPEFORM_URL = "recipe/recipeform";

   private WebDataBinder webDataBinder;
    private   AtomicReference<String> ID = null;
   @InitBinder("ingredient")
   public void initBinder(WebDataBinder webDataBinder){
       this.webDataBinder= webDataBinder;
   }


    public RecipeController(RecipesService recipesService) {
        this.recipesService = recipesService;
    }
    @GetMapping({"/recipe/{id}/show"})
    public String displayRecipeById (@PathVariable String id, Model model){

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
        log.debug(" let us update!");
        model.addAttribute("recipe", recipesService.findCommandById(id));
      return "recipe/recipeform";
    }

   @PostMapping("recipeee")
    public String saveOrUpdate( @ModelAttribute("recipe") RecipeCommand command){
//       webDataBinder.validate();
//       BindingResult bindingResult = webDataBinder.getBindingResult();
//
//       if (bindingResult.hasErrors()){
//
//            bindingResult.getAllErrors().forEach(objectError -> {
//                log.debug(objectError.toString());
//            });
//            return RECIPE_RECIPEFORM_URL;
//        }
        // 实质是把recipecommand 转化为 recipe  然后存储起来  然后返回recipecommand

       Mono<RecipeCommand> savedCommand = recipesService.saveRecipeCommand(command);
     //  savedRecipe.subscribe(result -> command.setId(result.getId()));
       savedCommand.subscribe();


       log.error("I am in recipeee");

        return "redirect:/recipe/" + command.getId() + "/show/";
    }
    @GetMapping("/recipe/{id}/delete")
    public String deleteById(@PathVariable String id){
        log.debug("Deleting id: " + id);

        recipesService.deleteById(id);

        return "redirect:/";
    }

    // 网页错误的处理
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({NotFoundException.class, TemplateInputException.class})
    public String handleNotFound(Exception exception, Model model){
        log.error("Handling not found exception");
        log.error(exception.getMessage());

        model.addAttribute("exception",exception);

        return "404error";
    }

}
