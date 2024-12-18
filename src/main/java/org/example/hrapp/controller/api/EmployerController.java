package org.example.hrapp.controller.api;

import lombok.RequiredArgsConstructor;
import org.example.hrapp.model.Employer;
import org.example.hrapp.service.EmployerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employers")
@RequiredArgsConstructor
public class EmployerController {

    private final EmployerService employerService;

    @PostMapping
    public ResponseEntity<Employer> createEmployer(@RequestBody Employer employer) {
        return ResponseEntity.ok(employerService.createEmployer(employer));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employer> getEmployer(@PathVariable Long id) {
        return ResponseEntity.ok(employerService.getEmployerById(id));
    }

    @GetMapping
    public ResponseEntity<List<Employer>> getAllEmployers() {
        return ResponseEntity.ok(employerService.getAllEmployers());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employer> updateEmployer(
            @PathVariable Long id,
            @RequestBody Employer employer) {
        return ResponseEntity.ok(employerService.updateEmployer(id, employer));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployer(@PathVariable Long id) {
        employerService.deleteEmployer(id);
        return ResponseEntity.ok().build();
    }
}