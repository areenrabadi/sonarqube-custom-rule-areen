package com.dgcash.emi.attachment.data.repositories;

import com.dgcash.common.core.data.repositories.DgCashRepository;
import com.dgcash.emi.attachment.data.entities.Attachment;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AttachmentRepository extends DgCashRepository<Attachment> {

    Optional<Attachment> findByTokenAndType_Code(String fileToken, String fileType);
}
