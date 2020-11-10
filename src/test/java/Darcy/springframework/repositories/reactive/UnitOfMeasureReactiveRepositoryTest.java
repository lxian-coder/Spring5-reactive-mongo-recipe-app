package Darcy.springframework.repositories.reactive;

import Darcy.springframework.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

/**
 * Darcy Xian  10/11/20  1:04 pm      spring5-recipe-app
 */
@RunWith(SpringRunner.class)
@DataMongoTest
public class UnitOfMeasureReactiveRepositoryTest {

    @Autowired
    UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;
    @Before
    public void setUp() throws Exception {
        unitOfMeasureReactiveRepository.deleteAll().block();
    }
   @Test
   public void testSave(){
       UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
       unitOfMeasure.setDescription("foo");

       unitOfMeasureReactiveRepository.save(unitOfMeasure).block();

       Long count = unitOfMeasureReactiveRepository.count().block();
       assertEquals(Long.valueOf(1L), count);
   }

    @Test
    public void findByDescription() {
        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setDescription("foo");

        unitOfMeasureReactiveRepository.save(unitOfMeasure).block();

        UnitOfMeasure fetchedUOM = unitOfMeasureReactiveRepository.findByDescription("foo").block();

        assertEquals("foo", fetchedUOM.getDescription());




    }
}