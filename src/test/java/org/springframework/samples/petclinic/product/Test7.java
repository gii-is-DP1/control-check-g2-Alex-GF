package org.springframework.samples.petclinic.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.text.ParseException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.feeding.FeedingType;
import org.springframework.samples.petclinic.feeding.FeedingTypeFormatter;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class Test7 {

    @Autowired
    FeedingTypeFormatter formatter;

    @Test
    public void test7(){
        testFormatterIsInjected();
        testFormatterObject2String();
        testFormatterString2Object();
        testFormatterString2ObjectNotFound();
    }
    
    public void testFormatterIsInjected(){
        assertNotNull(formatter);
    }
    
    public void testFormatterObject2String(){
        FeedingType feedingType=new FeedingType();
        feedingType.setName("Prueba");
        String result=formatter.print(feedingType, null);
        assertEquals("Prueba",result, "The method print of the formatter is not working properly.");
    }
    
    public void testFormatterString2Object(){
        String name="High Protein Puppy Food";
        FeedingType feedingType;
        try {
            feedingType = formatter.parse(name, null);
            assertNotNull(feedingType, "The method parse of the formatter is not working properly.");
            assertEquals(name, feedingType.getName(), "The method parse of the formatter is not working properly.");
        } catch (ParseException e) {           
            fail("The method parse of the formatter is not working properly.", e);
        }        
    }
    
    public void testFormatterString2ObjectNotFound(){
        String name="This is not a feeding type";
        assertThrows(ParseException.class, () -> formatter.parse(name, null), "The method parse of the formatter is not working properly.");          
    }
}
