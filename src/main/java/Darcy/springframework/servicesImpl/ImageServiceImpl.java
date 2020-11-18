package Darcy.springframework.servicesImpl;

import Darcy.springframework.domain.Recipe;
import Darcy.springframework.repositories.reactive.RecipeReactiveRepository;
import Darcy.springframework.services.ImageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.IOException;

/**
 * Darcy Xian  6/9/20  3:17 pm      spring5-recipe-app
 */
@Slf4j
@AllArgsConstructor
@Service
public class ImageServiceImpl implements ImageService {

    private final RecipeReactiveRepository recipeReactiveRepository;

    @Override
    public Mono<Void> saveImageFile(String recipeId, MultipartFile file) {

        // 新版
      Mono<Recipe> recipeMono =  recipeReactiveRepository.findById(recipeId)
                .map(recipe -> {
                    Byte[] byteObjects = new Byte[0];
                    try{
                        byteObjects = new Byte[file.getBytes().length];

                        int i = 0;

                        for (byte b : file.getBytes()){
                            byteObjects[i++] = b;
                        }

                        recipe.setImage(byteObjects);

                        return recipe;

                    }catch (IOException e){
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                });
        recipeReactiveRepository.save(recipeMono.block()).block();
        return Mono.empty();



//老版
//        log.debug("Received a file.");
//        try{
//            Recipe recipe = recipeReactiveRepository.findById(recipeId).block();
//            //按照MultipartFile的长度  建立Byte[]
//            Byte[] bytesObjects = new Byte[file.getBytes().length];
//
//            int i = 0;
//            // 用byte  把file文件的值全部复制出来
//            for (byte b : file.getBytes()){
//                bytesObjects[i++] = b;
//            }
//
//            recipe.setImage(bytesObjects);
//
//           recipeReactiveRepository.save(recipe).block();
//        }catch (IOException e){
//            //todo handle better
//            log.error("Error occurred",e);
//            e.printStackTrace();
//        }
    }
}
