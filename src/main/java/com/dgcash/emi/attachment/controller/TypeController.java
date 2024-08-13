package com.dgcash.emi.attachment.controller;

import com.dgcash.emi.attachment.data.dto.request.TypeRequest;
import com.dgcash.emi.attachment.data.dto.response.TypeResponse;
import com.dgcash.emi.attachment.facade.TypeFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/types")
@Slf4j
public class TypeController {

    private final TypeFacade typeFacade;

    @PostMapping
    public ResponseEntity<TypeResponse> create(@RequestHeader("Authorization") String token,
                                               @RequestBody TypeRequest typeRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(typeFacade.create(typeRequest));
    }

    @GetMapping
    public ResponseEntity<List<TypeResponse>> getAll(@RequestHeader("Authorization") String token) {
        return ResponseEntity.status(HttpStatus.OK).body(typeFacade.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TypeResponse> getById(@RequestHeader("Authorization") String token,
                                                @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(typeFacade.getById(id));
    }

    @PutMapping
    public ResponseEntity<TypeResponse> update(@RequestHeader("Authorization") String token,
                                               @PathVariable Long id,
                                               @RequestBody TypeRequest typeRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(typeFacade.update(id, typeRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@RequestHeader("Authorization") String token,
                                         @PathVariable Long id) {
        typeFacade.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
