package com.gotpasca.testdb.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "option_table", schema = "ONBOARDING", uniqueConstraints = @UniqueConstraint(columnNames = {"name", "tags"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OptionTable {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(columnDefinition = "UUID")
    private UUID id;

    @Column(length = 128, nullable = false)
    private String name;

    @Column(length = 128, nullable = false)
    private String tags;

    @OneToMany(mappedBy = "optionTable", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OptionEntry> entries;
}
