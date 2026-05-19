package com.memorytrainer.service;

import com.memorytrainer.model.Card;
import com.memorytrainer.repository.CardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
public class StudyService {

    private final CardRepository cardRepository;

    public StudyService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public List<Card> getStudyCards(Long deckId) {
        List<Card> cards = cardRepository.findByDeckIdOrderByTimesStudiedAsc(deckId);
        Collections.shuffle(cards);
        return cards;
    }

    @Transactional
    public void recordAnswer(Long cardId, boolean correct) {
        Card card = cardRepository.findById(cardId).orElseThrow();
        card.setTimesStudied(card.getTimesStudied() + 1);
        if (correct) {
            card.setTimesCorrect(card.getTimesCorrect() + 1);
        }
        cardRepository.save(card);
    }
}
