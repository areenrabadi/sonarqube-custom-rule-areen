package com.dgcash.emi.attachment.controller;

import com.dgcash.emi.attachment.data.dto.request.ExtensionRequest;
import com.dgcash.emi.attachment.data.dto.response.ExtensionResponse;
import com.dgcash.emi.attachment.facade.ExtensionFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/extensions")
@Slf4j
public class ExtensionController {

    private final ExtensionFacade extensionFacade;

    @PostMapping
    public ResponseEntity<ExtensionResponse> create(@RequestHeader("Authorization") String token,
                                                    @RequestBody ExtensionRequest extensionRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(extensionFacade.create(extensionRequest));
    }

    @GetMapping
    public ResponseEntity<List<ExtensionResponse>> getAll(@RequestHeader("Authorization") String token) {
        return ResponseEntity.status(HttpStatus.OK).body(extensionFacade.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExtensionResponse> getById(@RequestHeader("Authorization") String token,
                                                     @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(extensionFacade.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExtensionResponse> update(@RequestHeader("Authorization") String token,
                                                    @PathVariable Long id,
                                                    @RequestBody ExtensionRequest extensionRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(extensionFacade.update(id, extensionRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@RequestHeader("Authorization") String token,
                                         @PathVariable Long id) {
        extensionFacade.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
