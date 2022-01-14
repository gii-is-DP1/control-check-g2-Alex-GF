package org.springframework.samples.petclinic.feeding;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

@Component
public class FeedingTypeFormatter implements Formatter<FeedingType>{

    @Override
    public String print(FeedingType object, Locale locale) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public FeedingType parse(String text, Locale locale) throws ParseException {
        // TODO Auto-generated method stub
        return null;
    }
    
}
