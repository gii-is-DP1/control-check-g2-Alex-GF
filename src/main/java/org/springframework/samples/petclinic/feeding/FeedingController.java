package org.springframework.samples.petclinic.feeding;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class FeedingController {
    private static final String VIEWS_FEEDING_CREATE_OR_UPDATE_FORM = "feedings/createOrUpdateFeedingForm";

    private final FeedingService feedingService;

    @Autowired
    public FeedingController(FeedingService feedingService) {
        this.feedingService = feedingService;
    }

    @ModelAttribute("types")
    public Collection<FeedingType> populateProductTypes(){
        return this.feedingService.getAllFeedingTypes();
    }

    @GetMapping(value = "/feeding/create")
	public String initCreationForm(ModelMap model) {
		Feeding feeding = new Feeding();
		model.put("feeding", feeding);
		return VIEWS_FEEDING_CREATE_OR_UPDATE_FORM;
	}

    @PostMapping(value = "/feeding/create")
	public String processCreationForm(@Valid Feeding feeding, BindingResult result) throws UnfeasibleFeedingException {
		if (result.hasErrors()) {
			return VIEWS_FEEDING_CREATE_OR_UPDATE_FORM;
		}
		else {
            try{
			    this.feedingService.save(feeding);
            }catch(UnfeasibleFeedingException e){
                return VIEWS_FEEDING_CREATE_OR_UPDATE_FORM;
            }
			
			return "redirect:/welcome";
		}
	}
}
