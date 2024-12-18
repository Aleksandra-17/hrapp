package org.example.hrapp.controller.web;

import lombok.RequiredArgsConstructor;
import org.example.hrapp.model.Employer;
import org.example.hrapp.service.EmployerService;
import org.example.hrapp.service.VacancyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/employers")
@RequiredArgsConstructor
public class EmployerWebController {

    private final EmployerService employerService;
    private final VacancyService vacancyService;

    @GetMapping
    public String listEmployers(Model model) {
        model.addAttribute("employers", employerService.getAllEmployers());
        return "employer/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("employer", new Employer());
        return "employer/form";
    }

    @PostMapping("/create")
    public String createEmployer(@ModelAttribute Employer employer, RedirectAttributes redirectAttributes) {
        employerService.createEmployer(employer);
        redirectAttributes.addFlashAttribute("success", "Работодатель успешно добавлен");
        return "redirect:/employers";
    }

    @GetMapping("/{id}")
    public String viewEmployer(@PathVariable Long id, Model model) {
        Employer employer = employerService.getEmployerById(id);
        model.addAttribute("employer", employer);
        model.addAttribute("vacancies", vacancyService.getVacanciesByEmployerId(id));
        return "employer/view";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("employer", employerService.getEmployerById(id));
        return "employer/form";
    }

    @PostMapping("/{id}/edit")
    public String updateEmployer(@PathVariable Long id, @ModelAttribute Employer employer,
                                 RedirectAttributes redirectAttributes) {
        employerService.updateEmployer(id, employer);
        redirectAttributes.addFlashAttribute("success", "Данные работодателя успешно обновлены");
        return "redirect:/employers";
    }

    @PostMapping("/{id}/delete")
    public String deleteEmployer(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        employerService.deleteEmployer(id);
        redirectAttributes.addFlashAttribute("success", "Работодатель успешно удален");
        return "redirect:/employers";
    }
}