package com.gotpasca.testdb.controller;

import com.gotpasca.testdb.dto.OptionEntryResponse;
import com.gotpasca.testdb.dto.OptionResponse;
import com.gotpasca.testdb.model.OptionEntry;
import com.gotpasca.testdb.model.OptionTable;
import com.gotpasca.testdb.repository.OptionEntryRepository;
import com.gotpasca.testdb.repository.OptionTableRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@Tag(name = "Options", description = "API for querying onboarding option data")
public class OptionController {

    private final OptionTableRepository optionTableRepository;
    private final OptionEntryRepository optionEntryRepository;

    public OptionController(OptionTableRepository optionTableRepository, OptionEntryRepository optionEntryRepository) {
        this.optionTableRepository = optionTableRepository;
        this.optionEntryRepository = optionEntryRepository;
    }

    @Operation(summary = "Find option by name and tags", description = "Returns matching option and its entries by name and tags")
    @GetMapping("/options/search")
    public ResponseEntity<OptionResponse> findByNameAndTags(
            @Parameter(description = "Option name", required = true) @RequestParam String name,
            @Parameter(description = "Option tags", required = true) @RequestParam String tags) {
        OptionTable option = optionTableRepository.findByNameAndTags(name, tags)
                .orElseThrow(() -> new ResourceNotFoundException("Option not found for name=" + name + " tags=" + tags));

        List<OptionEntryResponse> entries = optionEntryRepository.findByOptionTableId(option.getId())
                .stream()
                .map(this::toEntryResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new OptionResponse(option.getId(), option.getName(), option.getTags(), entries));
    }

    @Operation(summary = "List entries by option ID", description = "Returns the entries for a specific option UUID")
    @GetMapping("/options/{optionId}/entries")
    public ResponseEntity<List<OptionEntryResponse>> findEntriesByOptionId(
            @Parameter(description = "Option UUID", required = true) @PathVariable UUID optionId) {
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
