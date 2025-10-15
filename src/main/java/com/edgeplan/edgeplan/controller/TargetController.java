package com.edgeplan.edgeplan.controller;

import com.edgeplan.edgeplan.service.TargetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.edgeplan.edgeplan.dto.TargetCreateRequest;
import com.edgeplan.edgeplan.dto.TargetUpdateRequest;
import com.edgeplan.edgeplan.dto.TargetResponse;

import java.util.List;

@RestController
@RequestMapping("/api/targets")
@RequiredArgsConstructor
public class TargetController {

    private final TargetService targetService;

    @GetMapping
    public ResponseEntity<List<TargetResponse>> listMine() {
        return ResponseEntity.ok(targetService.listMine());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TargetResponse> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(targetService.getMine(id));
    }

    @PostMapping
    public ResponseEntity<TargetResponse> create(@Valid @RequestBody TargetCreateRequest req) {
        return ResponseEntity.ok(targetService.create(req));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TargetResponse> update(@PathVariable Long id,
                                                 @Valid @RequestBody TargetUpdateRequest req) {
        return ResponseEntity.ok(targetService.update(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        targetService.delete(id);
        return ResponseEntity.noContent().build();
    }
}