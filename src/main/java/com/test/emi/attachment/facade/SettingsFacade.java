package com.test.emi.attachment.facade;

import com.test.emi.attachment.busniess.exceptions.SettingsNotFoundException;
import com.test.emi.attachment.busniess.service.SettingService;
import com.test.emi.attachment.data.dto.request.SettingRequest;
import com.test.emi.attachment.data.dto.response.SettingResponse;
import com.test.emi.attachment.data.mappers.SettingMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class SettingsFacade {

    private final SettingService settingService;
    private final SettingMapper settingMapper;

    public SettingResponse create(SettingRequest settingRequest){
        return settingMapper.toDto(settingService.create(settingMapper.toEntity(settingRequest)));
    }

    public SettingResponse getById(Long id){
        return settingMapper.toDto(settingService.getById(id).orElseThrow(SettingsNotFoundException::new));
    }

    public List<SettingResponse> getAll(){
        return settingService.getAll()
                .stream()
                .map(settingMapper::toDto)
                .toList();
    }

    public SettingResponse update(Long id, SettingRequest settingRequest){
        return settingService.getById(id)
                .map(s-> settingMapper.partialUpdate(settingRequest, s))
                .map(settingService::update)
                .map(settingMapper::toDto)
                .orElseThrow(SettingsNotFoundException::new);
    }

    public void delete(Long id){
        settingService.delete(id);
    }
}
