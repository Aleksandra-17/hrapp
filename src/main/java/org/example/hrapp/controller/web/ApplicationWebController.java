package org.example.hrapp.controller.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.hrapp.model.Application;
import org.example.hrapp.model.ApplicationStatus;
import org.example.hrapp.model.Candidate;
import org.example.hrapp.model.Vacancy;
import org.example.hrapp.service.ApplicationService;
import org.example.hrapp.service.CandidateService;
import org.example.hrapp.service.VacancyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/applications")
@RequiredArgsConstructor
public class ApplicationWebController {

    private final ApplicationService applicationService;
    private final CandidateService candidateService;
    private final VacancyService vacancyService;

    @GetMapping
    public String listApplications(Model model) {
        List<Application> applications = applicationService.getAllApplications();

        Map<String, String> statusStyles = new HashMap<>();
        statusStyles.put("NEW", "bg-info");
        statusStyles.put("REVIEWING", "bg-primary");
        statusStyles.put("INTERVIEW_SCHEDULED", "bg-warning");
        statusStyles.put("INTERVIEWED", "bg-secondary");
        statusStyles.put("OFFER_MADE", "bg-success");
        statusStyles.put("ACCEPTED", "bg-success");
        statusStyles.put("REJECTED", "bg-danger");

        Map<ApplicationStatus, String> statusNames = new HashMap<>();
        statusNames.put(ApplicationStatus.NEW, "Новая");
        statusNames.put(ApplicationStatus.REVIEWING, "На рассмотрении");
        statusNames.put(ApplicationStatus.INTERVIEW_SCHEDULED, "Назначено собеседование");
        statusNames.put(ApplicationStatus.INTERVIEWED, "Прошел собеседование");
        statusNames.put(ApplicationStatus.OFFER_MADE, "Сделано предложение");
        statusNames.put(ApplicationStatus.ACCEPTED, "Принято");
        statusNames.put(ApplicationStatus.REJECTED, "Отклонено");

        model.addAttribute("applications", applications);
        model.addAttribute("statusStyles", statusStyles);
        model.addAttribute("statusNames", statusNames);

        return "application/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("application", new Application());
        model.addAttribute("candidates", candidateService.getAllCandidates());
        model.addAttribute("vacancies", vacancyService.getAllVacancies());
        model.addAttribute("statuses", ApplicationStatus.values());
        return "application/form";
    }

    @PostMapping("/create")
    public String createApplication(@ModelAttribute Application application, RedirectAttributes redirectAttributes) {
        try {
            log.info("Создание новой заявки: {}", application);
            Application created = applicationService.createApplication(application);
            log.info("Заявка успешно создана: {}", created);
            redirectAttributes.addFlashAttribute("success", "Заявка успешно создана");
            return "redirect:/applications";
        } catch (Exception e) {
            log.error("Ошибка при создании заявки", e);
            redirectAttributes.addFlashAttribute("error", "Ошибка при создании заявки: " + e.getMessage());
            return "redirect:/applications/create";
        }
    }
    @ExceptionHandler(Exception.class)
    public String handleError(Exception e, RedirectAttributes redirectAttributes) {
        log.error("Ошибка в контроллере", e);
        redirectAttributes.addFlashAttribute("error", "Произошла ошибка: " + e.getMessage());
        return "redirect:/applications";
    }

    @GetMapping("/{id}")
    public String viewApplication(@PathVariable Long id, Model model) {
        Application application = applicationService.getApplicationById(id);

        Map<String, String> statusNames = new HashMap<>();
        statusNames.put("NEW", "Новая");
        statusNames.put("REVIEWING", "На рассмотрении");
        statusNames.put("INTERVIEW_SCHEDULED", "Назначено собеседование");
        statusNames.put("INTERVIEWED", "Прошел собеседование");
        statusNames.put("OFFER_MADE", "Сделано предложение");
        statusNames.put("ACCEPTED", "Принято");
        statusNames.put("REJECTED", "Отклонено");

        model.addAttribute("id", application.getId());
        model.addAttribute("status", application.getStatus());
        model.addAttribute("date", application.getApplicationDate());
        model.addAttribute("candidate", application.getCandidate());
        model.addAttribute("vacancy", application.getVacancy());
        model.addAttribute("comments", application.getComments());
        model.addAttribute("statusNames", statusNames);

        return "application/view";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Application application = applicationService.getApplicationById(id);

        log.info("=== ОТЛАДКА ДАННЫХ ===");
        log.info("Application ID: " + application.getId());
        log.info("Application Status: " + application.getStatus());
        log.info("Application Comments: " + application.getComments());

        model.addAttribute("appData", application);

        model.addAttribute("candidates", candidateService.getAllCandidates());
        model.addAttribute("vacancies", vacancyService.getAllVacancies());

        Map<ApplicationStatus, String> statusMap = new HashMap<>();
        statusMap.put(ApplicationStatus.NEW, "Новая");
        statusMap.put(ApplicationStatus.REVIEWING, "На рассмотрении");
        statusMap.put(ApplicationStatus.INTERVIEW_SCHEDULED, "Назначено собеседование");
        statusMap.put(ApplicationStatus.INTERVIEWED, "Прошел собеседование");
        statusMap.put(ApplicationStatus.OFFER_MADE, "Сделано предложение");
        statusMap.put(ApplicationStatus.ACCEPTED, "Принято");
        statusMap.put(ApplicationStatus.REJECTED, "Отклонено");

        model.addAttribute("statusMap", statusMap);
        model.addAttribute("allStatuses", ApplicationStatus.values());

        return "application/form";
    }

    @PostMapping("/{id}/edit")
    public String updateApplication(@PathVariable Long id,
                                    @ModelAttribute Application application,
                                    RedirectAttributes redirectAttributes) {
        log.info("Получен запрос на обновление заявки: {}", id);
        log.info("Данные для обновления: {}", application);

        Application updated = applicationService.updateApplication(id, application);
        log.info("Заявка обновлена: {}", updated);

        redirectAttributes.addFlashAttribute("success", "Заявка успешно обновлена");
        return "redirect:/applications";
    }

    @PostMapping("/{id}/delete")
    public String deleteApplication(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        applicationService.deleteApplication(id);
        redirectAttributes.addFlashAttribute("success", "Заявка успешно удалена");
        return "redirect:/applications";
    }

    @PostMapping("/{id}/status")
    public String updateStatus(@PathVariable Long id,
                               @RequestParam ApplicationStatus status,
                               RedirectAttributes redirectAttributes) {
        log.info("Обновление статуса заявки {} на {}", id, status);

        applicationService.updateApplicationStatus(id, status);

        redirectAttributes.addFlashAttribute("success", "Статус заявки успешно обновлен");
        return "redirect:/applications/" + id;
    }

    @GetMapping("/candidate/{candidateId}")
    public String getApplicationsByCandidate(@PathVariable Long candidateId, Model model) {
        model.addAttribute("applications", applicationService.getApplicationsByCandidateId(candidateId));
        model.addAttribute("candidate", candidateService.getCandidateById(candidateId));
        model.addAttribute("statuses", ApplicationStatus.values());
        return "application/list";
    }

    @GetMapping("/vacancy/{vacancyId}")
    public String getApplicationsByVacancy(@PathVariable Long vacancyId, Model model) {
        model.addAttribute("applications", applicationService.getApplicationsByVacancyId(vacancyId));
        model.addAttribute("vacancy", vacancyService.getVacancyById(vacancyId));
        model.addAttribute("statuses", ApplicationStatus.values());
        return "application/list";
    }
}




