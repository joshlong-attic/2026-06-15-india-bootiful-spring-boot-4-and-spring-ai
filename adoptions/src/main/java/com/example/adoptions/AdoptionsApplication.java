package com.example.adoptions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.modulith.events.IncompleteEventPublications;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class AdoptionsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdoptionsApplication.class, args);
	}

}

/*
@Component
class Retryer {

	private final IncompleteEventPublications eventPublications ;

    Retryer(IncompleteEventPublications eventPublications) {
        this.eventPublications = eventPublications;
    }

    @Scheduled (cron =  "0 * * * * *")
	void retry() {
	 this.eventPublications
			 .resubmitIncompletePublications(e -> true);
	}
}
*/