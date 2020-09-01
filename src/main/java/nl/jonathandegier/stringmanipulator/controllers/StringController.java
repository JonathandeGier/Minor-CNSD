package nl.jonathandegier.stringmanipulator.controllers;

import nl.jonathandegier.stringmanipulator.services.StringService;
import org.springframework.stereotype.Controller;

@Controller
public class StringController {

    private StringService stringService;

    public StringController(StringService service) {
        this.stringService = service;
    }


    public String manipulate(String input) {
        return stringService.manipulate(input);
    }

    public int wordsInString(String input) {
        return stringService.wordsInString(input);
    }

}
