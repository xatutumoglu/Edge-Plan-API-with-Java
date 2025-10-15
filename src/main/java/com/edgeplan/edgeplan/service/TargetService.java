package com.edgeplan.edgeplan.service;

import com.edgeplan.edgeplan.dto.TargetCreateRequest;
import com.edgeplan.edgeplan.dto.TargetResponse;
import com.edgeplan.edgeplan.dto.TargetUpdateRequest;

import java.util.List;

public interface TargetService {
    TargetResponse create(TargetCreateRequest req);
    TargetResponse update(Long id, TargetUpdateRequest req);
    void delete(Long id);
    List<TargetResponse> listMine();
    TargetResponse getMine(Long id);
}