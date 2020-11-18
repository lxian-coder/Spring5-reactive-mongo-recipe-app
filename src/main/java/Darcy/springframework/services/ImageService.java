package Darcy.springframework.services;

import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

/**
 * Darcy Xian  6/9/20  3:13 pm      spring5-recipe-app
 */
public interface ImageService {
    Mono<Void> saveImageFile(String recipeId, MultipartFile file);
}
