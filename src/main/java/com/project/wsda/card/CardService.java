package com.project.wsda.card;

import java.util.List;

public interface CardService {

    void saveCard(CardDto cardDto);

    Card findCardById(Integer id);

    List<Card> findAllCards();

    void updateCreditById(Integer id, Integer credit);

    void updateStateById(Integer id, String state);
}
