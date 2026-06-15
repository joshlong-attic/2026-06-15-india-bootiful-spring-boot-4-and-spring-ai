package com.example.adoptions.dogs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.core.dialect.JdbcPostgresDialect;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;
import java.util.Map;

@Controller
@ResponseBody
class DogsController {

    private final DogRepository repository;

    DogsController(DogRepository repository) {
        this.repository = repository;
    }

    @GetMapping(value = "/dogs", version = "2.0")
    Collection<Dog> dogsV2() {
        return this.repository.findAll();
    }

    @GetMapping(value = "/dogs", version = "1.0")
    Collection<Map<String, Object>> dogsV1() {
        return this.repository.findAll()
                .stream()
                .map(dog -> Map.of("id", dog.id(),
                        "name", (Object) dog.name()))
                .toList();
    }
}

@Configuration
class DataConfiguration {

    @Bean
    JdbcPostgresDialect jdbcPostgresDialect() {
        return JdbcPostgresDialect.INSTANCE;
    }
}

interface DogRepository extends ListCrudRepository<Dog, Integer> {

    // select * from dog where name = ?
    Collection<Dog> findByName(String name);
}

// look mom, no Lombok!
record Dog(@Id int id, String name, String owner, String description) {
}




