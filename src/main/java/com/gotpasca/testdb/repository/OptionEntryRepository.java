package com.gotpasca.testdb.repository;

import com.gotpasca.testdb.model.OptionEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OptionEntryRepository extends JpaRepository<OptionEntry, UUID> {
    List<OptionEntry> findByOptionTableId(UUID optionId);
}
