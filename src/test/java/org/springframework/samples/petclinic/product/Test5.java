package org.springframework.samples.petclinic.product;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.feeding.Feeding;
import org.springframework.samples.petclinic.feeding.FeedingService;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class Test5 {

    @Autowired(required = false)
    FeedingService fs;

    @Test public void test5(){
        feedingServiceIsInjected();
        feedingServiceCanGetFeedings();
    }
    
    public void feedingServiceIsInjected() {
        assertNotNull(fs,"FeedingService was not injected by spring");       
    }
    
    public void feedingServiceCanGetFeedings(){
        assertNotNull(fs,"FeedingService was not injected by spring");
        List<Feeding> feedings=fs.getAll();
        assertNotNull(feedings,"The list of feedings found by the FeedingService was null");
        assertFalse(feedings.isEmpty(),"The list of feedings found by the FeedingService was empty");       
    }
}
