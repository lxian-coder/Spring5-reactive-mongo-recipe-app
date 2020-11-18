package Darcy.springframework.controllers;

import Darcy.springframework.services.ImageService;
import Darcy.springframework.services.RecipesService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;



/**
 * Darcy Xian  6/9/20  3:11 pm      spring5-recipe-app
 */
@Slf4j
@AllArgsConstructor
@Controller
public class ImageController {

    private final ImageService imageService;
    private final RecipesService recipesService;

    @GetMapping("recipe/{id}/image")
    public String showUploadForm(@PathVariable String id, Model model){

        model.addAttribute("recipe", recipesService.findCommandById(id));

        return "recipe/imageuploadform";
    }

    @PostMapping("recipe/{id}/image")
    public String handleImagePost(@PathVariable String id,
                                  @RequestParam("imagefile") MultipartFile file){
        imageService.saveImageFile(id,file).block();

        return "redirect:/recipe/" + id + "/show";
    }





    //已经淘汰了 serlvet
//    @GetMapping("recipe/{id}/recipeimage")
//    public void renderImageFromDb(@PathVariable String id,
//                                  HttpServletResponse response) throws IOException{
//        //HttpServletResponse 是web服务器向客户端发送数据
//        RecipeCommand recipeCommand = recipesService.findCommandById(id).block();
//
//        byte[] byteArray = new byte[recipeCommand.getImage().length];
//
//        int i = 0;
//        for(Byte wrappedByte : recipeCommand.getImage()){
//            byteArray[i++] = wrappedByte; //auto unboxing
//        }
//
//        response.setContentType("image/jpeg");
//        InputStream is = new ByteArrayInputStream(byteArray);
//         // IOUtils is going to copy from the byte array input stream to the
//        // response outputStream
//        log.debug("i am in recipeimage");
//        IOUtils.copy(is,response.getOutputStream());
//
//    }
}



















