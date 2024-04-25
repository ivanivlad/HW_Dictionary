package com.ivanivlad.Dictionary.service;

import com.ivanivlad.Dictionary.dto.DictionaryDto;
import com.ivanivlad.Dictionary.repository.DictionaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DictionaryService {

    private final DictionaryRepository dictionaryRepository;

    public void create(DictionaryDto dto) {
        dictionaryRepository.add(dto.getWord(), dto.getDescription());
    }

    public Map<String, String> getAllWords() {
        return dictionaryRepository.getWords();
    }

    public void deleteWord(String word) {
        dictionaryRepository.delete(word);
    }

    public String update(DictionaryDto dto) {
        return dictionaryRepository.update(dto.getWord(), dto.getDescription());
    }

    public Map<String, String> getWords(Integer startElement, Integer pageSize) {
        return dictionaryRepository.getWords().entrySet().stream()
            .skip(startElement)
                .limit(pageSize)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
