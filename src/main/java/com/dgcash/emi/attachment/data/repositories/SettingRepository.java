package com.dgcash.emi.attachment.data.repositories;

import com.dgcash.common.core.data.repositories.DgCashRepository;
import com.dgcash.emi.attachment.data.entities.Setting;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SettingRepository extends DgCashRepository<Setting> {

    @Query("SELECT s FROM Setting s JOIN s.extensions e WHERE s.type.code = ?1 AND e.code = ?2")
    Optional<Setting> findByFileTypeAndExtension(String fileType, String extension);
}
