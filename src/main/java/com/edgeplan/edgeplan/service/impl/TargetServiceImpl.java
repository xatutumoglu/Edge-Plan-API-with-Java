package com.edgeplan.edgeplan.service.impl;

import com.edgeplan.edgeplan.dto.TargetCreateRequest;
import com.edgeplan.edgeplan.dto.TargetResponse;
import com.edgeplan.edgeplan.dto.TargetUpdateRequest;
import com.edgeplan.edgeplan.model.Status;
import com.edgeplan.edgeplan.model.Target;
import com.edgeplan.edgeplan.repository.TargetRepository;
import com.edgeplan.edgeplan.service.TargetService;
import com.edgeplan.edgeplan.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TargetServiceImpl implements TargetService {

    private final TargetRepository repo;
    private final UserService userService;

    private TargetResponse toDto(Target t) {
        return new TargetResponse(
                t.getId(),
                t.getTitle(),
                t.getDescription(),
                t.getEndDate(),
                t.getStatus(),
                t.getCreatedBy(),
                t.getChangedBy(),
                t.getCreatedAt(),
                t.getChangedAt()
        );
    }

    @Override
    public TargetResponse create(TargetCreateRequest req) {
        var user = userService.currentUser();
        var email = user.getEmail();

        Target t = Target.builder()
                .title(req.getTitle())
                .description(req.getDescription())
                .endDate(req.getEndDate())
                .status(Status.TODO)
                .createdBy(email)
                .user(user)
                .build();

        return toDto(repo.save(t));
    }

    @Override
    public TargetResponse update(Long id, TargetUpdateRequest req) {
        var user = userService.currentUser();
        var email = user.getEmail();

        Target t = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Target not found"));
        if (!t.getUser().getId().equals(user.getId())) throw new IllegalStateException("Forbidden");

        t.setTitle(req.getTitle());
        t.setDescription(req.getDescription());
        t.setEndDate(req.getEndDate());
        t.setStatus(req.getStatus());
        t.setChangedBy(email);

        return toDto(repo.save(t));
    }

    @Override
    public void delete(Long id) {
        var user = userService.currentUser();
        Target t = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Target not found"));
        if (!t.getUser().getId().equals(user.getId())) throw new IllegalStateException("Forbidden");
        repo.delete(t);
    }

    @Override
    public List<TargetResponse> listMine() {
        var user = userService.currentUser();
        return repo.findByUser(user).stream().map(this::toDto).toList();
    }

    @Override
    public TargetResponse getMine(Long id) {
        var user = userService.currentUser();
        Target t = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Target not found"));
        if (!t.getUser().getId().equals(user.getId())) throw new IllegalStateException("Forbidden");
        return toDto(t);
    }
}