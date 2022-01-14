package org.springframework.samples.petclinic.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.feeding.FeedingService;
import org.springframework.samples.petclinic.feeding.FeedingType;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class Test6 {
    @Autowired
    FeedingService fs;

    @Test
    public void test6(){
        validateFindFeedingTypeByName();
        validateNotFoundFeedingTypeByName();
    }

    public void validateFindFeedingTypeByName(){
        String typeName="High Protein Puppy Food";
        FeedingType feedingType=fs.getFeedingType(typeName);
        assertNotNull(feedingType, "getFeedingType by name is returning null");
        assertEquals(typeName,feedingType.getName(), "getFeedingType by name is not returning an existing feeding type");
    }
    
    public void validateNotFoundFeedingTypeByName(){
        String typeName="This is not a valid feeding type name";
        FeedingType feedingType=fs.getFeedingType(typeName);
        assertNull(feedingType, "getFeedingType by name is returning a feeding type that does not exist");
    }

}
