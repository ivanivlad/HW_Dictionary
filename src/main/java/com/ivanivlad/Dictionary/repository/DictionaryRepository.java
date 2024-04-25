package com.ivanivlad.Dictionary.repository;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.TreeMap;

@Component
public class DictionaryRepository {
    private final Map<String, String> wordsPare = new TreeMap<>();

    public void add(String word, String description) {
        if (wordsPare.containsKey(word)) {
            throw new RuntimeException("Word is already exist!");
        }
        wordsPare.put(word, description);
    }

    public String update(String word, String description) {
        if (!wordsPare.containsKey(word)) {
            throw new RuntimeException("It`s new word. Please, add word before");
        }
        return wordsPare.put(word, description);
    }

    public void delete(String word) {
        if (wordsPare.remove(word) == null) {
            throw new RuntimeException("Word is not exist!");
        }
    }

    @SneakyThrows
    public Map<String, String> getWords() {
        Thread.sleep(3000);
        return wordsPare;
    }
}
