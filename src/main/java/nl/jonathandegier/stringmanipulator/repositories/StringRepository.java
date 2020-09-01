package nl.jonathandegier.stringmanipulator.repositories;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class StringRepository {

    private Map<String, Integer> wordsInString;

    public StringRepository() {
        this.wordsInString = new HashMap<String, Integer>();
    }

    public boolean wordCountExists(String string) {
        return wordsInString.get(string) != null;
    }

    public int getWordCount(String string) {
        System.out.println("retrieved from cache");
        return wordsInString.get(string);
    }

    public void storeWordCount(String string, int count) {
        System.out.println("stored in cache");
        wordsInString.put(string, count);
    }
}
