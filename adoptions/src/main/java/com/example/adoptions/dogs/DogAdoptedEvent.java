package com.example.adoptions.dogs;

import org.springframework.modulith.events.Externalized;

@Externalized("messageChannelNameHere")
public record DogAdoptedEvent(int dogId) {
}
