package com.ivanivlad.Dictionary.controller;

import com.ivanivlad.Dictionary.dto.DictionaryDto;
import com.ivanivlad.Dictionary.service.DictionaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/dict")
@RequiredArgsConstructor
public class DictionaryController {
    private final DictionaryService dictionaryService;

    @PostMapping
    @CacheEvict(cacheNames = "getAllWords", allEntries = true)
    public ResponseEntity<?> addWord(@RequestBody DictionaryDto dto) {
        try {
            dictionaryService.create(dto);
            return ResponseEntity.status(CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.status(BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping
    @Cacheable(cacheNames = "getAllWords")
    @CachePut(cacheNames = "getAllWords", condition = "#refresh==true")
    public ResponseEntity<Map<String, String>> getAllWords(
            @RequestParam(required = false) boolean refresh) {
        return ResponseEntity.status(OK).body(dictionaryService.getAllWords());
    }

    @DeleteMapping("/{word}")
    @CacheEvict(cacheNames = "getAllWords", allEntries = true)
    public ResponseEntity<?> deleteWord(@PathVariable String word) {
        try {
            dictionaryService.deleteWord(word);
            return ResponseEntity.status(ACCEPTED).build();
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(e.getMessage());
        }
    }

    @PatchMapping("/{word}")
    @CacheEvict(cacheNames = "getAllWords", allEntries = true)
    public ResponseEntity<?> patchDictionary(@PathVariable String word,
                                             @RequestBody DictionaryDto dto) {
        try {
            dictionaryService.update(dto);
            return ResponseEntity.status(ACCEPTED).build();
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/word")
    @Cacheable(cacheNames = "getAllWords")
    public ResponseEntity<Map<String, String>> getAllWordsWithPagination(
            @RequestParam Integer startElement,
            @RequestParam Integer pageSize) {
        return ResponseEntity.status(OK).body(dictionaryService.getWords(startElement, pageSize));
    }
}