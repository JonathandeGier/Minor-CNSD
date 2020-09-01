package nl.jonathandegier.stringmanipulator.services;

import nl.jonathandegier.stringmanipulator.repositories.StringRepository;
import org.springframework.stereotype.Service;

@Service
public class StringService {

    private StringRepository repository;
    private StringManipulationService manipulationService;

    public StringService(StringRepository repository, StringManipulationService manipulationService) {
        this.repository = repository;
        this.manipulationService = manipulationService;
    }

    public String manipulate(String input) {
        return manipulationService.manipulate(input);
    }

    public int wordsInString(String input) {

        if (repository.wordCountExists(input)) {
            return repository.getWordCount(input);
        } else {
            int count = input.split(" ").length;
            repository.storeWordCount(input, count);
            return count;
        }
    }
}
