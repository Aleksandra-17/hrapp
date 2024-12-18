package org.example.hrapp.controller.web;

import lombok.RequiredArgsConstructor;
import org.example.hrapp.model.Application;
import org.example.hrapp.model.ApplicationStatus;
import org.example.hrapp.service.ApplicationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Controller
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardWebController {
    private final ApplicationService applicationService;

    @GetMapping
    public String dashboard(Model model) {
        List<Application> applications = applicationService.getAllApplications();
        Map<String, Long> statistics = applicationService.getApplicationStatistics();

        Map<String, Long> vacancyStats = applications.stream()
                .filter(app -> app.getVacancy() != null)
                .collect(Collectors.groupingBy(
                        app -> app.getVacancy().getPosition(),
                        Collectors.counting()
                ));

        Map<String, Long> dateStats = applications.stream()
                .filter(app -> app.getApplicationDate() != null)
                .collect(Collectors.groupingBy(
                        app -> app.getApplicationDate().format(DateTimeFormatter.ofPattern("dd.MM")),
                        Collectors.counting()
                ));

        model.addAttribute("applications", applications);
        model.addAttribute("statistics", statistics);
        model.addAttribute("vacancyStats", vacancyStats);
        model.addAttribute("dateStats", dateStats);

        return "dashboard/index";
    }
}