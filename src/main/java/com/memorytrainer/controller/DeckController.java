package com.memorytrainer.controller;

import com.memorytrainer.model.Deck;
import com.memorytrainer.repository.DeckRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/decks")
public class DeckController {

    private final DeckRepository deckRepository;

    public DeckController(DeckRepository deckRepository) {
        this.deckRepository = deckRepository;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("decks", deckRepository.findAll());
        return "decks/list";
    }

    @GetMapping("/new")
    public String newDeck(Model model) {
        model.addAttribute("deck", new Deck());
        return "decks/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute Deck deck, BindingResult result) {
        if (result.hasErrors()) {
            return "decks/form";
        }
        deckRepository.save(deck);
        return "redirect:/decks";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        Deck deck = deckRepository.findById(id).orElseThrow();
        model.addAttribute("deck", deck);
        return "decks/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @Valid @ModelAttribute Deck deck, BindingResult result) {
        if (result.hasErrors()) {
            return "decks/form";
        }
        deck.setId(id);
        Deck existing = deckRepository.findById(id).orElseThrow();
        existing.setName(deck.getName());
        existing.setDescription(deck.getDescription());
        deckRepository.save(existing);
        return "redirect:/decks";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        deckRepository.deleteById(id);
        return "redirect:/decks";
    }
}
