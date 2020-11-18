package Darcy.springframework.controllers;

import Darcy.springframework.services.RecipesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;



@Slf4j
@Controller
public class IndexController {

    private RecipesService recipesService;

    public IndexController(RecipesService recipesService) {
        this.recipesService = recipesService;
    }

    @RequestMapping({"/recipe","/recipes","/recipes/","/recipe","","/"})
    public String getRecipeList(Model model){
        log.debug("I am in index controller");

         model.addAttribute("recipes", recipesService.getRecipes());



         return "index";
    }

}
