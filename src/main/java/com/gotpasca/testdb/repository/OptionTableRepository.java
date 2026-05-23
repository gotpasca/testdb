package com.gotpasca.testdb.repository;

import com.gotpasca.testdb.model.OptionTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OptionTableRepository extends JpaRepository<OptionTable, UUID> {
    Optional<OptionTable> findByNameAndTags(String name, String tags);
}
