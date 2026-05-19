package com.memorytrainer.controller;

import com.memorytrainer.model.Card;
import com.memorytrainer.model.Deck;
import com.memorytrainer.repository.CardRepository;
import com.memorytrainer.repository.DeckRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/decks/{deckId}/cards")
public class CardController {

    private final CardRepository cardRepository;
    private final DeckRepository deckRepository;

    public CardController(CardRepository cardRepository, DeckRepository deckRepository) {
        this.cardRepository = cardRepository;
        this.deckRepository = deckRepository;
    }

    @GetMapping
    public String list(@PathVariable Long deckId, Model model) {
        Deck deck = deckRepository.findById(deckId).orElseThrow();
        model.addAttribute("deck", deck);
        model.addAttribute("cards", cardRepository.findByDeckId(deckId));
        return "cards/list";
    }

    @GetMapping("/new")
    public String newCard(@PathVariable Long deckId, Model model) {
        model.addAttribute("deckId", deckId);
        model.addAttribute("card", new Card());
        return "cards/form";
    }

    @PostMapping
    public String create(@PathVariable Long deckId, @Valid @ModelAttribute Card card, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("deckId", deckId);
            return "cards/form";
        }
        Deck deck = deckRepository.findById(deckId).orElseThrow();
        card.setDeck(deck);
        cardRepository.save(card);
        return "redirect:/decks/" + deckId + "/cards";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long deckId, @PathVariable Long id, Model model) {
        model.addAttribute("deckId", deckId);
        model.addAttribute("card", cardRepository.findById(id).orElseThrow());
        return "cards/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long deckId, @PathVariable Long id,
                         @Valid @ModelAttribute Card card, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("deckId", deckId);
            return "cards/form";
        }
        Card existing = cardRepository.findById(id).orElseThrow();
        existing.setQuestion(card.getQuestion());
        existing.setAnswer(card.getAnswer());
        cardRepository.save(existing);
        return "redirect:/decks/" + deckId + "/cards";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long deckId, @PathVariable Long id) {
        cardRepository.deleteById(id);
        return "redirect:/decks/" + deckId + "/cards";
    }
}
