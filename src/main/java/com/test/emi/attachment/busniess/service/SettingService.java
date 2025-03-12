package com.test.emi.attachment.busniess.service;

import com.test.emi.attachment.busniess.exceptions.SettingsNotFoundException;
import com.test.emi.attachment.data.entities.Setting;
import com.test.emi.attachment.data.repositories.SettingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class SettingService {

    private final SettingRepository settingRepository;

    public Optional<Setting> getSettingsByFileType(String fileType, String extension) {
        return settingRepository.findByFileTypeAndExtension(fileType, extension);
    }

    public Setting create(Setting setting) {
        return settingRepository.save(setting);
    }

    public Optional<Setting> getById(Long id) {
        return settingRepository.findById(id);
    }

    public List<Setting> getAll() {
        return settingRepository.findAll();
    }

    public Setting update(Setting setting) {
        Setting settingEntity = settingRepository.findById(setting.getId()).orElseThrow(SettingsNotFoundException::new);

        settingEntity.setType(setting.getType());
        settingEntity.setExtensions(setting.getExtensions());
        settingEntity.setDirectoryPattern(setting.getDirectoryPattern());
        settingEntity.setMaxAllowedSize(setting.getMaxAllowedSize());

        return settingEntity;
    }

    public void delete(Long id) {
        Setting setting = settingRepository.findById(id).orElseThrow(SettingsNotFoundException::new);
        setting.setDeletedAt(Timestamp.from(Instant.now()));
        settingRepository.save(setting);
    }
}
