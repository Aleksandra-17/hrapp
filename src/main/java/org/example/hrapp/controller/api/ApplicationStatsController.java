package org.example.hrapp.controller.api;

import lombok.RequiredArgsConstructor;
import org.example.hrapp.service.ApplicationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class ApplicationStatsController {

    private final ApplicationService applicationService;

    @GetMapping("/stats")
    public Map<String, Long> getApplicationStats() {
        return applicationService.getApplicationStatistics();
    }
}
