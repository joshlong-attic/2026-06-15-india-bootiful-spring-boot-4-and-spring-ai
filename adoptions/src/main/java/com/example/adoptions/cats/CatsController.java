package com.example.adoptions.cats;

import org.springframework.context.annotation.Configuration;
import org.springframework.resilience.annotation.ConcurrencyLimit;
import org.springframework.resilience.annotation.EnableResilientMethods;
import org.springframework.resilience.annotation.Retryable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.registry.ImportHttpServices;

import java.util.concurrent.atomic.AtomicInteger;

@Controller
@ResponseBody
class CatsController {

    private final CatsClient catsClient;

    private final AtomicInteger counter = new AtomicInteger(0);

    CatsController(CatsClient catsClient) {
        this.catsClient = catsClient;
    }

    @ConcurrencyLimit(10)
    @Retryable(maxRetries = 5, includes = IllegalStateException.class)
    @GetExchange("/cats")
    CatFacts facts() {

        if (this.counter.incrementAndGet() < 5) {
            IO.println("oops!");
            throw new IllegalStateException("Simulating a failure");
        }
        IO.println("yay!");

        return catsClient.facts();
    }
}


interface CatsClient {

    @GetExchange("https://www.catfacts.net/api")
    CatFacts facts();
}

record CatFact(String fact) {
}

record CatFacts(CatFact[] facts) {
}

@Configuration
@EnableResilientMethods
@ImportHttpServices(CatsClient.class)
class CatsClientConfiguration {

}