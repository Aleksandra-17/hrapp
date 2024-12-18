package org.example.hrapp.controller.web;

import lombok.RequiredArgsConstructor;
import org.example.hrapp.model.Candidate;
import org.example.hrapp.service.CandidateService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/candidates")
@RequiredArgsConstructor
public class CandidateWebController {

    private final CandidateService candidateService;

    @GetMapping
    public String listCandidates(Model model) {
        model.addAttribute("candidates", candidateService.getAllCandidates());
        return "candidate/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("candidate", new Candidate());
        return "candidate/form";
    }

    @PostMapping("/create")
    public String createCandidate(@ModelAttribute Candidate candidate, RedirectAttributes redirectAttributes) {
        candidateService.createCandidate(candidate);
        redirectAttributes.addFlashAttribute("success", "Кандидат успешно создан");
        return "redirect:/candidates";
    }

    @GetMapping("/{id}")
    public String viewCandidate(@PathVariable Long id, Model model) {
        model.addAttribute("candidate", candidateService.getCandidateById(id));
        return "candidate/view";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("candidate", candidateService.getCandidateById(id));
        return "candidate/form";
    }

    @PostMapping("/{id}/edit")
    public String updateCandidate(@PathVariable Long id, @ModelAttribute Candidate candidate,
                                  RedirectAttributes redirectAttributes) {
        candidateService.updateCandidate(id, candidate);
        redirectAttributes.addFlashAttribute("success", "Данные кандидата успешно обновлены");
        return "redirect:/candidates";
    }

    @PostMapping("/{id}/delete")
    public String deleteCandidate(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        candidateService.deleteCandidate(id);
        redirectAttributes.addFlashAttribute("success", "Кандидат успешно удален");
        return "redirect:/candidates";
    }
}