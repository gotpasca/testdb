package com.gotpasca.testdb.controller;

import com.gotpasca.testdb.dto.OptionEntryResponse;
import com.gotpasca.testdb.dto.OptionResponse;
import com.gotpasca.testdb.model.OptionEntry;
import com.gotpasca.testdb.model.OptionTable;
import com.gotpasca.testdb.repository.OptionEntryRepository;
import com.gotpasca.testdb.repository.OptionTableRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class OptionController {

    private final OptionTableRepository optionTableRepository;
    private final OptionEntryRepository optionEntryRepository;

    public OptionController(OptionTableRepository optionTableRepository, OptionEntryRepository optionEntryRepository) {
        this.optionTableRepository = optionTableRepository;
        this.optionEntryRepository = optionEntryRepository;
    }

    @GetMapping("/options/search")
    public ResponseEntity<OptionResponse> findByNameAndTags(@RequestParam String name, @RequestParam String tags) {
        OptionTable option = optionTableRepository.findByNameAndTags(name, tags)
                .orElseThrow(() -> new ResourceNotFoundException("Option not found for name=" + name + " tags=" + tags));

        List<OptionEntryResponse> entries = optionEntryRepository.findByOptionTableId(option.getId())
                .stream()
                .map(this::toEntryResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new OptionResponse(option.getId(), option.getName(), option.getTags(), entries));
    }

    @GetMapping("/options/{optionId}/entries")
    public ResponseEntity<List<OptionEntryResponse>> findEntriesByOptionId(@PathVariable UUID optionId) {
        List<OptionEntryResponse> entries = optionEntryRepository.findByOptionTableId(optionId)
                .stream()
                .map(this::toEntryResponse)
                .collect(Collectors.toList());

        if (entries.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(entries);
    }

    private OptionEntryResponse toEntryResponse(OptionEntry entry) {
        return new OptionEntryResponse(entry.getId(), entry.getData());
    }

    @ResponseStatus(org.springframework.http.HttpStatus.NOT_FOUND)
    static class ResourceNotFoundException extends RuntimeException {
        ResourceNotFoundException(String message) {
            super(message);
        }
    }
}
