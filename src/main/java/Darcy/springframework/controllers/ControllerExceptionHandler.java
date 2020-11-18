package Darcy.springframework.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;


/**
 * Darcy Xian  11/9/20  7:49 pm      spring5-recipe-app
 */
@Slf4j
@ControllerAdvice
public class
ControllerExceptionHandler {

//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(NumberFormatException.class)
//    public ModelAndView handleNumberFormat(Exception exception){
//
//        log.error("Handling Number Format Exception");
//        log.error(exception.getMessage());
//
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("400error");
//        modelAndView.addObject("exception", exception);
//
//        return modelAndView;
//    }
}
