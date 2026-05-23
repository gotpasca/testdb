package com.gotpasca.testdb.dto;

import java.util.List;
import java.util.UUID;

public record OptionResponse(UUID id, String name, String tags, List<OptionEntryResponse> entries) {
}
