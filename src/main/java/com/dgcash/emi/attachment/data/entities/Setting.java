package com.dgcash.emi.attachment.data.entities;

import com.dgcash.common.core.data.entities.AuditableEntityBase;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "SETTINGS")
public class Setting extends AuditableEntityBase {

    @Column(name = "MAX_ALLOWED_SIZE")
    private Long maxAllowedSize;

    @Column(name = "DIRECTORY_PATTERN", nullable = false)
    private String directoryPattern;

    @OneToOne
    @JoinColumn(name = "TYPE_ID", nullable = false, unique = true)
    private Type type;

    @ManyToMany
    @JoinTable(
            name = "SETTING_EXTENSIONS",
            joinColumns = @JoinColumn(name = "SETTING_ID"),
            inverseJoinColumns = @JoinColumn(name = "EXTENSION_ID")
    )
    private List<Extension> extensions;
}
