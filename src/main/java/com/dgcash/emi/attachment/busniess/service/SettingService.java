package com.dgcash.emi.attachment.busniess.service;

import com.dgcash.emi.attachment.busniess.exceptions.SettingsNotFoundException;
import com.dgcash.emi.attachment.data.entities.Setting;
import com.dgcash.emi.attachment.data.repositories.SettingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Service for managing settings entities, particularly for retrieving settings based on file type.
 * This service interacts with the {@link SettingRepository} for database operations.
 *
 * <p>
 * Provides methods for fetching settings related to specific file types.
 * </p>
 *
 * @author Ali Alkhatib
 * @since 2024-08-12
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class SettingService {

    private final SettingRepository settingRepository;

    /**
     * Retrieves settings from the database based on the file type.
     *
     * @param fileType The type of the file whose settings are to be retrieved.
     * @return The {@link Setting} entity matching the given file type.
     * @throws SettingsNotFoundException if no matching settings are found.
     */
    public Setting getSettingsByFileType(String fileType) {
        log.info("Fetching settings for file type: {}", fileType);
        return settingRepository.findByType_Code(fileType).orElseThrow(SettingsNotFoundException::new);
    }
}
