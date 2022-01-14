package org.springframework.samples.petclinic.feeding;

import java.time.LocalDate;

import org.springframework.samples.petclinic.pet.Pet;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Feeding {
    Integer id;
    LocalDate startDate;
    double weeksDuration;
    Pet pet;   
}
