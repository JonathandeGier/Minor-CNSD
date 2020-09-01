package nl.jonathandegier.stringmanipulator.services;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("reverse")
public class StringReverseService implements StringManipulationService {

    public String manipulate(String input) {
        return new StringBuilder(input).reverse().toString();
    }
}
