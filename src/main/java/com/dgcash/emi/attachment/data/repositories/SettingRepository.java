package com.dgcash.emi.attachment.data.repositories;

import com.dgcash.common.core.data.repositories.DgCashRepository;
import com.dgcash.emi.attachment.data.entities.Setting;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SettingRepository extends DgCashRepository<Setting> {
    Optional<Setting> findByType_Code(String fileType);
}
