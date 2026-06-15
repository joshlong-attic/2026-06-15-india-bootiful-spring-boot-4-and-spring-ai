package com.example.adoptions.dogs;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
class DogAdoptionsController {

    private final DogAdoptionsService dogAdoptionsService;

    DogAdoptionsController(DogAdoptionsService dogAdoptionsService) {
        this.dogAdoptionsService = dogAdoptionsService;
    }

    @PostMapping("/dogs/{dogId}/adoptions")
    void adopt(@PathVariable int dogId,
               @RequestParam String owner) {
        dogAdoptionsService.adopt(dogId, owner);
    }
}

@Service
@Transactional
class DogAdoptionsService {

    private final DogRepository repository;
    private final ApplicationEventPublisher applicationEventPublisher;

    DogAdoptionsService(DogRepository repository, ApplicationEventPublisher applicationEventPublisher) {
        this.repository = repository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    void adopt(int dogId, String owner) {
        this.repository.findById(dogId)
                .ifPresent(dog -> {
                    var updated = this.repository.save(new Dog(dogId, dog.name(),
                            owner, dog.description()));
                    applicationEventPublisher.publishEvent(
                            new DogAdoptedEvent(dogId));
                    IO.println("adopted " + updated.name() + " to " + owner);
                });
    }

}
