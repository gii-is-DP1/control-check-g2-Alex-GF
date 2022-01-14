package org.springframework.samples.petclinic.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.lang.reflect.Field;
import java.util.Optional;

import javax.persistence.ManyToOne;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.feeding.Feeding;
import org.springframework.samples.petclinic.feeding.FeedingRepository;
import org.springframework.samples.petclinic.feeding.FeedingType;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class Test4 {
    @Autowired
    FeedingRepository fr;

    @Test
    public void test4() {
        testAnnotations();
        testInitialFeedingsLinked();
    }

    void testAnnotations(){
        try{
            Field feedingType=Feeding.class.getDeclaredField("feedingType");
            ManyToOne annotationManytoOne=feedingType.getAnnotation(ManyToOne.class);
            assertNotNull(annotationManytoOne,"The feedingType property is not properly annotated");
        }catch(NoSuchFieldException ex){
            fail("The Feeding class should have an attribute called feedingType that is not present: "+ex.getMessage());
        }
    }    

    void testInitialFeedingsLinked() {
        Field feedingType;
        FeedingType ft;

        try {
            feedingType = Feeding.class.getDeclaredField("feedingType");
            feedingType.setAccessible(true);
        
            Optional<Feeding> f1=fr.findById(1);
            assertTrue(f1.isPresent(),"Feeding with id:1 not found");
            ft = (FeedingType) feedingType.get(f1.get());
            assertNotNull(ft,"The feeding with id:1 has not a feeding type associated");
            assertEquals(2,ft.getId(),"The id of the feeding type associated to the feeding with id:1 is not 2");

            Optional<Feeding> f2=fr.findById(2);
            assertTrue(f2.isPresent(),"Feeding with id:2 not found");
            ft = (FeedingType) feedingType.get(f2.get());
            assertNotNull(ft,"The feeding with id:2 has not a feeding type associated");
            assertEquals(1, ft.getId(),"The id of the feeding type associated to the feeding with id:2 is not 1");
        } catch (NoSuchFieldException e) {
            fail("The Feeding class should have an attribute called feedingType that is not present: "+e.getMessage());
        } catch (IllegalArgumentException e) {
            fail("The Feeding class should have an attribute called feedingType that is not present: "+e.getMessage());
        } catch (IllegalAccessException e) {
            fail("The Feeding class should have an attribute called feedingType that is not present: "+e.getMessage());
        }
    }


}
