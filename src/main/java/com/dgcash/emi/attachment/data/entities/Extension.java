package com.dgcash.emi.attachment.data.entities;

import com.dgcash.common.core.data.entities.AuditableEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "EXTENSIONS")
public class Extension extends AuditableEntityBase {

    @Column
    private String name;

    @Column
    private String code;

    @Column
    private String description;
}
