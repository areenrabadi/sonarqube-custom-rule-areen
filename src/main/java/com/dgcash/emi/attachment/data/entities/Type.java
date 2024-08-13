package com.dgcash.emi.attachment.data.entities;

import com.dgcash.common.core.data.entities.AuditableEntityBase;
import com.dgcash.common.core.data.entities.EntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "TYPES")
public class Type extends AuditableEntityBase {

    @Column
    private String name;

    @Column
    private String code;

    @Column
    private String description;
}
