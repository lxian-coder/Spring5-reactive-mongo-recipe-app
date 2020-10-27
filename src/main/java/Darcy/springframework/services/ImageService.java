package Darcy.springframework.services;

import org.springframework.web.multipart.MultipartFile;

/**
 * Darcy Xian  6/9/20  3:13 pm      spring5-recipe-app
 */
public interface ImageService {
    void saveImageFile(String recipeId, MultipartFile file);
}
