package org.example.hrapp.controller.web;

import lombok.RequiredArgsConstructor;
import org.example.hrapp.model.Vacancy;
import org.example.hrapp.service.VacancyService;
import org.example.hrapp.service.EmployerService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/vacancies")
@RequiredArgsConstructor
public class VacancyWebController {

    private final VacancyService vacancyService;
    private final EmployerService employerService;

    @GetMapping
    public String listVacancies(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFrom,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTo,
            Model model) {
        List<Vacancy> vacancies = vacancyService.getAllVacancies();

        if (status != null && !status.isEmpty()) {
            Boolean isActive = "active".equalsIgnoreCase(status);
            vacancies = vacancies.stream()
                    .filter(vacancy -> vacancy.getActive() != null && vacancy.getActive().equals(isActive))
                    .toList();
        }

        if (dateFrom != null) {
            vacancies = vacancies.stream()
                    .filter(vacancy -> vacancy.getCreatedAt() != null && !vacancy.getCreatedAt().toLocalDate().isBefore(dateFrom))
                    .toList();
        }

        if (dateTo != null) {
            vacancies = vacancies.stream()
                    .filter(vacancy -> vacancy.getCreatedAt() != null && !vacancy.getCreatedAt().toLocalDate().isAfter(dateTo))
                    .toList();
        }

        model.addAttribute("vacancies", vacancies);
        return "vacancy/list";
    }


    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("vacancy", new Vacancy());
        model.addAttribute("employers", employerService.getAllEmployers());
        return "vacancy/form";
    }

    @PostMapping("/create")
    public String createVacancy(@ModelAttribute Vacancy vacancy, RedirectAttributes redirectAttributes) {
        vacancyService.createVacancy(vacancy);
        redirectAttributes.addFlashAttribute("success", "Вакансия успешно создана");
        return "redirect:/vacancies";
    }

    @GetMapping("/{id}")
    public String viewVacancy(@PathVariable Long id, Model model) {
        Vacancy vacancy = vacancyService.getVacancyById(id);
        model.addAttribute("vacancy", vacancy);
        long activeApplications = vacancy.getApplications().stream()
                .filter(app -> !app.getStatus().toString().equals("REJECTED"))
                .count();
        model.addAttribute("activeApplications", activeApplications);
        return "vacancy/view";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("vacancy", vacancyService.getVacancyById(id));
        model.addAttribute("employers", employerService.getAllEmployers());
        return "vacancy/form";
    }

    @PostMapping("/{id}/edit")
    public String updateVacancy(@PathVariable Long id, @ModelAttribute Vacancy vacancy,
                                RedirectAttributes redirectAttributes) {
        System.out.println("Active status: " + vacancy.getActive());
        vacancyService.updateVacancy(id, vacancy);
        redirectAttributes.addFlashAttribute("success", "Вакансия успешно обновлена");
        return "redirect:/vacancies";
    }

    @PostMapping("/{id}/delete")
    public String deleteVacancy(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        vacancyService.deleteVacancy(id);
        redirectAttributes.addFlashAttribute("success", "Вакансия успешно удалена");
        return "redirect:/vacancies";
    }

    @GetMapping("/employer/{employerId}")
    public String getVacanciesByEmployer(@PathVariable Long employerId, Model model) {
        model.addAttribute("vacancies", vacancyService.getVacanciesByEmployerId(employerId));
        model.addAttribute("employer", employerService.getEmployerById(employerId));
        return "vacancy/list";
    }
}