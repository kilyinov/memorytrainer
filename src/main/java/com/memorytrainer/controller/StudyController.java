package com.memorytrainer.controller;

import com.memorytrainer.model.Card;
import com.memorytrainer.model.Deck;
import com.memorytrainer.repository.DeckRepository;
import com.memorytrainer.service.StudyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/decks/{deckId}/study")
public class StudyController {

    private final StudyService studyService;
    private final DeckRepository deckRepository;

    public StudyController(StudyService studyService, DeckRepository deckRepository) {
        this.studyService = studyService;
        this.deckRepository = deckRepository;
    }

    @GetMapping
    public String study(@PathVariable Long deckId, Model model) {
        Deck deck = deckRepository.findById(deckId).orElseThrow();
        List<Card> cards = studyService.getStudyCards(deckId);
        if (cards.isEmpty()) {
            return "redirect:/decks/" + deckId + "/cards";
        }
        model.addAttribute("deck", deck);
        model.addAttribute("cards", cards);
        return "study/session";
    }

    @PostMapping("/answer")
    @ResponseBody
    public String recordAnswer(@PathVariable Long deckId,
                               @RequestParam Long cardId,
                               @RequestParam boolean correct) {
        studyService.recordAnswer(cardId, correct);
        return "ok";
    }
}
