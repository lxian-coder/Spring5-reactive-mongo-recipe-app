package Darcy.springframework.controllers;

import Darcy.springframework.commands.RecipeCommand;
import Darcy.springframework.services.ImageService;
import Darcy.springframework.services.RecipesService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Darcy Xian  6/9/20  4:11 pm      spring5-recipe-app
 */
public class ImageControllerTest {
    @Mock
    ImageService imageService;
    @Mock
    RecipesService recipesService;

    ImageController imageController;
    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        imageController = new ImageController(imageService,recipesService);
        mockMvc = MockMvcBuilders.standaloneSetup(imageController)
                .setControllerAdvice(new ControllerExceptionHandler())
                .build();
    }
    @Test
    public void testImageNuberForamtBadRequest() throws Exception{
        mockMvc.perform(get("/recipe/aaa/recipeimage"))
                .andExpect(status().isBadRequest())
                .andExpect(view().name("400error"));
    }

    @Test
    public void showUploadForm() throws Exception{
        //given
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(1L);

        when(recipesService.findCommandById(anyLong())).thenReturn(recipeCommand);

        //when
        mockMvc.perform(get("/recipe/1/image"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("recipe"));

        verify(recipesService,times(1)).findCommandById(anyLong());

    }

    @Test
    public void handleImagePost() throws Exception{
        MockMultipartFile multipartFile=
                new MockMultipartFile("imagefile","testing.txt","text/plain",
                        "Spring Framework Darcy".getBytes());

        mockMvc.perform(multipart("/recipe/1/image").file(multipartFile))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location","/recipe/1/show"));

        verify(imageService, times(1)).saveImageFile(anyLong(),any());
    }

    @Test
    public void renderImageFromDB() throws Exception {
        //given
        RecipeCommand command = new RecipeCommand();
        command.setId(1L);

        String s = "fake image text";
        Byte[] byteBoxed = new Byte[s.getBytes().length];

        int i = 0;

        for(byte primByte : s.getBytes()){
            byteBoxed[i++] = primByte;
        }

        command.setImage(byteBoxed);
        when(recipesService.findCommandById(anyLong())).thenReturn(command);

        //when
        MockHttpServletResponse response = mockMvc.perform(get("/recipe/1/recipeimage"))
                 .andExpect(status().isOk())
                .andReturn().getResponse();

        byte[] reponseBytes = response.getContentAsByteArray();
        assertEquals(s.getBytes().length, reponseBytes.length);

    }
}



































