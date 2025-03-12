package com.test.emi.attachment.controller;

import com.test.emi.attachment.data.dto.request.SettingRequest;
import com.test.emi.attachment.data.dto.response.SettingResponse;
import com.test.emi.attachment.facade.SettingsFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/settings")
@Slf4j
public class SettingsController {

    private final SettingsFacade settingsFacade;

    @PostMapping
    public ResponseEntity<SettingResponse> create(@RequestHeader("Authorization") String token,
                                                  @RequestBody SettingRequest settingRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(settingsFacade.create(settingRequest));
    }

    @GetMapping
    public ResponseEntity<List<SettingResponse>> getAll(@RequestHeader("Authorization") String token) {
        return ResponseEntity.status(HttpStatus.OK).body(settingsFacade.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SettingResponse> getById(@RequestHeader("Authorization") String token,
                                                   @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(settingsFacade.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SettingResponse> update(@RequestHeader("Authorization") String token,
                                                  @PathVariable Long id,
                                                  @RequestBody SettingRequest settingRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(settingsFacade.update(id, settingRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@RequestHeader("Authorization") String token,
                                         @PathVariable Long id) {
        settingsFacade.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
