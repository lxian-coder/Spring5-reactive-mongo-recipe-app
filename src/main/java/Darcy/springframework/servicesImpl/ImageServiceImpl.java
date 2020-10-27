package Darcy.springframework.servicesImpl;

import Darcy.springframework.domain.Recipe;
import Darcy.springframework.repositories.RecipeRepository;
import Darcy.springframework.services.ImageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Darcy Xian  6/9/20  3:17 pm      spring5-recipe-app
 */
@Slf4j
@AllArgsConstructor
@Service
public class ImageServiceImpl implements ImageService {

    private final RecipeRepository recipeRepository;

    @Override
    public void saveImageFile(String recipeId, MultipartFile file) {

        log.debug("Received a file.");
        try{
            Recipe recipe = recipeRepository.findById(recipeId).get();
            //按照MultipartFile的长度  建立Byte[]
            Byte[] bytesObjects = new Byte[file.getBytes().length];

            int i = 0;
            // 用byte  把file文件的值全部复制出来
            for (byte b : file.getBytes()){
                bytesObjects[i++] = b;
            }

            recipe.setImage(bytesObjects);

            recipeRepository.save(recipe);
        }catch (IOException e){
            //todo handle better
            log.error("Error occurred",e);
            e.printStackTrace();
        }
    }
}
