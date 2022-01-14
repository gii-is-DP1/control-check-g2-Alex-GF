package org.springframework.samples.petclinic.feeding;

import java.util.List;
import java.util.Optional;

public interface FeedingRepository {
    List<Feeding> findAll();
    // List<FeedingType> findAllFeedingTypes();
    Optional<Feeding> findById(int id);
    Feeding save(Feeding p);
}
