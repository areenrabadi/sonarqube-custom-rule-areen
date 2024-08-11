package com.dgcash.emi.attachment.data.entities;

import com.dgcash.common.core.data.entities.AuditableEntityBase;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "ATTACHMENTS")
public class Attachment extends AuditableEntityBase {

    @Column(name="FILE_NAME", nullable = false)
    private String fileName;

    @Column(name="FILE_PATH", nullable = false)
    private String filePath;

    @Column(name="FILE_SIZE", nullable = false)
    private Long fileSize;

    @Column(nullable = false)
    private String token;

    @OneToOne
    @JoinColumn(nullable = false)
    private Type type;

    @OneToOne
    @JoinColumn(nullable = false)
    private Extension extension;
}
