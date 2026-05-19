package com.memorytrainer.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Question is required")
    @Column(length = 2000)
    private String question;

    @NotBlank(message = "Answer is required")
    @Column(length = 2000)
    private String answer;

    private int timesStudied;

    private int timesCorrect;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deck_id", nullable = false)
    private Deck deck;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }

    public String getAnswer() { return answer; }
    public void setAnswer(String answer) { this.answer = answer; }

    public int getTimesStudied() { return timesStudied; }
    public void setTimesStudied(int timesStudied) { this.timesStudied = timesStudied; }

    public int getTimesCorrect() { return timesCorrect; }
    public void setTimesCorrect(int timesCorrect) { this.timesCorrect = timesCorrect; }

    public Deck getDeck() { return deck; }
    public void setDeck(Deck deck) { this.deck = deck; }

    public int getSuccessRate() {
        if (timesStudied == 0) return 0;
        return (int) Math.round((double) timesCorrect / timesStudied * 100);
    }
}
