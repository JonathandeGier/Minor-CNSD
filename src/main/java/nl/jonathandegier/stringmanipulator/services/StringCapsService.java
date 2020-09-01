package nl.jonathandegier.stringmanipulator.services;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("caps")
public class StringCapsService implements StringManipulationService {

    public String manipulate(String input) {
        return input.toUpperCase();
    }
}
