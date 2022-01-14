package org.springframework.samples.petclinic.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.samples.petclinic.feeding.FeedingRepository;
import org.springframework.samples.petclinic.feeding.FeedingType;
import org.springframework.samples.petclinic.pet.PetRepository;
import org.springframework.samples.petclinic.pet.PetType;

@DataJpaTest
public class Test2 {
    @Autowired(required = false)
    FeedingRepository fr;
    @Autowired
    PetRepository pr;
    @Autowired(required = false)
    EntityManager em;
    
    @Test
    public void test2(){
        entityExists();
        repositoryContainsMethod();
        testConstraints();
        testAnnotations();
    }

   
    public void entityExists() {
        FeedingType p=new FeedingType();
        p.setId(7);
        p.setName("High Protein Puppy Food");        
        p.setDescription("Using a standard 8 oz/250 ml measuring cup which contains approximately 112 g of food: For a body weight of 3 - 12, feed with 1/2 to 2/3 cups until 3 months.");
    }  
    
    public void repositoryContainsMethod() {
        try {
            Method findAllFeedingTypes = FeedingRepository.class.getDeclaredMethod("findAllFeedingTypes");
            if(fr!=null){
                List<FeedingType> pts= (List<FeedingType>) findAllFeedingTypes.invoke(fr);
                assertNotNull(pts,"We can not query all the feeding types through the repository");
            }else
                fail("The repository was not injected into the tests, its autowired value was null");
        } catch(NoSuchMethodException e) {
            fail("There is no method findAllFindingTypes in FeedingRepository", e);
        } catch (IllegalAccessException e) {
            fail("There is no public method findAllFindingTypes in FeedingRepository", e);
        } catch (IllegalArgumentException e) {
            fail("There is no method findAllFindingTypes() in FeedingRepository", e);
        } catch (InvocationTargetException e) {
            fail("There is no method findAllFindingTypes() in FeedingRepository", e);
        }
    }

    
    public void testConstraints(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        PetType pt = pr.findPetTypes().get(0);
        
        FeedingType feedingType=new FeedingType();       
        feedingType.setName("ja");
        feedingType.setDescription("blabla");
        feedingType.setPetType(pt);
        assertFalse(validator.validate(feedingType).isEmpty(), "It should not allow names shorter than 5");

        feedingType=new FeedingType();       
        feedingType.setName("En un lugar de la mancha, de cuyo nombre no quiero acordarme, no ha mucho tiempo que vivía un hidalgo de los de lanza en astillero, adarga antigua, rocín flaco y galgo corredor.");
        feedingType.setDescription("blabla");
        feedingType.setPetType(pt);
        assertFalse(validator.validate(feedingType).isEmpty(), "It should not allow names longer than 30");

        feedingType=new FeedingType();       
        feedingType.setDescription("blabla");
        feedingType.setPetType(pt);
        assertFalse(validator.validate(feedingType).isEmpty(), "It should not allow empty names");

        feedingType=new FeedingType();       
        feedingType.setName("High Protein Puppy Food");
        feedingType.setPetType(pt);
        assertFalse(validator.validate(feedingType).isEmpty(), "It should not allow empty descriptions");

        feedingType=new FeedingType();       
        feedingType.setName("High Protein Puppy Food");        
        feedingType.setDescription("Using a standard 8 oz/250 ml measuring cup which contains approximately 112 g of food: For a body weight of 3 - 12, feed with 1/2 to 2/3 cups until 3 months.");
        assertFalse(validator.validate(feedingType).isEmpty(), "It should not allow empty pet type");

        feedingType=new FeedingType();
        feedingType.setName("Happy Puppy Food");        
        feedingType.setDescription("Using a standard 8 oz/250 ml measuring cup which contains approximately 112 g of food: For a body weight of 3 - 12, feed with 1/2 to 2/3 cups until 3 months.");
        feedingType.setPetType(pt);
        em.persist(feedingType);
        
        FeedingType pt2=new FeedingType();       
        feedingType.setName("High Protein Puppy Food");        
        feedingType.setDescription("This is another description.");
        feedingType.setPetType(pt);
        assertThrows(Exception.class,()->em.persist(pt2),"It should not allow two feeding types with the same name");        
    }

    void testAnnotations(){
        try{
            Field petType=FeedingType.class.getDeclaredField("petType");
            ManyToOne annotationManytoOne=petType.getAnnotation(ManyToOne.class);
            assertNotNull(annotationManytoOne,"The petType property is not properly annotated");
            JoinColumn annotationJoinColumn=petType.getAnnotation(JoinColumn.class);
            assertNotNull(annotationJoinColumn,"The petType property is not properly annotated");
            assertEquals("pet_type_id",annotationJoinColumn.name(),"The name of the join column in petType is not properly customized");
        }catch(NoSuchFieldException ex){
            fail("The FeedingType class should have a field that is not present: "+ex.getMessage());
        }
    }    

}
