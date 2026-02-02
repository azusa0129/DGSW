package project.teaming.project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.teaming.project.dto.CreateProjectRequest;
import project.teaming.project.dto.DeleteProjectRequest;
import project.teaming.project.dto.ProjectResponse;
import project.teaming.project.dto.UpdateProject;
import project.teaming.project.service.ProjectService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/project")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")

public class ProjectController {
    private final ProjectService projectService;

    @PostMapping
    public ResponseEntity<?> createProject(@RequestBody CreateProjectRequest request) {
        projectService.createProject(request);
        return ResponseEntity.ok(Map.of("message", "프로젝트가 생성되었습니다."));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteProject(@RequestBody DeleteProjectRequest request) {
        projectService.deleteProject(request);
        return ResponseEntity.ok(Map.of("message", "프로젝트가 삭제되었습니다."));
    }


    @PatchMapping()
    public ResponseEntity<?> updateProject (@RequestBody UpdateProject request) {
        projectService.updateProject(request);
        return ResponseEntity.ok(Map.of("message", "업데이트가 완료되었습니다."));
    }

    @GetMapping
    public List<ProjectResponse> findAll() {
        return projectService.findAll();
    }

}
