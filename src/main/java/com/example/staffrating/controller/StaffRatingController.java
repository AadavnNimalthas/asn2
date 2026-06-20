package com.example.staffrating.controller;

import com.example.staffrating.model.StaffRating;
import com.example.staffrating.service.StaffRatingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class StaffRatingController {

    private final StaffRatingService service;

    @Autowired
    public StaffRatingController(StaffRatingService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("ratings", service.findAll());
        return "index";
    }

    @GetMapping("/ratings/new")
    public String createForm(Model model) {
        model.addAttribute("staffRating", new StaffRating());
        return "create";
    }

    @PostMapping("/ratings")
    public String createRating(@Valid @ModelAttribute("staffRating") StaffRating staffRating, BindingResult result, Model model) {
        if (staffRating.getEmail() != null && service.emailExists(staffRating.getEmail())) {
            result.rejectValue("email", "error.staffRating", "Email already exists.");
        }
        if (result.hasErrors()) {
            return "create";
        }
        service.save(staffRating);
        return "redirect:/";
    }

    @GetMapping("/ratings/{id}")
    public String detail(@PathVariable Long id, Model model) {
        Optional<StaffRating> rating = service.findById(id);
        if (rating.isPresent()) {
            model.addAttribute("rating", rating.get());
            return "detail";
        }
        return "redirect:/";
    }

    @GetMapping("/ratings/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Optional<StaffRating> rating = service.findById(id);
        if (rating.isPresent()) {
            model.addAttribute("staffRating", rating.get());
            return "edit";
        }
        return "redirect:/";
    }

    @PostMapping("/ratings/{id}")
    public String updateRating(@PathVariable Long id, @Valid @ModelAttribute("staffRating") StaffRating staffRating, BindingResult result, Model model) {
        Optional<StaffRating> existingOpt = service.findById(id);
        if (existingOpt.isPresent()) {
            StaffRating existing = existingOpt.get();
            if (staffRating.getEmail() != null && !existing.getEmail().equals(staffRating.getEmail()) && service.emailExists(staffRating.getEmail())) {
                result.rejectValue("email", "error.staffRating", "Email already exists.");
            }
            if (result.hasErrors()) {
                staffRating.setId(id);
                return "edit";
            }
            existing.setName(staffRating.getName());
            existing.setEmail(staffRating.getEmail());
            existing.setRoleType(staffRating.getRoleType());
            existing.setClarity(staffRating.getClarity());
            existing.setNiceness(staffRating.getNiceness());
            existing.setKnowledgeableScore(staffRating.getKnowledgeableScore());
            existing.setComment(staffRating.getComment());
            service.save(existing);
        }
        return "redirect:/ratings/" + id;
    }

    @PostMapping("/ratings/{id}/delete")
    public String deleteRating(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/";
    }
}
